package me.kraulain.backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import me.kraulain.backend.handlers.*;

public class MainVerticle extends AbstractVerticle {

  private JDBCClient dbClient;
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  private static final String SQL_CREATE_ARTICLE_TABLE = "create table if not exists article (Id integer identity primary key, Name varchar(255) unique, Content clob)";

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    Future<Void> steps = prepareDatabase().compose(v -> startHttpServer());
    steps.setHandler(startFuture.completer());
  }

  private Future<Void> prepareDatabase() {
    Future<Void> future = Future.future();
    dbClient = JDBCClient.createShared(vertx, new JsonObject()
      .put("url", "jdbc:hsqldb:file:db/blog")
      .put("driver_class", "org.hsqldb.jdbcDriver")
      .put("max_pool_size", 30));

    dbClient.getConnection(ar -> {
      if (ar.failed()) {
        LOGGER.error("Could not open a database connection", ar.cause());
        future.fail(ar.cause());
      } else {
        SQLConnection connection = ar.result();
        connection.execute(SQL_CREATE_ARTICLE_TABLE, create -> {
          connection.close();
          if (create.failed()) {
            LOGGER.error("Database preparation error", create.cause());
            future.fail(create.cause());
          } else {
            future.complete();
          }
        });
      }
    });
    return future;
  }

  private Future<Void> startHttpServer() {
    Future<Void> future = Future.future();
    HttpServer server = vertx.createHttpServer();

    LOGGER.debug("in mainVerticle.start(..)");
    Router router = Router.router(vertx);
    router.route()
      .handler(
        CorsHandler.create("*")
          .allowedMethod(HttpMethod.GET)
          .allowedMethod(HttpMethod.POST)
          .allowedMethod(HttpMethod.PUT)
          .allowedMethod(HttpMethod.OPTIONS)
          .allowedHeader(HttpHeaders.ACCEPT.toString())
          .allowedHeader(HttpHeaders.CONTENT_TYPE.toString()));

    // Decode body of all requests
    router.route().handler(BodyHandler.create());

    // Health Check
    router.get("/health")
      .handler(new HealthCheckHandler());

    // Error
    router.route()
      .last()
      .handler(new ResourceNotFoundHandler());

    // blog endpoint
    router.mountSubRouter("/blog", blogRoutes());

    // notification endpoint
    router.mountSubRouter("/notification", notificationRoutes());

    // contact endpoint
    router.mountSubRouter("/contact", contactRoutes());

    // issues endpoint
    router.mountSubRouter("/issues", issuesRoutes());

    server.requestHandler(router::accept)
      .listen(8888, ar -> {
        if (ar.succeeded()) {
          LOGGER.info("HTTP server running on port 8888");
          future.complete();
        } else {
          LOGGER.error("Could not start a HTTP server", ar.cause());
          future.fail(ar.cause());
        }
      });
    return future;
  }

  private Router blogRoutes() {
    LOGGER.debug("Mounting '/blog' endpoint");
    Router router = Router.router(vertx);
    //Get all articles paginated
    router.get("/articles").handler(new GetAllArticlesHandler());

    return router;
  }

  private Router notificationRoutes() {
    LOGGER.debug("Mounting '/notification' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/push").handler(new ResourceNotFoundHandler());
    router.get("/push/:id").handler(new ResourceNotFoundHandler());
    router.get("/sms").handler(new ResourceNotFoundHandler());
    router.get("/sms/:id").handler(new ResourceNotFoundHandler());
    router.get("/emails").handler(new ResourceNotFoundHandler());
    router.get("/emails/:id").handler(new ResourceNotFoundHandler());
    //post
    router.post("/push").handler(new ResourceNotFoundHandler());
    router.post("/sms").handler(new ResourceNotFoundHandler());
    router.post("/emails").handler(new ResourceNotFoundHandler());
    //put
    router.put("/push/:id").handler(new ResourceNotFoundHandler());
    router.put("/sms/:id").handler(new ResourceNotFoundHandler());
    router.put("/emails/:id").handler(new ResourceNotFoundHandler());
    //delete
    router.delete("/push/:id").handler(new ResourceNotFoundHandler());
    router.delete("/sms/:id").handler(new ResourceNotFoundHandler());
    router.delete("/emails/:id").handler(new ResourceNotFoundHandler());

    return router;
  }

  private Router contactRoutes() {
    LOGGER.debug("Mounting '/contact' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/messages").handler(new GetMessagesHandler());
    router.get("/messages/:id").handler(new GetMessageHandler());
    //post
    router.post("/messages").handler(new PostMessageHandler());
    //put
    router.put("/messages/:id").handler(new PutMessageHandler());
    //delete
    router.delete("/messages/:id").handler(new DeleteMessageHandler());

    return router;
  }

  private Router issuesRoutes() {
    LOGGER.debug("Mounting '/issues' endpoint");
    Router router = Router.router(vertx);
    //Get all articles paginated
    //router.get("/issues").handler(new GetIssuesHandler());

    return router;
  }

}
