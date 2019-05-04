package me.kraulain.handlers.messages;

import me.kraulain.dao.MongoDAO;
import me.kraulain.data.Collections;
import me.kraulain.responses.MediaTypes;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

import java.net.HttpURLConnection;

public class PutMessageHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PutMessageHandler.class);
  private MongoDAO mongoDAO;

  public PutMessageHandler(MongoClient dbclient) {
    this.mongoDAO = new MongoDAO(dbclient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("put a " + Collections.Article + " {}",
      routingContext.request()
        .absoluteURI());

    String _id = routingContext.request().getParam("id");
    JsonObject query = new JsonObject().put("_id", _id);
    JsonObject replaceJson = routingContext.getBodyAsJson();

    Future<JsonObject> future = mongoDAO.update(Collections.Message, query, replaceJson);

    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

    future.setHandler(result -> {
      if (future.succeeded()) {
        response.put("success", Collections.Message + " Updated");
        response.put("data", future.result());
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_ACCEPTED);
      } else {
        response.put("error", Collections.Message + " Not Updated");
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST);
      }

      routingContext.response().end(response.encode());
    });
  }
}
