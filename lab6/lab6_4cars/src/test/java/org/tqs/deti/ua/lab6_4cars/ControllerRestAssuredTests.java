package org.tqs.deti.ua.lab6_4cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tqs.deti.ua.lab6_4cars.Car;
import org.tqs.deti.ua.lab6_4cars.CarController;
import org.tqs.deti.ua.lab6_4cars.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import java.util.Optional;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


@WebMvcTest(CarController.class)
class ControllerRestAssuredTests {

	@Autowired
	private MockMvc mvc;

	@SuppressWarnings("removal")
	@MockBean
	private CarManagerService service;

	@BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

	@Test
	void whenPostCar_thenCreateCar() throws Exception {
	    Car car = new Car("Toyota", "Corolla");

	    when(service.save(any(Car.class))).thenReturn(car);

	    RestAssuredMockMvc.given()
		    .contentType(MediaType.APPLICATION_JSON)
		    .body(asJsonString(car))
		.when()
		    .post("/api/cars")
		.then()
		    .statusCode(201)
		    .body("maker", equalTo("Toyota"))
		    .body("model", equalTo("Corolla"));

	    verify(service, times(1)).save(any(Car.class));
	}

	@Test
	void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
		Car car1 = new Car("Toyota", "Corolla");
		Car car2 = new Car("Toyota", "Supra");
		Car car3 = new Car("Honda", "Civic");

		List<Car> allCars = Arrays.asList(car1, car2, car3);

		when(service.getAllCars()).thenReturn(allCars);

		RestAssuredMockMvc.given().contentType(MediaType.APPLICATION_JSON)
		.mockMvc(mvc).when().get("/api/cars")
		.then().statusCode(200)
		.body("size()", is(3))
    	.body("[0].model", is(car1.getModel()))
    	.body("[1].model", is(car2.getModel()))
    	.body("[2].model", is(car3.getModel()));

		verify(service, times(1)).getAllCars();
	}

	@Test 
	void givenId_whenGetCar_thenReturnCorrespondentCar() throws Exception {
		Car car2 = new Car("Toyota", "Supra");

		when(service.getCarDetails(car2.getCarId())).thenReturn(Optional.of(car2));


		RestAssuredMockMvc.given().contentType(MediaType.APPLICATION_JSON)
		.mockMvc(mvc).when().get("/api/cars/"+car2.getCarId())
		.then().statusCode(200)
		.body("maker", is(car2.getMaker()))
		.body("model", is(car2.getModel()));

		verify(service, times(1)).getCarDetails(car2.getCarId());
	}


	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//d)
	@Test
	void whenGetValidReplacement_thenReturnValidReplacement() throws Exception {
		Car car1 = new Car("Toyota", "Corolla");
		Car car2 = new Car("Toyota", "Supra");

		when(service.getCarReplacement(car1)).thenReturn(Optional.of(car2));

		RestAssuredMockMvc.given()
		    .contentType(MediaType.APPLICATION_JSON)
		    .body(asJsonString(car1))
		.when()
		    .get("/api/cars/replace")
		.then()
		    .statusCode(200)
		    .body("maker", is(car2.getMaker()))
		    .body("model", is(car2.getModel()));

		verify(service, times(1)).getCarReplacement(car1);
	}

}
