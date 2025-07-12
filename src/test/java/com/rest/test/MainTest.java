 package com.test;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MainTest {

    @Test
    public void testBookPricesGreaterThanZero() {
        Response response = given()
                .auth().basic("user", "password")
                .when()
                .get("http://localhost:8085/books")
                .then()
                .statusCode(200)
                .extract().response();

        List<Float> prices = response.jsonPath().getList("price", Float.class);
        for (Float price : prices) {
            assert price > 0 : "Price is not greater than zero: " + price;
        }
    }

    @Test
    public void testBookListIsNotEmpty() {
        given()
                .auth().basic("user", "password")
                .when()
                .get("http://localhost:8085/books")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
