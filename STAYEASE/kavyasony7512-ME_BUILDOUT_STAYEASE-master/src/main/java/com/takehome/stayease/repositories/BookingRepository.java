package com.takehome.stayease.repositories;

import java.util.List;
import com.takehome.stayease.entities.Booking;
import com.takehome.stayease.entities.Hotel;
import com.takehome.stayease.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);

    List<Booking> findByHotel(Hotel hotel);

    List<Booking> findByUserAndCancelledFalse(User user);
}
