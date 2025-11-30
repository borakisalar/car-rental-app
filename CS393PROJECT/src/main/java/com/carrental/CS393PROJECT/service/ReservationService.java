package com.carrental.CS393PROJECT.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.ExtraService;
import com.carrental.CS393PROJECT.model.Reservation;
import com.carrental.CS393PROJECT.model.ReservationStatus;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.ExtraServiceRepository;
import com.carrental.CS393PROJECT.repos.ReservationRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final CarRepository carRepository;
	private final ExtraServiceRepository extraServiceRepository;

	public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository,
			ExtraServiceRepository extraServiceRepository) {
		this.reservationRepository = reservationRepository;
		this.carRepository = carRepository;
		this.extraServiceRepository = extraServiceRepository;
	}

	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}

	public List<Reservation> getAllRentedCars() {
		return reservationRepository.findCurrentlyRentedCars(LocalDateTime.now());
	}

	private String generateReservationNumber() {
		Random rnd = new Random();
		int number = 10000000 + rnd.nextInt(90000000);
		return String.valueOf(number);
	}

	public Reservation makeReservation(String barcode, LocalDateTime pickupDate, LocalDateTime dropoffDate) {

		Car car = carRepository.findById(barcode).orElseThrow(() -> new RuntimeException("Car not found"));

		if (reservationRepository.isCarBookedInPeriod(barcode, pickupDate, dropoffDate)) {
			throw new RuntimeException("Car is already booked for these dates.");
		}

		Reservation reservation = new Reservation();
		reservation.setCar(car);
		reservation.setPickUpDateTime(pickupDate);
		reservation.setDropOffDateTime(dropoffDate);
		reservation.setCreationDate(LocalDateTime.now());
		reservation.setStatus(ReservationStatus.ACTIVE);

		reservation.setReservationNumber(generateReservationNumber());

		if (car.getStatus().equals(CarStatus.AVAILABLE)) {
			throw new RuntimeException("Car is not available (Status: " + car.getStatus() + ")");
		}

		return reservationRepository.save(reservation);
	}

	public boolean cancelReservation(String reservationNumber) {
		Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
				.orElseThrow(() -> new RuntimeException("Reservation not found with number: " + reservationNumber));

		reservation.setStatus(ReservationStatus.CANCELLED);

		reservationRepository.save(reservation);

		return true;
	}

	public boolean addExtraToReservation(String reservationNumber, Long extraCode) {

		Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
				.orElseThrow(() -> new RuntimeException("Reservation not found"));

		ExtraService extra = extraServiceRepository.findById(extraCode)
				.orElseThrow(() -> new RuntimeException("Extra not found with code: " + extraCode));

		if (reservation.getExtras().contains(extra)) {
			return false;
		}

		reservation.getExtras().add(extra);
		reservationRepository.save(reservation);

		return true;
	}
}
