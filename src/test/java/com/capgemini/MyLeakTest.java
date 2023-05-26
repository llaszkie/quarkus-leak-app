package com.capgemini;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class MyLeakTest {

    @Test
    public void testHelloEndpoint() {
        given().when().get("/").then().statusCode(200).body(containsString("Resources leaked!"));
    }

}