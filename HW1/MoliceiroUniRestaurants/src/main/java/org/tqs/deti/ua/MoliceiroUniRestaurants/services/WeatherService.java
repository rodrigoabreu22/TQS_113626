package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.springframework.stereotype.Service;
import org.tqs.deti.ua.MoliceiroUniRestaurants.cache.CacheStatistics;
import org.tqs.deti.ua.MoliceiroUniRestaurants.cache.InMemoryCache;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.CityForecast;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.IpmaCityForecast;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.IpmaService;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private static final String BASE_URL = "https://api.ipma.pt/open-data/";

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final IpmaService service = retrofit.create(IpmaService.class);
    private final RestaurantService restaurantService;

    public WeatherService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    private final InMemoryCache<Long, HashMap<String, List<String>>> restaurantWeatherCache = new InMemoryCache<>();
    private final CacheStatistics restaurantWeatherCacheStatistics = new CacheStatistics();

    public HashMap<String, List<String>> getRestaurantWeatherData(long restaurantId) {
        try {

            HashMap<String, List<String>> restaurantWeather = restaurantWeatherCache.get(restaurantId);
            if (restaurantWeather != null) {
                log.info("Weather data retrieved from cache");
                log.info("Cache hit!");
                restaurantWeatherCacheStatistics.incrementHits();
                return restaurantWeather;
            }

            log.info("Cache miss!");

            restaurantWeatherCacheStatistics.incrementMisses();

            Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

            Call<IpmaCityForecast> call = service.getForecastForACity(restaurant.getWeatherId());
            Response<IpmaCityForecast> response = call.execute();

            if (!response.isSuccessful()) {
                log.error("API request failed with code: {} for city {}", response.code(), restaurant.getWeatherId());
                return new HashMap<>();
            }

            IpmaCityForecast forecast = response.body();
            if (forecast == null || forecast.getData() == null) {
                log.warn("No forecast data received for city {}", restaurant.getWeatherId());
                return new HashMap<>();
            }

            HashMap<String, List<String>> weatherData = new HashMap<>();
            for (CityForecast forecastDay : forecast.getData()) {
                List<String> dayData = new ArrayList<>();
                dayData.add(forecastDay.getTMin());
                dayData.add(forecastDay.getTMax());
                dayData.add(forecastDay.getPrecipitaProb());
                dayData.add(forecastDay.getPredWindDir());
                weatherData.put(forecastDay.getForecastDate(), dayData);
            }

            log.info("cache hit!");
            restaurantWeatherCache.put(restaurantId, weatherData);
            restaurantWeatherCacheStatistics.incrementPuts();

            return weatherData;
        } catch (Exception e) {
            log.error("Error fetching weather data for city: {}", e.getMessage());
            return new HashMap<>();
        }
    }

    public String getCacheStatistics(){
        return this.restaurantWeatherCacheStatistics.toString();
    }
}
