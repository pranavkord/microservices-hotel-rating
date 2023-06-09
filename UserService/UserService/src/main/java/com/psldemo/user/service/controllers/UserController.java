package com.psldemo.user.service.controllers;

import com.psldemo.user.service.entities.Reservation;
import com.psldemo.user.service.entities.User;
import com.psldemo.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	//create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<String>> getUsers() {
		List<String> list = Arrays.asList("Ram", "Shyam", "Sita", "Krishna");
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	//single user get


	@GetMapping("/{userId}")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		logger.info("Get Single User Handler: UserController");
		//        logger.info("Retry count: {}", retryCount);

		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}

	//creating fall back  method for circuitbreaker


	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
		//        logger.info("Fallback is executed because service is down : ", ex.getMessage());

		ex.printStackTrace();

		User user = User.builder()
				.email("dummy@gmail.com")
				.name("Dummy")
				.about("This user is created dummy because some service is down")
				.userId("141234").build();
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}


	//all user get
	@GetMapping("getAllUsers")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}


	// endpoint for fetching all room reservations for the user

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getAllReservationsByUserId(@PathVariable String id){
		List<Reservation> user = userService.getReservationsByUserId(id);
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
}
