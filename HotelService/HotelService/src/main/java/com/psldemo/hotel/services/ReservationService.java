package com.psldemo.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psldemo.hotel.entites.ChargeRequest;
import com.psldemo.hotel.entites.Reservation;
import com.psldemo.hotel.exceptions.BadRequestException;
import com.psldemo.hotel.exceptions.ResourceNotFoundException;
import com.psldemo.hotel.respositories.ReservationRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import java.util.List;

@Service
public class ReservationService {
  
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

    public void createReservation(Reservation reservation) throws BadRequestException {
        try {
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
	
	
	
	public void payForReservation(Long id, ChargeRequest chargeRequest) throws StripeException {
	  Reservation reservation = reservationRepository.findById(id).orElseThrow(
	      () -> new ResourceNotFoundException("reservation does not exist"));
	  
	  // Generate single use stripe token for credit card to be sent with payment request
	  String token = stripeService.getToken(chargeRequest.getCardNumber(), 
	      chargeRequest.getExpMonth(), chargeRequest.getExpYear(), chargeRequest.getCvc());
	  
	  // Make payment
	  Charge charge = stripeService.charge(chargeRequest, token);
	  
	  // Mark reservation as paid
	  reservation.setIsPaid(true);
	  reservationRepository.save(reservation);
	}
}
