package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Operation(summary = "Create a new restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid restaurant data provided")
    })
    @PostMapping("")
    public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of all restaurants retrieved")
    })
    @GetMapping("")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @Operation(summary = "Get a restaurant by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant found"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurant(@PathVariable long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @Operation(summary = "Update an existing restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable(value="id") Long id, @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(restaurant);
    }

    @Operation(summary = "Delete a restaurant by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable(value="id") Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
