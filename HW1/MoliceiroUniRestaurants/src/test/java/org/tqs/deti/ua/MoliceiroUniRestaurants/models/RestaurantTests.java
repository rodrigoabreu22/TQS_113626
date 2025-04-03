package org.tqs.deti.ua.MoliceiroUniRestaurants.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests {

    private Restaurant restaurant;
    private final long TEST_ID = 1L;
    private final String TEST_NAME = "Test Restaurant";
    private final int TEST_WEATHER_ID = 123;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(TEST_ID);
        restaurant.setName(TEST_NAME);
        restaurant.setWeatherId(TEST_WEATHER_ID);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(TEST_ID, restaurant.getId());
        assertEquals(TEST_NAME, restaurant.getName());
        assertEquals(TEST_WEATHER_ID, restaurant.getWeatherId());
    }

    @Test
    void testMealsListInitialization() {
        assertNotNull(restaurant.getMeals());
        assertEquals(0, restaurant.getMeals().size());
    }

    @Test
    void testSetMeals() {
        List<Meal> meals = new ArrayList<>();
        Meal meal1 = new Meal();
        meal1.setName("Burger");
        Meal meal2 = new Meal();
        meal2.setName("Pizza");
        meals.add(meal1);
        meals.add(meal2);

        restaurant.setMeals(meals);

        assertEquals(2, restaurant.getMeals().size());
        assertEquals("Burger", restaurant.getMeals().get(0).getName());
        assertEquals("Pizza", restaurant.getMeals().get(1).getName());
    }

    @Test
    void testToString() {
        String expectedString = String.format(
                "Restaurant{id=%d, name='%s', weatherId=%d, meals=%s}",
                TEST_ID, TEST_NAME, TEST_WEATHER_ID, new ArrayList<>()
        );

        assertEquals(expectedString, restaurant.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Restaurant sameRestaurant = new Restaurant();
        sameRestaurant.setId(TEST_ID);
        sameRestaurant.setName(TEST_NAME);
        sameRestaurant.setWeatherId(TEST_WEATHER_ID);

        Restaurant differentRestaurant = new Restaurant();
        differentRestaurant.setId(2L);
        differentRestaurant.setName("Different Restaurant");
        differentRestaurant.setWeatherId(456);

        assertEquals(restaurant, sameRestaurant);
        assertEquals(restaurant.hashCode(), sameRestaurant.hashCode());
        assertNotEquals(restaurant, differentRestaurant);
        assertNotEquals(restaurant.hashCode(), differentRestaurant.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        Restaurant newRestaurant = new Restaurant();
        assertNotNull(newRestaurant);
        assertEquals(0L, newRestaurant.getId());
        assertNull(newRestaurant.getName());
        assertEquals(0, newRestaurant.getWeatherId());
        assertNotNull(newRestaurant.getMeals());
    }
}