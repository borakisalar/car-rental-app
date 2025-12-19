package com.carrental.CS393PROJECT.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/reservations")
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
	public ReservationResponseDTO makeReservation(@RequestBody ReservationRequestDTO request,
			HttpServletResponse response) {
		try {
			ReservationResponseDTO dto = reservationService.makeReservation(request);
			response.setStatus(HttpStatus.OK.value());
			return dto;
		} catch (IllegalStateException e) {
			response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			return null;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return null;
		}
	}

	@Operation(summary = "Get all rented cars", description = "List all cars that are currently rented")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = RentedCarDTO.class))),
			@ApiResponse(responseCode = "404", description = "No rented cars found", content = @Content) })
	@GetMapping("/rented")
	public List<RentedCarDTO> getAllRentedCars(HttpServletResponse response) {
		List<RentedCarDTO> cars = reservationService.getAllRentedCars();
		if (cars.isEmpty()) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		response.setStatus(HttpStatus.OK.value());
		return cars;
	}

	@Operation(summary = "Add an extra to a reservation", description = "Adds an extra service to an existing reservation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Extra added successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation Extra not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/{resNum}/extras")
	public Boolean addExtra(@PathVariable String resNum, @RequestParam Long extraCode, HttpServletResponse response) {
		try {
			boolean result = reservationService.addExtraToReservation(resNum, extraCode);
			response.setStatus(HttpStatus.OK.value());
			return result;
		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return false;
		}
	}

	@Operation(summary = "Return the car", description = "Completes the reservation and returns the car")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Car return completed successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation or Car not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/{resNum}/return")
	public Boolean returnCar(@PathVariable("resNum") String resNum, HttpServletResponse response) {
		try {
			boolean result = reservationService.returnCar(resNum);
			response.setStatus(HttpStatus.OK.value());
			return result;
		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return false;
		}
	}

	@Operation(summary = "Cancel a reservation", description = "Cancels an active reservation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reservation cancelled successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation number not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/{resNum}/cancel")
	public Boolean cancelReservation(@PathVariable("resNum") String resNum, HttpServletResponse response) {
		try {
			boolean result = reservationService.cancelReservation(resNum);
			response.setStatus(HttpStatus.OK.value());
			return result;
		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return false;
		}
	}

	@Operation(summary = "Delete a reservation", description = "Deletes a reservation record")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reservation deleted successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/{resNum}")
	public Boolean deleteReservation(@PathVariable("resNum") String resNum, HttpServletResponse response) {
		try {
			boolean result = reservationService.deleteReservation(resNum);
			response.setStatus(HttpStatus.OK.value());
			return result;
		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return false;
		}
	}
}