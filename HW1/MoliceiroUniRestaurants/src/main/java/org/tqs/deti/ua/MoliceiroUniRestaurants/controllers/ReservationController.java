package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping("")
    public Reservation createReservation(@Valid @RequestBody Reservation reservation) {
        return reservationService.bookMeal(reservation);
    }

    @GetMapping("/{code}")
    public Reservation getReservation(@PathVariable(value="code") Long code) {
        return reservationService.getReservationByCode(code);
    }

    @GetMapping("/meal/{id}")
    public List<Reservation> getMealReservations(@PathVariable(value="id") Long id, @RequestParam(required = false) String status) {
        return reservationService.getMealReservations(id, status);
    }

    @PutMapping("/{code}/validate")
    public ResponseEntity<String> validateReservation(@PathVariable(value="code") Long code) {
        try {
            reservationService.validateReservation(code);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{code}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable(value="code") Long code) {
        try {
            reservationService.cancelReservation(code);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
