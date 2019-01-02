package me.kraulain.backend.handlers.notification.sms;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class DeleteSmsHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(DeleteSmsHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("mark issue as as deleted {}",
        routingContext.request()
          .absoluteURI());

      JsonObject response = new JsonObject();
      response.put("greeting", "Hello from issue delete handler");

      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_OK)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    }
}
