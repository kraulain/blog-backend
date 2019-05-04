package me.kraulain.handlers.articles;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.dao.ArticleDAO;
import me.kraulain.backend.entities.Article;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;

public class PostArticleHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(PostArticleHandler.class);
  private ArticleDAO articleDAO;

  public PostArticleHandler(JDBCClient dbClient) {
    articleDAO = new ArticleDAO(dbClient);
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("post an article {}",
        routingContext.request()
          .absoluteURI());

      Article article = routingContext.getBodyAsJson().mapTo(Article.class);
      articleDAO.insert(routingContext, article);
    }
}
