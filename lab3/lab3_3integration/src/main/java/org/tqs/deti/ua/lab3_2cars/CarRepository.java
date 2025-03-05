package org.tqs.deti.ua.lab3_2cars;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>{
    Car findByCarId(long id);
    List<Car> findAll();
}
