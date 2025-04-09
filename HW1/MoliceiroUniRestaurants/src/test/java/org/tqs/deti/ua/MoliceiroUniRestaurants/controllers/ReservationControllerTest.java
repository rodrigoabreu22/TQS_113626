package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.ReservationService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
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

        validReservation = new Reservation(testMeal);  // Automatically generates code and uses the meal
        validReservation.setStatus("VALID");

        canceledReservation = new Reservation(testMeal);  // Another reservation for testing cancellation
        canceledReservation.setStatus("CANCELLED");

        // Mocking the MealRepository to return a valid meal when queried by ID
        Mockito.when(reservationService.getReservationByCode(Mockito.anyString())).thenReturn(validReservation);
    }

    @Test
    void createReservation_ShouldReturnCreatedReservation() throws Exception {
        Mockito.when(reservationService.bookMeal(Mockito.anyLong())).thenReturn(validReservation);

        mockMvc.perform(post("/api/v1/reservation/meal/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(validReservation.getCode())))
                .andExpect(jsonPath("$.status", is("VALID")));
    }

    @Test
    void createReservation_ShouldReturnBadRequest_WhenBlankStatus() throws Exception {
        Reservation invalidReservation = new Reservation();
        invalidReservation.setMeal(testMeal); // Meal is valid, but status is blank

        mockMvc.perform(post("/api/v1/reservation/meal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReservation)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void createReservation_ShouldReturnBadRequest_WhenMissingMeal() throws Exception {
        Reservation invalidReservation = new Reservation();
        invalidReservation.setStatus("VALID"); // Status is valid, but meal is missing

        mockMvc.perform(post("/api/v1/reservation/meal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReservation)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void cancelReservation_ShouldReturnOk() throws Exception {
        Mockito.doNothing().when(reservationService).cancelReservation(Mockito.anyLong());

        mockMvc.perform(put("/api/v1/reservation/{id}/cancel", 1001L))
                .andExpect(status().isOk());
    }

    @Test
    void cancelReservation_ShouldReturnConflict_WhenInvalidState() throws Exception {
        Mockito.doThrow(new IllegalStateException("Cannot cancel already used reservation"))
                .when(reservationService).cancelReservation(Mockito.anyLong());

        mockMvc.perform(put("/api/v1/reservation/{id}/cancel", 1001L))
                .andExpect(status().isConflict())
                .andExpect(content().string("Cannot cancel already used reservation"));
    }

    @Test
    void getReservation_ShouldReturnReservation() throws Exception {
        Mockito.when(reservationService.getReservationByCode("ABC123")).thenReturn(validReservation);

        mockMvc.perform(get("/api/v1/reservation/code/{code}", "ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(validReservation.getCode())))
                .andExpect(jsonPath("$.status", is("VALID")));
    }
}
