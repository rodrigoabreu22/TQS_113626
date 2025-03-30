package org.tqs.deti.ua.MoliceiroUniRestaurants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Restaurant findByName(String name);
    Restaurant findById(long id);
}
