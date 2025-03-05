package org.tqs.deti.ua.lab3_2cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    @Autowired
    private CarRepository carRepository;

    Car save(Car car){
        return carRepository.save(car);
    }

    List<Car> getAllCars(){
        return carRepository.findAll();
    }

    Optional<Car> getCarDetails(long id){
        return Optional.ofNullable(carRepository.findByCarId(id));
    }

    Optional<Car> getCarReplacement(Car car){
        List<Car> allCars = this.getAllCars();

        //Criterio escolhido: carro tem de ser da mesma marca que o substitído.
        for (Car c : allCars){
            if (c.getMaker()==car.getMaker() && c!=car){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
}
