package com.takehome.stayease.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.takehome.stayease.dtos.BookingDTO;
import com.takehome.stayease.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{hotel_Id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HOTEL_MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<BookingDTO> createBooking(@PathVariable("hotel_Id") Long hotel_Id,
                                                    @Valid @RequestBody BookingDTO bookingDTO) {
        log.info("Creating booking for hotel id: {}", hotel_Id);
        return ResponseEntity.ok(bookingService.createBooking(hotel_Id, bookingDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HOTEL_MANAGER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BookingDTO> getBookingdetails(@PathVariable long id) {
        log.info("Fetching booking with id: {}", id);
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @DeleteMapping("/{booking_Id}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")    
    public ResponseEntity<Void> cancelBooking(@PathVariable long booking_Id) {
        log.info("Cancelling booking with id: {}", booking_Id);
        bookingService.cancelBooking(booking_Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}