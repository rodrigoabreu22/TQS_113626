package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WeatherServiceIT {

    @Autowired
    private WeatherService weatherService;

    private final long VALID_RESTAURANT_ID = 1L; // Must map to a real city with weatherId
    private final long INVALID_RESTAURANT_ID = 999L;

    @Test
    void whenValidRestaurantId_thenWeatherDataReturnedWithExpectedStructure() {
        Map<String, List<String>> result = weatherService.getRestaurantWeatherData(VALID_RESTAURANT_ID);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        assertEquals(5,result.size());

        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            String date = entry.getKey();
            List<String> forecast = entry.getValue();

            assertNotNull(date);
            assertEquals(4, forecast.size(), "Forecast should contain exactly 4 elements (tMin, tMax, precip, windDir)");

            forecast.forEach(value -> assertNotNull(value));
        }
    }

    @Test
    void whenInvalidRestaurantId_thenReturnsEmptyMap() {
        Map<String, List<String>> result = weatherService.getRestaurantWeatherData(INVALID_RESTAURANT_ID);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
