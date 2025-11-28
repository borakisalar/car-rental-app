package com.carrental.CS393PROJECT.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.model.Reservation;
import com.carrental.CS393PROJECT.repos.ReservationRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}
	
	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}
}
