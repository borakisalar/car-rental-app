package com.carrental.CS393PROJECT.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sun.jdi.Location;

@Repository
public interface LocationService extends JpaRepository<Location, Long>{

}
