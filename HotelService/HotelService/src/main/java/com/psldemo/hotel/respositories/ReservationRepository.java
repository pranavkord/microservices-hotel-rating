package com.psldemo.hotel.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.psldemo.hotel.entites.Reservation;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllById(Long id);
    
    @Query("SELECT u FROM Reservation u WHERE u.userId = ?1")
    List<Reservation> getReservationsByUserId(String id);
    
    

}
