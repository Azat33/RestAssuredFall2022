package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class Demo {
    public static void main(String[] args) {

//        b832e612299fb7512a12652975f993ac0c8d6111bb542845b7650f805a429e0b

        final String BASE_URL = "https://gorest.co.in/public/v2/";

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.header("Authorization", "Bearer b832e612299fb7512a12652975f993ac0c8d6111bb542845b7650f805a429e0b");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);

        Response response = requestSpecification
                .request(Method.GET, "users");

        Assert.assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());






   }
}
