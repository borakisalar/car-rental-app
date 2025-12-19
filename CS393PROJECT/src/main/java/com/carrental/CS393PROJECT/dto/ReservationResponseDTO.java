package com.carrental.CS393PROJECT.dto;

import java.time.LocalDateTime;

public class ReservationResponseDTO {
	private String reservationNumber;
	private LocalDateTime pickUpDateTime;
	private LocalDateTime dropOffDateTime;
	private Long pickUpLocationCode;
	private String pickUpLocationName;
	private Long dropOffLocationCode;
	private String dropOffLocationName;
	private double totalAmount;
	private Long memberId;
	private String memberName;

	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
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

	public Long getPickUpLocationCode() {
		return pickUpLocationCode;
	}

	public void setPickUpLocationCode(Long pickUpLocationCode) {
		this.pickUpLocationCode = pickUpLocationCode;
	}

	public String getPickUpLocationName() {
		return pickUpLocationName;
	}

	public void setPickUpLocationName(String pickUpLocationName) {
		this.pickUpLocationName = pickUpLocationName;
	}

	public Long getDropOffLocationCode() {
		return dropOffLocationCode;
	}

	public void setDropOffLocationCode(Long dropOffLocationCode) {
		this.dropOffLocationCode = dropOffLocationCode;
	}

	public String getDropOffLocationName() {
		return dropOffLocationName;
	}

	public void setDropOffLocationName(String dropOffLocationName) {
		this.dropOffLocationName = dropOffLocationName;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}