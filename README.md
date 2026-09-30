# BDD Studio

A tool for specifying autonomous Cyber-Physical Systems using a BDD-based domain-specific language, translating the specifications into UPPAAL Timed Automata, and verifying them using UPPAAL.

## Repository structure
```
.
├── DSL/
├── eclipse-server.jar
├── verification-server.py
├── bdd-studio.html
└── LICENSE
```
- **DSL/**: Eclipse/Xtext project containing the implementation of the DSL and language server.
- **eclipse-server.jar**: Executable language server providing parsing, validation, and translation to UPPAAL.
- **verification-server.py**: Local server that invokes UPPAAL's verifyta verifier.
- **bdd-studio.html**: Browser-based interface for writing BDD specifications, generating UPPAAL models, and viewing verification results.
- **LICENSE**: MIT License.
The language server and verification server run locally, while the user interacts with the tool through the browser.

## Usage

1. Start the language server (requires Java on the system path):

```bash
   java -jar eclipse-server.jar
```

2. Start the verification server, passing the absolute path of the directory where the model was saved from the UI:

```bash
   python verification-server.py <absolute-path-to-saved-model>
```

3. Open `bdd-studio.html` in a browser.

## License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.