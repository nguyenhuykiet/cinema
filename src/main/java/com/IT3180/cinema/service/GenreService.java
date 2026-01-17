package com.IT3180.cinema.service;

import java.util.ArrayList;
import java.util.List;

import com.IT3180.cinema.dto.movie.MovieSearchDTO;
import com.IT3180.cinema.model.Genre;
import com.IT3180.cinema.model.Movie;
import com.IT3180.cinema.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
	@Autowired
	private GenreRepository genreRepository;

	public List<Genre> getAllGenres() {
		return genreRepository.findAll();
	}

	public List<MovieSearchDTO> findMoviesByGenre(String slug) {
		Genre genre = genreRepository.findBySlug(slug)
				.orElseThrow(() -> new RuntimeException("Genre not found!"));

		List<Movie> movieSet = genre.getMovies();
		List<MovieSearchDTO> foundMovies = new ArrayList<>();

		for (Movie movie : movieSet)
			foundMovies.add(new MovieSearchDTO(movie));

		return foundMovies;
	}
}
