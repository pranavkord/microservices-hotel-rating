package com.psldemo.hotel.respositories;





//import com.backendapi.hotelmanagement.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.psldemo.hotel.entites.Hotel;
import com.psldemo.hotel.entites.Room;

@Repository
@Transactional(readOnly = true)
public interface RoomRepository extends JpaRepository<Room, Long> {
	
	List<Room> findByIdHotel(Hotel hotel);//  throws ResourceNotFoundException;
}
