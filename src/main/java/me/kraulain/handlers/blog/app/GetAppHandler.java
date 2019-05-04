package me.kraulain.backend.handlers.blog.app;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.AppDAO;

public class GetAppHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetAppHandler.class);

  private AppDAO appDAO;

  public GetAppHandler(JDBCClient dbClient) {
    appDAO = new AppDAO(dbClient);
  }

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info("get app {}",
      routingContext.request()
        .absoluteURI());

    String id = routingContext.request().getParam("id");
    appDAO.selectById(routingContext, Integer.valueOf(id));

  }
}
