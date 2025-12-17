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
@RequestMapping("/nguoi-dung")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/thong-tin")
	public ResponseEntity<UserProfileDTO> getUserProfile(Authentication authentication) {
		String email = authentication.getName();
		User user = userService.findByEmail(email);
		return ResponseEntity.ok(new UserProfileDTO(
				user.getFullName(),
				user.getDateOfBirth(),
				user.getEmail(),
				user.getTel()
		));
	}

	@PostMapping("/dang-ky")
	public ResponseEntity<String> registerNewUser(@RequestBody UserRegisterDTO userRegisterDTO) {
		try {
			userService.registerNewUser(userRegisterDTO);
			return new ResponseEntity<>("Register successfully!", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
