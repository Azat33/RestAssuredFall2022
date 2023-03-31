package org.example.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

public class JacksonObjectMapper {


    ObjectMapper objectMapper = new ObjectMapper();

    XmlMapper xmlMapper = new XmlMapper();

    @Test
    public void javaToJson() throws JsonProcessingException {
        String payload = "{\n" +
                "        \"name\": \"John Doe\",\n" +
                "        \"email\": \"john@doe.com\",\n" +
                "        \"gender\": \"male\",\n" +
                "        \"status\": \"active\"\n" +
                "    }";
        ExpectedUser expectedUser = objectMapper.readValue(payload, ExpectedUser.class);
        System.out.println(expectedUser.getEmail());
    }

    @Test
    public void javaToXml() throws JsonProcessingException {
        ExpectedUser expectedUser = new ExpectedUser();
        expectedUser.setName("Elon Musk");
        expectedUser.setEmail("elona123@tesla.com");
        expectedUser.setStatus("active");
        expectedUser.setGender("male");

        String xmlPayload = xmlMapper.writeValueAsString(expectedUser);
        System.out.println(xmlPayload);

        final String BASE_URL = "https://gorest.co.in/public/v2/";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.header("Authorization","Bearer 9bb228962a5436bc9ac217ad3511d60faa102226c6a7b8f46b24690095d0f249");
        requestSpecification.contentType(ContentType.XML);
        requestSpecification.accept(ContentType.XML);
        Response response = requestSpecification
                .body(xmlPayload)
                .request(Method.POST,"users");
        response.prettyPrint();
    }
}
