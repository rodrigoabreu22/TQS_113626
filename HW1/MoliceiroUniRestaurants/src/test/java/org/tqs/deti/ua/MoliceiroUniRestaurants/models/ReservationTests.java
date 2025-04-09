package org.tqs.deti.ua.MoliceiroUniRestaurants.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReservationTests {

    private Reservation reservation;
    private Meal mockMeal;
    private final long TEST_ID = 1L;
    private final String TEST_CODE = "ABC123";
    private final String DEFAULT_STATUS = "VALID";
    private final String TEST_STATUS = "CONFIRMED";

    @BeforeEach
    void setUp() {
        mockMeal = Mockito.mock(Meal.class);
        when(mockMeal.getId()).thenReturn(1L);
        when(mockMeal.getName()).thenReturn("Test Meal");

        reservation = new Reservation(mockMeal);
        reservation.setId(TEST_ID);
        reservation.setStatus(TEST_STATUS);
        reservation.setCode(TEST_CODE); // Set code manually for testing
    }

    @Test
    void testDefaultConstructor() {
        Reservation defaultReservation = new Reservation();
        assertNull(defaultReservation.getStatus());
        assertNull(defaultReservation.getMeal());
        assertEquals(0L, defaultReservation.getId());
        assertNull(defaultReservation.getCode()); // Code should be generated
    }

    @Test
    void testMealConstructor() {
        Reservation mealReservation = new Reservation(mockMeal);
        assertEquals(mockMeal, mealReservation.getMeal());
        assertEquals(DEFAULT_STATUS, mealReservation.getStatus());
        assertNotNull(mealReservation.getCode());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(TEST_ID, reservation.getId());
        assertEquals(TEST_STATUS, reservation.getStatus());
        assertEquals(mockMeal, reservation.getMeal());
        assertEquals(TEST_CODE, reservation.getCode());
    }

    @Test
    void testStatusChange() {
        String newStatus = "CANCELED";
        reservation.setStatus(newStatus);
        assertEquals(newStatus, reservation.getStatus());
    }

    @Test
    void testMealAssociation() {
        Meal newMeal = Mockito.mock(Meal.class);
        when(newMeal.getId()).thenReturn(2L);
        reservation.setMeal(newMeal);
        assertEquals(newMeal, reservation.getMeal());
    }

    @Test
    void testCodeGeneration() {
        Reservation newReservation = new Reservation(mockMeal);
        String code = newReservation.getCode();
        assertNotNull(code);
        assertEquals(6, code.length());
        assertTrue(code.matches("[A-Z0-9]{6}"));
    }

    @Test
    void testEqualsAndHashCode() {
        // Create identical reservation
        Reservation sameReservation = new Reservation(mockMeal);
        sameReservation.setId(TEST_ID);
        sameReservation.setStatus(TEST_STATUS);
        sameReservation.setCode(TEST_CODE);

        // Create different reservation
        Reservation differentReservation = new Reservation(mockMeal);
        differentReservation.setCode("XYZ789");

        // Test equality
        assertEquals(reservation, sameReservation);
        assertEquals(reservation.hashCode(), sameReservation.hashCode());

        // Test inequality
        assertNotEquals(reservation, differentReservation);
        assertNotEquals(reservation, new Reservation());
        assertNotEquals(reservation, null);
        assertNotEquals(reservation, new Object());
    }

    @Test
    void testFieldValidationAnnotations() {
        try {
            // Test @NotBlank on status field
            NotBlank notBlankAnnotation = Reservation.class
                    .getDeclaredField("status")
                    .getAnnotation(NotBlank.class);
            assertNotNull(notBlankAnnotation);
            assertEquals("Status is mandatory", notBlankAnnotation.message());

            // Test @NotNull on meal field
            NotNull notNullAnnotation = Reservation.class
                    .getDeclaredField("meal")
                    .getAnnotation(NotNull.class);
            assertNotNull(notNullAnnotation);
            assertEquals("Meal is mandatory", notNullAnnotation.message());

            // Test code field is not nullable
            Column codeColumn = Reservation.class
                    .getDeclaredField("code")
                    .getAnnotation(Column.class);
            assertNotNull(codeColumn);
            assertFalse(codeColumn.nullable());
            assertTrue(codeColumn.unique());

        } catch (NoSuchFieldException e) {
            fail("Field not found", e);
        }
    }
}