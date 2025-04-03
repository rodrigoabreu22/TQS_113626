package org.tqs.deti.ua.MoliceiroUniRestaurants.models;

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
    private final long TEST_CODE = 12345L;
    private final String DEFAULT_STATUS = "VALIDA";
    private final String TEST_STATUS = "CONFIRMADA";

    @BeforeEach
    void setUp() {
        mockMeal = Mockito.mock(Meal.class);
        when(mockMeal.getId()).thenReturn(1L);
        when(mockMeal.getName()).thenReturn("Test Meal");

        reservation = new Reservation();
        reservation.setCode(TEST_CODE);
        reservation.setStatus(TEST_STATUS);
        reservation.setMeal(mockMeal);
    }

    @Test
    void testDefaultConstructor() {
        Reservation defaultReservation = new Reservation();
        assertEquals("VALIDA", defaultReservation.getStatus());
        assertNull(defaultReservation.getMeal());
        assertEquals(0L, defaultReservation.getCode());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(TEST_CODE, reservation.getCode());
        assertEquals(TEST_STATUS, reservation.getStatus());
        assertEquals(mockMeal, reservation.getMeal());
    }

    @Test
    void testStatusChange() {
        String newStatus = "CANCELADA";
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
    void testToString() {
        String expectedString = String.format(
                "Reservation{code=%d, status='%s', meal=%s}",
                TEST_CODE, TEST_STATUS, mockMeal
        );
        assertEquals(expectedString, reservation.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Create identical reservation
        Reservation sameReservation = new Reservation();
        sameReservation.setCode(TEST_CODE);
        sameReservation.setStatus(TEST_STATUS);
        sameReservation.setMeal(mockMeal);

        // Create different reservation
        Reservation differentReservation = new Reservation();
        differentReservation.setCode(99999L);
        differentReservation.setStatus("CANCELADA");
        differentReservation.setMeal(Mockito.mock(Meal.class));

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

        } catch (NoSuchFieldException e) {
            fail("Field not found", e);
        }
    }
}