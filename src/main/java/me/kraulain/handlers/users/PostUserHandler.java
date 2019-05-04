package me.kraulain.handlers.users;

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

public class PostUserHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PostUserHandler.class);
  private MongoDAO mongoDAO;

  public PostUserHandler(MongoClient dbClient) {
    this.mongoDAO = new MongoDAO(dbClient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("post a " + Collections.User + " {}",
      routingContext.request()
        .absoluteURI());

    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

    JsonObject user = routingContext.getBodyAsJson();
    Future<Void> future = checkDuplicate(user).compose(result -> saveUser(user));


    future.setHandler(result -> {

      if(future.succeeded()){
        response.put("success", "User registered");
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_ACCEPTED);
        routingContext.response().headers().add("Location", "https://www.kraulain.me/login");
      } else {
        response.put("error", future.cause().getMessage());
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE);
      }

      routingContext.response().end(response.encode());
    });

  }

  private Future<Void> checkDuplicate(JsonObject user) {
    Future<Void> future = Future.future();

    JsonObject query = new JsonObject()
      .put("email", user.getString("email"));

    Future<JsonObject> queryFuture = mongoDAO.retrieveOne(Collections.User, query);

    queryFuture.setHandler(result -> {
      if (queryFuture.succeeded()) {
        if (queryFuture.result().isEmpty()) {
          future.complete();
        } else {
          future.fail("User already exists");
        }
      } else {
        future.fail("Invalid query");
      }
    });

    return future;
  }

  private Future<Void> saveUser(JsonObject user){
    Future<Void> future = Future.future();

    Future<JsonObject> saveFuture = mongoDAO.save(Collections.User, user);

    saveFuture.setHandler(result -> {
      if (saveFuture.succeeded()) {
        future.complete();
      } else {
        future.fail("Couldn't register");
      }
    });

    return future;
  }
}
