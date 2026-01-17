package com.IT3180.cinema.dto.movie;

import com.IT3180.cinema.model.Genre;
import com.IT3180.cinema.model.Movie;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.jspecify.annotations.NonNull;

@NoArgsConstructor
@Data
public class MovieDetailDTO {
	private Integer movieID;

	@NotBlank(message = "Title cannot blank!")
	private String title;

	private String directors;
	private String casts;
	private List<String> genres;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate openingDay;

	@NotNull(message = "Enter duration!")
	@Positive(message = "Duration must be positive!")
	private Integer duration;

	@NotNull(message = "Enter age rating!")
	@Min(value = 0, message = "Age rating cannot be negative!")
	@Max(value = 18, message = "Age rating cannot greater than 18!")
	private String ageRating;

	@Size(max = 1000)
	private String synopsis;

	@Size(max = 100)
	private String posterUrl;

	public MovieDetailDTO(@NonNull Movie movie) {
		this.movieID = movie.getMovieID();
		this.title = movie.getTitle();
		this.directors = movie.getDirectors();
		this.casts = movie.getCasts();
		this.genres = movie.extractGenreNames();
		this.openingDay = movie.getOpeningDay();
		this.duration = movie.getDuration();
		this.ageRating = movie.getAgeRating();
		this.synopsis = movie.getSynopsis();
		this.posterUrl = movie.getPosterUrl();
	}

	public List<Genre> convertToGenreList() {
		List<Genre> genreList = new ArrayList<>();
		for (String genre : genres)
			genreList.add(new Genre(genre));
		return genreList;
	}
}
