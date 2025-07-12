package com.rest.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test
public class MainTest_Amira {

    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    @Test
    public void testUpdateBook() {
        int bookId = 3;

        String updatedRequestBody = "{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"The Life-Changing Magic of Tidying Up\",\n" + // Also fixed typo: "he Life-Changing..."
                "    \"author\": \"Marie Kondo\",\n" +
                "    \"price\": 9.69\n" +
                "}";

        Response response = given()
                                .auth().basic("admin", "password")
                                .contentType("application/json")
                                .body(updatedRequestBody)
                            .when()
                                .put("http://localhost:8085/books/" + bookId)
                            .then()
                                .statusCode(200)
                                .extract().response();

        // Validate the updated book details
        response.then().body("price", equalTo(9.69f));
        response.then().body("name", equalTo("The Life-Changing Magic of Tidying Up"));
    }

}