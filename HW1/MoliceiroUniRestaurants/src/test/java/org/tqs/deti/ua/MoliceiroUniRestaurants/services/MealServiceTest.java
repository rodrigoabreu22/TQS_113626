package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.MealRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    private Meal meal1;
    private Meal meal2;
    private Meal meal3;
    private Restaurant restaurant;
    private final long MEAL1_ID = 1L;
    private final long MEAL2_ID = 2L;
    private final long RESTAURANT_ID = 10L;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate TOMORROW = TODAY.plusDays(1);
    private final LocalDate NEXT_WEEK = TODAY.plusDays(7);

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurant.setName("Test Restaurant");

        meal1 = new Meal();
        meal1.setId(MEAL1_ID);
        meal1.setName("Lunch Special");
        meal1.setDate(TODAY);
        meal1.setRestaurant(restaurant);

        meal2 = new Meal();
        meal2.setId(MEAL2_ID);
        meal2.setName("Dinner Special");
        meal2.setDate(TOMORROW);
        meal2.setRestaurant(restaurant);

        meal3 = new Meal();
        meal3.setId(3L);
        meal3.setName("Future Meal");
        meal3.setDate(NEXT_WEEK);
        meal3.setRestaurant(restaurant);
    }

    @Test
    void getMealById_ShouldReturnMeal() {
        when(mealRepository.findById(MEAL1_ID)).thenReturn(meal1);

        Meal found = mealService.getMealById(MEAL1_ID);

        assertNotNull(found);
        assertEquals(MEAL1_ID, found.getId());
        verify(mealRepository, times(1)).findById(MEAL1_ID);
    }

    @Test
    void getMealById_WithInvalidId_ShouldReturnNull() {
        when(mealRepository.findById(999L)).thenReturn(null);

        Meal found = mealService.getMealById(999L);

        assertNull(found);
        verify(mealRepository, times(1)).findById(999L);
    }

    @Test
    void getAllMeals_ShouldReturnAllMeals() {
        when(mealRepository.findAll()).thenReturn(Arrays.asList(meal1, meal2));

        List<Meal> meals = mealService.getAllMeals();

        assertEquals(2, meals.size());
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void getMealsByRestaurantId_WithDate_ShouldReturnFilteredMeals() {
        when(mealRepository.findMealsByRestaurantIdAndDate(RESTAURANT_ID, TODAY))
                .thenReturn(Arrays.asList(meal1));

        List<Meal> meals = mealService.getMealsByRestaurantId(RESTAURANT_ID, TODAY);

        assertEquals(1, meals.size());
        assertEquals(MEAL1_ID, meals.get(0).getId());
        verify(mealRepository, times(1)).findMealsByRestaurantIdAndDate(RESTAURANT_ID, TODAY);
        verify(mealRepository, never()).findMealsByRestaurantId(anyLong());
    }

    @Test
    void getMealsByRestaurantId_WithoutDate_ShouldReturnAllRestaurantMeals() {
        when(mealRepository.findMealsByRestaurantId(RESTAURANT_ID))
                .thenReturn(Arrays.asList(meal1, meal2));

        List<Meal> meals = mealService.getMealsByRestaurantId(RESTAURANT_ID, null);

        assertEquals(2, meals.size());
        verify(mealRepository, times(1)).findMealsByRestaurantId(RESTAURANT_ID);
        verify(mealRepository, never()).findMealsByRestaurantIdAndDate(anyLong(), any());
    }

    @Test
    void getWeeklyMealsByRestaurantId_ShouldReturnCurrentWeekMeals() {
        when(mealRepository.findMealsByRestaurantId(RESTAURANT_ID))
                .thenReturn(Arrays.asList(meal1, meal2, meal3));

        List<Meal> weeklyMeals = mealService.getWeeklyMealsByRestaurantId(RESTAURANT_ID);

        assertEquals(2, weeklyMeals.size());
        assertTrue(weeklyMeals.stream().anyMatch(m -> m.getId() == MEAL1_ID));
        assertTrue(weeklyMeals.stream().anyMatch(m -> m.getId() == MEAL2_ID));
        assertFalse(weeklyMeals.stream().anyMatch(m -> m.getId() == 3L));
        verify(mealRepository, times(1)).findMealsByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void createMeal_ShouldSaveAndReturnMeal() {
        when(mealRepository.save(any(Meal.class))).thenReturn(meal1);

        Meal created = mealService.createMeal(meal1);

        assertNotNull(created);
        assertEquals(MEAL1_ID, created.getId());
        verify(mealRepository, times(1)).save(meal1);
    }

    @Test
    void updateMeal_ShouldSaveAndReturnUpdatedMeal() {
        when(mealRepository.save(any(Meal.class))).thenReturn(meal1);
        meal1.setName("Updated Name");

        Meal updated = mealService.updateMeal(meal1);

        assertNotNull(updated);
        assertEquals("Updated Name", updated.getName());
        verify(mealRepository, times(1)).save(meal1);
    }

    @Test
    void deleteMeal_WithValidId_ShouldDeleteMeal() {
        when(mealRepository.findById(MEAL1_ID)).thenReturn(meal1);
        doNothing().when(mealRepository).delete(meal1);

        mealService.deleteMeal(MEAL1_ID);

        verify(mealRepository, times(1)).findById(MEAL1_ID);
        verify(mealRepository, times(1)).delete(meal1);
    }

    @Test
    void deleteMeal_WithInvalidId_ShouldNotThrowException() {
        when(mealRepository.findById(999L)).thenReturn(null);

        assertDoesNotThrow(() -> mealService.deleteMeal(999L));
        verify(mealRepository, never()).delete(any());
    }

    @Test
    void deleteMeal_WithNullMeal_ShouldNotCallDelete() {
        // Arrange
        when(mealRepository.findById(anyLong())).thenReturn(null);

        // Act
        mealService.deleteMeal(999L);

        // Assert
        verify(mealRepository, never()).delete(any());
    }

    @Test
    void deleteMeal_WithValidId_ShouldCallDelete() {
        // Arrange
        when(mealRepository.findById(MEAL1_ID)).thenReturn(meal1);
        doNothing().when(mealRepository).delete(meal1);

        // Act
        mealService.deleteMeal(MEAL1_ID);

        // Assert
        verify(mealRepository, times(1)).delete(meal1);
    }
}