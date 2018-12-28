package me.kraulain.backend.responses;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import me.kraulain.backend.data.JsonMapper;

import java.util.List;
import java.util.Optional;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public interface Responses {

    Logger LOGGER = LoggerFactory.getLogger(Responses.class);


    public default <T> void response(RoutingContext routingContext, Either<String, T> result,
                                     Option<HttpResponseStatus> status) {
        result.peek(successResult ->
                JsonMapper.pojoToJsonObject(successResult)
                        .peek(json -> success(routingContext, json, status)))
                .peekLeft(errorResult -> badRequest(routingContext));
    }

    public default <T> void responses(RoutingContext routingContext, Either<String, List<T>> result,
                                      Option<HttpResponseStatus> status) {
        result.peek(successResult ->
                JsonMapper.listToJsonArray(successResult)
                        .peek(json -> success(routingContext, json, status)))
                .peekLeft(errorResult -> badRequest(routingContext));
    }

    public static JsonObject wrapper(JsonObject response) {
        if (response.containsKey("data")) {
            return response;
        }
        return new JsonObject().put("data", response);
    }

    public static JsonObject wrapper(JsonArray response) {
        return new JsonObject().put("data", response);
    }

    public static Optional<JsonObject> unwrapperJsonObject(JsonObject response) {
        if (response.containsKey("data")) {
            return Optional.ofNullable(response.getJsonObject("data"));
        }
        return Optional.empty();
    }

    public static Optional<JsonArray> unwrapperJsonArray(JsonObject response) {
        if (response.containsKey("data")) {
            return Optional.ofNullable(response.getJsonArray("data"));
        }
        return Optional.empty();
    }


    public static <T> Optional<JsonObject> extractBody(HttpResponse<T> response) {
        if (ResponseStatues.is2xx(response)) {
            if (ResponseStatues.isNoContent(response)) {
                return Optional.of(new JsonObject());
            }
            return Optional.ofNullable(response.bodyAsJsonObject());
        }
        return Optional.empty();
    }


    public default void userError(RoutingContext routingContext, int statusCode) {
        routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaTypes.APPLICATION_JSON)
                .setStatusCode(statusCode)
                .end();
    }

    public default void serverError(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                .end();
    }


    public default void success(RoutingContext routingContext, JsonObject payload,
                                Option<HttpResponseStatus> status) {
        success(routingContext, payload, MediaTypes.APPLICATION_JSON, status);
    }

    public default void success(RoutingContext routingContext, JsonObject payload,
                                CharSequence contentType, Option<HttpResponseStatus> status) {
        if (payload != null && payload.isEmpty()) {
            noContent(routingContext);
            return;
        }

        switch (status.getOrElse(OK).code()) {
            case 200:
                ok(routingContext, payload, contentType);
                break;
            case 201:
                created(routingContext, payload, contentType);
                break;
            case 202:
                accepted(routingContext, payload, contentType);
                break;
        }
    }

    public default void success(RoutingContext routingContext, JsonArray payload,
                                Option<HttpResponseStatus> status) {
        success(routingContext, payload, MediaTypes.APPLICATION_JSON, status);
    }

    public default void success(RoutingContext routingContext, JsonArray payload,
                                CharSequence contentType, Option<HttpResponseStatus> status) {
        if (payload != null && payload.isEmpty()) {
            noContent(routingContext);
            return;
        }

        switch (status.getOrElse(OK).code()) {
            case 200:
                ok(routingContext, payload, contentType);
                break;
            case 201:
                created(routingContext, payload, contentType);
                break;
            case 202:
                accepted(routingContext, payload, contentType);
                break;
        }
    }

    public default void success(RoutingContext routingContext, JsonArray payload,
                                CharSequence contentType) {
        if (payload != null && payload.isEmpty()) {
            noContent(routingContext);
            return;
        }
        ok(routingContext, payload, contentType);
    }

    public default void success(RoutingContext routingContext, HttpResponse<Buffer> payload) {
        success(routingContext, payload, MediaTypes.APPLICATION_JSON);
    }

    public default void success(RoutingContext routingContext, HttpResponse<Buffer> payload,
                                CharSequence contentType) {
        if (payload != null && payload.body() == null) {
            noContent(routingContext);
            return;
        }
        ok(routingContext, payload, contentType);
    }

    public default void ok(RoutingContext routingContext, JsonObject payload) {
        ok(routingContext, payload, MediaTypes.APPLICATION_JSON);
    }

    public default void ok(RoutingContext routingContext, JsonObject payload,
                           CharSequence contentType) {
        HttpServerResponse response = routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(OK.code());

        if (payload == null) {
            response.end();
            return;
        }
        response.end(wrapper(payload).encode());
    }

    public default void ok(RoutingContext routingContext, JsonArray payload) {
        ok(routingContext, payload, MediaTypes.APPLICATION_JSON);
    }

    public default void ok(RoutingContext routingContext, JsonArray payload,
                           CharSequence contentType) {
        HttpServerResponse response = routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(OK.code());

        if (payload == null) {
            response.end();
            return;
        }
        response.end(wrapper(payload).encode());
    }

    public default void ok(RoutingContext routingContext, HttpResponse<Buffer> payload,
                           CharSequence contentType) {
        HttpServerResponse response = routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(OK.code());

        if (payload == null) {
            response.end();
            return;
        }
        response.end(payload.body());
    }

    public default void ok(RoutingContext routingContext, String payload,
                           CharSequence contentType) {
        HttpServerResponse response = routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(OK.code());

        if (payload == null) {
            response.end();
            return;
        }
        response.end(payload);
    }

    public default void created(RoutingContext routingContext, JsonObject payload) {
        created(routingContext, payload, MediaTypes.APPLICATION_JSON);
    }

    public default void created(RoutingContext routingContext, JsonObject payload,
                                CharSequence contentType) {
        // TODO: Add Location header
        routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .end(wrapper(payload).encode());
    }

    public default void created(RoutingContext routingContext, JsonArray payload) {
        created(routingContext, payload, MediaTypes.APPLICATION_JSON);
    }

    public default void created(RoutingContext routingContext, JsonArray payload,
                                CharSequence contentType) {
        // TODO: Add Location header
        routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .end(wrapper(payload).encode());
    }

    public default void noContent(RoutingContext routingContext) {
        routingContext
                .response()
                .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
                .end();
    }

    public default void noContent(RoutingContext routingContext, CharSequence contentType) {
        routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
                .end();
    }

    public default void accepted(RoutingContext routingContext) {
        JsonObject noPayload = null;
        accepted(routingContext, noPayload, MediaTypes.APPLICATION_JSON);
    }

    public default void accepted(RoutingContext routingContext, JsonObject payload,
                                 CharSequence contentType) {
        HttpServerResponse response = routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(HttpResponseStatus.ACCEPTED.code());

        if (payload == null) {
            response.end();
            return;
        }
        response.end(wrapper(payload).encode());

    }

    public default void accepted(RoutingContext routingContext, JsonArray payload,
                                 CharSequence contentType) {
        HttpServerResponse response = routingContext
                .response()
                .putHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .setStatusCode(HttpResponseStatus.ACCEPTED.code());

        if (payload == null) {
            response.end();
            return;
        }
        response.end(wrapper(payload).encode());

    }

    public default void badRequest(RoutingContext routingContext) {
        userError(routingContext, HttpResponseStatus.BAD_REQUEST.code());
    }

    public default void notFound(RoutingContext routingContext) {
        userError(routingContext, HttpResponseStatus.NOT_FOUND.code());
    }
}
