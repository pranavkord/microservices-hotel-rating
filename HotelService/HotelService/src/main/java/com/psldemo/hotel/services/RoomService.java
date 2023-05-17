package com.psldemo.hotel.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.psldemo.hotel.entites.Room;
import com.psldemo.hotel.exceptions.BadRequestException;
import com.psldemo.hotel.exceptions.ResourceNotFoundException;
import com.psldemo.hotel.respositories.RoomRepository;

import java.util.List;

public interface RoomService {

 
    public List<Room> fetchAllRooms();

    public Room findById(Long id);

    public void createRoom(Room room);

    public void updateRoom(Room room);

    public void deleteRoom(Long id);
}
