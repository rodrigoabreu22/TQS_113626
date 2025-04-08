package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    //@Mock
    //private ReservationRepository reservationRepository;
//
    //@InjectMocks
    //private ReservationService reservationService;
//
    //private Meal lunchMeal;
    //private Meal dinnerMeal;
    //private Reservation validReservation;
    //private Reservation canceledReservation;
    //private Reservation usedReservation;
    //private final long MEAL_ID = 1L;
    //private final long RESERVATION_CODE = 1001L;
    //private final LocalDate TODAY = LocalDate.now();
    //private final LocalDate TOMORROW = TODAY.plusDays(1);
//
    //@BeforeEach
    //void setUp() {
    //    // Setup meals
    //    lunchMeal = new Meal();
    //    lunchMeal.setId(MEAL_ID);
    //    lunchMeal.setType("Almoço");
    //    lunchMeal.setReservationLimit(10);
    //    lunchMeal.setDate(TODAY);
//
    //    dinnerMeal = new Meal();
    //    dinnerMeal.setId(2L);
    //    dinnerMeal.setType("Jantar");
    //    dinnerMeal.setReservationLimit(15);
    //    dinnerMeal.setDate(TODAY);
//
    //    // Setup reservations
    //    validReservation = new Reservation();
    //    validReservation.setCode(RESERVATION_CODE);
    //    validReservation.setStatus("VALIDA");
    //    validReservation.setMeal(lunchMeal);
//
    //    canceledReservation = new Reservation();
    //    canceledReservation.setCode(1002L);
    //    canceledReservation.setStatus("CANCELADA");
    //    canceledReservation.setMeal(dinnerMeal);
//
    //    usedReservation = new Reservation();
    //    usedReservation.setCode(1003L);
    //    usedReservation.setStatus("USADO");
    //    usedReservation.setMeal(lunchMeal);
    //}
//
    //// Updated test methods in ReservationServiceTest
//
    //@Test
    //void bookMeal_ShouldSucceed_ForLunchBeforeDeadlineAndUnderCapacity() {
    //    // Create test reservation with meal
    //    Reservation testReservation = new Reservation();
    //    testReservation.setMeal(lunchMeal);
//
    //    // Mock current time (before lunch deadline)
    //    LocalDateTime testTime = LocalDateTime.of(TODAY, LocalTime.of(11, 59));
    //    try (var mockedTime = mockStatic(LocalDateTime.class)) {
    //        mockedTime.when(LocalDateTime::now).thenReturn(testTime);
//
    //        when(reservationRepository.findByMealIdAndStatus(MEAL_ID, "VALIDA"))
    //                .thenReturn(Collections.emptyList());
    //        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
//
    //        Reservation result = reservationService.bookMeal(testReservation);
//
    //        assertNotNull(result);
    //        verify(reservationRepository).save(testReservation);
    //    }
    //}
//
    //@Test
    //void bookMeal_ShouldFail_ForLunchAfterDeadline() {
    //    // Create test reservation with meal
    //    Reservation testReservation = new Reservation();
    //    testReservation.setMeal(lunchMeal);
//
    //    // Mock current time (after lunch deadline)
    //    LocalDateTime testTime = LocalDateTime.of(TODAY, LocalTime.of(12, 1));
    //    try (var mockedTime = mockStatic(LocalDateTime.class)) {
    //        mockedTime.when(LocalDateTime::now).thenReturn(testTime);
//
    //        Reservation result = reservationService.bookMeal(testReservation);
//
    //        assertNull(result);
    //        verify(reservationRepository, never()).save(any());
    //    }
    //}
//
    //@Test
    //void bookMeal_ShouldSucceed_ForDinnerBeforeDeadlineAndUnderCapacity() {
    //    // Create test reservation with meal
    //    Reservation testReservation = new Reservation();
    //    testReservation.setMeal(dinnerMeal);
//
    //    // Mock current time (before dinner deadline)
    //    LocalDateTime testTime = LocalDateTime.of(TODAY, LocalTime.of(17, 59));
    //    try (var mockedTime = mockStatic(LocalDateTime.class)) {
    //        mockedTime.when(LocalDateTime::now).thenReturn(testTime);
//
    //        when(reservationRepository.findByMealIdAndStatus(dinnerMeal.getId(), "VALIDA"))
    //                .thenReturn(Collections.emptyList());
    //        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
//
    //        Reservation result = reservationService.bookMeal(testReservation);
//
    //        assertNotNull(result);
    //        verify(reservationRepository).save(testReservation);
    //    }
    //}
//
    //@Test
    //void bookMeal_ShouldFail_WhenAtCapacity() {
    //    // Create enough reservations to reach capacity
    //    List<Reservation> reservations = Arrays.asList(
    //            new Reservation(), new Reservation(), new Reservation()
    //    );
    //    lunchMeal.setReservationLimit(3);
//
    //    when(reservationRepository.findByMealIdAndStatus(MEAL_ID, "VALIDA"))
    //            .thenReturn(reservations);
//
    //    Reservation result = reservationService.bookMeal(validReservation);
//
    //    assertNull(result);
    //    verify(reservationRepository, never()).save(any());
    //}
//
    //// Get Reservation Tests
    //@Test
    //void getReservationByCode_ShouldReturnReservation() {
    //    when(reservationRepository.findByCode(RESERVATION_CODE)).thenReturn(validReservation);
//
    //    Reservation result = reservationService.getReservationByCode(RESERVATION_CODE);
//
    //    assertNotNull(result);
    //    assertEquals(RESERVATION_CODE, result.getCode());
    //}
//
    //@Test
    //void getReservationByCode_ShouldReturnNull_WhenNotFound() {
    //    when(reservationRepository.findByCode(999L)).thenReturn(null);
//
    //    Reservation result = reservationService.getReservationByCode(999L);
//
    //    assertNull(result);
    //}
//
    //// Get Meal Reservations Tests
    //@Test
    //void getMealReservations_ShouldReturnAll_WhenNoStatusFilter() {
    //    when(reservationRepository.findByMealId(MEAL_ID))
    //            .thenReturn(Arrays.asList(validReservation, canceledReservation));
//
    //    List<Reservation> result = reservationService.getMealReservations(MEAL_ID, null);
//
    //    assertEquals(2, result.size());
    //}
//
    //@Test
    //void getMealReservations_ShouldReturnFiltered_WhenStatusProvided() {
    //    when(reservationRepository.findByMealIdAndStatus(MEAL_ID, "VALIDA"))
    //            .thenReturn(Collections.singletonList(validReservation));
//
    //    List<Reservation> result = reservationService.getMealReservations(MEAL_ID, "VALIDA");
//
    //    assertEquals(1, result.size());
    //    assertEquals("VALIDA", result.get(0).getStatus());
    //}
//
    //// Cancel Reservation Tests
    //@Test
    //void cancelReservation_ShouldUpdateStatus_WhenValid() {
    //    when(reservationRepository.findByCode(RESERVATION_CODE)).thenReturn(validReservation);
    //    when(reservationRepository.save(any(Reservation.class))).thenReturn(validReservation);
//
    //    reservationService.cancelReservation(RESERVATION_CODE);
//
    //    assertEquals("CANCELADA", validReservation.getStatus());
    //    verify(reservationRepository).save(validReservation);
    //}
//
    //@Test
    //void cancelReservation_ShouldNotUpdate_WhenAlreadyCanceled() {
    //    when(reservationRepository.findByCode(RESERVATION_CODE)).thenReturn(canceledReservation);
//
    //    reservationService.cancelReservation(RESERVATION_CODE);
//
    //    verify(reservationRepository, never()).save(any());
    //}
//
    //// Validate Reservation Tests
    //@Test
    //void validateReservation_ShouldUpdateStatus_WhenValidForToday() {
    //    validReservation.getMeal().setDate(TODAY);
    //    when(reservationRepository.findByCode(RESERVATION_CODE)).thenReturn(validReservation);
    //    when(reservationRepository.save(any(Reservation.class))).thenReturn(validReservation);
//
    //    reservationService.validateReservation(RESERVATION_CODE);
//
    //    assertEquals("USADO", validReservation.getStatus());
    //    verify(reservationRepository).save(validReservation);
    //}
//
    //@Test
    //void validateReservation_ShouldNotUpdate_WhenNotForToday() {
    //    validReservation.getMeal().setDate(TOMORROW);
    //    when(reservationRepository.findByCode(RESERVATION_CODE)).thenReturn(validReservation);
//
    //    reservationService.validateReservation(RESERVATION_CODE);
//
    //    verify(reservationRepository, never()).save(any());
    //}
//
    //@Test
    //void validateReservation_ShouldNotUpdate_WhenAlreadyUsed() {
    //    usedReservation.getMeal().setDate(TODAY);
    //    when(reservationRepository.findByCode(usedReservation.getCode())).thenReturn(usedReservation);
//
    //    reservationService.validateReservation(usedReservation.getCode());
//
    //    verify(reservationRepository, never()).save(any());
    //}
}