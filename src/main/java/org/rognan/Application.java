package org.rognan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import static com.sun.net.httpserver.HttpServer.create;
import static java.lang.System.getProperty;

public class Application {
  private static final Logger LOG = Logger.getLogger(Application.class.getName());

  public static void main(String[] args) throws IOException {
    var port = 8080;

    var server = create(new InetSocketAddress(port), 0)
      .createContext("/", new PingController())
      .getServer();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      LOG.info("Application shutting down");
      server.stop(0);
    }));

    server.start();

    LOG.info("Application listening on port %d powered by %s %s".formatted(
      port, getProperty("java.vendor"), getProperty("java.version")
    ));
  }
}
