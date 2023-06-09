package com.psldemo.hotel.controllers;




import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.psldemo.hotel.entites.Room;
import com.psldemo.hotel.services.RoomService;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.fetchAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.findById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

  //@PreAuthorize("hasRole('ROLE_ADMIN')")
        @PostMapping("")
    public ResponseEntity<Map<String, Boolean>> createRoom(@Valid @RequestBody Room room) {
        roomService.createRoom(room);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

      //@PreAuthorize("hasRole('ROLE_ADMIN')")
            @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateRoom(@PathVariable Long id,
                                                            @Valid @RequestBody Room room) {
        room.setId(id);
        roomService.updateRoom(room);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

          //@PreAuthorize("hasRole('ROLE_ADMIN')")
                @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
