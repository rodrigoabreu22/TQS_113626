package org.tqs.deti.ua.MoliceiroUniRestaurants.ipma_forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IpmaService {
    @GET("forecast/meteorology/cities/daily/{globalIdLocal}.json")
    Call<IpmaCityForecast> getForecastForACity(@Path("globalIdLocal") int cityId);
}
