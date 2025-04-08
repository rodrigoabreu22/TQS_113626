package org.tqs.deti.ua.MoliceiroUniRestaurants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findById(long id);
    Reservation findByCode(String code);
    List<Reservation> findByMealId(long mealId);
    List<Reservation> findByMealIdAndStatus(long mealId, String status);

}
