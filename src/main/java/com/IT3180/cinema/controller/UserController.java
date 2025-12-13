package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.UserDTO;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{username}")
	public ResponseEntity<UserDTO> getUserProfile(@PathVariable String username) {
		User user = userService.findByUsername(username);
		UserDTO userDTO = new UserDTO(
				user.getUsername(),
				user.getFullName(),
				user.getDateOfBirth(),
				user.getRole(),
				user.getEmail(),
				user.getTel());
		return ResponseEntity.ok(userDTO);
	}

}
