package me.kraulain.backend.handlers.blog.reply;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class DeleteReplyHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(DeleteReplyHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("mark reply as as deleted {}",
        routingContext.request()
          .absoluteURI());

      JsonObject response = new JsonObject();
      response.put("greeting", "Hello from reply delete handler");

      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_OK)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    }
}
