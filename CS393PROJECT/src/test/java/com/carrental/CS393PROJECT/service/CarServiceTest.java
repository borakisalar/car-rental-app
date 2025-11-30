package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

	@Mock
	private CarRepository carRepository;
	
	@Mock
    private ReservationRepository reservationRepository;

	@InjectMocks
	private CarService carService;

	@Test
	void registerCar_Success() {
		Car car = new Car();
		car.setBarcode("12345");
		when(carRepository.findById("12345")).thenReturn(Optional.empty());
		when(carRepository.save(car)).thenReturn(car);

		Car savedCar = carService.registerCar(car);

		assertNotNull(savedCar);
		assertEquals("12345", savedCar.getBarcode());
		verify(carRepository).save(car);
	}

	@Test
	void registerCar_AlreadyExists_ThrowsException() {
		Car car = new Car();
		car.setBarcode("12345");
		when(carRepository.findById("12345")).thenReturn(Optional.of(car));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			carService.registerCar(car);
		});

		assertEquals("Car with this barcode already exists.", exception.getMessage());
		verify(carRepository, never()).save(any(Car.class));
	}

	@Test
	void getAllCars_ReturnsList() {
		Car c1 = new Car();
		Car c2 = new Car();
		when(carRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

		List<Car> cars = carService.getAllCars();

		assertEquals(2, cars.size());
	}

	@Test
	void getCarByBarcode_Found() {
		Car car = new Car();
		car.setBarcode("EXISTING");
		when(carRepository.findById("EXISTING")).thenReturn(Optional.of(car));

		Car found = carService.getCarByBarcode("EXISTING");

		assertNotNull(found);
		assertEquals("EXISTING", found.getBarcode());
	}

	@Test
	void getCarByBarcode_NotFound_ThrowsException() {
		when(carRepository.findById("MISSING")).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			carService.getCarByBarcode("MISSING");
		});
	}
	
	@Test
    void deleteCar_WhenCarExistsAndNotUsed_ShouldReturnTrue() {
        String barcode = "12345";
        Car car = new Car();
        car.setBarcode(barcode);

        when(carRepository.findById(barcode)).thenReturn(Optional.of(car));
        when(reservationRepository.existsByCarBarcode(barcode)).thenReturn(false);

        boolean result = carService.deleteCar(barcode);

        assertTrue(result);
        verify(carRepository, times(1)).delete(car);
    }

    @Test
    void deleteCar_WhenCarIsUsedInReservation_ShouldThrowException() {
        String barcode = "12345";
        Car car = new Car();
        car.setBarcode(barcode);

        when(carRepository.findById(barcode)).thenReturn(Optional.of(car));
        when(reservationRepository.existsByCarBarcode(barcode)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carService.deleteCar(barcode);
        });

        assertEquals("Car cannot be deleted; it is used in a reservation.", exception.getMessage());
        
        verify(carRepository, never()).delete(any(Car.class));
    }

    @Test
    void deleteCar_WhenCarDoesNotExist_ShouldThrowException() {
        String barcode = "99999";

        when(carRepository.findById(barcode)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carService.deleteCar(barcode);
        });

        assertEquals("Car not found with barcode: " + barcode, exception.getMessage());
        
        verify(reservationRepository, never()).existsByCarBarcode(anyString());
    }
}