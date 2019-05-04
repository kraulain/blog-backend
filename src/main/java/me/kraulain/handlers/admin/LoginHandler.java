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
    LOGGER.debug("login a " + Collections.User + " {}",
      routingContext.request()
        .absoluteURI());

    JsonObject query = routingContext.getBodyAsJson();
    Future<JsonObject> future = mongoDAO.retrieveOne(Collections.User, query);

    JsonObject response = new JsonObject();
    routingContext.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON);

    future.setHandler(result -> {
      if (future.succeeded()) {
        String passcode = generatePasscode(4);
        JsonObject user = future.result();

        JsonObject authClaim = new JsonObject()
          .put("phoneNumber", user.getString("phoneNumber"))
          .put("passcode", passcode)
          .put("role", user.getString("role")) //staff, client, admin
          .put("status", "unclaimed"); // unclaimed, claimed

        mongoDAO.save(Collections.Passcode, authClaim);

        JsonObject smsRequest = new JsonObject()
          .put("type", "sms")
          .put("title", "Login")
          .put("status", "new")
          .put("phoneNumber", user.getString("phoneNumber")); // new, sent, failed, archived

        if(user.getString("language").equals("en")){
          smsRequest.put("body", "Dear " + user.getString("name") + ", Your ITSparkles Login Pass Code is: " + passcode);
        } else if(user.getString("language").equals("fr")) {
          smsRequest.put("body", "Cher " + user.getString("name") + ", Votre Code D'access ITSparkles et le: " + passcode);
        } else {
          response.put("error", "Unsupported language");
          routingContext.response().setStatusCode(HttpURLConnection.HTTP_UNSUPPORTED_TYPE);
        }

        mongoDAO.save(Collections.Notification, smsRequest);

        response.put("success", "Awaiting pass code validation");
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_MOVED_TEMP);
        routingContext.response().headers().add("Location", "https://www.itsparkles.com/passcode");

      } else {
        response.put("error", Collections.User + " Not Found");
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
