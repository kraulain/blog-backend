package me.kraulain.handlers.articles;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.ArticleDAO;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class GetArticlesHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetArticlesHandler.class);
  private ArticleDAO articleDAO;

  public GetArticlesHandler(JDBCClient dbClient){
    articleDAO = new ArticleDAO(dbClient);
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("get all articles {}",
        routingContext.request()
          .absoluteURI());

      articleDAO.selectAll(routingContext);
    }
}
