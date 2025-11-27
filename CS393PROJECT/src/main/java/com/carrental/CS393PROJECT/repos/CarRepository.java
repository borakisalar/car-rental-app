package com.carrental.CS393PROJECT.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrental.CS393PROJECT.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, String>{

}
