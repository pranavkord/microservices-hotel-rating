package com.psldemo.hotel.services;


import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psldemo.hotel.entites.Reservation;
import com.psldemo.hotel.exceptions.BadRequestException;
import com.psldemo.hotel.exceptions.ResourceNotFoundException;
import com.psldemo.hotel.respositories.ReservationRepository;

import java.util.List;
import java.util.Optional;



public interface ReservationService {

	public List<Reservation> fetchAllReservations();
	
	public Reservation findById(Long id);
	
	List<Reservation> getAllReservationsByUserId(String id);
	
	public List<Reservation> findAllById(Long id);
	
	public void createReservation(Reservation reservation);
	
	public void updateReservation(Reservation reservation);
	
	public void deleteReservation(Long id);
	
	public List<Reservation> getReservationsByUserId(String id);
}
