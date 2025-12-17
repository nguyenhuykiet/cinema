package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.MovieDTO;
import com.IT3180.cinema.model.Movie;
import com.IT3180.cinema.repository.MovieRepository;

import com.IT3180.cinema.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ShowRepository showRepository;

	public List<MovieDTO> findByTitle(String title) {
		List<Movie> movieList = movieRepository.findByTitleContainingIgnoreCase(title);
		List<MovieDTO> foundMovies = new ArrayList<>();

		for (Movie movie : movieList) {
			foundMovies.add(new MovieDTO(movie.getTitle(),
					movie.getDirectors(),
					movie.getCasts(),
					movie.getGenres(),
					movie.getOpeningDay(),
					movie.getDuration(),
					movie.getAgeRating(),
					movie.getSynopsis()));
		}

		return foundMovies;
	}

	public void addNewMovie(MovieDTO movieDTO) {
		if (movieRepository.existsByTitle(movieDTO.getTitle())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie already exists!");
		}

		Movie newMovie = new Movie(movieDTO.getTitle(),
				movieDTO.getDirectors(),
				movieDTO.getCasts(),
				movieDTO.getGenres(),
				movieDTO.getOpeningDay(),
				movieDTO.getDuration(),
				movieDTO.getAgeRating(),
				movieDTO.getSynopsis());

		movieRepository.save(newMovie);
	}

	@Transactional
	public void updateMovie(Integer id, MovieDTO movieDTO) {

		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found!"));

		if (movieRepository.existsByTitle(movieDTO.getTitle()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Title already exists!");

		movie.setTitle(movieDTO.getTitle());
		movie.setDirectors(movieDTO.getDirectors());
		movie.setCasts(movieDTO.getCasts());
		movie.setGenres(movieDTO.getGenres());
		movie.setOpeningDay(movieDTO.getOpeningDay());
		movie.setDuration(movieDTO.getDuration());
		movie.setAgeRating(movieDTO.getAgeRating());
		movie.setSynopsis(movieDTO.getSynopsis());

		movieRepository.save(movie);
	}

	@Transactional
	public void deleteMovie(Integer movieID) {

		Movie movie = movieRepository.findById(movieID)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found!"));

		if (showRepository.existsByMovie_MovieID(movieID)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete movie!");
		}

		movieRepository.delete(movie);
	}
}
