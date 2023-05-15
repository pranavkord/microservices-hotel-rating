package com.psldemo.user.service.services;

import java.util.List;

import com.psldemo.user.service.entities.Reservation;
import com.psldemo.user.service.entities.User;

public interface UserService {

    //user operations

    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId

    User getUser(String userId);

	List<Reservation> getReservationsByUserId(String id);

    //TODO: delete
    //TODO: update


}
