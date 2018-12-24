package me.kraulain.backend.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class PostMessageHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PostMessageHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("post a message {}",
        routingContext.request()
          .absoluteURI());

      JsonObject response = new JsonObject();
      response.put("greeting", "Hello from message handler");

      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_OK)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    }
}
