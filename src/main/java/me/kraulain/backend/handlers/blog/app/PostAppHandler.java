package me.kraulain.backend.handlers.blog.app;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.AppDAO;
import me.kraulain.backend.entities.App;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class PostAppHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PostAppHandler.class);
  private AppDAO appDAO;

  public PostAppHandler(JDBCClient dbClient) {
    appDAO = new AppDAO(dbClient);
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("post an app {}",
        routingContext.request()
          .absoluteURI());

      App app = routingContext.getBodyAsJson().mapTo(App.class);
      appDAO.insert(routingContext, app);
    }
}
