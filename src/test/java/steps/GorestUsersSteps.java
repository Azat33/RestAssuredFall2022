package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.common.JsonConverter;
import org.example.pojo.ExpectedUser;
import org.example.pojo.ActualUser;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GorestUsersSteps {
    final String BASE_URL = "https://gorest.co.in/public/v2/";
    static RequestSpecification requestSpecification = given();
    static Response response;
    static JsonPath jsonPath;
    static ActualUser actualUser;

    @DataTableType
    public ExpectedUser user(Map<String, String> row) {
        return new ExpectedUser(
                row.get("name"),
                row.get("email"),
                row.get("gender"),
                row.get("status"),
                row.get("id")
        );
    }

    @When("all users are requested")
    public void all_users_are_requested() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.header("Authorization", "Bearer 9bb228962a5436bc9ac217ad3511d60faa102226c6a7b8f46b24690095d0f249");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.accept(ContentType.JSON);
        response = requestSpecification
                .request(Method.GET, "users");
        jsonPath = response.jsonPath();
    }

    @Then("{int} users are returned")
    public void users_are_returned(int expectedStatusCode) {
        int userCount = response.jsonPath().getList("id").size();
        Assert.assertEquals(expectedStatusCode, userCount);
    }

    @When("the following user is created")
    public void the_following_user_is_created(ExpectedUser expectedUser) throws JsonProcessingException {
        String payload = JsonConverter.serializationToJson(expectedUser);
        response = requestSpecification
                .body(payload)
                .post("users");
        jsonPath = response.jsonPath();
    }

    @Then("a status code {int} is returned")
    public void a_status_code_is_returned(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("the following user is returned")
    public void the_following_user_is_returned(ExpectedUser expectedUser) throws JsonProcessingException {
        response = requestSpecification
                .get("users/" + jsonPath.getString("id"));
        Assert.assertEquals(expectedUser.getName(), jsonPath.getString("name"));
        Assert.assertEquals(expectedUser.getEmail(), jsonPath.getString("email"));
        Assert.assertEquals(expectedUser.getGender(), jsonPath.getString("gender"));
        Assert.assertEquals(expectedUser.getStatus(), jsonPath.getString("status"));

        actualUser = JsonConverter.deSerializationFromJson(response, ActualUser.class);
        Assert.assertEquals(expectedUser.getName(), actualUser.getName());
        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assert.assertEquals(expectedUser.getGender(), actualUser.getGender());
        Assert.assertEquals(expectedUser.getStatus(), actualUser.getStatus());
    }

    @When("the user is deleted")
    public void the_user_is_deleted() {
        response = requestSpecification
                .delete("users/" + actualUser.getId());
    }
}
