package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.MovieDTO;
import com.IT3180.cinema.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/the-loai")
public class GenreController {
	@Autowired
	private GenreService genreService;

	@GetMapping
	public Set<MovieDTO> findMoviesByGenre(@RequestParam String genre) {
		return genreService.findMoviesByGenre(genre);
	}
}
