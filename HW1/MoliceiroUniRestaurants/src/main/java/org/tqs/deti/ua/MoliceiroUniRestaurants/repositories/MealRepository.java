package org.tqs.deti.ua.MoliceiroUniRestaurants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    Meal findById(long id);
    List<Meal> findMealsByRestaurantId(long restaurantId);
    List<Meal> findMealsByRestaurantIdAndDate(long restaurantId, LocalDate date);
}
