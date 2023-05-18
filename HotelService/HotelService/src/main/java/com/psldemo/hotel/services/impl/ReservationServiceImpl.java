package com.psldemo.hotel.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psldemo.hotel.entites.Reservation;
import com.psldemo.hotel.exceptions.BadRequestException;
import com.psldemo.hotel.exceptions.ResourceNotFoundException;
import com.psldemo.hotel.payload.CreateReservationRequest;
import com.psldemo.hotel.respositories.ReservationRepository;
import com.psldemo.hotel.services.ReservationService;
import com.psldemo.hotel.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class ReservationServiceImpl implements ReservationService {
  
     @Autowired
     StripeService stripeService;
	
	 @Autowired	
	    private ReservationRepository reservationRepository;
	    private static final String RESERVATION_NOT_FOUND_MSG = "reservation with id %d not found";

	    public List<Reservation> fetchAllReservations() {
	        return reservationRepository.findAll();
	    }

	    public Reservation findById(Long id) throws ResourceNotFoundException {
	        return reservationRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG, id)));
	    }
	    
	    public List<Reservation> getAllReservationsByUserId(String id) throws ResourceNotFoundException {
	        return reservationRepository.getReservationsByUserId(id);
	    }

	    public List<Reservation> findAllById(Long id) {
	        return reservationRepository.findAllById(id);
	    }

	    public void createReservation(CreateReservationRequest createReservationRequest) throws BadRequestException {
	        try {
	           
	            Reservation reservation = new Reservation();
	            reservation.setHotelRoom(createReservationRequest.getHotelRoom());
	            reservation.setUserId(createReservationRequest.getUserId());
	            reservation.setPrice(createReservationRequest.getPrice());
	            reservation.setStartDate(createReservationRequest.getStartDate());
	            reservation.setEndDate(createReservationRequest.getEndDate());
	            reservation.setNumOfAdult(createReservationRequest.getNumOfAdult());
	            reservation.setNumOfChildren(createReservationRequest.getNumOfChildren());
	            reservation.setContactNameSurname(createReservationRequest.getContactNameSurname());
	            reservation.setContactPhone(createReservationRequest.getContactPhone());
	            reservation.setContactEmail(createReservationRequest.getContactEmail());
	            reservation.setNote(createReservationRequest.getNote());
	            reservation.setApproved(createReservationRequest.getApproved());          

	            payForReservation(createReservationRequest);
	            reservation.setIsPaid(true);
	         	     
	            reservationRepository.save(reservation);     
	        } catch (Exception e) {
	            throw new BadRequestException("invalid request");
	        }
	    }

	    public void updateReservation(Reservation reservation) throws BadRequestException {
	        try {
	            reservationRepository.save(reservation);
	        } catch (Exception e) {
	            throw new BadRequestException("invalid request");
	        }
	    }

	    public void deleteReservation(Long id) throws ResourceNotFoundException {
	        boolean reservationExist = reservationRepository.findById(id).isPresent();

	        if (!reservationExist)
	            throw new ResourceNotFoundException("reservation does not exist");

	        reservationRepository.deleteById(id);
	    }
	    

		public List<Reservation> getReservationsByUserId(String id) {
			
			List<Reservation> list = reservationRepository.getReservationsByUserId(id);
			// TODO Auto-generated method stub
			return list;
		}
		
		public void payForReservation(CreateReservationRequest createReservationRequest) throws StripeException {     
		    // Generate single use stripe token for credit card to be sent with payment request
		    String token = stripeService.getToken(createReservationRequest.getCardNumber(), 
		        createReservationRequest.getExpMonth(), createReservationRequest.getExpYear(), createReservationRequest.getCvc());
		      
		    // Make payment
		    Charge charge = stripeService.charge(createReservationRequest, token);
	    }
}
