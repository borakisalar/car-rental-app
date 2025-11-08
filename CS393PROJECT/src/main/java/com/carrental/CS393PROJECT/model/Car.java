package com.carrental.CS393PROJECT.model;


public class Car {
	private String barcode;
	private String licensePlateNumber;
	private int numberOfSeats;
	private String brand;
	private String model;
	private double mileage;
	private TransmissionType transmissionType;
	private double dailyPrice;
	private String category;
	private Location location;
	private CarStatus status;
	
	public Car(String barcode, String licensePlateNumber, int numberOfSeats, String brand, String model,
			TransmissionType transmissionType, double dailyPrice, String category, Location location) {

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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
}
