package com.IT3180.cinema.service;

import com.IT3180.cinema.dto.movie.MovieDetailDTO;
import com.IT3180.cinema.dto.movie.MovieSearchDTO;
import com.IT3180.cinema.model.Genre;
import com.IT3180.cinema.model.Movie;
import com.IT3180.cinema.repository.GenreRepository;
import com.IT3180.cinema.repository.MovieRepository;
import com.IT3180.cinema.repository.ShowRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private FileStorageService fileStorageService;

	public MovieDetailDTO findById(Integer id) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Movie not found!"));

		return new MovieDetailDTO(movie);
	}

	public List<MovieSearchDTO> searchMoviesByTitle(String title) {
		List<Movie> movieList = movieRepository.findByTitleContainingIgnoreCase(title);
		List<MovieSearchDTO> foundMovies = new ArrayList<>();

		for (Movie movie : movieList)
			foundMovies.add(new MovieSearchDTO(movie));

		return foundMovies;
	}

	@Transactional
	public void addNewMovie(MovieDetailDTO movieDetailDTO, MultipartFile poster) throws IOException {
		if (movieRepository.existsByTitle(movieDetailDTO.getTitle()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie already exists!");

		String posterUrl = fileStorageService.storePoster(poster);

		List<Genre> genreListDTO = movieDetailDTO.convertToGenreList();
		List<Genre> genreList = new ArrayList<>();

		for (Genre genreDTO : genreListDTO) {
			Genre genre = genreRepository.findBySlug(genreDTO.getSlug())
					.orElseGet(() -> genreRepository.save(new Genre(genreDTO.getName())));
			genreList.add(genre);
		}

		Movie newMovie = new Movie(movieDetailDTO.getTitle(),
				movieDetailDTO.getDirectors(),
				movieDetailDTO.getCasts(),
				genreList,
				movieDetailDTO.getOpeningDay(),
				movieDetailDTO.getDuration(),
				movieDetailDTO.getAgeRating(),
				movieDetailDTO.getSynopsis());
		newMovie.setPosterUrl(posterUrl);

		movieRepository.save(newMovie);
	}

	@Transactional
	public void updateMovie(Integer id, MovieDetailDTO movieDetailDTO, MultipartFile poster) throws IOException {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found!"));

		if (movieRepository.existsByTitle(movieDetailDTO.getTitle()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Title already exists!");

		List<Genre> genreListDTO = movieDetailDTO.convertToGenreList();
		List<Genre> genreList = new ArrayList<>();

		for (Genre genreDTO : genreListDTO) {
			Genre genre = genreRepository.findBySlug(genreDTO.getSlug())
					.orElseGet(() -> genreRepository.save(new Genre(genreDTO.getName())));
			genreList.add(genre);
		}

		movie.setTitle(movieDetailDTO.getTitle());
		movie.setDirectors(movieDetailDTO.getDirectors());
		movie.setCasts(movieDetailDTO.getCasts());
		movie.setGenres(genreList);
		movie.setOpeningDay(movieDetailDTO.getOpeningDay());
		movie.setDuration(movieDetailDTO.getDuration());
		movie.setAgeRating(movieDetailDTO.getAgeRating());
		movie.setSynopsis(movieDetailDTO.getSynopsis());

		if (poster != null && !poster.isEmpty()) {
			fileStorageService.deletePosterIfExists(movie.getPosterUrl());
			String newPosterUrl = fileStorageService.storePoster(poster);
			movie.setPosterUrl(newPosterUrl);
		}

		movieRepository.save(movie);
	}

	@Transactional
	public void deleteMovie(Integer movieID) {
		Movie movie = movieRepository.findById(movieID)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found!"));

		if (showRepository.existsByMovie_MovieID(movieID))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete movie!");

		movieRepository.delete(movie);
	}

	public List<MovieSearchDTO> getAllMovies() {
		return movieRepository.findAll().stream().map(MovieSearchDTO::new).toList();
	}

}
