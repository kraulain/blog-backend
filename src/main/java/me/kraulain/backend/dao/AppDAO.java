package me.kraulain.backend.dao;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AppDAO {

  private JDBCClient dbClient;
  private String SELECT_ALL = "select * from app";
  private String SELECT_BY_ID = "select * from app where id = ?";

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
            if(res.result().equals(null)){
              appsReference.set(null);
            }else {
              appsReference.set(res.result().getResults());
            }
          }
        });
      }
    });

    return appsReference.get();
  }

  public JsonArray selectById( int id) {

    final AtomicReference<JsonArray> appsReference = new AtomicReference<>();

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        JsonArray params = new JsonArray();
        params.add(id);
        connection.queryWithParams(SELECT_ALL, params, res -> {
          connection.close();
          if (res.succeeded()) {
            if(res.result().equals(null)){
              appsReference.set(null);
            }else {
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

  //Todo: select one

  //Todo: update

  //Todo: delete
}
