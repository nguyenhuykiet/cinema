package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.ticket.BookingRequest;
import com.IT3180.cinema.dto.ticket.TicketDTO;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.repository.UserRepository;
import com.IT3180.cinema.service.TicketService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Đặt vé
	 * @param request
	 * @param authentication
	 * @return
	 */
	@PostMapping("/book")
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<String> bookTicket(@RequestBody BookingRequest request, Authentication authentication) {
		ticketService.bookTicket(request, authentication.getName());
		return ResponseEntity.ok("Booking successful!");
	}

	/**
	 * Huỷ vé
	 * @param id
	 * @param authentication
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<String> cancelTicket(@PathVariable Integer id, Authentication authentication) {
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

		ticketService.cancelTicket(id, user);
		return ResponseEntity.ok("Ticket cancelled!");
	}

	/**
	 * Xem vé đã đặt
	 * @param authentication
	 * @return
	 */
	@GetMapping("/my")
	@PreAuthorize("hasRole('GUEST')")
	public List<TicketDTO> myTickets(Authentication authentication) {
		return ticketService.getMyTickets(authentication.getName());
	}
}
