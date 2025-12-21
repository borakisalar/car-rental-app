package com.carrental.CS393PROJECT.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.dto.CarDTO;
import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.CarStatus;
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

	public boolean deleteCar(String barcode) {
		Car car = getCarByBarcode(barcode);

		boolean isUsed = reservationRepository.existsByCarBarcode(barcode);
		if (isUsed) {
			return false;
		}

		carRepository.delete(car);

		return true;
	}

	public List<CarDTO> searchAvailableCars(CarCategory category, TransmissionType transmission, Double minPrice,
			Double maxPrice, LocalDateTime start, LocalDateTime end, Integer seats, Long pickupLocCode) {

		List<Car> allCars = carRepository.findAll();

		return allCars.stream().filter(car -> car.getStatus() == CarStatus.AVAILABLE)
				.filter(car -> pickupLocCode == null || car.getLocation().getCode().equals(pickupLocCode))
				.filter(car -> category == null || car.getCategory() == category)
				.filter(car -> transmission == null || car.getTransmissionType() == transmission)
				.filter(car -> seats == null || car.getNumberOfSeats() >= seats)
				.filter(car -> (minPrice == null || car.getDailyPrice() >= minPrice)
						&& (maxPrice == null || car.getDailyPrice() <= maxPrice))
				.filter(car -> !reservationRepository.isCarBookedInPeriod(car.getBarcode(), start, end))
				.map(this::convertToDTO).collect(Collectors.toList());
	}

	private CarDTO convertToDTO(Car car) {
		CarDTO dto = new CarDTO();
		dto.setBarcode(car.getBarcode());
		dto.setBrand(car.getBrand());
		dto.setModel(car.getModel());
		dto.setCategory(car.getCategory());
		dto.setDailyPrice(car.getDailyPrice());
		dto.setLicensePlateNumber(car.getLicensePlateNumber());
		dto.setMileage(car.getMileage());
		dto.setNumberOfSeats(car.getNumberOfSeats());
		dto.setStatus(car.getStatus());
		dto.setTransmissionType(car.getTransmissionType());
		dto.setLocationCode(car.getLocation().getCode());
		return dto;
	}
}
