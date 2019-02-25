package me.kraulain.backend.dao;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.entities.App;
import me.kraulain.backend.entities.Article;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;
import java.util.List;

public class ArticleDAO {

  private JDBCClient dbClient;
  private String SELECT_ALL = "SELECT * FROM article";
  private String SELECT_BY_ID = "SELECT * FROM article WHERE id = ?";
  private String INSERT = "INSERT INTO article VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
  private String UPDATE = "UPDATE app SET title = ?, sub_title = ?, image_url = ?, body = ?, published_date = ?, status = ?, language = ? WHERE id = ?";
  private String DELETE = "DELETE FROM app WHERE id = ?";

  public ArticleDAO(JDBCClient dbClient) {
    this.dbClient = dbClient;
  }

  public void selectAll(RoutingContext routingContext) {

    JsonObject response = new JsonObject();
    response.put("title", "All articles");

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query(SELECT_ALL, res -> {
          connection.close();
          if (res.succeeded()) {
            List<JsonArray> articles = res.result().getResults();
            response.put("message", "Successfully got all articles");
            response.put("pageIndex", 0);
            response.put("articles", articles);
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_OK)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          } else {
            response.put("message", "Failed to get all articles");
            response.put("error", res.cause());
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_SERVER_ERROR)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          }
        });
      } else {
        response.put("message", "Failed to get all articles");
        response.put("error", ar.cause());
        routingContext.response()
          .setStatusCode(HttpURLConnection.HTTP_SERVER_ERROR)
          .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
          .end(response.encode());
      }
    });
  }

  public void selectById(RoutingContext routingContext, int id) {
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(id);
        connection.queryWithParams(SELECT_BY_ID, params, res -> {
          connection.close();
          if (res.succeeded()) {
            List<JsonArray> article = res.result()
              .getResults();
            JsonObject response = new JsonObject();
            response.put("title", "Article");
            response.put("article", article);
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_OK)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          } else {
            routingContext.fail(res.cause());
          }
        });
      } else {
        routingContext.fail(ar.cause());
      }
    });
  }

  public void insert(RoutingContext routingContext, Article article) {
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(article.getTitle())
          .add(article.getSubTitle())
          .add(article.getImageUrl())
          .add(article.getBody())
          .add(article.getPublishedDate())
          .add(article.getStatus())
          .add(article.getLanguage());
        connection.updateWithParams(INSERT, params, res -> {
          connection.close();
          if (res.succeeded()) {
            JsonObject response = new JsonObject();
            response.put("title", "Create article");
            response.put("succeeded", res.result().toJson());
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_CREATED)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          } else {
            routingContext.fail(res.cause());
          }
        });
      } else {
        routingContext.fail(ar.cause());
      }
    });
  }

  public void update(RoutingContext routingContext, Article article, int id) {
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(article.getTitle())
          .add(article.getSubTitle())
          .add(article.getImageUrl())
          .add(article.getBody())
          .add(article.getPublishedDate())
          .add(article.getStatus())
          .add(article.getLanguage())
          .add(id);
        connection.updateWithParams(UPDATE, params, res -> {
          connection.close();
          if (res.succeeded()) {
            JsonObject response = new JsonObject();
            response.put("title", "Update Article");
            response.put("succeeded", res.result().toJson());
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_ACCEPTED)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          } else {
            routingContext.fail(res.cause());
          }
        });
      } else {
        routingContext.fail(ar.cause());
      }
    });
  }

  public void delete(RoutingContext routingContext, int id) {
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray().add(id);
        connection.updateWithParams(DELETE, params, res -> {
          connection.close();
          if (res.succeeded()) {
            JsonObject response = new JsonObject();
            response.put("title", "Delete app");
            response.put("data", res.result().toJson());
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_ACCEPTED)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          } else {
            routingContext.fail(res.cause());
          }
        });
      } else {
        routingContext.fail(ar.cause());
      }
    });
  }
}
