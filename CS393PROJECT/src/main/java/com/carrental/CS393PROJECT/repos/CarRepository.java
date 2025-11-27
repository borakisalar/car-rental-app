package com.carrental.CS393PROJECT.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrental.CS393PROJECT.model.Car;

public interface CarRepository extends JpaRepository<Car, Long>{

}
