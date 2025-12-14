package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.UserRegisterDTO;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
	}

	@Transactional
	public void registerNewUser(UserRegisterDTO userRegisterDTO) {
		if(userRepository.existsByUsername(userRegisterDTO.getUsername()))
			throw new RuntimeException("Username already exists!");

		if(userRepository.existsByEmail(userRegisterDTO.getEmail()))
			throw new RuntimeException("Email already exists!");

		User newUser = new User(userRegisterDTO.getUsername(),
								passwordEncoder.encode(userRegisterDTO.getPassword()),
								userRegisterDTO.getFullName(),
								userRegisterDTO.getDateOfBirth(),
								userRegisterDTO.getRole(),
								userRegisterDTO.getEmail(),
								userRegisterDTO.getTel());
		userRepository.save(newUser);
	}

}
