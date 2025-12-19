package com.carrental.CS393PROJECT.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.CS393PROJECT.dto.RentedCarDTO;
import com.carrental.CS393PROJECT.dto.ReservationRequestDTO;
import com.carrental.CS393PROJECT.dto.ReservationResponseDTO;
import com.carrental.CS393PROJECT.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@Operation(summary = "Make a reservation", description = "Creates a new reservation if the car is available")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reservation completed successfully", content = @Content(schema = @Schema(implementation = ReservationResponseDTO.class))),
			@ApiResponse(responseCode = "406", description = "Car is not available", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping
	public ResponseEntity<ReservationResponseDTO> makeReservation(@RequestBody ReservationRequestDTO request) {
		try {
			ReservationResponseDTO response = reservationService.makeReservation(request);
			return ResponseEntity.ok(response);
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "Get all rented cars", description = "List all cars that are currently rented")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = RentedCarDTO.class))),
			@ApiResponse(responseCode = "404", description = "No rented cars found", content = @Content) })
	@GetMapping("/rented")
	public ResponseEntity<List<RentedCarDTO>> getAllRentedCars() {
		List<RentedCarDTO> cars = reservationService.getAllRentedCars();
		if (cars.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cars);
	}

	@Operation(summary = "Add an extra to a reservation", description = "Adds an extra service to an existing reservation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Extra added successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation or Extra not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/{resNum}/extras")
	public ResponseEntity<Boolean> addExtra(@PathVariable String resNum, @RequestParam Long extraCode) {
		try {
			boolean result = reservationService.addExtraToReservation(resNum, extraCode);
			return ResponseEntity.ok(result);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "Return the car", description = "Completes the reservation and updates car location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Car return completed successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/{resNum}/return")
	public ResponseEntity<Boolean> returnCar(@PathVariable String resNum) {
		try {
			boolean result = reservationService.returnCar(resNum);
			return ResponseEntity.ok(result);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "Cancel a reservation", description = "Cancels an active reservation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reservation cancelled successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/{resNum}/cancel")
	public ResponseEntity<Boolean> cancelReservation(@PathVariable String resNum) {
		try {
			boolean result = reservationService.cancelReservation(resNum);
			return ResponseEntity.ok(result);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Operation(summary = "Delete a reservation", description = "Deletes a reservation record")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reservation deleted successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/{resNum}")
	public ResponseEntity<Boolean> deleteReservation(@PathVariable String resNum) {
		try {
			boolean result = reservationService.deleteReservation(resNum);
			return ResponseEntity.ok(result);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}