package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.MovieDTO;
import com.IT3180.cinema.model.Genre;
import com.IT3180.cinema.model.Movie;
import com.IT3180.cinema.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {
	@Autowired
	private GenreRepository genreRepository;

	public List<Genre> getAllGenres() {
		return genreRepository.findAll();
	}

	public Set<MovieDTO> findMoviesByGenre(String name) {
		Genre genre = genreRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("Genre not found!"));

		Set<Movie> movieSet = genre.getMovies();
		Set<MovieDTO> foundMovies = new HashSet<>();

		for (Movie movie : movieSet) {
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
}
