import argparse
import json
import os
import re
import shutil
import subprocess
import sys
import threading
import time
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path

PORT = 5010
VERIFYTA_COMMAND = "verifyta"

WORKING_DIR = None
RUN_LOCK = threading.Lock()


class RelayHandler(BaseHTTPRequestHandler):
    def do_OPTIONS(self):
        self.send_response(204)
        self._send_cors_headers()
        self.end_headers()

    def do_POST(self):
        if self.path != "/run":
            self._respond_json(404, {"error": "Unknown endpoint: " + self.path})
            return

        payload = self._read_json_body()
        if payload is None:
            return

        uppaal_xml = payload.get("uppaalXml")
        queries = payload.get("queries")
        epsilon = payload.get("epsilon")
        alpha = payload.get("alpha")

        if not uppaal_xml or not queries:
            self._respond_json(400, {"error": "Request body must include 'uppaalXml' and 'queries'"})
            return

        smc_args = []

        if epsilon is not None:
            try:
                epsilon = float(epsilon)
            except (TypeError, ValueError):
                self._respond_json(400, {"error": "'epsilon' must be a number"})
                return
            if not (0 < epsilon < 0.5):
                self._respond_json(400, {"error": "'epsilon' must be between 0 and 0.5"})
                return
            smc_args += ["-E", str(epsilon)]

        if alpha is not None:
            try:
                alpha = float(alpha)
            except (TypeError, ValueError):
                self._respond_json(400, {"error": "'alpha' must be a number"})
                return
            if not (0 < alpha < 1):
                self._respond_json(400, {"error": "'alpha' must be between 0 and 1"})
                return
            smc_args += ["-a", str(alpha)]

        verifyta_path = shutil.which(VERIFYTA_COMMAND)
        if not verifyta_path:
            self._respond_json(500, {"error": "'verifyta' was not found on PATH."})
            return

        WORKING_DIR.mkdir(parents=True, exist_ok=True)
        model_path = WORKING_DIR / "model.xml"
        queries_path = WORKING_DIR / "queries.q"
        results_path = WORKING_DIR / "results.txt"

        with RUN_LOCK:
            model_path.write_text(uppaal_xml, encoding="utf-8")
            queries_path.write_text(queries, encoding="utf-8")

            start_time = time.perf_counter()
            error_response = None
            completed = None
            elapsed_seconds = None
            results_text = None

            try:
                with results_path.open("w", encoding="utf-8") as results_file:
                    completed = subprocess.run(
                        [verifyta_path, "-s"] + smc_args + [str(model_path), str(queries_path)],
                        stdout=results_file,
                        stderr=subprocess.PIPE,
                        cwd=str(WORKING_DIR),
                        text=True
                    )
                elapsed_seconds = time.perf_counter() - start_time
                results_text = results_path.read_text(encoding="utf-8", errors="replace")
            except Exception as error:
                error_response = {"error": "Failed to run verifyta: " + str(error)}
            finally:
                self._cleanup_generated_files(model_path, queries_path, results_path)

        if error_response is not None:
            self._respond_json(500, error_response)
            return

        self._respond_json(200, {
            "success": completed.returncode == 0,
            "exitCode": completed.returncode,
            "stderr": completed.stderr,
            "resultsText": results_text,
            "elapsedSeconds": elapsed_seconds
        })

    def _cleanup_generated_files(self, *paths):
        for path in paths:
            try:
                path.unlink(missing_ok=True)
            except OSError as error:
                sys.stderr.write("[relay] could not delete {}: {}\n".format(path, error))

    def _read_json_body(self):
        content_length = int(self.headers.get("Content-Length", 0))
        raw_body = self.rfile.read(content_length) if content_length else b""

        try:
            return json.loads(raw_body.decode("utf-8"))
        except (json.JSONDecodeError, UnicodeDecodeError) as error:
            self._respond_json(400, {"error": "Invalid JSON body: " + str(error)})
            return None

    def _send_cors_headers(self):
        self.send_header("Access-Control-Allow-Origin", "*")
        self.send_header("Access-Control-Allow-Methods", "POST, OPTIONS")
        self.send_header("Access-Control-Allow-Headers", "Content-Type")

    def _respond_json(self, status_code, payload):
        body = json.dumps(payload).encode("utf-8")
        self.send_response(status_code)
        self._send_cors_headers()
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, log_format, *args):
        sys.stderr.write("[relay] " + (log_format % args) + "\n")


def main():
    global WORKING_DIR

    parser = argparse.ArgumentParser(
        description="Relay server that writes an UPPAAL model and queries to disk and runs verifyta on them."
    )
    parser.add_argument(
        "working_dir",
        type=str,
        help="Directory to write model.xml, queries.q, and results.txt into"
    )
    parser.add_argument(
        "--port",
        type=int,
        default=PORT,
        help="Port to listen on (default: {})".format(PORT)
    )
    args = parser.parse_args()

    looks_like_windows_path = re.match(r"^[A-Za-z]:[\\/]", args.working_dir) is not None
    if looks_like_windows_path and os.name != "nt":
        print(
            "Warning: '{}' looks like a Windows path, but this Python is not running "
            "natively on Windows (os.name is '{}'), so the drive letter will be treated "
            "as a plain folder name instead of a drive. Run this with Windows Python "
            "(e.g. 'python' from Command Prompt or PowerShell), not python3 from "
            "Git Bash/WSL/MSYS.".format(args.working_dir, os.name)
        )

    WORKING_DIR = Path(args.working_dir).resolve()
    WORKING_DIR.mkdir(parents=True, exist_ok=True)

    server = ThreadingHTTPServer(("localhost", args.port), RelayHandler)
    print("BDD relay server listening on http://localhost:{}".format(args.port))
    print("Working directory (resolved): {}".format(WORKING_DIR))
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("Shutting down.")
        server.shutdown()


if __name__ == "__main__":
    main()