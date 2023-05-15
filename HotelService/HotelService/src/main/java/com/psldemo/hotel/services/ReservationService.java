package com.psldemo.hotel.services;

//ion;


import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psldemo.hotel.entites.Reservation;
import com.psldemo.hotel.exceptions.BadRequestException;
import com.psldemo.hotel.exceptions.ResourceNotFoundException;
import com.psldemo.hotel.respositories.ReservationRepository;

import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {

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
    
    public List<Reservation> getAllReservationsByUserId(Long id) throws ResourceNotFoundException {
        return reservationRepository.getAllReservationsByUserId(id);
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
}
