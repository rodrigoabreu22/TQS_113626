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

    @Autowired
    ReservationRepository reservationRepository;


    public Reservation bookMeal(Reservation reservation) {
        long mealId = reservation.getMeal().getId();
        int validReservations = reservationRepository.findByMealIdAndStatus(mealId, "VALIDA").size();
        int reservationsLimit = reservation.getMeal().getReservationLimit();

        String mealType = reservation.getMeal().getType();
        LocalDateTime now = LocalDateTime.now();

        // Time constraints for lunch and dinner
        LocalDateTime lunchDeadline = LocalDateTime.of(now.toLocalDate(), LocalTime.of(12, 0));
        LocalDateTime dinnerDeadline = LocalDateTime.of(now.toLocalDate(), LocalTime.of(18, 0));

        if (validReservations < reservationsLimit) {
            if (mealType.equalsIgnoreCase("Almoço") && now.isBefore(lunchDeadline)) {
                return reservationRepository.save(reservation);
            }
            else if (mealType.equalsIgnoreCase("Jantar") && now.isBefore(dinnerDeadline)) {
                return reservationRepository.save(reservation);
            }
        }

        return null;
    }


    public Reservation getReservationByCode(long code) {
        return reservationRepository.findByCode(code);
    }

    public List<Reservation> getMealReservations(long mealId, String status) {
        if (status == null){
            return reservationRepository.findByMealId(mealId);
        }
        return reservationRepository.findByMealIdAndStatus(mealId, status);
    }

    public void cancelReservation(long reservationId) {
        Reservation res = getReservationByCode(reservationId);
        if (res.getStatus().equals("VALIDA")){
            res.setStatus("CANCELADA");
            reservationRepository.save(res);
        }
        System.out.println("Invalid reservation!");
    }

    public void validateReservation(long reservationId) {
        Reservation res = getReservationByCode(reservationId);
        LocalDate today = LocalDate.now();  // Get current date
        LocalDate mealDay = res.getMeal().getDate();  // Get meal date

        // Ensure the reservation is for today
        if (!mealDay.isEqual(today)) {
            System.out.println("Reservation is not valid for today!");
            return;
        }

        if (res.getStatus().equals("VALIDA")) {
            res.setStatus("USADO");
            reservationRepository.save(res);
        }
        else {
            System.out.println("Invalid reservation!");
        }
    }
}
