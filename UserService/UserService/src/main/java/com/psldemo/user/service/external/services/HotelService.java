package com.psldemo.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.psldemo.user.service.entities.Hotel;
import com.psldemo.user.service.entities.Rating;
import com.psldemo.user.service.entities.Reservation;

import java.util.List;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotels/{hotelId}")
    Hotel getHotel(@PathVariable("hotelId") String hotelId);

	@GetMapping("/hotels/reservation/user/{userId}")
	List<Reservation> getReservationsByUserId(String id);
}
