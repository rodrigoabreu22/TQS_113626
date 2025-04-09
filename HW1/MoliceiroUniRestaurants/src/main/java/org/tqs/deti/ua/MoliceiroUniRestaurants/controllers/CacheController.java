package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.WeatherService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/v1/cache")
public class CacheController {

    private WeatherService weatherApi;
    private static final Logger log = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    public CacheController(WeatherService weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Operation(summary = "Get weather API cache statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cache statistics retrieved successfully")
    })
    @GetMapping("/statistics")
    public String getCacheStatistics() {
        log.info("GET /cache/statistics");
        return weatherApi.getCacheStatistics();
    }
}

