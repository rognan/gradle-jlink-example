package org.rognan;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PingController implements HttpHandler {
  public void handle(HttpExchange exchange) throws IOException {
    var response = "PONG";
    exchange.sendResponseHeaders(200, response.length());
    try (var stream = exchange.getResponseBody()) {
      stream.write(response.getBytes(StandardCharsets.UTF_8));
    }
  }
}
