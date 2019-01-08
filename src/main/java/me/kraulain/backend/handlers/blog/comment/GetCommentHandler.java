package me.kraulain.backend.handlers.blog.comment;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class GetCommentHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetCommentHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("get comment {}",
        routingContext.request()
          .absoluteURI());

      JsonObject response = new JsonObject();
      response.put("greeting", "Hello from  get single comment handler");

      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_OK)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    }
}