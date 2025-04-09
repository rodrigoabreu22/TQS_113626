package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.MealRepository;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Meal lunchMeal;
    private Meal dinnerMeal;
    private Reservation validReservation;
    private Reservation canceledReservation;
    private Reservation usedReservation;
    private final long MEAL_ID = 1L;
    private final long RESERVATION_ID = 1001L;
    private final String RESERVATION_CODE = "CODE1001";
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate TOMORROW = TODAY.plusDays(1);

    @BeforeEach
    void setUp() {
        // Setup meals
        lunchMeal = new Meal();
        lunchMeal.setId(MEAL_ID);
        lunchMeal.setType("Lunch");
        lunchMeal.setReservationLimit(10);
        lunchMeal.setDate(TODAY);

        dinnerMeal = new Meal();
        dinnerMeal.setId(2L);
        dinnerMeal.setType("Dinner");
        dinnerMeal.setReservationLimit(15);
        dinnerMeal.setDate(TODAY);

        // Setup reservations
        validReservation = new Reservation(lunchMeal);
        validReservation.setId(RESERVATION_ID);
        validReservation.setCode(RESERVATION_CODE);
        validReservation.setStatus("VALID");

        canceledReservation = new Reservation(dinnerMeal);
        canceledReservation.setId(1002L);
        canceledReservation.setCode("CODE1002");
        canceledReservation.setStatus("CANCELLED");

        usedReservation = new Reservation(lunchMeal);
        usedReservation.setId(1003L);
        usedReservation.setCode("CODE1003");
        usedReservation.setStatus("USED");
    }

    @Test
    void bookMeal_ShouldSucceed_ForLunchBeforeDeadlineAndUnderCapacity() {
        when(mealRepository.findById(MEAL_ID)).thenReturn(lunchMeal);
        when(reservationRepository.findByMealIdAndStatus(MEAL_ID, "VALID"))
                .thenReturn(Collections.emptyList());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(validReservation);

        // Mock current time (before lunch deadline)
        LocalDateTime testTime = LocalDateTime.of(TODAY, LocalTime.of(11, 59));
        try (var mockedTime = mockStatic(LocalDateTime.class)) {
            mockedTime.when(LocalDateTime::now).thenReturn(testTime);

            Reservation result = reservationService.bookMeal(MEAL_ID);

            assertNotNull(result);
            assertEquals("VALID", result.getStatus());
            verify(reservationRepository).save(any(Reservation.class));
        }
    }

    @Test
    void bookMeal_ShouldFail_ForLunchAfterDeadline() {
        when(mealRepository.findById(MEAL_ID)).thenReturn(lunchMeal);

        // Mock current time (after lunch deadline)
        LocalDateTime testTime = LocalDateTime.of(TODAY, LocalTime.of(12, 1));
        try (var mockedTime = mockStatic(LocalDateTime.class)) {
            mockedTime.when(LocalDateTime::now).thenReturn(testTime);

            Reservation result = reservationService.bookMeal(MEAL_ID);

            assertNull(result);
            verify(reservationRepository, never()).save(any());
        }
    }

    @Test
    void bookMeal_ShouldSucceed_ForDinnerBeforeDeadlineAndUnderCapacity() {
        when(mealRepository.findById(dinnerMeal.getId())).thenReturn(dinnerMeal);
        when(reservationRepository.findByMealIdAndStatus(dinnerMeal.getId(), "VALID"))
                .thenReturn(Collections.emptyList());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(canceledReservation);

        // Mock current time (before dinner deadline)
        LocalDateTime testTime = LocalDateTime.of(TODAY, LocalTime.of(17, 59));
        try (var mockedTime = mockStatic(LocalDateTime.class)) {
            mockedTime.when(LocalDateTime::now).thenReturn(testTime);

            Reservation result = reservationService.bookMeal(dinnerMeal.getId());

            assertNotNull(result);
            verify(reservationRepository).save(any(Reservation.class));
        }
    }

    @Test
    void bookMeal_ShouldFail_WhenAtCapacity() {
        when(mealRepository.findById(MEAL_ID)).thenReturn(lunchMeal);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(lunchMeal), new Reservation(lunchMeal), new Reservation(lunchMeal)
        );
        lunchMeal.setReservationLimit(3);

        when(reservationRepository.findByMealIdAndStatus(MEAL_ID, "VALID"))
                .thenReturn(reservations);

        Reservation result = reservationService.bookMeal(MEAL_ID);

        assertNull(result);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void bookMeal_ShouldFail_WhenMealNotFound() {
        when(mealRepository.findById(999L)).thenReturn(null);

        Reservation result = reservationService.bookMeal(999L);

        assertNull(result);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void bookMeal_ShouldFail_WhenMealDateIsPast() {
        lunchMeal.setDate(TODAY.minusDays(1));
        when(mealRepository.findById(MEAL_ID)).thenReturn(lunchMeal);

        Reservation result = reservationService.bookMeal(MEAL_ID);

        assertNull(result);
        verify(reservationRepository, never()).save(any());
    }

    // Get Reservation Tests
    @Test
    void getReservationById_ShouldReturnReservation() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(validReservation);

        Reservation result = reservationService.getReservationById(RESERVATION_ID);

        assertNotNull(result);
        assertEquals(RESERVATION_ID, result.getId());
    }

    @Test
    void getReservationById_ShouldReturnNull_WhenNotFound() {
        when(reservationRepository.findById(999L)).thenReturn(null);

        Reservation result = reservationService.getReservationById(999L);

        assertNull(result);
    }

    @Test
    void getReservationByCode_ShouldReturnReservation() {
        when(reservationRepository.findByCode(RESERVATION_CODE)).thenReturn(validReservation);

        Reservation result = reservationService.getReservationByCode(RESERVATION_CODE);

        assertNotNull(result);
        assertEquals(RESERVATION_CODE, result.getCode());
    }

    @Test
    void getReservationByCode_ShouldReturnNull_WhenNotFound() {
        when(reservationRepository.findByCode("INVALID_CODE")).thenReturn(null);

        Reservation result = reservationService.getReservationByCode("INVALID_CODE");

        assertNull(result);
    }

    // Get Meal Reservations Tests
    @Test
    void getMealReservations_ShouldReturnAll_WhenNoStatusFilter() {
        when(reservationRepository.findByMealId(MEAL_ID))
                .thenReturn(Arrays.asList(validReservation, canceledReservation));

        List<Reservation> result = reservationService.getMealReservations(MEAL_ID, null);

        assertEquals(2, result.size());
    }

    @Test
    void getMealReservations_ShouldReturnFiltered_WhenStatusProvided() {
        when(reservationRepository.findByMealIdAndStatus(MEAL_ID, "VALID"))
                .thenReturn(Collections.singletonList(validReservation));

        List<Reservation> result = reservationService.getMealReservations(MEAL_ID, "VALID");

        assertEquals(1, result.size());
        assertEquals("VALID", result.get(0).getStatus());
    }

    // Cancel Reservation Tests
    @Test
    void cancelReservation_ShouldUpdateStatus_WhenValid() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(validReservation);

        reservationService.cancelReservation(RESERVATION_ID);

        assertEquals("CANCELLED", validReservation.getStatus());
        verify(reservationRepository).save(validReservation);
    }

    @Test
    void cancelReservation_ShouldThrow_WhenReservationNotFound() {
        when(reservationRepository.findById(999L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservation(999L);
        });
    }

    @Test
    void cancelReservation_ShouldThrow_WhenAlreadyUsed() {
        when(reservationRepository.findById(usedReservation.getId())).thenReturn(usedReservation);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.cancelReservation(usedReservation.getId());
        });
    }

    @Test
    void cancelReservation_ShouldThrow_WhenAlreadyCancelled() {
        when(reservationRepository.findById(canceledReservation.getId())).thenReturn(canceledReservation);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.cancelReservation(canceledReservation.getId());
        });
    }

    // Validate Reservation Tests
    @Test
    void validateReservation_ShouldReturnTrue_WhenValidForToday() {
        validReservation.getMeal().setDate(TODAY);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(validReservation);

        boolean result = reservationService.validateReservation(RESERVATION_ID);

        assertTrue(result);
        assertEquals("USED", validReservation.getStatus());
        verify(reservationRepository).save(validReservation);
    }

    @Test
    void validateReservation_ShouldReturnFalse_WhenNotForToday() {
        validReservation.getMeal().setDate(TOMORROW);
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(validReservation);

        boolean result = reservationService.validateReservation(RESERVATION_ID);

        assertFalse(result);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void validateReservation_ShouldReturnFalse_WhenStatusNotValid() {
        canceledReservation.getMeal().setDate(TODAY);
        when(reservationRepository.findById(canceledReservation.getId())).thenReturn(canceledReservation);

        boolean result = reservationService.validateReservation(canceledReservation.getId());

        assertFalse(result);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void validateReservation_ShouldReturnFalse_WhenReservationNotFound() {
        when(reservationRepository.findById(999L)).thenReturn(null);

        boolean result = reservationService.validateReservation(999L);

        assertFalse(result);
        verify(reservationRepository, never()).save(any());
    }
}