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
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

import java.net.HttpURLConnection;
import java.util.Random;

public class LoginHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(LoginHandler.class);
  private MongoDAO mongoDAO;

  public LoginHandler(MongoClient dbclient) {
    this.mongoDAO = new MongoDAO(dbclient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("login a " + Collections.Admin + " {}",
      routingContext.request()
        .absoluteURI());

    String phoneNumber = routingContext.request().getParam("phoneNumber");
    JsonObject query = new JsonObject().put("phoneNumber", phoneNumber);

    Future<JsonObject> future = mongoDAO.retrieveOne(Collections.Admin, query);

    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

    future.setHandler(result -> {
      if (future.succeeded() && !future.result().isEmpty()) {
        String passCode = generatePasscode(4);
        JsonObject admin = future.result();

        JsonObject authClaim = new JsonObject()
          .put("phoneNumber", admin.getString("phoneNumber"))
          .put("passCode", passCode)
          .put("status", "unclaimed"); // unclaimed, claimed

        mongoDAO.save(Collections.PassCode, authClaim);

        JsonObject notification = new JsonObject()
          .put("type", "sms")
          .put("title", "Login")
          .put("status", "new") // new, sent, failed, archived
          .put("phoneNumber", admin.getString("phoneNumber"))
          .put("body", "Dear " + admin.getString("name") + ",\nYour kraulain.me Login Pass Code is: " + passCode);

        mongoDAO.save(Collections.Notification, notification);

        response.put("success", "Awaiting pass code validation");
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_MOVED_TEMP);
        routingContext.response().headers().add("Location", "https://admin.kraulain.me/passCode");

      } else {
        response.put("error", Collections.Admin + " Not Found");
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_NOT_FOUND);
      }

      routingContext.response().end(response.encode());
    });
  }

  private String generatePasscode(int length) {
    String passcode = "";
    Random random = new Random();

    for (int i = 0; i < length ; i++){
      passcode += (char)(random.nextInt(26) + 'a');
    }

    return passcode.toUpperCase();
  }
}
