package me.kraulain.backend.handlers.blog.app;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.AppDAO;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;
import java.util.List;

public class GetAppsHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetAppsHandler.class);
  private AppDAO appDAO;

  public GetAppsHandler(JDBCClient dbClient) {
    appDAO = new AppDAO(dbClient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("get all apps {}",
      routingContext.request()
        .absoluteURI());

    List<JsonArray> apps = appDAO.selectAll();

    JsonObject response = new JsonObject();

    if (!apps.equals(null)) {
      response.put("title", "Wiki home");
      response.put("apps", apps);
      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_OK)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    } else {
      response.put("error", "Something went wrong");
      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    }


  }
}
