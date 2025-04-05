package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.springframework.stereotype.Service;
import org.tqs.deti.ua.MoliceiroUniRestaurants.cache.CacheStatistics;
import org.tqs.deti.ua.MoliceiroUniRestaurants.cache.InMemoryCache;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.IpmaCityForecast;
import org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast.IpmaService;
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

    private static final String BASE_URL = "http://api.ipma.pt/open-data/";

    private final InMemoryCache<Long, HashMap<String, List<String>>> restaurantWeatherCache = new InMemoryCache<>();
    private final CacheStatistics restaurantWeatherCacheStatistics = new CacheStatistics();

    // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // create a typed interface to use the remote API (a client)
    IpmaService service = retrofit.create(IpmaService.class);

    public HashMap<String, List<String>> getRestaurantWeatherData(long restaurantId) {
        HashMap<String, List<String>> restaurantWeather = restaurantWeatherCache.get(restaurantId);
        if (restaurantWeather != null) {
            log.info("Weather data retrieved from cache");
            log.info("Cache hit!");
            restaurantWeatherCacheStatistics.incrementHits();
            return restaurantWeather;
        }

        log.info("Cache miss!");

        restaurantWeatherCacheStatistics.incrementMisses();

        Call<IpmaCityForecast> callSync = service.getForecastForACity((int) restaurantId);
        HashMap<String, List<String>> weatherData = new HashMap<>();

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var weather_days = forecast.getData().listIterator();
                while (weather_days.hasNext()) {
                    var firstDay = weather_days.next();

                    List<String> weatherDataValues = new ArrayList<>();
                    weatherDataValues.add(firstDay.getTMin());
                    weatherDataValues.add(firstDay.getTMax());
                    weatherDataValues.add(firstDay.getPrecipitaProb());
                    weatherDataValues.add(firstDay.getPredWindDir());

                    weatherData.put(firstDay.getForecastDate(), weatherDataValues);
                }
            } else {
                System.out.println( "No results for this request!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log.info("cache hit!");
        restaurantWeatherCache.put(restaurantId, weatherData);
        restaurantWeatherCacheStatistics.incrementPuts();

        return weatherData;
    }
}
