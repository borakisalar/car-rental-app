# Car Rental System - CS393 PROJECT

A comprehensive Car Rental Management System built with Spring Boot, Java, and MySQL. This project provides a robust backend for managing car fleets, members, reservations, and location-based rentals.

## Features

- **Car Management**: Track car details, categories (Economy, SUV, Luxury, etc.), and availability.
- **Member Management**: Manage user profiles and registration.
- **Advanced Reservation System**: 
  - Search for available cars based on location, dates, and specifications.
  - Book cars with optional extra services (GPS, Child Seat, Insurance, etc.).
  - Real-time status tracking (Pending, Confirmed, Completed, Cancelled).
- **Location Services**: Multi-location support for pick-up and drop-off.
- **Automated Data Seeding**: Pre-loaded with initial data for cars, locations, and members via `DataInitializer`.
- **System Architecture**: UML diagram available in `umldiagram.png`.

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.5.7
- **Database**: MySQL 8.0
- **Persistence**: Spring Data JPA / Hibernate
- **Build Tool**: Gradle
- **Documentation**: Swagger UI / OpenAPI 3

## Prerequisites

- **Java JDK 17** or higher
- **MySQL Server**
- **Gradle** (included via wrapper)

## Configuration

1. **Database Setup**:
   - Create a database named `car_rental_db` in MySQL.
   - Update `src/main/resources/application.properties` with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/car_rental_db?createDatabaseIfNotExist=true
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

2. **Server Port**:
   - The application runs on port `8081` by default.

## Getting Started

To run the application locally:

```bash
./gradlew bootRun
```

The application will automatically initialize the database schema and seed it with sample data.

## API Documentation

Once the application is running, you can access the interactive API documentation at:

- **Swagger UI**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
- **OpenAPI JSON**: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

## Project Structure

- `com.carrental.CS393PROJECT.controller`: REST API endpoints.
- `com.carrental.CS393PROJECT.service`: Business logic layer.
- `com.carrental.CS393PROJECT.model`: JPA entities (Car, Member, Reservation, etc.).
- `com.carrental.CS393PROJECT.repos`: Data access layer.
- `com.carrental.CS393PROJECT.dto`: Data Transfer Objects for API communication.
- `com.carrental.CS393PROJECT.DataInitializer`: Bootstraps the application with initial data.
