package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.MealService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealController.class)
class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MealService mealService;

    private Meal createValidMeal(Long id) {
        Meal meal = new Meal();
        meal.setId(id);
        meal.setName("Daily Special");
        meal.setType("Lunch");
        meal.setReservationLimit(50);
        meal.setDate(LocalDate.now());

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        meal.setRestaurant(restaurant);

        return meal;
    }

    @Test
    void addMeal_ShouldReturnCreatedMeal() throws Exception {
        Meal newMeal = createValidMeal(1L);

        Mockito.when(mealService.createMeal(Mockito.any(Meal.class)))
                .thenReturn(newMeal);

        mockMvc.perform(post("/api/v1/meal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMeal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Daily Special")))
                .andExpect(jsonPath("$.type", is("Lunch")))
                .andExpect(jsonPath("$.reservationLimit", is(50)))
                .andExpect(jsonPath("$.restaurant.id", is(1)));
    }

    @Test
    void addMeal_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        Meal invalidMeal = new Meal(); // Missing all required fields

        mockMvc.perform(post("/api/v1/meal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMeal)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllMeals_ShouldReturnAllMeals() throws Exception {
        Meal meal1 = createValidMeal(1L);
        Meal meal2 = createValidMeal(2L);
        meal2.setType("Dinner");

        List<Meal> meals = Arrays.asList(meal1, meal2);

        Mockito.when(mealService.getAllMeals())
                .thenReturn(meals);

        mockMvc.perform(get("/api/v1/meal/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type", is("Lunch")))
                .andExpect(jsonPath("$[1].type", is("Dinner")));
    }

    @Test
    void getMealById_ShouldReturnMeal() throws Exception {
        Meal meal = createValidMeal(1L);

        Mockito.when(mealService.getMealById(1L))
                .thenReturn(meal);

        mockMvc.perform(get("/api/v1/meal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Daily Special")));
    }

    @Test
    void getMealsByRestaurantId_WithDate_ShouldReturnFilteredMeals() throws Exception {
        Meal meal = createValidMeal(1L);
        LocalDate testDate = LocalDate.of(2023, 6, 15);
        meal.setDate(testDate);

        Mockito.when(mealService.getMealsByRestaurantId(1L, testDate))
                .thenReturn(List.of(meal));

        mockMvc.perform(get("/api/v1/meal/restaurant/1?date=2023-06-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is("2023-06-15")));
    }

    @Test
    void updateMeal_ShouldReturnUpdatedMeal() throws Exception {
        Meal updatedMeal = createValidMeal(1L);
        updatedMeal.setName("Updated Special");
        updatedMeal.setReservationLimit(60);

        Mockito.when(mealService.updateMeal(Mockito.any(Meal.class)))
                .thenReturn(updatedMeal);

        mockMvc.perform(put("/api/v1/meal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMeal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Special")))
                .andExpect(jsonPath("$.reservationLimit", is(60)));
    }

    @Test
    void deleteMeal_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(mealService).deleteMeal(1L);

        mockMvc.perform(delete("/api/v1/meal/1"))
                .andExpect(status().isOk());

        Mockito.verify(mealService, Mockito.times(1)).deleteMeal(1L);
    }

    // Additional test for weekly meals
    @Test
    void getWeeklyMeals_ShouldReturnMealsForWeek() throws Exception {
        Meal meal1 = createValidMeal(1L);
        Meal meal2 = createValidMeal(2L);
        meal2.setDate(LocalDate.now().plusDays(3));

        Mockito.when(mealService.getWeeklyMealsByRestaurantId(1L))
                .thenReturn(List.of(meal1, meal2));

        mockMvc.perform(get("/api/v1/meal/restaurant/1/week"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}