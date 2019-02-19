package me.kraulain.backend.handlers.blog.app;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.AppDAO;
import me.kraulain.backend.entities.App;

public class PutAppHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PutAppHandler.class);
  private AppDAO appDAO;

  public PutAppHandler(JDBCClient dbClient) {
    appDAO = new AppDAO(dbClient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.debug("update an app {}",
      routingContext.request()
        .absoluteURI());

    String id = routingContext.request().getParam("id");
    App app = routingContext.getBodyAsJson().mapTo(App.class);
    appDAO.update(routingContext, app, Integer.valueOf(id));

  }
}
