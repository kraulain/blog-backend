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

public class DeleteArticleHandler implements Handler<RoutingContext> {
  private final static Logger LOGGER = LoggerFactory.getLogger(DeleteArticleHandler.class);
  private ArticleDAO articleDAO;

  public DeleteArticleHandler(JDBCClient dbClient){
    articleDAO = new ArticleDAO(dbClient);
  }

    @Override
    public void handle(RoutingContext routingContext) {
      LOGGER.debug("mark article as as deleted {}",
        routingContext.request()
          .absoluteURI());

      String id = routingContext.request().getParam("id");
      articleDAO.delete(routingContext, Integer.valueOf(id));
    }
}
