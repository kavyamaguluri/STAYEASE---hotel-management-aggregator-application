# STAYEASE---hotel-management-aggregator-application

Problem Statement
Develop and deploy a RESTful API service using Spring Boot to streamline the room booking process for a hotel management aggregator application. You are required to use MySQL to persist the data.


Key Features
Please note that this is a simplified version of an online room booking system, and you should focus on implementing the specified features effectively within the given constraints

You can make the following assumptions:

The application only has a single type of room and all bookings are for two guests

Any hotel manager can update any hotel details i.e you do not have to keep track of who manages which hotel

Another service handles check-in and check-out functionalities

The service must implement authentication and authorization

The service uses JWT tokens for stateless authentication.

The service must have three roles: CUSTOMER, HOTEL MANAGER, and ADMIN

The service must have two types of API endpoints:

Public endpoints - Anyone can access (Ex. Registration, Login)

Private endpoints - Only authenticated users can access (Ex. Book a room)

Note: Some of the design choices are left to you. All design decisions such as designing the database schema, and providing resource access based on roles must have a thorough thought process behind them.

The API must have the following features:

User Registration and Login
Users must be able to register by providing their email address and, password

The password must be hashed and stored using BCrypt

Fields: Email, Password, First Name, Last Name, Role

The Role must be defaulted to "Customer" if it is not specified

A JWT token must be generated upon successful registration or login

Hotel Management
Store and manage hotel details

Fields: Hotel Name, Location, Description, Number of Available Rooms

The number of available rooms indicates whether a booking can be made or not

Anyone can browse all the available hotels (Public endpoint)

Only the administrator is allowed to create and delete hotels

The hotel manager can only update the hotel details

Booking Management
Customers must be able to book rooms using the service

A single room can be booked per request

Only hotel managers are allowed to cancel the booking

Business Rules
Users can be of three types: USER, HOTEL_MANAGER, ADMIN

Only admins can create and delete hotels

Only managers can update hotel details and cancel bookings

Customers cannot cancel their bookings

Hotels track available rooms and prevent overbooking

Dates must be in format: YYYY-MM-DD

The check-in date must be a future date

The check-out date must be after the check-in date


Validation and Error Handling
Handle common errors and return appropriate HTTP codes (Ex. 404, User not found)

Additional Requirements
Use logs to log information and errors

Handle common errors gracefully and return appropriate HTTP codes (Ex. 404, User not found)

Include basic unit tests while making use of MockMvc and Mockito (Minimum 3)

Publish your code to a public GitHub repository

Write meaningful, incremental commit messages

Include a descriptive README.MD for your application codebase

Generate a JAR file for your application and provide instructions on how to run it

Create and add a public Postman Collection in the README.MD (Optional)


Endpoints
POST /hotels/{hotelId}/book - For making a booking

DELETE /bookings/{bookingId} - For cancelling a booking

You are required to design other RESTful endpoints based on the requirements


Publishing and Documentation
Write meaningful commit messages

Include a descriptive README.MD for your application codebase

Create and add a public Postman Collection in the README.MD


Additional Notes
Implement the solution using a layered approach - Ex. Entity, Controller, Service, Repository

User API Documentation
Base URL
http://localhost:8081/

Endpoints
1. Register User
Endpoint:

POST /api/users/register

Request Body:


{

  "email": "user@example.com",

  "password": "Test@123!",

  "firstName": "John",

  "lastName": "Doe",

  "role": "ROLE"

}

ROLE defaults to "USER". Can be "ADMIN", "HOTEL_MANAGER", "USER"

Password Requirements:

At least 8 characters

Must contain special character

Must contain uppercase letter

Must contain number

Response: (200 OK)


{

  "token": "jwt-token-here"

}

Error Codes:

400: Invalid input (password/email format)

404: User already exists

2. User Login
Endpoint:

POST /api/users/login

Request Body:


{

  "email": "user@example.com",

  "password": "Test@123!"

}

Response: (200 OK)


{

  "token": "jwt-token-here"

}

Error Codes:

404: Invalid credentials
3. Create Hotel (Admin Only)
Endpoint:

POST /api/hotels

Headers Required:

Authorization: Bearer {adminToken}
Request Body:


{

  "name": "Hotel Name",

  "location": "Hotel Location",

  "description": "Hotel Description",

  "totalRooms": 10,

  "availableRooms": 10

}

Response: (200 OK)


{

  "id": 1,

  "name": "Hotel Name",

  "location": "Hotel Location",

  "description": "Hotel Description",

  "totalRooms": 10,

  "availableRooms": 10

}

Error Codes:

401: Unauthorized

403: Forbidden (non-admin user)

4. Get All Hotels (Public)
Endpoint:

GET /api/hotels

Response: (200 OK)


{

[

  {

    "id": 1,

    "name": "Hotel Name",

    "location": "Location",

    "description": "Description",

    "availableRooms": 10

  }

]

}

5. Update Hotel (Manager Only)
Endpoint:

PUT /api/hotels/{hotel_Id}

Headers Required:

Authorization: Bearer {managerToken}
Request Body:


{

  "name": "Updated Name",

  "availableRooms": 15

}

Response: (200 OK)


{

  "id": 1,

  "name": "Updated Name",

  "availableRooms": 15

}

Error Codes:

401: Unauthorized

404: Hotel not found

6.Delete Hotel (Admin Only)
Endpoint:

DELETE /api/hotels/{hotel_Id}

Headers Required:

Authorization: Bearer {adminToken}
Response: (204 No Content)

Error Codes:

401: Unauthorized

404: Hotel not found

7. Create Booking
Endpoint:

POST /api/bookings/{hotel_Id}

Headers Required:

Authorization: Bearer {customerToken}
Request Body:


{

  "checkInDate": "2025-02-20",

  "checkOutDate": "2025-02-22"

}

Response: (200 OK)


{

  "bookingId": 1,

  "hotelId": 1,

  "checkInDate": "2025-02-20",

  "checkOutDate": "2025-02-22"

}

Error Codes:

401: Unauthorized

404: No rooms available/Hotel not found

8. Get Booking Details
Endpoint:

GET /api/bookings/{booking_Id}

Headers Required:

Authorization: Bearer {customerToken}
Response: (200 OK)


{

  "bookingId": 1,

  "hotelId": 1,

  "checkInDate": "2025-02-20",

  "checkOutDate": "2025-02-22"

}

Error Codes:

401: Unauthorized

404: Booking not found

9. Cancel Booking (Manager Only)
Endpoint:

DELETE /api/bookings/{booking_Id}

Headers Required:

Authorization: Bearer {managerToken}
Response: (204 No Content)

Error Codes:

401: Unauthorized (customer or invalid token)

404: Booking not found
