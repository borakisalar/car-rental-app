package com.carrental.CS393PROJECT;

import com.carrental.CS393PROJECT.model.*;
import com.carrental.CS393PROJECT.service.CarService;
import com.carrental.CS393PROJECT.service.LocationService;
import com.carrental.CS393PROJECT.service.MemberService;
import com.carrental.CS393PROJECT.service.ReservationService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CarService carService;
    private final LocationService locationService;
    private final MemberService memberService;
    private final ReservationService reservationService;

    
    public DataInitializer(CarService carService, 
                           LocationService locationService, 
                           MemberService memberService, 
                           ReservationService reservationService) {
        this.carService = carService;
        this.locationService = locationService;
        this.memberService = memberService;
        this.reservationService = reservationService;
    }
    private void loadData() {
       
        Location loc1 = new Location();
        loc1.setName("Istanbul Airport");

        locationService.saveLocation(loc1);

        Location loc2 = new Location();
        loc2.setName("Sabiha Gokcen");

        locationService.saveLocation(loc2);

        
        Car car1 = new Car();
        car1.setBarcode("CAR001");
        car1.setBrand("Toyota");
        car1.setModel("Corolla");
        car1.setDailyPrice(1500.0);
        carService.registerCar(car1);

        Car car2 = new Car();
        car2.setBarcode("CAR002");
        car2.setBrand("BMW");
        car2.setModel("320i");
        carService.registerCar(car2);

        
        Member member1 = new Member();
        member1.setName("Ahmet");
        member1.setEmail("ahmet@test.com");
        member1.setPhone("5551234567");
        member1.setDrivingLicenseNumber("DL123456");
        memberService.registerMember(member1);

        Member member2 = new Member();
        member2.setName("Ayse");
        member2.setEmail("ayse@test.com");
        member2.setPhone("5559876543");
        member2.setDrivingLicenseNumber("DL654321");
        memberService.registerMember(member2);

        
        Reservation res = new Reservation();

        res.setPickUpDateTime(LocalDateTime.now().plusDays(1)); // Tomorrow
        res.setDropOffDateTime(LocalDateTime.now().plusDays(3)); // 3 days later
        res.setPickUpLocation(loc1);
        res.setDropOffLocation(loc1);
        reservationService.saveReservation(res);
    }
    @Override
    public void run(String... args) throws Exception {
        
        if (locationService.getAllLocations().isEmpty()) {
            loadData();
        }
    }

   
}