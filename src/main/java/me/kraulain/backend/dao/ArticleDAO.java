package me.kraulain.backend.dao;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;
import me.kraulain.backend.entities.App;
import me.kraulain.backend.responses.MediaTypes;

import java.net.HttpURLConnection;
import java.util.List;

public class ArticleDAO {

  private JDBCClient dbClient;
  private String SELECT_ALL = "SELECT * FROM app";
  private String SELECT_BY_ID = "SELECT * FROM app WHERE id = ?";
  private String INSERT = "INSERT INTO app VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
  private String UPDATE = "UPDATE app SET name = ?, sub_title = ?, description = ?, image_urls = ?, play_store_url = ?, app_store_url = ?, status = ?, language = ? WHERE id = ?";
  private String DELETE = "DELETE FROM app WHERE id = ?";

  public ArticleDAO(JDBCClient dbClient) {
    this.dbClient = dbClient;
  }

  public void selectAll(RoutingContext routingContext) {

    JsonObject response = new JsonObject();
    response.put("title", "All apps");

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query(SELECT_ALL, res -> {
          connection.close();
          if (res.succeeded()) {
            List<JsonArray> apps = res.result().getResults();
            response.put("message", "Successfully got all apps");
            response.put("pageIndex", 0);
            response.put("apps", apps);
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_OK)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          } else {
            response.put("message", "Failed to get all apps");
            response.put("error", res.cause());
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_SERVER_ERROR)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end(response.encode());
          }
        });
      } else {
        response.put("message", "Failed to get all apps");
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
            List<JsonArray> apps = res.result()
              .getResults();
            JsonObject response = new JsonObject();
            response.put("title", "App");
            response.put("app", apps);
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

  public void insert(RoutingContext routingContext, App app) {
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(app.getName())
          .add(app.getSubTitle())
          .add(app.getDescription())
          .add(app.getImageUrls())
          .add(app.getPlayStoreUrl())
          .add(app.getAppStoreUrl())
          .add(app.getStatus())
          .add(app.getLanguage());
        connection.updateWithParams(INSERT, params, res -> {
          connection.close();
          if (res.succeeded()) {
            JsonObject response = new JsonObject();
            response.put("title", "Create app");
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

  public void update(RoutingContext routingContext, App app, int id) {
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(app.getName())
          .add(app.getSubTitle())
          .add(app.getDescription())
          .add(app.getImageUrls())
          .add(app.getPlayStoreUrl())
          .add(app.getAppStoreUrl())
          .add(app.getStatus())
          .add(app.getLanguage())
          .add(id);
        connection.updateWithParams(UPDATE, params, res -> {
          connection.close();
          if (res.succeeded()) {
            JsonObject response = new JsonObject();
            response.put("title", "Update app");
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
