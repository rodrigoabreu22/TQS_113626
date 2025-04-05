package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getRestaurantWeatherData(@PathVariable(value="id") Long id) {
        try {
            HashMap<String, List<String>> weatherData = weatherService.getRestaurantWeatherData(id);
            return ResponseEntity.ok(weatherData);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Error fetching weather data: " + e.getMessage());
        }
    }

}
