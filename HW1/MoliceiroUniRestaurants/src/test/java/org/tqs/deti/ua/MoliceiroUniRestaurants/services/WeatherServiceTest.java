package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.IpmaCityForecast;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.IpmaService;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.CityForecast;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WeatherServiceTest {

    @Mock
    private Retrofit retrofit;

    @Mock
    private IpmaService ipmaService;

    @Mock
    private Call<IpmaCityForecast> call;

    @InjectMocks
    private WeatherService weatherService;

    private IpmaCityForecast mockForecast;
    private CityForecast mockWeather;

    @BeforeEach
    void setUp() {
        // Mock the service creation
        when(retrofit.create(IpmaService.class)).thenReturn(ipmaService);
        when(ipmaService.getForecastForACity(anyInt())).thenReturn(call);

        // Setup mock weather data
        mockWeather = new CityForecast();
        mockWeather.setForecastDate("2023-05-01");
        mockWeather.setTMin("10");
        mockWeather.setTMax("20");
        mockWeather.setPrecipitaProb("30");
        mockWeather.setPredWindDir("NE");

        mockForecast = new IpmaCityForecast();
        List<CityForecast> weatherList = new ArrayList<>();
        weatherList.add(mockWeather);
        mockForecast.setData(weatherList);
    }

    @Test
    void getRestaurantWeatherData_Success() throws IOException {
        // Arrange
        when(call.execute()).thenReturn(Response.success(mockForecast));

        // Act
        HashMap<String, List<String>> result = weatherService.getRestaurantWeatherData(1010500L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("2023-05-01"));

        List<String> weatherValues = result.get("2023-05-01");
        assertEquals("10", weatherValues.get(0)); // TMin
        assertEquals("20", weatherValues.get(1)); // TMax
        assertEquals("30", weatherValues.get(2)); // PrecipitaProb
        assertEquals("NE", weatherValues.get(3)); // PredWindDir

        verify(ipmaService).getForecastForACity(1010500);
    }

    @Test
    void getRestaurantWeatherData_EmptyResponse() throws IOException {
        // Arrange
        when(call.execute()).thenReturn(Response.success(null));

        // Act
        HashMap<String, List<String>> result = weatherService.getRestaurantWeatherData(1010500L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getRestaurantWeatherData_ApiFailure() throws IOException {
        // Arrange
        when(call.execute()).thenReturn(Response.error(500, okhttp3.ResponseBody.create(null, "Server error")));

        // Act
        HashMap<String, List<String>> result = weatherService.getRestaurantWeatherData(1010500L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getRestaurantWeatherData_IOException() throws IOException {
        // Arrange
        when(call.execute()).thenThrow(new IOException("Network error"));

        // Act
        HashMap<String, List<String>> result = weatherService.getRestaurantWeatherData(1010500L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getRestaurantWeatherData_MultipleDays() throws IOException {
        // Arrange
        CityForecast secondDay = new CityForecast();
        secondDay.setForecastDate("2023-05-02");
        secondDay.setTMin("12");
        secondDay.setTMax("22");
        secondDay.setPrecipitaProb("40");
        secondDay.setPredWindDir("NW");

        List<CityForecast> weatherList = new ArrayList<>();
        weatherList.add(mockWeather);
        weatherList.add(secondDay);
        mockForecast.setData(weatherList);

        when(call.execute()).thenReturn(Response.success(mockForecast));

        // Act
        HashMap<String, List<String>> result = weatherService.getRestaurantWeatherData(1010500L);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.containsKey("2023-05-01"));
        assertTrue(result.containsKey("2023-05-02"));
    }
}
