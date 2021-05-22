package org.rognan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
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

        final String startupMessage = "Application running on port %d powered by %s %s"
            .formatted(port,
                System.getProperty("java.vendor"),
                System.getProperty("java.version"));

        LOG.info(startupMessage);
    }

    private static void handler(HttpExchange exchange) throws IOException {
        var response = "PONG";
        exchange.sendResponseHeaders(200, response.length());
        try (var stream = exchange.getResponseBody()) {
            stream.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
