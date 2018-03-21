package org.rognan;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Application {
    public static void main(String[] args) throws Exception {
        int port = 8080;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", exchange -> {
            String response = "PONG";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        });

        server.setExecutor(null);
        server.start();

        System.out.println("Minimal webapp running on port " + port +
                " powered by Java " + System.getProperty("java.version"));

    }
}
