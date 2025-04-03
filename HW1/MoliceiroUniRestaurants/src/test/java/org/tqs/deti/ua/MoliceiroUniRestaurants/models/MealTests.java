package org.tqs.deti.ua.MoliceiroUniRestaurants.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MealTests {

    private Meal meal;
    private Restaurant mockRestaurant;
    private final long TEST_ID = 1L;
    private final String TEST_NAME = "Test Meal";
    private final String TEST_TYPE = "lunch";
    private final int TEST_RESERVATION_LIMIT = 50;
    private final LocalDate TEST_DATE = LocalDate.of(2023, 12, 25);

    @BeforeEach
    void setUp() {
        mockRestaurant = Mockito.mock(Restaurant.class);
        when(mockRestaurant.getId()).thenReturn(1L);
        when(mockRestaurant.getName()).thenReturn("Test Restaurant");

        meal = new Meal();
        meal.setId(TEST_ID);
        meal.setName(TEST_NAME);
        meal.setType(TEST_TYPE);
        meal.setReservationLimit(TEST_RESERVATION_LIMIT);
        meal.setDate(TEST_DATE);
        meal.setRestaurant(mockRestaurant);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(TEST_ID, meal.getId());
        assertEquals(TEST_NAME, meal.getName());
        assertEquals(TEST_TYPE, meal.getType());
        assertEquals(TEST_RESERVATION_LIMIT, meal.getReservationLimit());
        assertEquals(TEST_DATE, meal.getDate());
        assertEquals(mockRestaurant, meal.getRestaurant());
    }

    @Test
    void testReservationsListInitialization() {
        assertNotNull(meal.getReservations());
        assertTrue(meal.getReservations().isEmpty());
    }

    @Test
    void testSetReservations() {
        List<Reservation> reservations = new ArrayList<>();
        Reservation res1 = new Reservation();
        res1.setCode(1L);
        Reservation res2 = new Reservation();
        res2.setCode(2L);
        reservations.add(res1);
        reservations.add(res2);

        meal.setReservations(reservations);

        assertEquals(2, meal.getReservations().size());
        assertEquals(1L, meal.getReservations().get(0).getCode());
        assertEquals(2L, meal.getReservations().get(1).getCode());
    }

    @Test
    void testToString() {
        String expectedString = String.format(
                "Meal{id=%d, name='%s', type='%s', reservationLimit=%d, date=%s, restaurant=%s}",
                TEST_ID, TEST_NAME, TEST_TYPE, TEST_RESERVATION_LIMIT, TEST_DATE, mockRestaurant
        );

        assertEquals(expectedString, meal.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Meal sameMeal = new Meal();
        sameMeal.setId(TEST_ID);
        sameMeal.setName(TEST_NAME);
        sameMeal.setType(TEST_TYPE);
        sameMeal.setReservationLimit(TEST_RESERVATION_LIMIT);
        sameMeal.setDate(TEST_DATE);
        sameMeal.setRestaurant(mockRestaurant);

        Meal differentMeal = new Meal();
        differentMeal.setId(2L);
        differentMeal.setName("Different Meal");
        differentMeal.setType("dinner");
        differentMeal.setReservationLimit(20);
        differentMeal.setDate(LocalDate.now());
        differentMeal.setRestaurant(Mockito.mock(Restaurant.class));

        assertEquals(meal, sameMeal);
        assertEquals(meal.hashCode(), sameMeal.hashCode());
        assertNotEquals(meal, differentMeal);
        assertNotEquals(meal.hashCode(), differentMeal.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        Meal newMeal = new Meal();
        assertNotNull(newMeal);
        assertEquals(0L, newMeal.getId());
        assertNull(newMeal.getName());
        assertNull(newMeal.getType());
        assertEquals(0, newMeal.getReservationLimit());
        assertNull(newMeal.getDate());
        assertNull(newMeal.getRestaurant());
        assertNotNull(newMeal.getReservations());
    }

    @Test
    void testFieldValidationConstraints() {
        // This would typically be an integration test with @Valid,
        // but we can verify the annotations are present
        try {
            NotNull notNullAnnotation = Meal.class
                    .getDeclaredField("reservationLimit")
                    .getAnnotation(NotNull.class);
            assertNotNull(notNullAnnotation);
            assertEquals("´Reservation limit is mandatory.", notNullAnnotation.message());

            NotBlank notBlankAnnotation = Meal.class
                    .getDeclaredField("name")
                    .getAnnotation(NotBlank.class);
            assertNotNull(notBlankAnnotation);
            assertEquals("Name is mandatory", notBlankAnnotation.message());
        } catch (NoSuchFieldException e) {
            fail("Field not found", e);
        }
    }
}
