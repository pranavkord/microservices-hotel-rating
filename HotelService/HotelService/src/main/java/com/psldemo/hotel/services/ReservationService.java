package com.psldemo.hotel.services;

import com.psldemo.hotel.entites.Reservation;
import java.util.List;

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
