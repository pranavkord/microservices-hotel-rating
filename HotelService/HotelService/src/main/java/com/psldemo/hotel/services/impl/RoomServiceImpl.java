package com.psldemo.hotel.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.psldemo.hotel.entites.Room;
import com.psldemo.hotel.exceptions.BadRequestException;
import com.psldemo.hotel.exceptions.ResourceNotFoundException;
import com.psldemo.hotel.respositories.RoomRepository;
import com.psldemo.hotel.services.RoomService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService{

	private final RoomRepository roomRepository;
    private static final String ROOM_NOT_FOUND_MSG = "room with id %d not found";

    public List<Room> fetchAllRooms() {
        return roomRepository.findAll();
    }

    public Room findById(Long id) throws ResourceNotFoundException {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ROOM_NOT_FOUND_MSG, id)));
    }

    public void createRoom(Room room) throws BadRequestException {
        try {
            roomRepository.save(room);
        } catch (Exception e) {
            throw new BadRequestException("invalid request");
        }
    }

    public void updateRoom(Room room) throws BadRequestException {
        try {
            roomRepository.save(room);
        } catch (Exception e) {
            throw new BadRequestException("invalid request");
        }
    }

    public void deleteRoom(Long id) throws ResourceNotFoundException {
        boolean roomExist = roomRepository.findById(id).isPresent();

        if (!roomExist)
            throw new ResourceNotFoundException("room does not exist");

        roomRepository.deleteById(id);
    }
}
