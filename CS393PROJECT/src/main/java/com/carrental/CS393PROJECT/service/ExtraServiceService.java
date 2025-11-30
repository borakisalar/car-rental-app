package com.carrental.CS393PROJECT.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.model.ExtraService;
import com.carrental.CS393PROJECT.repos.ExtraServiceRepository;

@Service
public class ExtraServiceService {
	private final ExtraServiceRepository extraServiceRepository;
	
	public ExtraServiceService(ExtraServiceRepository extraServiceRepository) {
		this.extraServiceRepository = extraServiceRepository;
	}
	
	public ExtraService saveExtraService(ExtraService extraService) {
		return extraServiceRepository.save(extraService);
	}
	
	public List<ExtraService> getAllExtraServices() {
		return extraServiceRepository.findAll();
	}
	
	
}
