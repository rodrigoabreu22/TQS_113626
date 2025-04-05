package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.RestaurantService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    private Restaurant createTestRestaurant(Long id, String name, int weatherId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setWeatherId(weatherId);
        return restaurant;
    }

    @Test
    void createRestaurant_ShouldReturnCreatedRestaurant() throws Exception {
        Restaurant newRestaurant = createTestRestaurant(1L, "New Restaurant", 1010500);

        Mockito.when(restaurantService.createRestaurant(Mockito.any(Restaurant.class)))
                .thenReturn(newRestaurant);

        mockMvc.perform(post("/api/v1/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRestaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Restaurant")))
                .andExpect(jsonPath("$.weatherId", is(1010500)));
    }

    @Test
    void createRestaurant_ShouldReturnBadRequest_WhenNameIsBlank() throws Exception {
        Restaurant invalidRestaurant = new Restaurant();
        invalidRestaurant.setName("");
        invalidRestaurant.setWeatherId(1010500);

        mockMvc.perform(post("/api/v1/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRestaurant)))
                .andExpect(status().isBadRequest())
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()));
    }

    @Test
    void getAllRestaurants_ShouldReturnAllRestaurants() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
                createTestRestaurant(1L, "Restaurant 1", 1010500),
                createTestRestaurant(2L, "Restaurant 2", 1010501)
        );

        Mockito.when(restaurantService.getAllRestaurants())
                .thenReturn(restaurants);

        mockMvc.perform(get("/api/v1/restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Restaurant 1")))
                .andExpect(jsonPath("$[1].name", is("Restaurant 2")));
    }

    @Test
    void getRestaurant_ShouldReturnRestaurant() throws Exception {
        Restaurant restaurant = createTestRestaurant(1L, "Test Restaurant", 1010500);

        Mockito.when(restaurantService.getRestaurant(1L))
                .thenReturn(restaurant);

        mockMvc.perform(get("/api/v1/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Restaurant")));
    }

    @Test
    void updateRestaurant_ShouldReturnUpdatedRestaurant() throws Exception {
        Restaurant updatedRestaurant = createTestRestaurant(1L, "Updated Restaurant", 1010500);

        Mockito.when(restaurantService.updateRestaurant(Mockito.any(Restaurant.class)))
                .thenReturn(updatedRestaurant);

        mockMvc.perform(put("/api/v1/restaurant/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRestaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Restaurant")));
    }

    @Test
    void deleteRestaurant_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(restaurantService).deleteRestaurant(1L);

        mockMvc.perform(delete("/api/v1/restaurant/1"))
                .andExpect(status().isOk());

        Mockito.verify(restaurantService, Mockito.times(1)).deleteRestaurant(1L);
    }
}