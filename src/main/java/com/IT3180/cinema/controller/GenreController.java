package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.movie.MovieSearchDTO;
import com.IT3180.cinema.service.GenreService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genre")
public class GenreController {
	@Autowired
	private GenreService genreService;

	/**
	 * Liệt kê phim theo thể loại
	 * @param slug
	 * @return
	 */
	@GetMapping("/{slug}")
	public List<MovieSearchDTO> findMoviesByGenre(@PathVariable String slug) {
		return genreService.findMoviesByGenre(slug);
	}
}
