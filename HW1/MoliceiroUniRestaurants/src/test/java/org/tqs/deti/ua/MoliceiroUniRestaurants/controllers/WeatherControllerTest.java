package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.WeatherService;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
@AutoConfigureMockMvc(addFilters = false)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void getRestaurantWeatherData_ShouldReturnWeatherData() throws Exception {
        // Prepare mock data
        HashMap<String, List<String>> mockWeatherData = new HashMap<>();
        mockWeatherData.put("2023-06-15", List.of("15", "25", "30", "NE"));
        mockWeatherData.put("2023-06-16", List.of("16", "26", "40", "NW"));

        // Mock service call
        Mockito.when(weatherService.getRestaurantWeatherData(1L))
                .thenReturn(mockWeatherData);

        // Perform request and verify
        mockMvc.perform(get("/api/v1/weather/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("2023-06-15")))
                .andExpect(jsonPath("$", hasKey("2023-06-16")))
                .andExpect(jsonPath("$['2023-06-15']", hasSize(4)))
                .andExpect(jsonPath("$['2023-06-15'][0]", is("15"))) // TMin
                .andExpect(jsonPath("$['2023-06-15'][1]", is("25"))) // TMax
                .andExpect(jsonPath("$['2023-06-15'][2]", is("30"))) // PrecipProb
                .andExpect(jsonPath("$['2023-06-15'][3]", is("NE"))); // WindDir
    }

    @Test
    void getRestaurantWeatherData_ShouldReturnEmptyWhenNoData() throws Exception {
        Mockito.when(weatherService.getRestaurantWeatherData(2L))
                .thenReturn(new HashMap<>());

        mockMvc.perform(get("/api/v1/weather/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", anEmptyMap()));
    }

    @Test
    void getRestaurantWeatherData_ShouldHandleServiceException() throws Exception {
        Mockito.when(weatherService.getRestaurantWeatherData(3L))
                .thenThrow(new RuntimeException("Weather service unavailable"));

        mockMvc.perform(get("/api/v1/weather/3"))
                .andExpect(status().isServiceUnavailable()) // 503 instead of 500
                .andExpect(content().string(containsString("Error fetching weather data: Weather service unavailable")));
    }
}