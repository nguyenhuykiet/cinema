package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.ticket.BookingRequest;
import com.IT3180.cinema.dto.ticket.TicketDTO;
import com.IT3180.cinema.model.Seat;
import com.IT3180.cinema.model.Show;
import com.IT3180.cinema.model.Ticket;
import com.IT3180.cinema.model.User;
import com.IT3180.cinema.repository.SeatRepository;
import com.IT3180.cinema.repository.ShowRepository;
import com.IT3180.cinema.repository.TicketRepository;
import com.IT3180.cinema.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {
	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Transactional
	public void bookTicket(BookingRequest request, String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

		Show show = showRepository.findById(request.getShowId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show not found!"));

		Seat seat = seatRepository.findById(request.getSeatId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found!"));

		if (!show.getAuditorium().getAuditoriumID().equals(seat.getAuditorium().getAuditoriumID()))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found!");

		if (ticketRepository.existsByShow_ShowIDAndSeat_SeatID(show.getShowID(), seat.getSeatID()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seat already booked!");

		Ticket ticket = new Ticket(LocalDateTime.now(), user, show, seat);

		ticketRepository.save(ticket);
	}

	@Transactional
	public void cancelTicket(Integer ticketId, User user) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found!"));

		if (!ticket.getUser().getUserID().equals(user.getUserID()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed to cancel!");

		LocalDateTime showTime = ticket.getShow().getShowTime();
		LocalDateTime now = LocalDateTime.now();

		if (Duration.between(now, showTime).toMinutes() < 30)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket can only be cancelled at least 30 minutes before showtime!");

		ticketRepository.delete(ticket);
	}

	public List<TicketDTO> getMyTickets(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return ticketRepository.findByUser_UserID(user.getUserID()).stream().map(TicketDTO::new).toList();
	}
}
