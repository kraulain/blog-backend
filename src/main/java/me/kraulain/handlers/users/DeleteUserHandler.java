package me.kraulain.handlers.users;

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

public class DeleteUserHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(DeleteUserHandler.class);
  private MongoDAO mongoDAO;

  public DeleteUserHandler(MongoClient dbclient) {
    this.mongoDAO = new MongoDAO(dbclient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("mark a " + Collections.User + " as deleted {}",
      routingContext.request()
        .absoluteURI());

    String _id = routingContext.request().getParam("id");
    JsonObject query = new JsonObject().put("_id", _id);
    JsonObject replaceJson = new JsonObject();
    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);


    Future<JsonObject> replaceFuture = mongoDAO.retrieveOne(Collections.User, query);
    replaceFuture.setHandler(replaceResult -> {
      replaceJson.mergeIn(replaceFuture.result());
      replaceJson.put("status", "inactive");

      Future<JsonObject> future = mongoDAO.update(Collections.User, query, replaceJson);

      future.setHandler(result -> {
        if (future.succeeded()) {
          response.put("success", Collections.User + " Deleted");
          response.put("data", future.result());
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_NO_CONTENT);
        } else {
          response.put("error", Collections.User + " Not Deleted");
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_NOT_FOUND);
        }

        routingContext.response().end(response.encode());
      });
    });
  }
}
