package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.user.UserRegisterDTO;
import com.IT3180.cinema.model.Role;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
	}

	@Transactional
	public void registerNewUser(UserRegisterDTO userRegisterDTO) {
		if (userRepository.existsByEmail(userRegisterDTO.getEmail()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");

		User newUser = new User(userRegisterDTO.getEmail(),
				passwordEncoder.encode(userRegisterDTO.getPassword()),
				userRegisterDTO.getFullName(),
				userRegisterDTO.getDateOfBirth(),
				userRegisterDTO.getTel());
		newUser.setRole(Role.GUEST);

		userRepository.save(newUser);
	}
}
