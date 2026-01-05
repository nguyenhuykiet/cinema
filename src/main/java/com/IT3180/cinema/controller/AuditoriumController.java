package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.auditorium.AuditoriumDTO;
import com.IT3180.cinema.service.AuditoriumService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auditorium")
public class AuditoriumController {
	@Autowired
	private AuditoriumService auditoriumService;

	/**
	 * Tạo phòng chiếu
	 * @param auditoriumDTO
	 * @return
	 */
	@PostMapping("/new")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> createNewAuditorium(@Valid @RequestBody AuditoriumDTO auditoriumDTO) {
		auditoriumService.createNewAuditorium(auditoriumDTO.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body("Auditorium is created!");
	}

	/**
	 * Xoá phòng chiếu
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteAuditorium(@PathVariable Integer id) {
		auditoriumService.deleteAuditorium(id);
		return ResponseEntity.ok("Auditorium is deleted!");
	}
}
