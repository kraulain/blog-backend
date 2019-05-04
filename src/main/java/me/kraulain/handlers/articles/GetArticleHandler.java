package me.kraulain.handlers.articles;

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

public class GetArticleHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetArticleHandler.class);
  private MongoDAO mongoDAO;

  public GetArticleHandler(MongoClient dbclient){
    this.mongoDAO = new MongoDAO(dbclient);
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("get a "+ Collections.Article+" {}",
        routingContext.request()
          .absoluteURI());

      String _id = routingContext.request().getParam("id");
      JsonObject query = new JsonObject().put("_id", _id);

      Future<JsonObject> future = mongoDAO.retrieveOne(Collections.Article, query);

      JsonObject response = new JsonObject();
      routingContext.response()
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

      future.setHandler(result -> {
        if(future.succeeded()){
          response.put("success", Collections.Article + " Retrieved");
          response.put("data", future.result());
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_OK);
        }else {
          response.put("error", Collections.Article + " Not Retrieved");
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_NOT_FOUND);
        }

        routingContext.response().end(response.encode());
      });
    }
}
