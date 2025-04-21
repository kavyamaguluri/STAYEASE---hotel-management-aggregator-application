Hotel Room Booking REST API
A secure, role-based RESTful API service built using Spring Boot and MySQL, designed to streamline the hotel room booking process for a hotel management aggregator system.

Problem Statement
Develop and deploy a REST API to manage hotel room bookings. The service supports user authentication and role-based access control using JWT tokens, allowing for registration, hotel and booking management, and room availability tracking.

Features
User Authentication & Authorization

Registration & Login

Password hashing using BCrypt

JWT-based stateless authentication

Role Management

Roles: CUSTOMER, HOTEL_MANAGER, ADMIN

Hotel Management

Public access to hotel listings

Admin: Create/Delete hotels

Manager: Update hotel details

Room Booking

Customer: Book room

Manager: Cancel bookings

Validation & Error Handling

Format validations, date validations, HTTP error codes

**Security**

Protected private endpoints

Access control based on user roles

Logging

Logs key events and errors

Tech Stack used

Java 17

Spring Boot

Spring Security

Spring Data JPA

MySQL

JWT (JSON Web Tokens)

Gradle

Postman

API Endpoints

User APIs
 Register User
POST /api/users/register

 Login User
POST /api/users/login

Hotel APIs
 Create Hotel (Admin Only)
POST /api/hotels

 Get All Hotels
GET /api/hotels

 Update Hotel (Manager Only)
PUT /api/hotels/{hotelId}

 Delete Hotel (Admin Only)
DELETE /api/hotels/{hotelId}

Booking APIs
 Book a Room 
POST /api/bookings/{hotelId}

 Get Booking Details 
GET /api/bookings/{bookingId}

 Cancel Booking 
DELETE /api/bookings/{bookingId}
