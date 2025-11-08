package com.carrental.CS393PROJECT.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reservation {
	private String reservationNumber;
	private LocalDateTime creationDate;
	private LocalDateTime pickUpDateTime;
	private LocalDateTime dropOffDateTime;
	private Location pickUpLocation;
	private Location dropOffLocation;
	private LocalDateTime returnDate;
	private ReservationStatus status;
	private Member member;
	private Car car;
	private ArrayList<ExtraService> extras;
	
	public Reservation(String reservationNumber, LocalDateTime pickUpDateTime, LocalDateTime dropOffDateTime,
			Member member, Car car, Location pickUpLocation, Location dropOffLocation, ArrayList<ExtraService> extras) {

		this.reservationNumber = reservationNumber;
		this.pickUpDateTime = pickUpDateTime;
		this.dropOffDateTime = dropOffDateTime;
		this.member = member;
		this.car = car;
		this.pickUpLocation = pickUpLocation;
		this.dropOffLocation = dropOffLocation;
		this.extras = extras;

		this.creationDate = LocalDateTime.now();
		this.status = ReservationStatus.ACTIVE;
		this.returnDate = null;
	}

	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getPickUpDateTime() {
		return pickUpDateTime;
	}

	public void setPickUpDateTime(LocalDateTime pickUpDateTime) {
		this.pickUpDateTime = pickUpDateTime;
	}

	public LocalDateTime getDropOffDateTime() {
		return dropOffDateTime;
	}

	public void setDropOffDateTime(LocalDateTime dropOffDateTime) {
		this.dropOffDateTime = dropOffDateTime;
	}

	public Location getPickUpLocation() {
		return pickUpLocation;
	}

	public void setPickUpLocation(Location pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}

	public Location getDropOffLocation() {
		return dropOffLocation;
	}

	public void setDropOffLocation(Location dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}

	public LocalDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}
}
