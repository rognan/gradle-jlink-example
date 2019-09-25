package org.rognan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;

import static com.sun.net.httpserver.HttpServer.create;

public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws IOException {
        var port = 8080;

        var server = create(new InetSocketAddress(port), 0)
            .createContext("/", Application::handler)
            .getServer();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Application shutting down");
            server.stop(0);
        }));

        server.start();

        LOG.info("Application running on port " + port +
            " powered by Java " + System.getProperty("java.version")
        );
    }

    private static void handler(HttpExchange exchange) throws IOException {
        var response = "PONG";
        exchange.sendResponseHeaders(200, response.length());
        try (var stream = exchange.getResponseBody()) {
            stream.write(response.getBytes());
        }
    }
}
