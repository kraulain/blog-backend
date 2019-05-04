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

public class GetMessageHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetMessageHandler.class);
  private MongoDAO mongoDAO;

  public GetMessageHandler(MongoClient dbclient){
    this.mongoDAO = new MongoDAO(dbclient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("get a "+ Collections.Message +" {}",
      routingContext.request()
        .absoluteURI());

    String _id = routingContext.request().getParam("id");
    JsonObject query = new JsonObject().put("_id", _id);

    Future<JsonObject> future = mongoDAO.retrieveOne(Collections.Message, query);

    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

    future.setHandler(result -> {
      if(future.succeeded()){
        response.put("success", Collections.Message + " Retrieved");
        response.put("data", future.result());
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_OK);
      }else {
        response.put("error", Collections.Message + " Not Retrieved");
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_NOT_FOUND);
      }

      routingContext.response().end(response.encode());
    });
  }
}
