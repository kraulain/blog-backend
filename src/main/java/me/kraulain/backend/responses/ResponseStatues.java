package me.kraulain.backend.responses;

import io.vertx.ext.web.client.HttpResponse;

public interface ResponseStatues {

    public static <T> boolean is1xx(HttpResponse<T> response) {
        return response.statusCode() >= 100 && response.statusCode() < 200;
    }

    public static <T> boolean is2xx(HttpResponse<T> response) {
        return response.statusCode() >= 200 && response.statusCode() < 300;
    }

    public static <T> boolean is3xx(HttpResponse<T> response) {
        return response.statusCode() >= 300 && response.statusCode() < 400;
    }

    public static <T> boolean is4xx(HttpResponse<T> response) {
        return response.statusCode() >= 400 && response.statusCode() < 500;
    }

    public static <T> boolean is5xx(HttpResponse<T> response) {
        return response.statusCode() >= 500 && response.statusCode() < 600;
    }

    public static <T> boolean isOK(HttpResponse<T> response) {
        return response.statusCode() == 200;
    }

    public static <T> boolean isCreated(HttpResponse<T> response) {
        return response.statusCode() == 201;
    }

    public static <T> boolean isAccepted(HttpResponse<T> response) {
        return response.statusCode() == 202;
    }

    public static <T> boolean isNoContent(HttpResponse<T> response) {
        return response.statusCode() == 204;
    }

    public static <T> boolean isBadRequest(HttpResponse<T> response) {
        return response.statusCode() == 400;
    }

    public static <T> boolean isNotFound(HttpResponse<T> response) {
        return response.statusCode() == 404;
    }


}
