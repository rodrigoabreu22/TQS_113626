package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.WeatherService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{id}")
    public HashMap<String, List<String>> getRestaurantWeatherData(@PathVariable(value="id") Long id) {
        return weatherService.getRestaurantWeatherData(id);
    }

}
