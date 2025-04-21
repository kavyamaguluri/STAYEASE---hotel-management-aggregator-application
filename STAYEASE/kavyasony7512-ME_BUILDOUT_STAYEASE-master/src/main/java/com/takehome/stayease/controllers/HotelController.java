package com.takehome.stayease.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.takehome.stayease.entities.Hotel;
import com.takehome.stayease.services.HotelService;

@RestController
@RequestMapping("/api/hotels")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")    
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.createHotel(hotel);
        
        return new ResponseEntity<>(savedHotel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        log.info("Fetching all hotels");
        return ResponseEntity.ok(hotelService.getAllHotels());
        
    }

    @PutMapping("/{hotel_Id}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<Hotel> updateHotel(@PathVariable long hotel_Id,
                                             @Valid @RequestBody Hotel hotel) {
        log.info("Updating hotel with id: {}", hotel_Id);
        return ResponseEntity.ok(hotelService.updateHotel(hotel_Id, hotel));
    }

    @DeleteMapping("/{hotel_Id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable long hotel_Id) {
    log.info("Deleting hotel with id: {}", hotel_Id);
    hotelService.deleteHotel(hotel_Id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

} 


