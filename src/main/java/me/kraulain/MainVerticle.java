package me.kraulain;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import me.kraulain.data.Collections;
import me.kraulain.handlers.*;
import me.kraulain.handlers.admin.ClaimPasscodeHandler;
import me.kraulain.handlers.admin.LoginHandler;
import me.kraulain.handlers.articles.*;
import me.kraulain.handlers.courses.*;
import me.kraulain.handlers.users.*;
import me.kraulain.handlers.messages.*;
import me.kraulain.handlers.notifications.*;
import me.kraulain.handlers.visits.*;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);
  private MongoClient dbClient;
  private JWTAuth provider;


  @Override
  public void start(Future<Void> startFuture) throws Exception {
    Future<Void> steps = prepareDatabase().compose(v -> startHttpServer());
    steps.setHandler(startFuture.completer());
  }

  private Future<Void> prepareDatabase() {
    Future<Void> future = Future.future();
    initDB();
    if(dbClient != null){
      future.complete();
    }
    return future;
  }

  private Future<Void> startHttpServer() {
    Future<Void> future = Future.future();

    // Create a JWT Auth Provider
    JWTAuthOptions config = new JWTAuthOptions()
      .setKeyStore(new KeyStoreOptions()
        .setType("jks")
        .setPath("kraulain-me.jks")
        .setPassword("kraulain-me"));

    provider = JWTAuth.create(vertx, config);

    HttpServerOptions serverOptions = new HttpServerOptions()
      .setSsl(true)
      .setKeyStoreOptions(new JksOptions()
        .setPath("kraulain-me.jks")
        .setPassword("kraulain-me"));


    HttpServer server = vertx.createHttpServer(serverOptions);

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

    // orders endpoint
    router.mountSubRouter("/admin", adminRoutes());
    // articles endpoint
    router.mountSubRouter("/articles", articlesRoutes());
    // courses endpoint
    router.mountSubRouter("/courses", coursesRoutes());
    // messages endpoint
    router.mountSubRouter("/messages", messagesRoutes());
    // notifications endpoint
    router.mountSubRouter("/notifications", notificationsRoutes());
    // users endpoint
    router.mountSubRouter("/users", usersRoutes());
    // visits endpoint
    router.mountSubRouter("/visits", visitsRoutes());

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

  private Router adminRoutes() {
    LOGGER.debug("Mounting '/admin' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/login/:phoneNumber").handler(new LoginHandler(dbClient));
    router.get("/claimpasscode/:passCode").handler(new ClaimPasscodeHandler(dbClient, provider));

    return router;
  }

  private Router articlesRoutes() {
    LOGGER.debug("Mounting '/articles' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/").handler(new GetArticlesHandler(dbClient));
    router.get("/:id").handler(new GetArticleHandler(dbClient));
    //post
    router.post("/").handler(new PostArticleHandler(dbClient));
    //put
    router.put("/:id").handler(new PutArticleHandler(dbClient));
    //delete
    router.delete("/:id").handler(new DeleteArticleHandler(dbClient));

    return router;
  }

  private Router coursesRoutes() {
    LOGGER.debug("Mounting '/courses' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/").handler(new GetCoursesHandler(dbClient));
    router.get("/:id").handler(new GetCourseHandler(dbClient));
    //post
    router.post("/").handler(new PostCourseHandler(dbClient));
    //put
    router.put("/:id").handler(new PutCourseHandler(dbClient));
    //delete
    router.delete("/:id").handler(new DeleteCourseHandler(dbClient));

    return router;
  }

  private Router messagesRoutes() {
    LOGGER.debug("Mounting '/messages' endpoint");
    Router router = Router.router(vertx);
    //Get
    router.get("/").handler(new GetMessagesHandler(dbClient));
    router.get("/:id").handler(new GetMessageHandler(dbClient));
    //post
    router.post("/").handler(new PostMessageHandler(dbClient));
    //put
    router.put("/:id").handler(new PutMessageHandler(dbClient));
    //delete
    router.delete("/:id").handler(new DeleteMessageHandler(dbClient));

    return router;
  }

  private Router notificationsRoutes() {
    LOGGER.debug("Mounting '/notifications' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/").handler(new GetNotificationsHandler(dbClient));
    router.get("/:id").handler(new GetNotificationHandler(dbClient));
    //post
    router.post("/").handler(new PostNotificationHandler(dbClient));
    //put
    router.put("/:id").handler(new PutNotificationHandler(dbClient));
    //delete
    router.delete("/:id").handler(new DeleteNotificationHandler(dbClient));

    return router;
  }

  private Router usersRoutes() {
    LOGGER.debug("Mounting '/users' endpoint");
    Router router = Router.router(vertx);
    //Get
    router.get("/").handler(new GetUsersHandler(dbClient));
    router.get("/:id").handler(new GetUserHandler(dbClient));
    //post
    router.post("/").handler(new PostUserHandler(dbClient));
    //put
    router.put("/:id").handler(new PutUserHandler(dbClient));
    //delete
    router.delete("/:id").handler(new DeleteUserHandler(dbClient));

    return router;
  }

  private Router visitsRoutes() {
    LOGGER.debug("Mounting '/visits' endpoint");
    Router router = Router.router(vertx);
    //Get
    router.get("/").handler(new GetVisitsHandler(dbClient));
    router.get("/:id").handler(new GetVisitHandler(dbClient));
    //post
    router.post("/").handler(new PostVisitHandler(dbClient));
    //put
    router.put("/:id").handler(new PutVisitHandler(dbClient));
    //delete
    router.delete("/:id").handler(new DeleteVisitHandler(dbClient));

    return router;
  }

  private void initDB() {
    JsonObject dbConfig = new JsonObject().put("host", "172.17.0.2")
      .put("port", 27017)
      .put("username", "kraulain-me-user")
      .put("password", "kraulain-me-password")
      .put("db_name", "kraulain-me")
      .put("authMechanism", "SCRAM-SHA-1")
      .put("authSource", "admin");

    this.dbClient = MongoClient.createShared(vertx, dbConfig);

    //drop collections
//    for (Collections c: Collections.values()){
//      this.dbClient.dropCollection(c.name(), res -> {
//        if (res.succeeded()) {
//          LOGGER.info("dropped "+c+" collection");
//        } else {
//          LOGGER.debug(res.cause());
//        }
//      });
//    }

    //create collections
    for (Collections c : Collections.values()) {
      this.dbClient.createCollection(c.name(), res -> {
        if (res.succeeded()) {
          LOGGER.info("Created " + c + " collection");
        } else {
          LOGGER.debug(res.cause());
        }
      });
    }
  }

}
