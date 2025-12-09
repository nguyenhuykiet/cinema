package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.UserDTO;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void registerNewUser(UserDTO userDTO) {
		if(userRepository.existsByUsername(userDTO.getUsername()))
			throw new IllegalStateException("Tên đăng nhập đã được sử dụng!");

		if(userRepository.existsByEmail(userDTO.getEmail()))
			throw new IllegalStateException("Email đã được sử dụng!");

		User newUser = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFullName(), userDTO.getDateOfBirth(), userDTO.getRole(), userDTO.getTel(), userDTO.getEmail(), userDTO.getBooks());

		String rawPassword = newUser.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		newUser.setPassword(encodedPassword);
		userRepository.save(newUser);
	}

	public void changePassword(User user, String oldPassword, String newPassword) {
		User existingUser = userRepository.findByUsername(user.getUsername());

		if (!passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
			throw new IllegalArgumentException("Mật khẩu cũ không chính xác!");
		}

		if(oldPassword.equals(newPassword)) {
			throw new IllegalArgumentException("Mật khẩu mới không được trùng mật khẩu cũ!");
		}

		String encodedPassword = passwordEncoder.encode(newPassword);
		existingUser.setPassword(encodedPassword);
		userRepository.save(existingUser);
	}

}
