package me.kraulain.backend.dao;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import me.kraulain.backend.entities.App;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AppDAO {

  private JDBCClient dbClient;
  private String SELECT_ALL = "select * from app";
  private String SELECT_BY_ID = "select * from app where id = ?";
  private String INSERT = "insert into app values (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
  private String UPDATE = "update app set name = ?, sub_title = ?, description = ?, image_urls = ?, play_store_url = ?, app_store_url = ?, status = ?, language = ? where id = ?";
  private String DELETE = "delete from app where id = ?";

  public AppDAO(JDBCClient dbClient) {
    this.dbClient = dbClient;
  }

  public Future<List<JsonArray>> selectAll() {
    Future<List<JsonArray>> future = Future.future();

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query(SELECT_ALL, res -> {
          connection.close();
          if (res.succeeded()) {
            if (res.result().equals(null)) {
              future.fail("no apps in db");
            } else {
              future.complete(
                res.result()
                  .getResults());
            }
          }
        });
      } else {
        future.fail("couldn't get apps");
      }
    });

    return future;
  }

  public Future<JsonArray> selectById(int id) {

    Future<JsonArray> future = Future.future();

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(id);
        connection.queryWithParams(SELECT_BY_ID, params, res -> {
          connection.close();
          if (res.succeeded()) {
            if (res.result().equals(null)) {
              future.fail("item not found");
            } else {
              future.complete(
                res.result()
                  .getResults()
                  .stream()
                  .findFirst()
                  .orElseGet(() -> new JsonArray()));
            }
          }
        });
      } else {
        future.fail("couldn't fetch item");
      }
    });

    return future;
  }

  public Future<Boolean> insert(App app) {

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
          .add(app.getLanguage());
        connection.updateWithParams(INSERT, params, res -> {
          connection.close();
          if (res.succeeded()) {
            future.complete(true);
          } else {
            future.fail("couldn't insert item");
          }
        });
      } else {
        future.fail("error performing insert query");
      }
    });

    return future;
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
