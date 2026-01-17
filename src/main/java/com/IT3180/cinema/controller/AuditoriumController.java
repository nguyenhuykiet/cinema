package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.auditorium.AuditoriumDTO;
import com.IT3180.cinema.dto.show.ShowDetailDTO;
import com.IT3180.cinema.service.AuditoriumService;

import com.IT3180.cinema.service.ShowService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorium")
public class AuditoriumController {
	@Autowired
	private AuditoriumService auditoriumService;

	@Autowired
	private ShowService showService;

	/**
	 * Xem thông tin phòng chiếu
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public AuditoriumDTO findById(@PathVariable Integer id) {
		return auditoriumService.findById(id);
	}

	/**
	 * Liệt kê tất cả phòng chiếu
	 * @return
	 */
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<AuditoriumDTO> findAll() {
		return auditoriumService.findAll();
	}

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

	/**
	 * Liệt kê suất chiếu của phòng
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/show")
	@PreAuthorize("hasRole('ADMIN')")
	public List<ShowDetailDTO> getShowsByAuditorium(@PathVariable Integer id) {
		return showService.getShowsByAuditorium(id);
	}
}
