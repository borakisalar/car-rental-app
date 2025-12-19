package com.carrental.CS393PROJECT.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.CS393PROJECT.dto.CarDTO;
import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.TransmissionType;
import com.carrental.CS393PROJECT.service.CarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cars")
public class CarController {
	private final CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

	@Operation(summary = "Search available cars", description = "Search for cars based on availability and given data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Available cars found", content = @Content(schema = @Schema(implementation = CarDTO.class))),
			@ApiResponse(responseCode = "404", description = "No available cars found", content = @Content) })
	@GetMapping("/search")
	public List<CarDTO> searchCars(@RequestParam(name = "category", required = false) CarCategory category,
			@RequestParam(name = "transmission", required = false) TransmissionType transmission,
			@RequestParam(name = "minPrice", required = false) Double minPrice, @RequestParam(name = "maxPrice", required = false) Double maxPrice,
			@RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
			@RequestParam(name = "seats", required = false) Integer seats, @RequestParam(name = "pickupLocCode") Long pickupLocCode,
			HttpServletResponse response) {

		List<CarDTO> cars = carService.searchAvailableCars(category, transmission, minPrice, maxPrice, start, end,
				seats, pickupLocCode);

		if (cars.isEmpty()) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		response.setStatus(HttpStatus.OK.value());
		return cars;
	}

	@Operation(summary = "Delete a car", description = "Deletes a car if its available")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Car deleted successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "404", description = "Car not found", content = @Content),
			@ApiResponse(responseCode = "406", description = "Cannot be deleted - Car is used in a reservation", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/{barcode}")
	public Boolean deleteCar(@PathVariable("barcode") String barcode, HttpServletResponse response) {
		try {
			boolean deleted = carService.deleteCar(barcode);
			if (!deleted) {
				response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
				return false;
			}
			response.setStatus(HttpStatus.OK.value());
			return true;
		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return false;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return false;
		}
	}
}