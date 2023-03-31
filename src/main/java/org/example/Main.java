package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.common.JsonConverter;
import org.example.pojo.ExpectedUser;
import org.example.pojo.ActualUser;


public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ExpectedUser expectedUser = new ExpectedUser("Azat", "lala@gmail.com", "male", "active", "");
        String json = objectMapper.writeValueAsString(expectedUser);

        String BASE_URL = "https://gorest.co.in/public/v2/";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.header("Authorization", "Bearer b832e612299fb7512a12652975f993ac0c8d6111bb542845b7650f805a429e0b");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        Response response = requestSpecification
                .body(json)
                .request(Method.POST, "users");
        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        String idUser = jsonPath.getString("id");

        Response response2 = requestSpecification
                .request(Method.DELETE, "users/" + idUser);

//        List<User> users = response.then()
//                .extract().body().jsonPath().getList("",User.class);
//        int i=0;
//        for (int i = 0; i < users.size()-1;i++){
//            System.out.println(users.get(i).getId());
//        }

//        String payload = gson.toJson(user);
//        System.out.println(payload);
        ObjectMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(expectedUser);

        System.out.println(json);
        System.out.println(xml);
        System.out.println("____________________________________________");


        String jsonUser = JsonConverter.serializationToJson(expectedUser);
        System.out.println(jsonUser);
//        ActualUser actualUser = JsonConverter.deSerializationFromJson(jsonUser, ActualUser.class);
//        System.out.println(actualUser.getEmail());

    }

}