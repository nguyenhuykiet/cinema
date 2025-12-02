package com.IT3180.cinema.service;

import com.IT3180.cinema.model.Audience;
import com.IT3180.cinema.repository.AudienceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudienceService {

	@Autowired
	private AudienceRepository audienceRepository;

	public Audience findByUsername(String username) {
		return audienceRepository.findByUsername(username);
	}

	public boolean existsByUsername(String username) {
		return audienceRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return audienceRepository.existsByEmail(email);
	}

}
