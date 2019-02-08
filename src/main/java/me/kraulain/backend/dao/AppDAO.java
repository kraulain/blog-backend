package me.kraulain.backend.dao;

import io.vertx.ext.jdbc.JDBCClient;

public class AppDAO {
  private JDBCClient dbClient;

  public AppDAO(JDBCClient dbClient){
    this.dbClient = dbClient;
  }

  //Todo: select all or search

  //Todo: select one

  //Todo: update

  //Todo: delete
}
