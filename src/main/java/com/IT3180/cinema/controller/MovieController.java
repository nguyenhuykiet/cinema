package com.IT3180.cinema.controller;

import com.IT3180.cinema.dto.movie.MovieDetailDTO;
import com.IT3180.cinema.dto.movie.MovieSearchDTO;
import com.IT3180.cinema.dto.show.ShowDetailDTO;
import com.IT3180.cinema.service.MovieService;
import com.IT3180.cinema.service.ShowService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	private MovieService movieService;

	@Autowired
	private ShowService showService;

	
	/**
	 * Hiển thị danh sách phim
	 * @return
	 */
	@GetMapping("/all")
	public List<MovieSearchDTO> allMovies() {
		return movieService.getAllMovies();
	}

/**
	 * Hiển thị thông tin phim chi tiết
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public MovieDetailDTO findById(@PathVariable Integer id) {
		return movieService.findById(id);
	}

	/**
	 * Tìm kiếm phim
	 * @param title
	 * @return
	 */
	@GetMapping("/search/{title}")
	public List<MovieSearchDTO> searchMoviesByTitle(@PathVariable String title) {
		return movieService.searchMoviesByTitle(title);
	}

	/**
	 * Thêm phim mới
	 * @param movieDetailDTO
	 * @param poster
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addNewMovie(@RequestPart("movie") MovieDetailDTO movieDetailDTO, @RequestPart("poster") MultipartFile poster) throws IOException {
		if (poster.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Poster image is required!");

		movieService.addNewMovie(movieDetailDTO, poster);
		return ResponseEntity.ok("Movie is added!");
	}


	/**
	 * Cập nhật thông tin phim
	 * @param id
	 * @param movieDetailDTO
	 * @param poster
	 * @return
	 * @throws IOException
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateMovie(@PathVariable Integer id, @RequestPart("movie") MovieDetailDTO movieDetailDTO, @RequestPart(value = "poster", required = false) MultipartFile poster) throws IOException {
		movieService.updateMovie(id, movieDetailDTO, poster);
		return ResponseEntity.ok("Movie updated successfully!");
	}

	/**
	 * Xoá phim
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteMovie(@PathVariable Integer id) {
		movieService.deleteMovie(id);
		return ResponseEntity.ok("Movie is deleted!");
	}

	/**
	 * Lấy danh sách suất chiếu của phim
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/show")
	public List<ShowDetailDTO> getShows(@PathVariable Integer id) {
		return showService.getShowsByMovie(id);
	}
}
