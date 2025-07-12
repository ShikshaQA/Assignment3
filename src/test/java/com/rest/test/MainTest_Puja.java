package com.rest.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test
public class MainTest {

    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    @Test
    public void verifyStatusCode() {

        // Base URL of the API
        RestAssured.baseURI = "http://localhost:8085/books";

        // Username and password for Basic Authentication
        String username = "user"; // Replace with the correct username
        String password = "password"; // Replace with the correct password

        // Create the request specification
        RequestSpecification requestSpecification = given()
                .auth().preemptive().basic(username, password) // Use preemptive basic auth
                .log().all(); // Log all request details (headers, body, etc.)

        // Send GET request and get the response
        Response response = requestSpecification.get();

        // Print the response details for debugging
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.prettyPrint());
        System.out.println("Response Headers: " + response.getHeaders());

        // Perform validation on the response
        ValidatableResponse validatableResponse = response.then();

        /* Validate status code */
        validatableResponse.statusCode(200);

        // Validate status line
        validatableResponse.statusLine("HTTP/1.1 200 ");
    }
    
    @Test
    public void testCreateBookWithMissingFields() {
        String invalidRequest = "{ \"name\": \"Test Book\" }"; // Missing author & price

        given()
            .auth().basic("admin", "password")
            .contentType("application/json")
            .body(invalidRequest)
        .when()
            .post("http://localhost:8085/books")
        .then()
            .statusCode(400); // Expecting Bad Request or custom error
    }

    @Test
    public void testUnauthorizedAccess() {
        given()
            .auth().basic("wrongUser", "wrongPassword")
            .when()
            .get("http://localhost:8085/books")
            .then()
            .statusCode(401); // Unauthorized
    }

}