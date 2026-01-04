package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.seat.SeatDTO;
import com.IT3180.cinema.dto.show.ShowDTO;
import com.IT3180.cinema.service.ShowService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/show")
public class ShowController {
	@Autowired
	private ShowService showService;

	@GetMapping("/{id}")
	public ShowDTO findById(@PathVariable Integer id) {
		return showService.findById(id);
	}

	@PostMapping("/new")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> createShow(@Valid @RequestBody ShowDTO showDTO) {
		showService.createShow(showDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Show created successfully!");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteShow(@PathVariable Integer id) {
		showService.deleteShow(id);
		return ResponseEntity.ok("Show deleted successfully!");
	}

	@GetMapping("/{id}/seat")
	public List<SeatDTO> getSeats(@PathVariable Integer id) {
		return showService.getSeatsOfShow(id);
	}
}
