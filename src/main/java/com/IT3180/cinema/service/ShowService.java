package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.seat.SeatDTO;
import com.IT3180.cinema.dto.show.ShowDTO;
import com.IT3180.cinema.model.Auditorium;
import com.IT3180.cinema.model.Movie;
import com.IT3180.cinema.model.Show;
import com.IT3180.cinema.repository.AuditoriumRepository;
import com.IT3180.cinema.repository.MovieRepository;
import com.IT3180.cinema.repository.ShowRepository;
import com.IT3180.cinema.repository.TicketRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShowService {
	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private AuditoriumRepository auditoriumRepository;

	@Autowired
	private TicketRepository ticketRepository;

	public ShowDTO findById(Integer id) {
		Show show = showRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show not found!"));
		return new ShowDTO(show);
	}

	@Transactional
	public void createShow(ShowDTO showDTO) {
		Movie movie = movieRepository.findById(showDTO.getMovieId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found!"));

		Auditorium auditorium = auditoriumRepository.findById(showDTO.getAuditoriumId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auditorium not found!"));

		if (showRepository.existsByAuditoriumAndShowTime(auditorium, showDTO.getShowTime()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Show already exists in this auditorium at that time!");

		if (showDTO.getShowTime().toLocalDate().isBefore(LocalDate.now()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Show date must be today or later!");

		Show show = new Show(movie, auditorium, showDTO.getShowTime(), showDTO.getPrice());

		showRepository.save(show);
	}

	@Transactional
	public void deleteShow(Integer showID) {
		Show show = showRepository.findById(showID)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show not found!"));

		if (ticketRepository.existsByShow_ShowID(showID))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete show: tickets already booked!");

		if (show.getShowTime().isBefore(LocalDateTime.now()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete past show!");

		showRepository.delete(show);
	}

	public List<ShowDTO> getShowsByMovie(Integer movieId) {
		return showRepository.findByMovie_MovieID(movieId).stream().map(ShowDTO::new).toList();
	}

	public List<SeatDTO> getSeatsOfShow(Integer showId) {
		Show show = showRepository.findById(showId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show not found!"));

		return show.getAuditorium().getSeats().stream()
				.map(seat -> new SeatDTO(seat.getName(), ticketRepository.existsByShow_ShowIDAndSeat_SeatID(showId, seat.getSeatID()))).toList();
	}
}
