package org.example;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.pojo.ExpectedUser;
import org.junit.Test;

public class GsonDataConverter {


    Gson gson = new Gson();
    @Test
    public void testGsonDeserialization(){
        String payload = "{\n" +
                "        \"name\": \"John Doe\",\n" +
                "        \"email\": \"john@doe.com\",\n" +
                "        \"gender\": \"male\",\n" +
                "        \"status\": \"active\"\n" +
                "    }";

        ExpectedUser expectedUser = gson.fromJson(payload, ExpectedUser.class);
        System.out.println(expectedUser.getName());
        System.out.println(expectedUser.getEmail());
    }

    @Test
    public void testSerialization(){
        ExpectedUser expectedUser = new ExpectedUser();
        expectedUser.setName("Azat Karapashov");
        expectedUser.setEmail("azat91@gmail.com");
        expectedUser.setStatus("active");
        expectedUser.setGender("male");

        String payload = gson.toJson(expectedUser);
        System.out.println(payload);

        final String BASE_URL = "https://gorest.co.in/public/v2/";
        RequestSpecification requestSpecification  = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.header("Authorization","Bearer 9bb228962a5436bc9ac217ad3511d60faa102226c6a7b8f46b24690095d0f249");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);

        Response response = requestSpecification
                .body(payload)
                .request(Method.POST, "users");
        response.prettyPrint();
    }


}
