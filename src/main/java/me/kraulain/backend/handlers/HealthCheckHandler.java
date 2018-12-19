package com.afgrey.solarlinx.user.handlers;

import com.afgrey.solarlinx.common.responses.MediaTypes;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HealthCheckHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext event) {
        JsonObject response = new JsonObject();
        response.put("status",
                HttpResponseStatus.OK
                        .reasonPhrase());
        response.put("timestamp", LocalDateTime.now()
                .format(DateTimeFormatter.ISO_DATE_TIME));
        event.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(response.encode());
    }
}