package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.ReservationService;

@Controller
@RequestMapping("/reservations")  // Changed from "/reservation" for clarity
public class ReservationViewController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/search")
    public String showSearchPage() {
        return "reservation/search";  // Initial page load
    }

    @GetMapping("/search-result")
    public String searchReservation(@RequestParam String code, Model model) {

        Reservation reservation = reservationService.getReservationByCode(code);
        model.addAttribute("reservation", reservation);
        model.addAttribute("code", code);
        return "reservation/search";  // Same template, now with data
    }
}


