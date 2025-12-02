package com.IT3180.cinema.service;

import com.IT3180.cinema.model.Auditorium;
import com.IT3180.cinema.repository.AuditoriumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumService {

	@Autowired
	AuditoriumRepository auditoriumRepository;

	public Auditorium findByAuditoriumName(String name) {
		return auditoriumRepository.findByAuditoriumName(name);
	}

	public boolean existsByAuditoriumName(String name) {
		return auditoriumRepository.existsByAuditoriumName(name);
	}

}
