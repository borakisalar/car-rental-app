package com.carrental.CS393PROJECT.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "Reservations")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 8)
	private String reservationNumber;
	
	
	private LocalDateTime creationDate;
	private LocalDateTime pickUpDateTime;
	private LocalDateTime dropOffDateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_location_id")
	private Location pickUpLocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dropoff_location_id")
	private Location dropOffLocation;
	
	private LocalDateTime returnDate;
	private ReservationStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "car_id")
	private Car car;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "reservation_extras",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "extra_id")
    )
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
