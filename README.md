# STAYEASE---hotel-management-aggregator-application

A Spring Boot RESTful API to streamline the room booking process for a hotel management aggregator platform.
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
Future Enhancements
Project Overview
STAYEASE simplifies hotel booking by allowing users to:
Browse hotels
Book rooms
Administer hotel listings
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
JUnit 5 + Mockito + MockMvc
Features
User Registration and Login
Secure Password Storage using BCrypt
JWT Authentication for Stateless Sessions
Role-Based Access Control
Hotel CRUD Operations (Admin/Manager)
Booking Management (Customer)
Custom Exception Handling
Validation for Inputs
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
MySQL
Layers:
Controller → Service → Repository → MySQL (DB)
Security Layer for JWT Authentication
Installation & Run Locally
Prerequisites
Java 17+
Gradle
MySQL
Steps
Clone the repository
git clone https://github.com/kavyamaguluri/STAYEASE---hotel-management-aggregator-application
cd stayease
Configure MySQL database
Create a database:
sql
CREATE DATABASE stayease;
Update application.properties:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/stayease
spring.datasource.username=root
spring.datasource.password=yourpassword
Build the project
gradlew clean install
Run the application
gradlew bootrun
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
Authentication:
Use Authorization: Bearer { token} in headers for secured endpoints.
Authentication & Authorization
JWT is used for securing APIs.
Roles:
CUSTOMER: Can register, login, and book hotels.
HOTEL_MANAGER: Can update hotels, cancel bookings.
ADMIN: Can create and delete hotels.
Testing
MockMvc used to test controller endpoints.
Mockito used to mock service layer.
Basic unit tests cover Registration, Login, Hotel Creation, and Booking flows.
Logging
SLF4J (@Slf4j) is used for logging important actions and exceptions.
