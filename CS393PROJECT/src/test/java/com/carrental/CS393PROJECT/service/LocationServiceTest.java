package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.model.Location;

@SpringBootTest
@Transactional
public class LocationServiceTest {

	@Autowired
	private LocationService locationService;

	@Test
	void saveLocation_Success() {
		Location loc = new Location();
		loc.setName("Istanbul Airport");

		Location saved = locationService.saveLocation(loc);

		assertNotNull(saved.getCode());
		assertEquals("Istanbul Airport", saved.getName());
	}

	@Test
	void getAllLocations_ReturnsList() {
		Location loc = new Location();
		loc.setName("Sabiha Gokcen");
		locationService.saveLocation(loc);

		List<Location> locations = locationService.getAllLocations();
		assertFalse(locations.isEmpty());
	}
}