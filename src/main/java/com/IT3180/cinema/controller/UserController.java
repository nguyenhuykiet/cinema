package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.UserProfileDTO;
import com.IT3180.cinema.dto.UserRegisterDTO;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserProfileDTO> getUserProfile(Authentication authentication) {
		String username = authentication.getName();
		System.out.println("USERNAME = " + authentication.getName());
		User user = userService.findByUsername(username);
		return ResponseEntity.ok(new UserProfileDTO(
				user.getFullName(),
				user.getDateOfBirth(),
				user.getRole(),
				user.getEmail(),
				user.getTel()
		));
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerNewUser(@RequestBody UserRegisterDTO userRegisterDTO) {
		try {
			userService.registerNewUser(userRegisterDTO);
			return new ResponseEntity<>("Registered successfully!", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
