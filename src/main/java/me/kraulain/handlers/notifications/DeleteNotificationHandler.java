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

public class DeleteNotificationHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(DeleteNotificationHandler.class);
  private MongoDAO mongoDAO;

  public DeleteNotificationHandler(MongoClient dbclient) {
    this.mongoDAO = new MongoDAO(dbclient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("mark a " + Collections.Notification + " as deleted {}",
      routingContext.request()
        .absoluteURI());

    String _id = routingContext.request().getParam("id");
    JsonObject query = new JsonObject().put("_id", _id);

    JsonObject replaceJson = new JsonObject();

    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);


    Future<JsonObject> replaceFuture = mongoDAO.retrieveOne(Collections.Notification, query);
    replaceFuture.setHandler(replaceResult -> {
      replaceJson.mergeIn(replaceFuture.result());
      replaceJson.put("status", "archived"); //published, unpublished, archived

      Future<JsonObject> future = mongoDAO.update(Collections.Notification, query, replaceJson);

      future.setHandler(result -> {
        if (future.succeeded()) {
          response.put("success", Collections.Notification + " Deleted");
          response.put("data", future.result());
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_NO_CONTENT);
        } else {
          response.put("error", Collections.Notification + " Not Deleted");
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_NOT_FOUND);
        }

        routingContext.response().end(response.encode());
      });
    });
  }
}
