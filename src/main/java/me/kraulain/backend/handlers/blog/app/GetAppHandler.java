package me.kraulain.backend.handlers.blog.app;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.AppDAO;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class GetAppHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetAppHandler.class);

  private AppDAO appDAO;

  public GetAppHandler(JDBCClient dbClient) {
    appDAO = new AppDAO(dbClient);
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("get app {}",
        routingContext.request()
          .absoluteURI());

      JsonObject response = new JsonObject();
      response.put("greeting", "Hello from  get single app handler");

      routingContext.response()
        .setStatusCode(HttpURLConnection.HTTP_OK)
        .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
        .end(response.encode());
    }
}
