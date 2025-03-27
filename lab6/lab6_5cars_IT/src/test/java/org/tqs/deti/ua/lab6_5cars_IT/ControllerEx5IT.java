package org.tqs.deti.ua.lab6_5cars_IT;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
public class ControllerEx5IT {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @SuppressWarnings({ "rawtypes", "resource" })
    @Container
    @Order(1)
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12")
        .withUsername("rodrigo")
        .withPassword("pwd")
        .withDatabaseName("lab6_5test");

    @DynamicPropertySource
    @Order(2)
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Order(3)
    public void whenValidInput_thenCreateCar(){
        Car corsa = new Car("Opel", "Corsa");

        RestAssuredMockMvc.given()
            .contentType("application/json")
            .body(asJsonString(corsa))
        .when()
            .post("/api/cars")
        .then()
            .statusCode(201)
            .body("maker", equalTo("Opel"))
            .body("model", equalTo("Corsa"));
    }

    @Test
    @Order(4)
    public void givenCars_whenGetCars_thenStatus200(){
        RestAssuredMockMvc.given()
        .when()
            .get("/api/cars")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }
}
