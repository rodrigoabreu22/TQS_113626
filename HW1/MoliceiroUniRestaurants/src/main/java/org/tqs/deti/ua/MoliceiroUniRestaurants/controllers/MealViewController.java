package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.MealService;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.RestaurantService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/restaurants/{id}/meals")
public class MealViewController {

    private final MealService mealService;
    private final RestaurantService restaurantService;

    @Autowired
    public MealViewController(MealService mealService, RestaurantService restaurantService) {
        this.mealService = mealService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String getMealsPage(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
        }

        List<Meal> meals = mealService.getWeeklyMealsByRestaurantId(id);

        // Group meals by date and type
        Map<LocalDate, Map<String, List<Meal>>> mealsByDateAndType = meals.stream()
                .collect(Collectors.groupingBy(
                        Meal::getDate,
                        Collectors.groupingBy(Meal::getType)
                ));

        // Sort the dates in ascending order (soonest date first)
        Map<LocalDate, Map<String, List<Meal>>> sortedMealsByDateAndType = mealsByDateAndType.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Sort by date
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // In case of duplicate keys, keep the first one (won't happen here)
                        LinkedHashMap::new // To maintain insertion order after sorting
                ));

        // Ensure that mealsByDateAndType is never null (initialize it with an empty map if it's empty)
        if (sortedMealsByDateAndType == null) {
            sortedMealsByDateAndType = new HashMap<>();
        }

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("mealsByDateAndType", sortedMealsByDateAndType);

        return "meals/list";
    }
}
