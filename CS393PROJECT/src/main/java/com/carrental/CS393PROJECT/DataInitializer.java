package com.carrental.CS393PROJECT;

import com.carrental.CS393PROJECT.model.*;
import com.carrental.CS393PROJECT.service.CarService;
import com.carrental.CS393PROJECT.service.ExtraServiceService;
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
	private final ExtraServiceService extraServiceService;

	public DataInitializer(CarService carService, LocationService locationService, MemberService memberService,
			ReservationService reservationService, ExtraServiceService extraServiceService) {
		this.carService = carService;
		this.locationService = locationService;
		this.memberService = memberService;
		this.reservationService = reservationService;
		this.extraServiceService = extraServiceService;
	}

	private void loadData() {

		Location loc1 = new Location();
		loc1.setName("Istanbul Airport");
		locationService.saveLocation(loc1);

		Location loc2 = new Location();
		loc2.setName("Sabiha Gokcen");
		locationService.saveLocation(loc2);

		Location loc3 = new Location();
		loc3.setName("Istanbul Kadikoy");
		locationService.saveLocation(loc3);

		Location loc4 = new Location();
		loc4.setName("Izmir City Center");
		locationService.saveLocation(loc4);

		Car car1 = new Car();
		car1.setBarcode("CAR001");
		car1.setLicensePlateNumber("34ABC123");
		car1.setBrand("Toyota");
		car1.setModel("Corolla");
		car1.setNumberOfSeats(5);
		car1.setMileage(15000);
		car1.setTransmissionType(TransmissionType.AUTOMATIC);
		car1.setCategory(CarCategory.COMPACT_CAR);
		car1.setStatus(CarStatus.BEING_SERVICED);
		car1.setDailyPrice(1500.0);
		car1.setLocation(loc1);
		carService.registerCar(car1);

		Car car2 = new Car();
		car2.setBarcode("CAR002");
		car2.setLicensePlateNumber("22ABV153");
		car2.setBrand("BMW");
		car2.setModel("320i");
		car2.setNumberOfSeats(5);
		car2.setMileage(55000);
		car2.setTransmissionType(TransmissionType.MANUAL);
		car2.setCategory(CarCategory.LUXURY_CAR);
		car2.setStatus(CarStatus.AVAILABLE);
		car2.setDailyPrice(1800.0);
		car2.setLocation(loc3);
		carService.registerCar(car2);

		Car car3 = new Car();
		car3.setBarcode("CAR003");
		car3.setLicensePlateNumber("34BJK1903");
		car3.setBrand("Mercedes");
		car3.setModel("Vito");
		car3.setNumberOfSeats(10);
		car3.setMileage(90000);
		car3.setTransmissionType(TransmissionType.MANUAL);
		car3.setCategory(CarCategory.MINIVAN);
		car3.setStatus(CarStatus.AVAILABLE);
		car3.setDailyPrice(2200.0);
		car3.setLocation(loc2);
		carService.registerCar(car3);

		Member member1 = new Member();
		member1.setName("Ahmet");
		member1.setAddress("Kadikoy, Istanbul");
		member1.setEmail("ahmet@test.com");
		member1.setPhone("5551234567");
		member1.setDrivingLicenseNumber("DL123456");
		memberService.registerMember(member1);

		Member member2 = new Member();
		member2.setName("Ayse");
		member2.setAddress("Beyoglu, Istanbul");
		member2.setEmail("ayse@test.com");
		member2.setPhone("5559876543");
		member2.setDrivingLicenseNumber("DL654321");
		memberService.registerMember(member2);

		Member member3 = new Member();
		member3.setName("Mehmet");
		member3.setAddress("Cankaya, Ankara");
		member3.setEmail("mehmet@test.com");
		member3.setPhone("5557778899");
		member3.setDrivingLicenseNumber("DL789012");
		memberService.registerMember(member3);

		ExtraService es = new ExtraService();
		es.setName("GPS");
		es.setPrice(50.0);
		extraServiceService.saveExtraService(es);

		ExtraService es1 = new ExtraService();
		es1.setName("Touch Screen");
		es1.setPrice(150.0);
		extraServiceService.saveExtraService(es1);

		ExtraService es2 = new ExtraService();
		es2.setName("WIFI");
		es2.setPrice(25.0);
		extraServiceService.saveExtraService(es2);

		ExtraService es3 = new ExtraService();
		es3.setName("Snow Tires");
		es3.setPrice(200.0);
		extraServiceService.saveExtraService(es3);

		Reservation res1 = new Reservation();

		res1.setReservationNumber("12345678");
		res1.setCreationDate(LocalDateTime.now());
		res1.setPickUpDateTime(LocalDateTime.now().minusDays(1));
		res1.setDropOffDateTime(LocalDateTime.now().plusDays(3));
		res1.setPickUpLocation(loc1);
		res1.setDropOffLocation(loc1);
		res1.setStatus(ReservationStatus.ACTIVE);
		res1.setMember(member3);
		res1.setCar(car1);

		res1.getExtras().add(es);
		res1.getExtras().add(es2);

		reservationService.saveReservation(res1);

		Reservation res2 = new Reservation();

		res2.setReservationNumber("87654321");
		res2.setCreationDate(LocalDateTime.now().minusDays(10));
		res2.setPickUpDateTime(LocalDateTime.now().minusDays(8));
		res2.setDropOffDateTime(LocalDateTime.now().minusDays(5));
		res2.setReturnDate(LocalDateTime.now().minusDays(5));
		res2.setPickUpLocation(loc3);
		res2.setDropOffLocation(loc4);
		res2.setStatus(ReservationStatus.COMPLETED);
		res2.setMember(member2);
		res2.setCar(car2);
		reservationService.saveReservation(res2);

		res2.getExtras().add(es3);
		res2.getExtras().add(es1);
	}

	@Override
	public void run(String... args) throws Exception {

		if (locationService.getAllLocations().isEmpty()) {
			loadData();
		}
	}

}