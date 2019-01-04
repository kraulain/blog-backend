package me.kraulain.backend.handlers.notification.push;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class PostPushHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PostPushHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("post an push {}",
        routingContext.request()
          .absoluteURI());

      JsonObject response = new JsonObject();
      response.put("greeting", "Hello from post push handler");

      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_OK)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    }
}