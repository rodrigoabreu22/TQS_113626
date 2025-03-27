package org.tqs.deti.ua;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
//import static io.restassured.module.jsv.JsonSchemaValidator.*;
//import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import java.util.concurrent.TimeUnit;

public class ListAllToDosEndpointTest {
    private static String baseUrl = "https://jsonplaceholder.typicode.com/";

    @Test 
    public void todos_endpoint_returns_200() {
        when().get(baseUrl+"todos").then().statusCode(200);
    }

    @Test
    public void todos4Test(){
        when().get(baseUrl+"todos/4").then().body("title",is("et porro tempora"));
    }

    @Test
    public void todos_endpoint_get_ids_198_and_199(){
        when().get(baseUrl+"todos").then().body("id", hasItems(198,199));
    }

    @Test
    public void todos_endpoint_results_less_then_2_seconds(){
        when().get(baseUrl+"todos").then().time(lessThan(2L), TimeUnit.SECONDS);
    }
}
