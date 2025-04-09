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

@RestController
@RequestMapping("/api/v1/meal")
public class MealController {

    @Autowired
    private MealService mealService;

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


    @GetMapping("/all")
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{id}")
    public Meal getMealById(@PathVariable(value = "id") Long id) {
        return mealService.getMealById(id);
    }

    @GetMapping("/restaurant/{id}")
    public List<Meal> getMealsByRestaurantId(@PathVariable(value = "id") Long restaurantId, @RequestParam(required = false) LocalDate date) {
        return mealService.getMealsByRestaurantId(restaurantId, date);
    }

    @GetMapping("/restaurant/{id}/week")
    public List<Meal> getMealsByWeek(@PathVariable(value = "id") Long restaurantId) {
        return mealService.getWeeklyMealsByRestaurantId(restaurantId);
    }

    @PutMapping("/{id}")
    public Meal updateMeal(@PathVariable(value = "id") Long id, @RequestBody Meal meal) {
        return mealService.updateMeal(meal);
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable(value = "id") Long id) {
        mealService.deleteMeal(id);
    }
}
