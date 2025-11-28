package com.carrental.CS393PROJECT.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.model.Location;
import com.carrental.CS393PROJECT.repos.LocationRepository;

@Service
public class LocationService {
	private final LocationRepository locationRepository;
	
	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	
	public Location saveLocation(Location location) {
		return locationRepository.save(location);
	}
	
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}
	
	
}
