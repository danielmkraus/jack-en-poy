package org.danielmkraus.jackenpoy;


import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.cli.*;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import static io.undertow.Handlers.resource;

@AllArgsConstructor
public class Server {
    public static final String SERVER_PORT_ARGUMENT = "p";
    public static final String SERVER_BIND_ADDRESS_ARGUMENT = "b";
    public static final String SERVER_CONTEXT_PATH_ARGUMENT = "c";
    private static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
    private static final String DEFAULT_CONTEXT_PATH = "/";
    private static final String DEFAULT_SERVER_PORT = "8080";

    private final UndertowJaxrsServer undertowServer = new UndertowJaxrsServer();
    private final Integer port;
    private final String bindAddress;
    private final String contextPath;

    public static void main(String[] args) throws ParseException {
        start(args);
    }

    public static Server start(String[] args) throws ParseException {
        CommandLine cmd = getCommandLineArguments(args);
        Server server = new Server(
                Integer.parseInt(
                        cmd.getOptionValue(SERVER_PORT_ARGUMENT, DEFAULT_SERVER_PORT)),
                cmd.getOptionValue(SERVER_BIND_ADDRESS_ARGUMENT, DEFAULT_BIND_ADDRESS),
                cmd.getOptionValue(SERVER_CONTEXT_PATH_ARGUMENT, DEFAULT_CONTEXT_PATH));
        server.start();
        return server;
    }

    static CommandLine getCommandLineArguments(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(
                Option.builder(SERVER_PORT_ARGUMENT)
                        .argName(SERVER_PORT_ARGUMENT)
                        .numberOfArgs(1)
                        .desc("Server port")
                        .longOpt("port")
                        .type(Integer.class)
                        .build());
        options.addOption(
                Option.builder(SERVER_BIND_ADDRESS_ARGUMENT)
                        .argName(SERVER_BIND_ADDRESS_ARGUMENT)
                        .numberOfArgs(1)
                        .desc("Server bind address")
                        .longOpt("bind")
                        .build());
        options.addOption(
                Option.builder(SERVER_CONTEXT_PATH_ARGUMENT)
                        .argName(SERVER_CONTEXT_PATH_ARGUMENT)
                        .numberOfArgs(1)
                        .desc("Server context path")
                        .longOpt("context-path")
                        .build());
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }


    public void start() {
        Undertow.Builder serverBuilder = Undertow.builder()
                .addHttpListener(port, bindAddress);
        undertowServer.start(serverBuilder);

        undertowServer.addResourcePrefixPath("/webapp/", resource(new ClassPathResourceManager(
                Server.class.getClassLoader(),
                "webapp/"
        )).addWelcomeFiles("index.html"));

        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
        deployment.setApplicationClass(JackEnPoyApplication.class.getName());

        DeploymentInfo di = undertowServer.undertowDeployment(deployment, "/api")
                .setClassLoader(Server.class.getClassLoader())
                .setContextPath(contextPath)
                .setDeploymentName("Jack en poy Application");
        undertowServer.deploy(di);
    }

    public void stop() {
        undertowServer.stop();
    }
}