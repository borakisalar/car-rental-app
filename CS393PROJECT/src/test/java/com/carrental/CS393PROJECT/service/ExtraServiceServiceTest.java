package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.model.ExtraService;

@SpringBootTest
@Transactional
public class ExtraServiceServiceTest {

	@Autowired
	private ExtraServiceService extraServiceService;

	@Test
	void saveExtraService_Success() {
		ExtraService extra = new ExtraService();
		extra.setName("Heated Seat");
		extra.setPrice(80.0);

		ExtraService saved = extraServiceService.saveExtraService(extra);

		assertNotNull(saved.getId());
		assertEquals("Heated Seat", saved.getName());
	}
	
	@Test
	void getAllExtraServices_Success() {
		ExtraService extra1 = new ExtraService();
		extra1.setName("GPS Navigation");
		extra1.setPrice(15.0);
		extraServiceService.saveExtraService(extra1);

		ExtraService extra2 = new ExtraService();
		extra2.setName("Baby Seat");
		extra2.setPrice(10.0);
		extraServiceService.saveExtraService(extra2);

		List<ExtraService> services = extraServiceService.getAllExtraServices();

		assertNotNull(services, "The returned list should not be null");
		
		assertTrue(services.size() >= 2, "List should contain at least the 2 inserted services");
		
		boolean containsGPS = services.stream().anyMatch(e -> e.getName().equals("GPS Navigation"));
		boolean containsBabySeat = services.stream().anyMatch(e -> e.getName().equals("Baby Seat"));
		
		assertTrue(containsGPS, "List should contain the GPS Navigation extra");
		assertTrue(containsBabySeat, "List should contain the Baby Seat extra");
	}
}