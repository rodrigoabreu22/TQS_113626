package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.RestaurantService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    private Restaurant validRestaurant;
    private Restaurant invalidRestaurant;
    private Meal testMeal;

    @BeforeEach
    void setUp() {
        validRestaurant = new Restaurant();
        validRestaurant.setId(1L);
        validRestaurant.setName("Test Restaurant");
        validRestaurant.setWeatherId(1010500);

        invalidRestaurant = new Restaurant(); // Missing required fields

        testMeal = new Meal();
        testMeal.setId(1L);
        testMeal.setName("Test Meal");
        validRestaurant.setMeals(Collections.singletonList(testMeal));
    }

    @Test
    void createRestaurant_WithValidData_ShouldReturnCreatedRestaurant() throws Exception {
        Mockito.when(restaurantService.createRestaurant(any(Restaurant.class)))
                .thenReturn(validRestaurant);

        mockMvc.perform(post("/api/v1/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRestaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) validRestaurant.getId())))
                .andExpect(jsonPath("$.name", is(validRestaurant.getName())))
                .andExpect(jsonPath("$.weatherId", is(validRestaurant.getWeatherId())));
    }

    @Test
    void createRestaurant_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRestaurant)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllRestaurants_ShouldReturnAllRestaurants() throws Exception {
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("Restaurant 2");
        restaurant2.setWeatherId(1010501);

        List<Restaurant> restaurants = Arrays.asList(validRestaurant, restaurant2);

        Mockito.when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        mockMvc.perform(get("/api/v1/restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) validRestaurant.getId())))
                .andExpect(jsonPath("$[1].id", is((int) restaurant2.getId())));
    }

    @Test
    void getRestaurant_WithExistingId_ShouldReturnRestaurant() throws Exception {
        Mockito.when(restaurantService.getRestaurant(validRestaurant.getId()))
                .thenReturn(validRestaurant);

        mockMvc.perform(get("/api/v1/restaurant/{id}", validRestaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) validRestaurant.getId())));
    }

    @Test
    void getRestaurant_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        Mockito.when(restaurantService.getRestaurant(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/restaurant/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRestaurant_WithValidData_ShouldReturnUpdatedRestaurant() throws Exception {
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setId(validRestaurant.getId());
        updatedRestaurant.setName("Updated Name");
        updatedRestaurant.setWeatherId(1010501);

        Mockito.when(restaurantService.updateRestaurant(any(Restaurant.class)))
                .thenReturn(updatedRestaurant);

        mockMvc.perform(put("/api/v1/restaurant/{id}", validRestaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRestaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedRestaurant.getName())))
                .andExpect(jsonPath("$.weatherId", is(updatedRestaurant.getWeatherId())));
    }

    @Test
    void deleteRestaurant_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurant/{id}", validRestaurant.getId()))
                .andExpect(status().isOk());

        Mockito.verify(restaurantService, Mockito.times(1))
                .deleteRestaurant(validRestaurant.getId());
    }

    @Test
    void getRestaurant_ShouldNotReturnMealsDueToJsonIgnore() throws Exception {
        Mockito.when(restaurantService.getRestaurant(validRestaurant.getId()))
                .thenReturn(validRestaurant);

        mockMvc.perform(get("/api/v1/restaurant/{id}", validRestaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meals").doesNotExist()); // Due to @JsonIgnore
    }
}