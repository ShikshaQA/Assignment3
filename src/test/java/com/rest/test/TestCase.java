package com.rest.test;


import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

@Test
public class TestCase {

    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    
    @Test
    public void testCreateBook() {
        String requestBody = "{\n" +
                "    \"name\": \"A to the Bodhisattva Way of Life\",\n" +
                "    \"author\": \"Santideva\",\n" +
                "    \"price\": 15.41\n" +
                "}";

        Response response = given()
                                .auth().basic("admin", "password")
                                .contentType("application/json")
                                .body(requestBody)
                            .when()
                                .post("http://localhost:8085/books")
                            .then()
                                .statusCode(201)
                                .extract().response();

        // Validate the response body
        response.then().body("name", equalTo("A to the Bodhisattva Way of Life"))
                .body("author", equalTo("Santideva"))
                .body("price", equalTo(15.41f));
    }
    
    @Test
    public void testGetBookById() {
        int bookId =2;

        Response response = given()
                                .auth().basic("admin", "password")
                                .contentType("application/json")
                            .when()
                                .get("http://localhost:8085/books/" + bookId)
                            .then()
                                .statusCode(200)
                                .extract().response();

        // Print for debugging
        System.out.println("Response: " + response.getBody().asString());
        // Validate the book details
        response.then().body("id", equalTo(bookId))
                .body("name", equalTo("The Life-Changing Magic of Tidying Up"))
                .body("author", equalTo("Marie Kondo"))
                .body("price", equalTo(9.69f));
    }


}
