package com.carrental.CS393PROJECT.dto;

import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.TransmissionType;
import java.time.LocalDateTime;

public class RentedCarDTO {
	private String brand;
	private String model;
	private CarCategory carType;
	private TransmissionType transmissionType;
	private String barcode;
	private String reservationNumber;
	private String memberName;
	private LocalDateTime dropOffDateTime;
	private String dropOffLocationName;
	private long reservationDayCount;

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

	public CarCategory getCarType() {
		return carType;
	}

	public void setCarType(CarCategory carType) {
		this.carType = carType;
	}

	public TransmissionType getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(TransmissionType transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public LocalDateTime getDropOffDateTime() {
		return dropOffDateTime;
	}

	public void setDropOffDateTime(LocalDateTime dropOffDateTime) {
		this.dropOffDateTime = dropOffDateTime;
	}

	public String getDropOffLocationName() {
		return dropOffLocationName;
	}

	public void setDropOffLocationName(String dropOffLocationName) {
		this.dropOffLocationName = dropOffLocationName;
	}

	public long getReservationDayCount() {
		return reservationDayCount;
	}

	public void setReservationDayCount(long reservationDayCount) {
		this.reservationDayCount = reservationDayCount;
	}
}