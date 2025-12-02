package com.IT3180.cinema.service;

import com.IT3180.cinema.model.Movie;
import com.IT3180.cinema.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepository;

	public List<Movie> findByTitleContainingIgnoreCase(String title) {
		return movieRepository.findByTitleContainingIgnoreCase(title);
	}

	public List<Movie> findByGenresContainingIgnoreCase(String genre) {
		return movieRepository.findByGenresContainingIgnoreCase(genre);
	}

	public boolean existsByTitle(String title) {
		return movieRepository.existsByTitle(title);
	}

}
