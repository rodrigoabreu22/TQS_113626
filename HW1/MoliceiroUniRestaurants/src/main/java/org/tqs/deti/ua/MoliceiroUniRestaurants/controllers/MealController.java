package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tqs.deti.ua.MoliceiroUniRestaurants.dto.MealDTO;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.MealService;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/v1/meal")
public class MealController {

    @Autowired
    private MealService mealService;

    @Operation(summary = "Create a new meal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meal created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request - Restaurant ID is mandatory")
    })
    @PostMapping("")
    public Meal addMeal(@Valid @RequestBody MealDTO mealDto) {
        if (mealDto.getRestaurantId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant ID is mandatory");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setId(mealDto.getRestaurantId());

        Meal meal = new Meal();
        meal.setName(mealDto.getName());
        meal.setType(mealDto.getType());
        meal.setReservationLimit(mealDto.getReservationLimit());
        meal.setDate(mealDto.getDate());
        meal.setRestaurant(restaurant);

        return mealService.createMeal(meal);
    }

    @Operation(summary = "Get all meals")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of all meals retrieved")
    })
    @GetMapping("/all")
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @Operation(summary = "Get meal by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meal found"),
        @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    @GetMapping("/{id}")
    public Meal getMealById(@PathVariable(value = "id") Long id) {
        return mealService.getMealById(id);
    }

    @Operation(summary = "Get meals by restaurant ID (optional filter by date)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meals retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant or meals not found")
    })
    @GetMapping("/restaurant/{id}")
    public List<Meal> getMealsByRestaurantId(@PathVariable(value = "id") Long restaurantId, @RequestParam(required = false) LocalDate date) {
        return mealService.getMealsByRestaurantId(restaurantId, date);
    }

    @Operation(summary = "Get weekly meals by restaurant ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Weekly meals retrieved successfully")
    })
    @GetMapping("/restaurant/{id}/week")
    public List<Meal> getMealsByWeek(@PathVariable(value = "id") Long restaurantId) {
        return mealService.getWeeklyMealsByRestaurantId(restaurantId);
    }

    @Operation(summary = "Update an existing meal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meal updated successfully"),
        @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    @PutMapping("/{id}")
    public Meal updateMeal(@PathVariable(value = "id") Long id, @RequestBody Meal meal) {
        return mealService.updateMeal(meal);
    }

    @Operation(summary = "Delete a meal by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meal deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable(value = "id") Long id) {
        mealService.deleteMeal(id);
    }
}

