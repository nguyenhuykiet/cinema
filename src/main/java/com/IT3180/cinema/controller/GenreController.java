package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.MovieDTO;
import com.IT3180.cinema.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
	@Autowired
	private GenreService genreService;

	@GetMapping("/{slug}")
	public List<MovieDTO> findMoviesByGenre(@PathVariable String slug) {
		return genreService.findMoviesByGenre(slug);
	}
}
