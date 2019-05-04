package me.kraulain.dao;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import me.kraulain.data.Collections;

public class MongoDAO {

  private MongoClient dbClient;

  public MongoDAO(MongoClient dbClient) {
    this.dbClient = dbClient;
  }

  public Future<JsonObject> save(Collections collection, JsonObject document) {

    Future<JsonObject> future = Future.future();

    dbClient.save(collection.name(), document, res -> {
      if (res.succeeded()) {
        document.put("id", res.result());
        future.complete(document);
      } else {
        future.fail(res.cause());
      }
    });

    return future;
  }

  public Future<JsonObject> retrieveOne(Collections collection, JsonObject query) {
    Future<JsonObject> future = Future.future();

    dbClient.find(collection.name(), query, res -> {
      if (res.succeeded()) {
        JsonObject document = new JsonObject();
        for (JsonObject jsonObject : res.result()) {
          document = jsonObject;
          break;
        }
        future.complete(document);
      } else {
        future.fail(res.cause());
      }
    });

    return future;
  }

  public Future<JsonArray> search(Collections collection, JsonObject query) {

    Future<JsonArray> future = Future.future();

    dbClient.find(collection.name(), query, res -> {
      if (res.succeeded()) {

        JsonArray documents = new JsonArray();
        for (JsonObject jsonObject : res.result()) {
          documents.add(jsonObject);
        }
        future.complete(documents);
      } else {
        future.fail(res.cause());
      }
    });

    return future;
  }

  public Future<JsonArray> retrieveBatch(Collections collection, JsonObject query) {

    Future<JsonArray> future = Future.future();

    dbClient.find(collection.name(), query, res -> {
      if (res.succeeded()) {

        JsonArray documents = new JsonArray();
        for (JsonObject jsonObject : res.result()) {
          documents.add(jsonObject);
        }
        future.complete(documents);
      } else {
        future.fail(res.cause());
      }
    });

    return future;
  }

  public Future<JsonObject> update(Collections collection, JsonObject query, JsonObject replace) {

    Future<JsonObject> future = Future.future();

    dbClient.replaceDocuments(collection.name(), query, replace, res -> {
      if (res.succeeded()) {
        future.complete(replace);
      } else {
        future.fail(res.cause());
      }
    });

    return future;
  }

  public Future<JsonObject> delete(Collections collection, JsonObject query) {

    Future<JsonObject> future = Future.future();

    dbClient.removeDocuments(collection.name(), query, res -> {
      if (res.succeeded()) {
        future.complete(new JsonObject());
      } else {
        future.fail(res.cause());
      }
    });

    return future;
  }
}
