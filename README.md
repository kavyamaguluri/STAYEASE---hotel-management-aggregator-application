STAYEASE — Hotel Management Aggregator Platform
A Spring Boot RESTful API that streamlines the room booking process for a hotel management aggregator platform.
It handles User Registration, Authentication using JWT, Role-Based Authorization, Hotel Management, and Booking Management.

Table of Contents
Project Overview

Tech Stack

Features

System Design

Installation & Run Locally

API Endpoints

Authentication & Authorization

Testing

Logging

Project Overview
STAYEASE simplifies hotel booking by allowing users to:

Browse hotels

Book rooms

Manage hotel listings

Manage room availability

It enforces role-based access (CUSTOMER, HOTEL_MANAGER, ADMIN) and uses JWT-based authentication for secure access.

Tech Stack
Java 17

Spring Boot 3

Spring Security

JWT (JSON Web Token)

MySQL

Hibernate (JPA)

Lombok

Gradle

JUnit 5, Mockito, MockMvc

Features
User Registration and Login

Secure Password Storage using BCrypt

JWT Authentication for Stateless Sessions

Role-Based Access Control

Hotel CRUD Operations (Admin/Manager)

Booking Management (Customer)

Custom Exception Handling

Input Validation

Logging with SLF4J

Unit Testing using MockMvc and Mockito

System Design

[ User (Frontend / Postman) ] 
         |
       (HTTP)
         ↓
[ Spring Boot Controller Layer ]
         |
     Service Layer
         |
    Repository Layer
         |
       MySQL (Database)
       
       Security Layer: Handles JWT Authentication and Authorization.
Installation & Run Locally
Prerequisites
Java 17+

Gradle

MySQL

Steps
Clone the Repository      
git clone https://github.com/kavyamaguluri/STAYEASE---hotel-management-aggregator-application
cd stayease

Configure MySQL Database
CREATE DATABASE stayease;

Update application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/stayease
spring.datasource.username=root
spring.datasource.password=yourpassword

Build the Project
./gradlew clean install

Run the Application
./gradlew bootRun
API Base URL: http://localhost:8081/

API Endpoints

Feature	Method	Endpoint	Access
Register User	POST	/api/users/register	Public
User Login	POST	/api/users/login	Public
Create Hotel	POST	/api/hotels	Admin Only
Get All Hotels	GET	/api/hotels	Public
Update Hotel	PUT	/api/hotels/{hotelId}	Hotel Manager Only
Delete Hotel	DELETE	/api/hotels/{hotelId}	Admin Only
Book a Room	POST	/api/bookings/{hotelId}	Customer Only
Cancel Booking	DELETE	/api/bookings/{bookingId}	Hotel Manager Only
Get Booking Details	GET	/api/bookings/{bookingId}	Customer Only

Authentication & Authorization
Authentication: JWT tokens used to secure endpoints.

Authorization: Based on user roles:

CUSTOMER: Register, login, book hotels.

HOTEL_MANAGER: Update hotels, cancel bookings.

ADMIN: Create and delete hotels.

Authorization Header:
Authorization: Bearer {token}

Testing
MockMvc: Used for testing controller endpoints.

Mockito: Used to mock service layer and isolate unit tests.

Coverage:

User Registration

User Login

Hotel Creation

Booking Flow

Logging
SLF4J (via @Slf4j) is used for:

Logging important events

Capturing exceptions

Monitoring API activities
