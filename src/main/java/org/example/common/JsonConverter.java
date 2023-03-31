package org.example.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serializationToJson(Object pojo) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pojo);
    }

    public static <T> T deSerializationFromJson(Response response, Class<T> pojoClass) throws JsonProcessingException {
        String json = response.getBody().asString();
        return objectMapper.readValue(json, pojoClass);
    }
}
