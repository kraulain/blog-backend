package me.kraulain.backend.handlers.blog.article;

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

public class GetArticleHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(GetArticleHandler.class);

  private ArticleDAO articleDAO;

  public GetArticleHandler(JDBCClient dbClient) {
    articleDAO = new ArticleDAO(dbClient);
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("get article {}",
        routingContext.request()
          .absoluteURI());

      String id = routingContext.request().getParam("id");
      articleDAO.selectById(routingContext, Integer.valueOf(id));
    }
}
