package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.MealRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {
    @Autowired
    MealRepository mealRepository;

    public Meal getMealById(long id) {
        return mealRepository.findById(id);
    }

    public List<Meal> getAllMeals() {
            return mealRepository.findAll();
    }

    public List<Meal> getMealsByRestaurantId(long id, LocalDate date) {
        if (date == null) {
            return mealRepository.findMealsByRestaurantId(id);
        }
        return mealRepository.findMealsByRestaurantIdAndDate(id, date);
    }

    public List<Meal> getWeeklyMealsByRestaurantId(long id) {
        List<Meal> meals = mealRepository.findMealsByRestaurantId(id);
        List<Meal> weeklyMeals = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(4);

        for (Meal meal : meals) {
            if (!meal.getDate().isBefore(today) && !meal.getDate().isAfter(endDate)) {
                weeklyMeals.add(meal);
            }
        }

        return weeklyMeals;
    }

    public Meal createMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public void deleteMeal(long id) {
        Meal meal = getMealById(id);
        mealRepository.delete(meal);
    }
}
