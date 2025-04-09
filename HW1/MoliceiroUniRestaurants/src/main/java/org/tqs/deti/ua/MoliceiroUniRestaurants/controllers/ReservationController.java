package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Operation(summary = "Create a reservation for a meal by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation created successfully"),
        @ApiResponse(responseCode = "400", description = "Reservation failed or meal unavailable")
    })
    @PostMapping("/meal/{id}")
    public ResponseEntity<Reservation> createReservation(@PathVariable(value="id") Long id) {
        Reservation reservation = reservationService.bookMeal(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Get reservation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation found"),
        @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable(value="id") Long code) {
        return reservationService.getReservationById(code);
    }

    @Operation(summary = "Get reservation by reservation code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation found"),
        @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @GetMapping("/code/{code}")
    public Reservation getReservation(@PathVariable(value="code") String code) {
        return reservationService.getReservationByCode(code);
    }

    @Operation(summary = "Get reservations for a meal by ID, optionally filtered by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Meal or reservations not found")
    })
    @GetMapping("/meal/{id}")
    public List<Reservation> getMealReservations(@PathVariable(value="id") Long id, @RequestParam(required = false) String status) {
        return reservationService.getMealReservations(id, status);
    }

    @Operation(summary = "Validate a reservation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation validated successfully"),
        @ApiResponse(responseCode = "409", description = "Reservation could not be validated")
    })
    @PutMapping("/{id}/validate")
    public ResponseEntity<String> validateReservation(@PathVariable(value="id") Long id) {
        try {
            reservationService.validateReservation(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "Cancel a reservation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation cancelled successfully"),
        @ApiResponse(responseCode = "409", description = "Reservation could not be cancelled")
    })
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable(value="id") Long id) {
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}

