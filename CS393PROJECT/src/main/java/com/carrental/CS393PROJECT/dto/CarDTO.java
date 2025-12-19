package com.carrental.CS393PROJECT.dto;

import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.TransmissionType;

public class CarDTO {
	private String barcode;
	private String licensePlateNumber;
	private int numberOfSeats;
	private String brand;
	private String model;
	private double mileage;
	private double dailyPrice;
	private CarCategory category;
	private CarStatus status;
	private TransmissionType transmissionType;
	private Long locationCode;

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

	public Long getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(Long locationCode) {
		this.locationCode = locationCode;
	}
}