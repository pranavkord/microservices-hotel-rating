package com.psldemo.hotel.controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.psldemo.hotel.entites.Reservation;
import com.psldemo.hotel.services.ReservationService;
import com.stripe.exception.StripeException;
import com.psldemo.hotel.entites.ChargeRequest;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/hotels/reservation")
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;

  //@PreAuthorize("hasRole('ROLE_ADMIN')")
        @GetMapping("")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.fetchAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationByUsername(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/all{id}")
    public ResponseEntity<List<Reservation>> getAllReservationById(@PathVariable Long id) {
        List<Reservation> reservation = reservationService.findAllById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Boolean>> createReservation(@Valid @RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateReservation(@PathVariable Long id,
                                                           @Valid @RequestBody Reservation reservation) {
        reservation.setId(id);
        reservationService.updateReservation(reservation);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getAllReservationsByUserId(@PathVariable String id){
		List<Reservation> user = reservationService.getReservationsByUserId(id);
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	@PostMapping("/{id}/payment")
	public ResponseEntity<Object> payForReservation(@PathVariable Long id, @RequestBody ChargeRequest chargeRequest) throws StripeException {
	  reservationService.payForReservation(id, chargeRequest);
	  Map<String, Boolean> map = new HashMap<>();
      map.put("success", true);
      return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
