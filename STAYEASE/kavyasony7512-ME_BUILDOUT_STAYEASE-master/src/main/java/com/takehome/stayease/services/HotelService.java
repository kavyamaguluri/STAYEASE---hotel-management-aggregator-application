package com.takehome.stayease.services;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import com.takehome.stayease.entities.Hotel;
import com.takehome.stayease.exceptions.ResourceNotFoundException;
import com.takehome.stayease.exceptions.UnauthorizedException;
import com.takehome.stayease.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }   

    @Transactional
    public Hotel createHotel(Hotel hotel) {
        try{
             return hotelRepository.save(hotel);
        }
        catch(RuntimeException ex){
            throw new UnauthorizedException(" user have no permission");
        }
   
    }
  

    @Transactional
    public Hotel updateHotel(Long id, Hotel hotel) {
        return hotelRepository.findById(id).map(existingHotel -> {
            existingHotel.setName(hotel.getName());
            existingHotel.setLocation(hotel.getLocation());
            existingHotel.setDescription(hotel.getDescription());
            existingHotel.setAvailableRooms(hotel.getAvailableRooms());
            return hotelRepository.save(existingHotel); 
       }).orElseThrow(() -> new ResourceNotFoundException("Hotel with id " + id + " not found."));
    }

   @Transactional
    public void deleteHotel(long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

        hotelRepository.delete(hotel);
        log.info("Hotel deleted successfully: {}", hotel.getName());
    }    
}
