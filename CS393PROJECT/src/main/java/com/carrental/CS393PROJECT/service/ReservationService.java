package com.carrental.CS393PROJECT.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.dto.RentedCarDTO;
import com.carrental.CS393PROJECT.dto.ReservationRequestDTO;
import com.carrental.CS393PROJECT.dto.ReservationResponseDTO;
import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.ExtraService;
import com.carrental.CS393PROJECT.model.Member;
import com.carrental.CS393PROJECT.model.Location;
import com.carrental.CS393PROJECT.model.Reservation;
import com.carrental.CS393PROJECT.model.ReservationStatus;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.ExtraServiceRepository;
import com.carrental.CS393PROJECT.repos.LocationRepository;
import com.carrental.CS393PROJECT.repos.MemberRepository;
import com.carrental.CS393PROJECT.repos.ReservationRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final CarRepository carRepository;
	private final ExtraServiceRepository extraServiceRepository;
	private final MemberRepository memberRepository;
	private final LocationRepository locationRepository;

	public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository,
			ExtraServiceRepository extraServiceRepository, MemberRepository memberRepository,
			LocationRepository locationRepository) {
		this.reservationRepository = reservationRepository;
		this.carRepository = carRepository;
		this.extraServiceRepository = extraServiceRepository;
		this.memberRepository = memberRepository;
		this.locationRepository = locationRepository;
	}

	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}

	public List<RentedCarDTO> getAllRentedCars() {
		LocalDateTime now = LocalDateTime.now();
		List<Reservation> activeReservations = reservationRepository.findCurrentlyRentedCars(now);

		return activeReservations.stream().map(res -> {
			RentedCarDTO dto = new RentedCarDTO();
			Car car = res.getCar();
			dto.setBrand(car.getBrand());
			dto.setModel(car.getModel());
			dto.setCarType(car.getCategory());
			dto.setTransmissionType(car.getTransmissionType());
			dto.setBarcode(car.getBarcode());
			dto.setReservationNumber(res.getReservationNumber());
			dto.setMemberName(res.getMember().getName());
			dto.setDropOffDateTime(res.getDropOffDateTime());
			dto.setDropOffLocationName(res.getDropOffLocation().getName());

			long dayCount = ChronoUnit.DAYS.between(res.getPickUpDateTime(), res.getDropOffDateTime());
			dto.setReservationDayCount(dayCount == 0 ? 1 : dayCount);
			return dto;
		}).collect(Collectors.toList());
	}

	private String generateReservationNumber() {
		Random rnd = new Random();
		int number = 10000000 + rnd.nextInt(90000000);
		return String.valueOf(number);
	}

	@Transactional
	public ReservationResponseDTO makeReservation(ReservationRequestDTO request) {
		Car car = carRepository.findById(request.getCarBarcode())
				.orElseThrow(() -> new RuntimeException("Car not found"));

		if (!car.getStatus().equals(CarStatus.AVAILABLE)) {
			throw new IllegalStateException("Car is not available.");
		}

		if (reservationRepository.isCarBookedInPeriod(request.getCarBarcode(), request.getPickUpDateTime(),
				request.getDropOffDateTime())) {
			throw new IllegalStateException("Car is already booked for these dates.");
		}

		Member member = memberRepository.findById(request.getMemberId())
				.orElseThrow(() -> new RuntimeException("Member not found"));

		Location pickUpLoc = locationRepository.findByCode(request.getPickUpLocationCode())
				.orElseThrow(() -> new RuntimeException("Pick up location not found"));

		Location dropOffLoc = locationRepository.findByCode(request.getDropOffLocationCode())
				.orElseThrow(() -> new RuntimeException("Drop off location not found"));

		Reservation reservation = new Reservation();
		reservation.setReservationNumber(generateReservationNumber());
		reservation.setCreationDate(LocalDateTime.now());
		reservation.setPickUpDateTime(request.getPickUpDateTime());
		reservation.setDropOffDateTime(request.getDropOffDateTime());
		reservation.setStatus(ReservationStatus.ACTIVE);
		reservation.setCar(car);
		reservation.setMember(member);
		reservation.setPickUpLocation(pickUpLoc);
		reservation.setDropOffLocation(dropOffLoc);

		List<ExtraService> extras = new ArrayList<>();
		double extrasTotal = 0;
		if (request.getExtraIds() != null) {
			for (Long extraId : request.getExtraIds()) {
				ExtraService extra = extraServiceRepository.findById(extraId)
						.orElseThrow(() -> new RuntimeException("Extra not found: " + extraId));
				extras.add(extra);
				extrasTotal += extra.getPrice();
			}
		}
		reservation.setExtras(extras);

		reservationRepository.save(reservation);

		long days = ChronoUnit.DAYS.between(request.getPickUpDateTime(), request.getDropOffDateTime());
		if (days == 0)
			days = 1;
		double totalAmount = (days * car.getDailyPrice()) + extrasTotal;

		ReservationResponseDTO response = new ReservationResponseDTO();
		response.setReservationNumber(reservation.getReservationNumber());
		response.setPickUpDateTime(reservation.getPickUpDateTime());
		response.setDropOffDateTime(reservation.getDropOffDateTime());
		response.setPickUpLocationCode(pickUpLoc.getCode());
		response.setPickUpLocationName(pickUpLoc.getName());
		response.setDropOffLocationCode(dropOffLoc.getCode());
		response.setDropOffLocationName(dropOffLoc.getName());
		response.setMemberId(member.getId());
		response.setMemberName(member.getName());
		response.setTotalAmount(totalAmount);

		return response;
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

	@Transactional
	public boolean returnCar(String reservationNumber) {
		Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
				.orElseThrow(() -> new RuntimeException("Reservation not found"));

		reservation.setStatus(ReservationStatus.COMPLETED);
		reservation.setReturnDate(LocalDateTime.now());

		Location carCurrentLoc = reservation.getCar().getLocation();
		Location dropOffLoc = reservation.getDropOffLocation();

		if (!carCurrentLoc.getCode().equals(dropOffLoc.getCode())) {
			Car car = reservation.getCar();
			car.setLocation(dropOffLoc);
			carRepository.save(car);
		}

		reservationRepository.save(reservation);
		return true;
	}

	public boolean deleteReservation(String reservationNumber) {
		Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
				.orElseThrow(() -> new RuntimeException("Reservation not found"));

		reservation.setCar(null);
		reservation.setMember(null);
		reservation.getExtras().clear();
		reservationRepository.save(reservation);

		reservationRepository.delete(reservation);
		return true;
	}
}
