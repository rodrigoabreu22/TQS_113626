package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(long id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurantRepository.findByName(name);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(long id) {
        Restaurant restaurant = restaurantRepository.findById(id);
        if (restaurant != null) {
            restaurantRepository.delete(restaurant);
        }
    }
}
