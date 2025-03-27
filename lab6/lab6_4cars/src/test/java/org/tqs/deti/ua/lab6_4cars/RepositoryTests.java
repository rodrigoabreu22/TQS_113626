package org.tqs.deti.ua.lab6_4cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.tqs.deti.ua.lab6_4cars.Car;
import org.tqs.deti.ua.lab6_4cars.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class RepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private CarRepository carRepository;
    
    @Test
    void whenFindCorollaById_thenReturnCorollaCar() {
        Car corolla = new Car("Toyota", "Corolla");
        entityManager.persistAndFlush(corolla);
    
        Car found = carRepository.findByCarId(corolla.getCarId());
        assertThat(found).isEqualTo(corolla);
    }
    
    @Test
    void whenCreateCorolla_thenReturnCorollaCar(){
    
        Car persistedCorolla = entityManager.persistFlushFind(new Car("Toyota", "Corolla"));
    
        Car found = carRepository.findByCarId(persistedCorolla.getCarId());
        assertThat(found).isNotNull().extracting(Car::getCarId).isEqualTo(persistedCorolla.getCarId());
    }
    
    @Test
    void whenInvalidCarId_thenReturnNull(){
        Car car = carRepository.findByCarId(-3);
        assertThat(car).isNull();
    }
    
    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car corolla = new Car("toyota", "corolla");
        Car civic = new  Car("honda", "civic");
        Car gt3 = new  Car("porsche", "gt3");
    
        entityManager.persist(corolla);
        entityManager.persist(civic);
        entityManager.persist(gt3);
        entityManager.flush();
    
        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getCarId).containsOnly(corolla.getCarId(), civic.getCarId(), gt3.getCarId());
    }   
}
