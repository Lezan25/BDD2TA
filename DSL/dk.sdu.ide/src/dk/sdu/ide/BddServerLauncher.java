package dk.sdu.ide;

import java.util.Collection;
import java.util.List;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dk.sdu.bdd.Model;
import dk.sdu.model.AbstractModel;
import dk.sdu.translation.AbstractModelPrinter;
import dk.sdu.translation.ConcreteToAbstract;
import dk.sdu.generator.TranslateAbstractToUppaal;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.CloseReason;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.websocket.jakarta.WebSocketEndpoint;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.xtext.ide.server.LanguageServerImpl;
import org.eclipse.xtext.ide.server.ServerModule;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public class BddServerLauncher {

    // LSP endpoint — handles diagnostics, scoping, validation feedback
    @ServerEndpoint("/bdd")
    public static class BddLspEndpoint extends WebSocketEndpoint<LanguageClient> {

        private final Injector injector;

        public BddLspEndpoint(Injector injector) {
            this.injector = injector;
        }

        @Override
        protected void configure(Launcher.Builder<LanguageClient> builder) {
            LanguageServerImpl server = injector.getInstance(LanguageServerImpl.class);
            builder.setLocalService(server)
                   .setRemoteInterface(LanguageClient.class);
        }

        @Override
        protected void connect(Collection<Object> localServices, LanguageClient remoteProxy) {
            localServices.stream()
                .filter(s -> s instanceof LanguageServerImpl)
                .map(s -> (LanguageServerImpl) s)
                .findFirst()
                .ifPresent(s -> s.connect(remoteProxy));
        }

        @Override
        public void onOpen(Session session, EndpointConfig config) {
            super.onOpen(session, config);
        }

        @Override
        public void onError(Session session, Throwable thr) {
            thr.printStackTrace();
        }

        @Override
        public void onClose(Session session, CloseReason reason) {}
    }

    // Generate endpoint — receives DSL text, returns abstract dump + UPPAAL XML
    @ServerEndpoint("/generate")
    public static class BddGenerateEndpoint {

        private final Injector injector;

        public BddGenerateEndpoint(Injector injector) {
            this.injector = injector;
        }

        @OnMessage
        public String onMessage(String dslText, Session session) {
            try {
                XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
                Resource resource = resourceSet.createResource(URI.createURI("dummy:/generated.bdd"));
                resource.load(new java.io.ByteArrayInputStream(dslText.getBytes()), null);

                IResourceValidator validator = injector.getInstance(IResourceValidator.class);
                var issues = validator.validate(resource, CheckMode.ALL, null);

                List<String> errors = issues.stream()
                    .filter(i -> i.getSeverity() == Severity.ERROR)
                    .map(i -> "Line " + i.getLineNumber() + ": " + i.getMessage())
                    .toList();

                if (!errors.isEmpty())
                    return new Gson().toJson(new GenerateResponse(null, null, errors));

                Model model = (Model) resource.getContents().get(0);
                AbstractModel.Model abstractModel = new ConcreteToAbstract().translateModel(model);
                String abstractDump = new AbstractModelPrinter().print(abstractModel);
                String xml = new TranslateAbstractToUppaal().generate(abstractModel);

                return new Gson().toJson(new GenerateResponse(abstractDump, xml, List.of()));

            } catch (Exception e) {
                return new Gson().toJson(new GenerateResponse(null, null, List.of("Internal error: " + e.getMessage())));
            }
        }

        record GenerateResponse(String abstractSyntax, String uppaalXml, List<String> errors) {}
    }

    public static void main(String[] args) throws Exception {
        new dk.sdu.BddStandaloneSetup().createInjectorAndDoEMFRegistration();
        Injector injector = Guice.createInjector(new ServerModule());

        Server server = new Server(5008);
        ServletContextHandler handler = new ServletContextHandler("/");
        server.setHandler(handler);

        JakartaWebSocketServletContainerInitializer.configure(handler, (servletContext, wsContainer) -> {
            wsContainer.setDefaultMaxSessionIdleTimeout(0);

            wsContainer.addEndpoint(
                ServerEndpointConfig.Builder
                    .create(BddLspEndpoint.class, "/bdd")
                    .configurator(new ServerEndpointConfig.Configurator() {
                        @Override
                        public <T> T getEndpointInstance(Class<T> endpointClass) {
                            return endpointClass.cast(new BddLspEndpoint(injector));
                        }
                    })
                    .build()
            );

            wsContainer.addEndpoint(
                ServerEndpointConfig.Builder
                    .create(BddGenerateEndpoint.class, "/generate")
                    .configurator(new ServerEndpointConfig.Configurator() {
                        @Override
                        public <T> T getEndpointInstance(Class<T> endpointClass) {
                            return endpointClass.cast(new BddGenerateEndpoint(injector));
                        }
                    })
                    .build()
            );
        });

        server.start();
        System.out.println("BDD server started at ws://localhost:5008");
        System.out.println("  LSP endpoint:      ws://localhost:5008/bdd");
        System.out.println("  Generate endpoint: ws://localhost:5008/generate");
        server.join();
    }
}