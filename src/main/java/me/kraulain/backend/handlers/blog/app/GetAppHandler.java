package me.kraulain.backend.handlers.blog.app;

import io.vertx.core.Future;
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

      String id = routingContext.request().getParam("id");
      appDAO.selectById(routingContext, Integer.valueOf(id));

    }
}
