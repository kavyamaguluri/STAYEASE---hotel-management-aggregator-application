package com.takehome.stayease.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import com.takehome.stayease.dtos.BookingDTO;
import com.takehome.stayease.entities.Booking;
import com.takehome.stayease.entities.Hotel;
import com.takehome.stayease.entities.Role;
import com.takehome.stayease.entities.User;
import com.takehome.stayease.exceptions.BadRequestException;
import com.takehome.stayease.exceptions.ResourceNotFoundException;
import com.takehome.stayease.repositories.BookingRepository;
import com.takehome.stayease.repositories.HotelRepository;


@Service
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final UserService userService;

    public BookingService(BookingRepository bookingRepository, HotelRepository hotelRepository,
                          UserService userService) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.userService = userService;
    }

    @Transactional
    public BookingDTO createBooking(long hotelId, BookingDTO bookingDTO) {

        User currentUser = userService.getCurrentUser();

        Hotel hotel = hotelRepository.findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        validateBookingDates(bookingDTO);
        if (hotel.getAvailableRooms() <= 0) {
            throw new ResourceNotFoundException("No rooms available for booking");
        }
        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setHotel(hotel);
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);
        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created successfully: {}", savedBooking.getId());
        return new BookingDTO(
                savedBooking.getId(),
                savedBooking.getHotel().getId(),
                savedBooking.getCheckInDate(),
                savedBooking.getCheckOutDate()
        );
    }

    public BookingDTO getBookingById(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        if (booking.getUser().getId() != currentUser.getId()
        && currentUser.getRole() !=Role.ADMIN
        && currentUser.getRole() != Role.HOTEL_MANAGER) {
        throw new BadRequestException("You don't have permission to view this booking");
        }    
        return new BookingDTO(
                booking.getId(),
                booking.getHotel().getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );
    }

    @Transactional
    public void cancelBooking(long id) {
        Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

        if (booking.isCancelled()) {
            throw new BadRequestException("Booking is already cancelled");
        }
        
        booking.setCancelled(true);
        bookingRepository.save(booking);

        Hotel hotel = booking.getHotel();
        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        hotelRepository.save(hotel);

        log.info("Booking cancelled successfully: {}", booking.getId());
    }

    private void validateBookingDates(BookingDTO bookingDTO) {
        LocalDate currentDate = LocalDate.now();

        if (bookingDTO.getCheckInDate().isBefore(currentDate)) {
            throw new BadRequestException("Check-in date must be in the future");
        }

        if (bookingDTO.getCheckOutDate().isBefore(bookingDTO.getCheckInDate())
                || bookingDTO.getCheckOutDate().isEqual(bookingDTO.getCheckInDate())) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }
    }
}