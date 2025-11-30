package com.carrental.CS393PROJECT.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cars")
public class Car {
	@Id
	private String barcode;

	private String licensePlateNumber;
	private int numberOfSeats;
	private String brand;
	private String model;
	private double mileage;

	private double dailyPrice;
	private CarCategory category;

	public Car() {

	}

	@Enumerated(EnumType.STRING)
	private CarStatus status;

	@Enumerated(EnumType.STRING)
	private TransmissionType transmissionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	public Car(String barcode, String licensePlateNumber, int numberOfSeats, String brand, String model,
			TransmissionType transmissionType, double dailyPrice, CarCategory category, Location location) {

		this.barcode = barcode;
		this.licensePlateNumber = licensePlateNumber;
		this.numberOfSeats = numberOfSeats;
		this.brand = brand;
		this.model = model;
		this.transmissionType = transmissionType;
		this.dailyPrice = dailyPrice;
		this.category = category;
		this.location = location;

		this.mileage = 0.0;
		this.status = CarStatus.AVAILABLE;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public double getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public CarCategory getCategory() {
		return category;
	}

	public void setCategory(CarCategory category) {
		this.category = category;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}

	public TransmissionType getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(TransmissionType transmissionType) {
		this.transmissionType = transmissionType;
	}

}
