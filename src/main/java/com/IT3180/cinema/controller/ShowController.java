package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.seat.SeatDTO;
import com.IT3180.cinema.dto.show.ShowConstructDTO;
import com.IT3180.cinema.dto.show.ShowDetailDTO;
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

	/**
	 * Thông tin suất chiếu
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ShowDetailDTO findById(@PathVariable Integer id) {
		return showService.findById(id);
	}

	/**
	 * Tạo suất chiếu
	 * @param showConstructDTO
	 * @return
	 */
	@PostMapping("/new")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> createShow(@Valid @RequestBody ShowConstructDTO showConstructDTO) {
		showService.createShow(showConstructDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Show created successfully!");
	}

	/**
	 * Xoá suất chiếu
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteShow(@PathVariable Integer id) {
		showService.deleteShow(id);
		return ResponseEntity.ok("Show deleted successfully!");
	}

	/**
	 * Lấy sơ đồ ghế
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/seat")
	public List<SeatDTO> getSeats(@PathVariable Integer id) {
		return showService.getSeatsOfShow(id);
	}
}
