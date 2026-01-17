package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.user.UserProfileDTO;
import com.IT3180.cinema.dto.user.UserRegisterDTO;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * Xem thông tin cá nhân
	 * @param authentication
	 * @return
	 */
	@GetMapping("/profile")
	@PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
	public UserProfileDTO getUserProfile(Authentication authentication) {
		String email = authentication.getName();
		User user = userService.findByEmail(email);
		return new UserProfileDTO(user);
	}

	/**
	 * Đăng ký tài khoản
	 * @param userRegisterDTO
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<String> registerNewUser(@RequestBody UserRegisterDTO userRegisterDTO) {
		try {
			userService.registerNewUser(userRegisterDTO);
			return new ResponseEntity<>("Register successfully!", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
