package org.tqs.deti.ua.lab6_4cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.tqs.deti.ua.lab6_4cars.Car;
import org.tqs.deti.ua.lab6_4cars.CarManagerService;
import org.tqs.deti.ua.lab6_4cars.CarRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceTests {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    private Car corolla_aux;
    private Car honda_aux;

    @BeforeEach
    public void setup(){
        Car corolla = new Car("Toyota", "Corolla");
        corolla.setCarId(111L);

        Car civic = new Car("Honda","Civic");
        Car gt3 = new Car("Porsche", "GT3");
        Car supra = new Car("Toyota", "Supra");

        this.corolla_aux = corolla;
        this.honda_aux = civic;

        List<Car> allCars = Arrays.asList(corolla,civic,gt3, supra);

        Mockito.when(carRepository.findByCarId(corolla.getCarId())).thenReturn(corolla);
        Mockito.when(carRepository.findByCarId(civic.getCarId())).thenReturn(civic);
        Mockito.when(carRepository.findByCarId(gt3.getCarId())).thenReturn(gt3);
        Mockito.when(carRepository.findByCarId(-99L)).thenReturn(null);
        Mockito.when(carRepository.findAll()).thenReturn(allCars);

        Mockito.when(carRepository.findByCarId(supra.getCarId())).thenReturn(supra);
    }

    @Test
    void whenValidId_thenCarShouldBeFound(){
        Optional<Car> fromDB = carService.getCarDetails(111L);
        assertThat(fromDB.get().getModel()).isEqualTo("Corolla");
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenInvalidId_thenCarShouldNotBeFound(){
        Optional<Car> fromDB = carService.getCarDetails(-99L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDB).isEmpty();
    }

    @Test
     void given3Cars_whengetAll_thenReturn3Records() {
        Car corolla = new Car("Toyota", "Corolla");
        Car civic = new Car("Honda","Civic");
        Car gt3 = new Car("Porsche", "GT3");
        Car supra = new Car("Toyota", "Supra");


        List<Car> allCars = carService.getAllCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(4).extracting(Car::getCarId).contains(corolla.getCarId(), civic.getCarId(), gt3.getCarId(), supra.getCarId());
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }


    ///d)
    @Test
    void givenCarWithExistingReplacement_thenReturnValidReplacement(){
        Optional<Car> fromDB = carService.getCarReplacement(corolla_aux);
        verifyFindAllCarsIsCalledOnce();
        assertThat(fromDB.get().getModel()).isEqualTo("Supra");
    }

    @Test
    void givenCarWithNoExistingReplacement_thenDontReturndReplacement(){
        Optional<Car> fromDB = carService.getCarReplacement(honda_aux);
        verifyFindAllCarsIsCalledOnce();
        assertThat(fromDB).isEmpty();
    }

}
