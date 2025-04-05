package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation bookMeal(Reservation reservation) {
        if (reservation == null || reservation.getMeal() == null) {
            throw new IllegalArgumentException("Reservation and meal must not be null");
        }

        LocalDate mealDate = reservation.getMeal().getDate();
        if (mealDate == null || mealDate.isBefore(LocalDate.now())) {
            return null;
        }

        long mealId = reservation.getMeal().getId();
        int validReservations = reservationRepository.findByMealIdAndStatus(mealId, "VALIDA").size();
        int limit = reservation.getMeal().getReservationLimit();

        if (validReservations >= limit) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        String mealType = reservation.getMeal().getType();
        LocalTime deadline = mealType.equalsIgnoreCase("Almoço") ? LocalTime.of(12, 0) : LocalTime.of(18, 0);

        if (mealDate.isEqual(LocalDate.now()) && now.toLocalTime().isAfter(deadline)) {
            return null;
        }

        reservation.setStatus("VALIDA"); // Explicitly set status
        return reservationRepository.save(reservation);
    }

    public Reservation getReservationByCode(long code) {
        return reservationRepository.findByCode(code);
    }

    public List<Reservation> getMealReservations(long mealId, String status) {
        return status == null ?
                reservationRepository.findByMealId(mealId) :
                reservationRepository.findByMealIdAndStatus(mealId, status);
    }

    public void cancelReservation(long reservationId) {
        Reservation res = getReservationByCode(reservationId);
        if (res != null && "VALIDA".equals(res.getStatus())) {
            res.setStatus("CANCELADA");
            reservationRepository.save(res);
        }
    }

    public boolean validateReservation(long reservationId) {
        Reservation res = getReservationByCode(reservationId);
        if (res == null || !"VALIDA".equals(res.getStatus())) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate mealDate = res.getMeal().getDate();

        if (!today.equals(mealDate)) {
            return false;
        }

        res.setStatus("USADO");
        reservationRepository.save(res);
        return true;
    }
}