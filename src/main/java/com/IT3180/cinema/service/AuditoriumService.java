package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.auditorium.AuditoriumDTO;
import com.IT3180.cinema.model.Auditorium;
import com.IT3180.cinema.model.Seat;
import com.IT3180.cinema.repository.AuditoriumRepository;
import com.IT3180.cinema.repository.SeatRepository;
import com.IT3180.cinema.repository.ShowRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuditoriumService {
	private static final int ROWS = 10;
	private static final int COLS = 12;

	@Autowired
	private AuditoriumRepository auditoriumRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ShowRepository showRepository;

	public AuditoriumDTO findById(Integer id) {
		Auditorium auditorium = auditoriumRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Auditorium not found!"));

		return new AuditoriumDTO(auditorium);
	}

	public List<AuditoriumDTO> findAll() {
		List<Auditorium> auditoriums = auditoriumRepository.findAll();
		List<AuditoriumDTO> auditoriumDTOs = new ArrayList<>();

		for (Auditorium auditorium : auditoriums)
			auditoriumDTOs.add(new AuditoriumDTO(auditorium));

		return auditoriumDTOs;
	}

	@Transactional
	public void createNewAuditorium(String name) {
		Auditorium auditorium = new Auditorium(name);

		auditoriumRepository.save(auditorium);

		List<Seat> seats = new ArrayList<>();

		for (int i = 0; i < ROWS; ++i) {
			char row = (char) ('A' + i);

			for (int j = 1; j <= COLS; ++j) {
				Seat seat = new Seat(row + String.format("%02d", j), auditorium);
				seats.add(seat);
			}
		}

		seatRepository.saveAll(seats);
	}

	@Transactional
	public void deleteAuditorium(Integer auditoriumID) {
		Auditorium auditorium = auditoriumRepository.findById(auditoriumID)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auditorium not found"));

		if (showRepository.existsByAuditorium_AuditoriumID(auditoriumID))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete auditorium that has shows");

		auditoriumRepository.delete(auditorium);
	}
}
