package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Demo2 {

    static Response response;
    static RequestSpecification requestSpecification;
    final static String BASE_URL = "https://gorest.co.in/public/v2/";

    JsonPath jsonPath;
    static String userId = "";
    static String email = "";

    @Before
    public void setUp(){
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.header("Authorization","Bearer 9bb228962a5436bc9ac217ad3511d60faa102226c6a7b8f46b24690095d0f249");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
    }

    @Test
    public void createNewUser(){
        String payload = "{\n" +
                "        \"name\": \"Jw Doe\",\n" +
                "        \"email\": \"ert@doe.com\",\n" +
                "        \"gender\": \"male\",\n" +
                "        \"status\": \"active\"\n" +
                "    }";
        requestSpecification.body(payload);
        response = requestSpecification
                .request(Method.POST,"users");

        jsonPath = response.jsonPath();
        userId = jsonPath.getString("id");
        email = jsonPath.getString("email");
        response.prettyPrint();
        Assert.assertEquals(201,response.getStatusCode());

    }

    @Test
    public void updateNewUserName(){
        String payload = "{\n" +
                "    \"name\": \"Azat\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        requestSpecification.body(payload);
        response = requestSpecification
                .request(Method.PATCH,"users/" + userId);
        response.prettyPrint();
        Assert.assertEquals(200,response.getStatusCode());
        int idUser = Integer.parseInt(userId);
        response.then().body("id", Matchers.is(idUser));
    }

    @Test
    public void updateWithPut(){
        String payload = "{\n" +
                "    \"name\": \"Row We\",\n" +
                "    \"email\": \"ert@doe.com\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";
        requestSpecification.body(payload);
        Response response = requestSpecification
                .request(Method.PUT, "users/" + userId);
        Assert.assertEquals(200, response.getStatusCode());
        response.prettyPrint();
        System.out.println(email);

    }
    @Test
    public void getUserWithEmail(){
        requestSpecification.queryParam("email", email);
        response = requestSpecification
                .request(Method.GET, "users");
        response.prettyPrint();
    }

    @Test
    public void  deleteUser(){
        response = requestSpecification
                .request(Method.DELETE, "users/" + userId);
        Assert.assertEquals(204, response.getStatusCode());
        response.prettyPrint();
    }

    @Test
    public void findUserTest(){
        requestSpecification.queryParam("name", "Anand Dutta");
        response = requestSpecification
                .request(Method.GET, "users");
        response.prettyPrint();
    }
}