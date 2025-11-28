package com.carrental.CS393PROJECT.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Locations")
public class Location {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long code;
	
	private String name;
	
	@OneToMany(mappedBy = "location") 
    private List<Car> cars;
	public Location() {
		
	}
	
	public Location(Long code, String name) {
        this.code = code;
        this.name = name;
        
    }
	
	public Long getCode() {
		return code;
	}
	
	public void setCode(Long code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
