package com.afgrey.solarlinx.common.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.control.Either;
import io.vavr.jackson.datatype.VavrModule;
import io.vertx.core.impl.StringEscapeUtils;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by Afgrey Development Team.
 */
public interface JsonMapper {

    Logger LOGGER = LoggerFactory.getLogger(JsonMapper.class);

    public static ObjectMapper getMapper() {
        return getMapper(new ObjectMapper());
    }

    public static ObjectMapper getMapper(ObjectMapper mapper) {
        if (Objects.nonNull(mapper)) {
            mapper.registerModule(new JavaTimeModule());
            mapper.registerModule(new VavrModule());
            mapper.configure(MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return mapper;
    }

    public static <T> Either<String, T> jsonObjectToPojo(Class<T> clazz, JsonObject json) {
        return Optional.ofNullable(json)
                .stream()
                .peek(_1 -> LOGGER.trace("jsonObjectToPojo(..) => json: {}", json.encodePrettily()))
                .findFirst()
                .map(jsonObject -> jsonToPojo(clazz, jsonObject.encode()))
                .orElse(Either.left(String.format("Can't marshall json=%s into an object of class=%s", json, clazz.getSimpleName())));
    }

    public static <T> Either<String, T> jsonToPojo(Class<T> clazz, String json) {
        LOGGER.trace("jsonToPojo(..) => json: {}", json);
        try {
            return Either.right(getMapper().readValue(StringEscapeUtils.unescapeJavaScript(json), clazz));
        } catch (Exception e) {
            LOGGER.trace("Exception while mapping string json->object", e);
            return Either.left(e.getMessage());
        }
    }

    public static <T> Either<String, List<T>> jsonToListPojo(Class<T> clazz, String json) {
        LOGGER.trace("jsonToPojo(..) => json: {}", json);
        try {
            return Either.right(List.of(getMapper().readValue(StringEscapeUtils.unescapeJavaScript(json), clazz)));
        } catch (Exception e) {
            LOGGER.trace("Exception while mapping string json->object", e);
            return Either.left(e.getMessage());
        }
    }

    public static <T> Either<String, List<T>> jsonObjectsToPojos(Class<T> clazz, List<JsonObject> jsonItems) {
        List<Either<String, T>> pojos = Optional.ofNullable(jsonItems)
                .stream()
                .flatMap(jsonObjects -> jsonItems.stream().map(JsonObject::encode).collect(toList()).stream())
                .map(jsonObject -> jsonToPojo(clazz, jsonObject))
                .collect(toList());
        if (pojos.stream().anyMatch(Either::isLeft)) {
            return Either.left(pojos.stream()
                    .filter(Either::isLazy)
                    .findAny()
                    .get()
                    .getLeft());
        }
        return Either.right(pojos.stream()
                .filter(Either::isRight)
                .map(Either::get)
                .collect(toList()));
    }

    public static <T> Either<String, JsonObject> pojoToJsonObject(T pojo) {
        LOGGER.trace("pojoToJsonObject(..) => pojo: {}", pojo);
        if (Objects.isNull(pojo)) {
            return Either.left("Can't decode a null object");
        }

        try {
            return Either.right((new JsonObject(getMapper().writeValueAsString(pojo))));
        } catch (JsonProcessingException ex) {
            String msg = "An error occurred while mapping pojo->json";
            LOGGER.trace(msg, ex);
            return Either.left(msg);
        }

    }

    public static <T> Either<String, JsonArray> listToJsonArray(List<T> list) {
        LOGGER.trace("listToJsonArray(..) => list: {}", list);
        if (Objects.isNull(list)) {
            return Either.left("Can't decode a null list");
        }

        try {
            return Either.right(new JsonArray(getMapper().writeValueAsString(list)));
        } catch (JsonProcessingException ex) {
            String msg = "An error occurred while mapping pojo->json";
            LOGGER.trace(msg, ex);
            return Either.left(msg);
        }
    }
}
