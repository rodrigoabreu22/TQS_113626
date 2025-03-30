package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Reservation createReservation(@RequestBody Reservation reservation) {
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

    @PutMapping("/{code}/cancel")
    public void cancelReservation(@PathVariable(value="code") Long code) {
        reservationService.cancelReservation(code);
    }

    @PutMapping("/{code}/validate")
    public void validateReservation(@PathVariable(value="code") Long code) {
        reservationService.validateReservation(code);
    }
}
