package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Meal;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.MealRepository;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.ReservationRepository;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.RestaurantRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MealRepository mealRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, MealRepository mealRepository) {
        this.reservationRepository = reservationRepository;
        this.mealRepository = mealRepository;
    }

    public Reservation bookMeal(long mealId) {
        Meal meal = mealRepository.findById(mealId);
        if (meal == null) {
            return null;
        }

        LocalDate mealDate = meal.getDate();
        if (mealDate == null || mealDate.isBefore(LocalDate.now())) {
            return null;
        }

        int validReservations = reservationRepository.findByMealIdAndStatus(mealId, "VALID").size();
        int limit = meal.getReservationLimit();

        if (validReservations >= limit) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        String mealType = meal.getType();
        LocalTime deadline = mealType.equalsIgnoreCase("Lunch") ? LocalTime.of(12, 0) : LocalTime.of(18, 0);

        if (mealDate.isEqual(LocalDate.now()) && now.toLocalTime().isAfter(deadline)) {
            return null;
        }

        Reservation reservation = new Reservation(meal);

        reservation.setStatus("VALIDA"); // Explicitly set status
        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getMealReservations(long mealId, String status) {
        return status == null ?
                reservationRepository.findByMealId(mealId) :
                reservationRepository.findByMealIdAndStatus(mealId, status);
    }

    public void cancelReservation(long reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res != null && "VALID".equals(res.getStatus())) {
            res.setStatus("CANCELLED");
            reservationRepository.save(res);
        }
    }

    public boolean validateReservation(long reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res == null || !"VALID".equals(res.getStatus())) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate mealDate = res.getMeal().getDate();

        if (!today.equals(mealDate)) {
            return false;
        }

        res.setStatus("USED");
        reservationRepository.save(res);
        return true;
    }

    public Reservation getReservationByCode(String code) {
        return reservationRepository.findByCode(code);
    }
}