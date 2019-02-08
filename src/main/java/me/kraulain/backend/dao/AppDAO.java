package me.kraulain.backend.dao;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import me.kraulain.backend.entities.App;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AppDAO {

  private JDBCClient dbClient;
  private String SELECT_ALL_APPS = "select * from app";

  public AppDAO(JDBCClient dbClient) {
    this.dbClient = dbClient;
  }

  public List<JsonArray> selectAll() {

    final AtomicReference<List<JsonArray>> appsReference = new AtomicReference<>();

    dbClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query(SELECT_ALL_APPS, res -> {
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

  //Todo: select all or search

  //Todo: select one

  //Todo: update

  //Todo: delete
}
