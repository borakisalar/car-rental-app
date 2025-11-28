package com.carrental.CS393PROJECT.service;

import java.security.Identity;
import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.ReservationRepository;

import ch.qos.logback.core.joran.conditional.IfAction;

@Service
public class CarService {
	private final CarRepository carRepository;
	
	public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
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
		return carRepository.findById(barcode).orElseThrow(() -> new RuntimeException("Car not found with barcode: " + barcode));
	}
	
}
