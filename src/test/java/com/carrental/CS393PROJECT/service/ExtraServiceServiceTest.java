package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

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
}