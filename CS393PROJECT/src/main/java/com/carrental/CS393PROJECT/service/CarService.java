package com.carrental.CS393PROJECT.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.TransmissionType;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.ReservationRepository;

@Service
public class CarService {
	private final CarRepository carRepository;
	private final ReservationRepository reservationRepository;

	public CarService(CarRepository carRepository, ReservationRepository reservationRepository) {
		this.carRepository = carRepository;
		this.reservationRepository = reservationRepository;
	}

	public Car registerCar(Car car) {
		if (carRepository.findById(car.getBarcode()).isPresent()) {
			throw new RuntimeException("Car with this barcode already exists.");
		}
		return carRepository.save(car);
	}

	public List<Car> getAllCars() {
		return carRepository.findAll();
	}

	public Car getCarByBarcode(String barcode) {
		return carRepository.findById(barcode)
				.orElseThrow(() -> new RuntimeException("Car not found with barcode: " + barcode));
	}

	public List<Car> searchAvailableCars(String location, CarCategory category, TransmissionType transmission,
			Double minPrice, Double maxPrice, Integer seatCount, LocalDateTime pickupDate, LocalDateTime dropoffDate) {

		if (pickupDate == null || dropoffDate == null || location == null) {
			throw new IllegalArgumentException("Location and Dates are required.");
		}
		if (pickupDate.isAfter(dropoffDate)) {
			throw new IllegalArgumentException("Pickup date must be before drop-off date.");
		}

		return carRepository.searchAvailableCars(location, category, transmission, minPrice, maxPrice, seatCount,
				pickupDate, dropoffDate);
	}

	public boolean deleteCar(String barcode) {
		Car car = getCarByBarcode(barcode);

		boolean isUsed = reservationRepository.existsByCarBarcode(barcode);
		if (isUsed) {
			throw new RuntimeException("Car cannot be deleted; it is used in a reservation.");
		}

		carRepository.delete(car);

		return true;
	}
}
