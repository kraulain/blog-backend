package me.kraulain.handlers.notifications;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.dao.MongoDAO;
import me.kraulain.data.Collections;
import me.kraulain.responses.MediaTypes;

import java.net.HttpURLConnection;

public class PostNotificationHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PostNotificationHandler.class);
  private MongoDAO mongoDAO;

  public PostNotificationHandler(MongoClient dbClient) {
    this.mongoDAO = new MongoDAO(dbClient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("post a " + Collections.Notification + " {}",
      routingContext.request()
        .absoluteURI());

    JsonObject article = routingContext.getBodyAsJson();
    Future<JsonObject> future = mongoDAO.save(Collections.Notification, article);

    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

    future.setHandler(result -> {

      if (future.succeeded()) {
        response.put("success", Collections.Notification + " Saved");
        response.put("data", future.result());
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_CREATED);
      } else {
        response.put("error", Collections.Notification + " Not Saved");
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST);
      }

      routingContext.response().end(response.encode());

    });

  }
}
