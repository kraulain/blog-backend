package me.kraulain.backend.dao;

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
  private String INSERT = "insert into app values (NULL, ?, ?, ?, ?, ?, ?, ?)";

  public AppDAO(JDBCClient dbClient) {
    this.dbClient = dbClient;
  }

  public List<JsonArray> selectAll() {

    final AtomicReference<List<JsonArray>> appsReference = new AtomicReference<>();

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query(SELECT_ALL, res -> {
          connection.close();
          if (res.succeeded()) {
            if (res.result().equals(null)) {
              appsReference.set(null);
            } else {
              appsReference.set(res.result().getResults());
            }
          }
        });
      }
    });

    return appsReference.get();
  }

  public JsonArray selectById(int id) {

    final AtomicReference<JsonArray> appsReference = new AtomicReference<>();

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(id);
        connection.queryWithParams(SELECT_BY_ID, params, res -> {
          connection.close();
          if (res.succeeded()) {
            if (res.result().equals(null)) {
              appsReference.set(null);
            } else {
              appsReference.set(
                res.result()
                  .getResults()
                  .stream()
                  .findFirst()
                  .orElseGet(() -> new JsonArray()));
            }
          }
        });
      }
    });

    return appsReference.get();
  }

  public Boolean insert(App app) {

    final AtomicReference<Boolean> appsReference = new AtomicReference<>();

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
            appsReference.set(true);
          } else {
            appsReference.set(false);
          }
        });
      }
    });

    return appsReference.get();
  }

  //Todo: update

  //Todo: delete
}
