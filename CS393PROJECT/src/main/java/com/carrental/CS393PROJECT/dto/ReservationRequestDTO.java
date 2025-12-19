package com.carrental.CS393PROJECT.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationRequestDTO {
	private String carBarcode;
	private LocalDateTime pickUpDateTime;
	private LocalDateTime dropOffDateTime;
	private Long memberId;
	private Long pickUpLocationCode;
	private Long dropOffLocationCode;
	private List<Long> extraIds;

	public String getCarBarcode() {
		return carBarcode;
	}

	public void setCarBarcode(String carBarcode) {
		this.carBarcode = carBarcode;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getPickUpLocationCode() {
		return pickUpLocationCode;
	}

	public void setPickUpLocationCode(Long pickUpLocationCode) {
		this.pickUpLocationCode = pickUpLocationCode;
	}

	public Long getDropOffLocationCode() {
		return dropOffLocationCode;
	}

	public void setDropOffLocationCode(Long dropOffLocationCode) {
		this.dropOffLocationCode = dropOffLocationCode;
	}

	public List<Long> getExtraIds() {
		return extraIds;
	}

	public void setExtraIds(List<Long> extraIds) {
		this.extraIds = extraIds;
	}
}