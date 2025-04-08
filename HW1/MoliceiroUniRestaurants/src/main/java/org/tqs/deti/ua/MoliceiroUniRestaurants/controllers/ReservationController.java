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

    @PostMapping("/meal/{id}")
    public ResponseEntity<Reservation> createReservation(@PathVariable(value="id") Long id) {
        Reservation reservation = reservationService.bookMeal(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable(value="id") Long code) {
        return reservationService.getReservationById(code);
    }

    @GetMapping("/code/{code}")
    public Reservation getReservation(@PathVariable(value="code") String code) {
        return reservationService.getReservationByCode(code);
    }

    @GetMapping("/meal/{id}")
    public List<Reservation> getMealReservations(@PathVariable(value="id") Long id, @RequestParam(required = false) String status) {
        return reservationService.getMealReservations(id, status);
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<String> validateReservation(@PathVariable(value="id") Long id) {
        try {
            reservationService.validateReservation(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

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
