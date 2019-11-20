package com.klausner.websocket;

import com.klausner.websocket.controller.ChatEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.websocket.server.ServerContainer;


@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try {
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
            wscontainer.addEndpoint(ChatEndpoint.class);
            server.start();
            server.dump(System.err);
            server.join();
        }
        catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
