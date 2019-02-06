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
import me.kraulain.backend.handlers.blog.app.*;
import me.kraulain.backend.handlers.blog.article.*;
import me.kraulain.backend.handlers.blog.book.*;
import me.kraulain.backend.handlers.blog.comment.*;
import me.kraulain.backend.handlers.blog.course.*;
import me.kraulain.backend.handlers.blog.podcast.*;
import me.kraulain.backend.handlers.blog.presentation.*;
import me.kraulain.backend.handlers.blog.reply.*;
import me.kraulain.backend.handlers.blog.user.*;
import me.kraulain.backend.handlers.blog.video.*;
import me.kraulain.backend.handlers.contact.message.*;
import me.kraulain.backend.handlers.issues.Issue.*;
import me.kraulain.backend.handlers.notification.email.*;
import me.kraulain.backend.handlers.notification.push.*;
import me.kraulain.backend.handlers.notification.sms.*;

import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

  private JDBCClient dbClient;
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);
  private List<String> createAllTables = new ArrayList<>();

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

    initDBQueries();

    dbClient.getConnection(ar -> {
      if (ar.failed()) {
        LOGGER.error("Could not open a database connection", ar.cause());
        future.fail(ar.cause());
      } else {
        SQLConnection connection = ar.result();
        connection.batch(createAllTables, create -> {
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
    router.mountSubRouter("/issue", issuesRoutes());
    // visit endpoint
    router.mountSubRouter("/visit", visitRoutes());

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
    //Get
    router.get("/app").handler(new GetAppsHandler());
    router.get("/app/:id").handler(new GetAppHandler());
    router.get("/article").handler(new GetArticlesHandler());
    router.get("/article/:id").handler(new GetArticleHandler());
    router.get("/book").handler(new GetBooksHandler());
    router.get("/book/:id").handler(new GetBookHandler());
    router.get("/comment").handler(new GetCommentsHandler());
    router.get("/comment/:id").handler(new GetCommentHandler());
    router.get("/course").handler(new GetCoursesHandler());
    router.get("/course/:id").handler(new GetCourseHandler());
    router.get("/presentation").handler(new GetPresentationsHandler());
    router.get("/presentation/:id").handler(new GetPresentationHandler());
    router.get("/reply").handler(new GetRepliesHandler());
    router.get("/reply/:id").handler(new GetReplyHandler());
    router.get("/user").handler(new GetUsersHandler());
    router.get("/user/:id").handler(new GetUserHandler());
    router.get("/video").handler(new GetVideosHandler());
    router.get("/video/:id").handler(new GetVideoHandler());
    router.get("/podcast").handler(new GetPodcastsHandler());
    router.get("/podcast/:id").handler(new GetPodcastHandler());
    //post
    router.post("/app").handler(new PostAppHandler());
    router.post("/article").handler(new PostArticleHandler());
    router.post("/book").handler(new PostBookHandler());
    router.post("/comment").handler(new PostCommentHandler());
    router.post("/course").handler(new PostCourseHandler());
    router.post("/presentation").handler(new PostPresentationHandler());
    router.post("/reply").handler(new PostReplyHandler());
    router.post("/user").handler(new PostUserHandler());
    router.post("/video").handler(new PostVideoHandler());
    router.post("/podcast").handler(new PostPodcastHandler());
    //put
    router.put("/app/:id").handler(new PutAppHandler());
    router.put("/article/:id").handler(new PutArticleHandler());
    router.put("/book/:id").handler(new PutBookHandler());
    router.put("/comment/:id").handler(new PutCommentHandler());
    router.put("/course/:id").handler(new PutCourseHandler());
    router.put("/presentation/:id").handler(new PutPresentationHandler());
    router.put("/reply/:id").handler(new PutReplyHandler());
    router.put("/user/:id").handler(new PutUserHandler());
    router.put("/video/:id").handler(new PutVideoHandler());
    router.put("/podcast/:id").handler(new PutPodcastHandler());
    //delete
    router.delete("/app/:id").handler(new DeleteAppHandler());
    router.delete("/article/:id").handler(new DeleteArticleHandler());
    router.delete("/book/:id").handler(new DeleteBookHandler());
    router.delete("/comment/:id").handler(new DeleteCommentHandler());
    router.delete("/course/:id").handler(new DeleteCourseHandler());
    router.delete("/presentation/:id").handler(new DeletePresentationHandler());
    router.delete("/reply/:id").handler(new DeleteReplyHandler());
    router.delete("/user/:id").handler(new DeleteUserHandler());
    router.delete("/video/:id").handler(new DeleteVideoHandler());
    router.delete("/podcast/:id").handler(new DeletePodcastHandler());

    return router;
  }

  private Router notificationRoutes() {
    LOGGER.debug("Mounting '/notification' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/push").handler(new GetPushesHandler());
    router.get("/push/:id").handler(new GetPushHandler());
    router.get("/sms").handler(new GetSmssHandler());
    router.get("/sms/:id").handler(new GetSmsHandler());
    router.get("/emails").handler(new GetEmailsHandler());
    router.get("/emails/:id").handler(new GetEmailHandler());
    //post
    router.post("/push").handler(new PostPushHandler());
    router.post("/sms").handler(new PostSmsHandler());
    router.post("/emails").handler(new PostEmailHandler());
    //put
    router.put("/push/:id").handler(new PutPushHandler());
    router.put("/sms/:id").handler(new PutSmsHandler());
    router.put("/emails/:id").handler(new PutEmailHandler());
    //delete
    router.delete("/push/:id").handler(new DeletePushHandler());
    router.delete("/sms/:id").handler(new DeleteSmsHandler());
    router.delete("/emails/:id").handler(new DeleteEmailHandler());

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
    LOGGER.debug("Mounting '/issue' endpoint");
    Router router = Router.router(vertx);
    //Get
    router.get("/issues").handler(new GetVisitsHandler());
    router.get("/issues/:id").handler(new GetVisitHandler());
    //post
    router.post("/issues").handler(new PostVisitHandler());
    //put
    router.put("/issues/:id").handler(new PutVisitHandler());
    //delete
    router.delete("/issues/:id").handler(new DeleteVisitHandler());

    return router;
  }

  private Router visitRoutes() {
    LOGGER.debug("Mounting '/visit' endpoint");

    Router router = Router.router(vertx);
    //Get
    router.get("/visits").handler(new GetVisitsHandler());
    router.get("/visits/:id").handler(new GetVisitHandler());
    //post
    router.post("/visits").handler(new PostVisitHandler());
    //put
    router.put("/visits/:id").handler(new PutVisitHandler());
    //delete
    router.delete("/visits/:id").handler(new DeleteVisitHandler());

    return router;
  }

  private void initDBQueries(){
    String CREATE_ARTICLE_TABLE = "create table if not exists article (id integer identity primary key, " +
      "title varchar(100), sub_title varchar(100), image_url varchar(255), body clob, published_date date, status varchar(15), language varchar(3))";
    String CREATE_APP_TABLE = "create table if not exists app (id integer identity primary key, " +
      "name varchar(100), sub_title varchar(100), description clob, image_urls varchar(255), play_store_url varchar(255), app_store_url varchar(255), status varchar(15), language varchar(3))";
    String CREATE_BOOK_TABLE = "create table if not exists book (id integer identity primary key, " +
      "title varchar(100), sub_title varchar(100), image_url varchar(255), description clob, published_date date, status varchar(15), language varchar(3))";
    String CREATE_COMMENT_TABLE = "create table if not exists comment (id integer identity primary key, " +
      "user_id integer, body clob, published_date date, status varchar(15), language varchar(3), FOREIGN KEY (user_id) REFERENCES user(id))";
    String CREATE_COURSE_TABLE = "create table if not exists course (id integer identity primary key, " +
      "title varchar(100), sub_title varchar(100), image_url varchar(255), description clob, articles_id varchar(255), status varchar(15), language varchar(3))";
    String CREATE_EMAIL_TABLE = "create table if not exists email (id integer identity primary key, " +
      "receivers clob, subject varchar(255), sender varchar(50), body clob, status varchar(15), language varchar(3))";
    String CREATE_ISSUE_TABLE = "create table if not exists issue (id integer identity primary key, " +
      "occurrence_date date, cause varchar(255), error_message varchar(255),status varchar(15))";
    String CREATE_MESSAGE_TABLE = "create table if not exists message (id integer identity primary key, " +
      "email varchar(50), name varchar(100), message clob, published_date date, status varchar(15), language varchar(3))";
    String CREATE_PODCAST_TABLE = "create table if not exists podcast (id integer identity primary key, " +
      "title varchar(100), sub_title varchar(100), image_url varchar(255), soundcloud_url varchar(255), description clob, status varchar(15), language varchar(3))";
    String CREATE_PRESENTATION_TABLE = "create table if not exists presentation (id integer identity primary key, " +
      "title varchar(100), sub_title varchar(100), image_url varchar(255), slides varchar(255), description clob, status varchar(15), language varchar(3))";
    String CREATE_PUSH_TABLE = "create table if not exists push (id integer identity primary key, " +
      "title varchar(100), image_url varchar(255), body clob, status varchar(15), language varchar(3))";
    String CREATE_REPLY_TABLE = "create table if not exists reply (id integer identity primary key, " +
      "user_id integer, name varchar(255), reply_to integer, body clob, published_date date, status varchar(15), language varchar(3), FOREIGN KEY (user_id) REFERENCES user(id), FOREIGN KEY (reply_to) REFERENCES comment(id))";
    String CREATE_SMS_TABLE = "create table if not exists sms (id integer identity primary key, " +
      "receiver clob, body clob, status varchar(15), language varchar(3))";
    String CREATE_USER_TABLE = "create table if not exists user (id integer identity primary key, " +
      "name varchar(100), email varchar(100), status varchar(15), language varchar(3))";
    String CREATE_VIDEO_TABLE = "create table if not exists video (id integer identity primary key, " +
      "title varchar(100), sub_title varchar(100), image_url varchar(255), youtube_url varchar(255), description clob, status varchar(15), language varchar(3))";
    createAllTables.add(CREATE_ARTICLE_TABLE);
    createAllTables.add(CREATE_APP_TABLE);
    createAllTables.add(CREATE_BOOK_TABLE);
    createAllTables.add(CREATE_COMMENT_TABLE);
    createAllTables.add(CREATE_COURSE_TABLE);
    createAllTables.add(CREATE_EMAIL_TABLE);
    createAllTables.add(CREATE_ISSUE_TABLE);
    createAllTables.add(CREATE_MESSAGE_TABLE);
    createAllTables.add(CREATE_PODCAST_TABLE);
    createAllTables.add(CREATE_PRESENTATION_TABLE);
    createAllTables.add(CREATE_PUSH_TABLE);
    createAllTables.add(CREATE_REPLY_TABLE);
    createAllTables.add(CREATE_SMS_TABLE);
    createAllTables.add(CREATE_USER_TABLE);
    createAllTables.add(CREATE_VIDEO_TABLE);
  }

}
