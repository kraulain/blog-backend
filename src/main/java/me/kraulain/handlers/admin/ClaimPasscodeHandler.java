package me.kraulain.handlers.admin;

import me.kraulain.dao.MongoDAO;
import me.kraulain.data.Collections;
import me.kraulain.responses.MediaTypes;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

import java.net.HttpURLConnection;

public class ClaimPasscodeHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(ClaimPasscodeHandler.class);
  private MongoDAO mongoDAO;
  private JWTAuth provider;

  public ClaimPasscodeHandler(MongoClient dbClient, JWTAuth provider){
    this.mongoDAO = new MongoDAO(dbClient);
    this.provider = provider;
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("get a "+ Collections.Passcode+" {}",
        routingContext.request()
          .absoluteURI());

      JsonObject passcodeQuery = routingContext.getBodyAsJson();
      Future<JsonObject> future = mongoDAO.retrieveOne(Collections.Passcode, passcodeQuery);

      JsonObject response = new JsonObject();
      routingContext.response()
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

      future.setHandler(result -> {
        if(future.succeeded()){

          JsonObject passcode = future.result();
          passcode.put("status", "claimed"); // claimed, unclaimed
          mongoDAO.delete(Collections.Passcode, passcodeQuery);

          JsonObject claims = new JsonObject()
            .put("phoneNumber", passcode.getString("phoneNumber"))
            .put("role", passcode.getString("role"));

          String token = provider.generateToken(claims, new JWTOptions().setAlgorithm("ES256"));

          response.put("success", "User logged in succesfully");
          routingContext.response().headers().add("Authorization", "Bearer " + token);
          if(passcode.getString("role").equals("client")){
            routingContext.response().headers().add("Location", "https://www.itsparkles.com/dashboard");
          } else {
            routingContext.response().headers().add("Location", "https://admin.itsparkles.com/dashboard");
          }
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_OK);

        }else {
          response.put("error", "Invalid Pass Code");
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE);
        }

        routingContext.response().end(response.encode());
      });
    }
}
