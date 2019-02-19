package me.kraulain.backend.handlers.blog.app;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.AppDAO;

public class GetAppsHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetAppsHandler.class);
  private AppDAO appDAO;

  public GetAppsHandler(JDBCClient dbClient) {
    appDAO = new AppDAO(dbClient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info("get all apps {}",
      routingContext.request()
        .absoluteURI());

    appDAO.selectAll(routingContext);


  }
}
