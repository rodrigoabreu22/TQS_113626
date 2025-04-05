package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.ReservationService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    private Meal testMeal;
    private Reservation validReservation;
    private Reservation canceledReservation;

    @BeforeEach
    void setUp() {
        testMeal = new Meal();
        testMeal.setId(1L);
        testMeal.setName("Test Meal");
        testMeal.setType("Lunch");
        testMeal.setReservationLimit(10);

        validReservation = new Reservation();
        validReservation.setCode(1001L);
        validReservation.setStatus("VALIDA");
        validReservation.setMeal(testMeal);

        canceledReservation = new Reservation();
        canceledReservation.setCode(1002L);
        canceledReservation.setStatus("CANCELADA");
        canceledReservation.setMeal(testMeal);
    }

    @Test
    void createReservation_ShouldReturnCreatedReservation() throws Exception {
        Mockito.when(reservationService.bookMeal(Mockito.any(Reservation.class)))
                .thenReturn(validReservation);

        mockMvc.perform(post("/api/v1/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(1001)))
                .andExpect(jsonPath("$.status", is("VALIDA")))
                .andExpect(jsonPath("$.meal.id", is(1)));
    }

    @Test
    void createReservation_ShouldReturnBadRequest_WhenMissingMeal() throws Exception {
        Reservation invalidReservation = new Reservation();
        invalidReservation.setStatus("VALIDA"); // Meal is null

        mockMvc.perform(post("/api/v1/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReservation)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createReservation_ShouldReturnBadRequest_WhenBlankStatus() throws Exception {
        Reservation invalidReservation = new Reservation();
        invalidReservation.setStatus(""); // Blank status
        invalidReservation.setMeal(testMeal);

        mockMvc.perform(post("/api/v1/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReservation)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReservation_ShouldReturnReservation() throws Exception {
        Mockito.when(reservationService.getReservationByCode(1001L))
                .thenReturn(validReservation);

        mockMvc.perform(get("/api/v1/reservation/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(1001)))
                .andExpect(jsonPath("$.status", is("VALIDA")))
                .andExpect(jsonPath("$.meal.name", is("Test Meal")));
    }

    @Test
    void getMealReservations_ShouldReturnAllStatusesWhenNoFilter() throws Exception {
        List<Reservation> reservations = Arrays.asList(validReservation, canceledReservation);

        Mockito.when(reservationService.getMealReservations(1L, null))
                .thenReturn(reservations);

        mockMvc.perform(get("/api/v1/reservation/meal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status", is("VALIDA")))
                .andExpect(jsonPath("$[1].status", is("CANCELADA")));
    }

    @Test
    void getMealReservations_ShouldFilterByStatus() throws Exception {
        Mockito.when(reservationService.getMealReservations(1L, "VALIDA"))
                .thenReturn(List.of(validReservation));

        mockMvc.perform(get("/api/v1/reservation/meal/1?status=VALIDA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("VALIDA")));
    }

    @Test
    void cancelReservation_ShouldReturnOk() throws Exception {
        Mockito.doNothing().when(reservationService).cancelReservation(1001L);

        mockMvc.perform(put("/api/v1/reservation/1001/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    void cancelReservation_ShouldReturnConflict_WhenInvalidState() throws Exception {
        Mockito.doThrow(new IllegalStateException("Cannot cancel already used reservation"))
                .when(reservationService).cancelReservation(1002L);

        mockMvc.perform(put("/api/v1/reservation/1002/cancel"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Cannot cancel already used reservation"));
    }
}
