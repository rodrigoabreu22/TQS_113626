package org.tqs.deti.ua.lab3_2cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;


@WebMvcTest(CarController.class)
class ControllerTests {

	@Autowired
	private MockMvc mvc;

	@SuppressWarnings("removal")
	@MockBean
	private CarManagerService service;

	@Test
	void whenPostCar_thenCreateCar() throws Exception {
	    Car car = new Car("Toyota", "Corolla");

	    when(service.save(any(Car.class))).thenReturn(car);

	    mvc.perform(post("/api/cars")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(car)))
	        .andExpect(status().isCreated())
	        .andExpect(jsonPath("$.maker").value("Toyota"))
	        .andExpect(jsonPath("$.model").value("Corolla"));

	    verify(service, times(1)).save(any(Car.class));
	}

	@Test
	void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
		Car car1 = new Car("Toyota", "Corolla");
		Car car2 = new Car("Toyota", "Supra");
		Car car3 = new Car("Honda", "Civic");

		List<Car> allCars = Arrays.asList(car1, car2, car3);

		when(service.getAllCars()).thenReturn(allCars);

		mvc.perform(
				get("/api/cars").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].model",is(car1.getModel())))
				.andExpect(jsonPath("$[1].model",is(car2.getModel())))
				.andExpect(jsonPath("$[2].model",is(car3.getModel())));

		verify(service, times(1)).getAllCars();
	}

	@Test 
	void givenId_whenGetCar_thenReturnCorrespondentCar() throws Exception {
		Car car2 = new Car("Toyota", "Supra");

		when(service.getCarDetails(car2.getCarId())).thenReturn(Optional.of(car2));

		mvc.perform(
				get("/api/cars/{id}", car2.getCarId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.maker",is(car2.getMaker())))
				.andExpect(jsonPath("$.model",is(car2.getModel())));

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

		mvc.perform(
				get("/api/cars/replace").contentType(MediaType.APPLICATION_JSON).content(asJsonString(car1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.maker",is(car2.getMaker())))
				.andExpect(jsonPath("$.model",is(car2.getModel())));

		verify(service, times(1)).getCarReplacement(car1);
	}

}
