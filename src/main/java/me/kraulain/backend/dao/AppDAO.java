package me.kraulain.backend.dao;

import io.vertx.core.Future;
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

public class AppDAO {

  private JDBCClient dbClient;
  private String SELECT_ALL = "SELECT * FROM app";
  private String SELECT_BY_ID = "SELECT * FROM app WHERE id = ?";
  private String INSERT = "insert into app values (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
  private String UPDATE = "update app set name = ?, sub_title = ?, description = ?, image_urls = ?, play_store_url = ?, app_store_url = ?, status = ?, language = ? where id = ?";
  private String DELETE = "delete from app where id = ?";

  public AppDAO(JDBCClient dbClient) {
    this.dbClient = dbClient;
  }

  public void selectAll(RoutingContext routingContext) {
    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query(SELECT_ALL, res -> {
          connection.close();
          if (res.succeeded()) {
            List<JsonArray> apps = res.result().getResults();
            JsonObject response = new JsonObject();
            response.put("title", "All apps");
            response.put("apps", apps);
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
            routingContext.put("succeeded", res.result().toJson());
            routingContext.response()
              .setStatusCode(HttpURLConnection.HTTP_CREATED)
              .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
              .end();
          } else {
            routingContext.fail(res.cause());
          }
        });
      } else {
        routingContext.fail(ar.cause());
      }
    });
  }

  public Future<Boolean> update(App app) {

    Future<Boolean> future = Future.future();

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
          .add(app.getId());
        connection.updateWithParams(UPDATE, params, res -> {
          connection.close();
          if (res.succeeded()) {
            future.complete(true);
          } else {
            future.fail("could't update object");
          }
        });
      } else {
        future.fail("error while performing update query object");
      }
    });

    return future;
  }

  public Future<Boolean> delete(int id) {

    Future<Boolean> future = Future.future();

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray().add(id);
        connection.updateWithParams(DELETE, params, res -> {
          connection.close();
          if (res.succeeded()) {
            future.complete(true);
          } else {
            future.fail("couldn't delete object");
          }
        });
      } else {
        future.fail("erro while performing delete query");
      }
    });

    return future;
  }
}
