package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

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
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.WeatherService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

@Controller
@RequestMapping("/restaurants")
public class RestaurantViewController {

    private final RestaurantService restaurantService;
    private final WeatherService weatherService;
    private final MealService mealService;

    public RestaurantViewController(RestaurantService restaurantService,
                                    WeatherService weatherService,
                                    MealService mealService) {
        this.restaurantService = restaurantService;
        this.weatherService = weatherService;
        this.mealService = mealService;
    }

    @GetMapping("")
    public String listRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "restaurant-list";
    }

    @GetMapping("/{id}")
    public String viewRestaurantWithMeals(@PathVariable Long id, Model model) {
        // Get restaurant details
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Get weather data
        HashMap<String, List<String>> weatherData = weatherService.getRestaurantWeatherData(id);

        // Get and organize meals data
        List<Meal> meals = mealService.getWeeklyMealsByRestaurantId(id);
        Map<LocalDate, Map<String, List<Meal>>> mealsByDateAndType = meals.stream()
                .collect(Collectors.groupingBy(
                        Meal::getDate,
                        Collectors.groupingBy(Meal::getType)
                ));

        // Sort meals by date
        Map<LocalDate, Map<String, List<Meal>>> sortedMeals = mealsByDateAndType.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        // Add all attributes to model
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("weatherData", weatherData);
        model.addAttribute("mealsByDateAndType", sortedMeals);

        return "restaurant-details"; // Single combined template
    }
}
