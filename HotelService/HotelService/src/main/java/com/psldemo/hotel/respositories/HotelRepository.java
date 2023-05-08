package com.psldemo.hotel.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psldemo.hotel.entites.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
