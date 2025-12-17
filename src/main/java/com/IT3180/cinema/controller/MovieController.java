package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.MovieDTO;
import com.IT3180.cinema.model.Movie;
import com.IT3180.cinema.service.MovieService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/phim")
public class MovieController {
	@Autowired
	private MovieService movieService;

	@GetMapping("/tim-kiem/{title}")
	public ResponseEntity<List<MovieDTO>> searchMoviesByTitle(@RequestParam String title) {
		List<MovieDTO> foundMovies = movieService.findByTitle(title);
		return ResponseEntity.ok(foundMovies);
	}

	@PostMapping()
	public ResponseEntity<String> addNewMovie(@Valid @RequestBody MovieDTO movieDTO) {

		movieService.addNewMovie(movieDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Movie is added!");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateMovie(@PathVariable Integer id, @Valid @RequestBody MovieDTO movieDTO) {
		movieService.updateMovie(id, movieDTO);
		return ResponseEntity.ok("Movie is updated!");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable Integer id) {

		movieService.deleteMovie(id);
		return ResponseEntity.ok("Movie is deleted!");
	}
}
