package org.tqs.deti.ua.MoliceiroUniRestaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.MealService;
import org.tqs.deti.ua.MoliceiroUniRestaurants.services.ReservationService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservations")  // Changed from "/reservation" for clarity
public class ReservationViewController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MealService mealService;

    @GetMapping("/search")
    public String showSearchPage() {
        return "reservation-search";  // Initial page load
    }

    @GetMapping("/search-result")
    public String searchReservation(@RequestParam String code, Model model) {

        Reservation reservation = reservationService.getReservationByCode(code);
        model.addAttribute("reservation", reservation);
        model.addAttribute("code", code);
        return "reservation-search";  // Same template, now with data
    }

    @GetMapping("/meal/{id}")
    public String showMealReservations(@PathVariable Long id, Model model) {
        Meal meal = mealService.getMealById(id);

        // Get VALID and USED reservations separately
        List<Reservation> validReservations = reservationService.getMealReservations(id, "VALID");
        List<Reservation> usedReservations = reservationService.getMealReservations(id, "USED");

        int reservationLimit = meal.getReservationLimit();
        int usedCount = usedReservations.size();
        int validCount = validReservations.size();

        // Combine both lists for displaying in the UI
        List<Reservation> allReservations = new ArrayList<>();
        allReservations.addAll(validReservations);
        allReservations.addAll(usedReservations);

        model.addAttribute("meal", meal);
        model.addAttribute("reservations", allReservations);
        model.addAttribute("limit", reservationLimit);
        model.addAttribute("valid", validCount + usedCount);
        model.addAttribute("available", reservationLimit - (validCount+ usedCount));

        return "meal-reservations.html";
    }
}


