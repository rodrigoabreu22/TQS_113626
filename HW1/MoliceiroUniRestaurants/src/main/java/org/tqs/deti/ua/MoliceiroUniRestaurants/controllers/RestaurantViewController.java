package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.RestaurantService;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.WeatherService;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantViewController {

    private final RestaurantService restaurantService;
    private final WeatherService weatherService;

    public RestaurantViewController(RestaurantService restaurantService, WeatherService weatherService) {
        this.restaurantService = restaurantService;
        this.weatherService = weatherService;
    }

    @GetMapping("")
    public String listRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "restaurants/list"; // Corresponds to src/main/resources/templates/restaurants/list.html
    }

    @GetMapping("/{id}")
    public String viewRestaurant(@PathVariable Long id, Model model) {

        // Get restaurant details
        Restaurant restaurant = restaurantService.getRestaurant(id);

        if (restaurant != null) {
            model.addAttribute("restaurant", restaurant);


            // Get weather data
            HashMap<String, List<String>> weatherData = weatherService.getRestaurantWeatherData(id);

            model.addAttribute("restaurant", restaurant);
            model.addAttribute("weatherData", weatherData);

            return "restaurants/details";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }// Template name
    }
}
