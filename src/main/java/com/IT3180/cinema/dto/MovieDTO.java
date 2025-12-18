package com.IT3180.cinema.dto;

import com.IT3180.cinema.model.Genre;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

	@NotBlank(message = "Title cannot blank!")
	private String title;

	private String directors;
	private String casts;
	private List<String> genres;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Future(message = "Invalid opening day!")
	private LocalDate openingDay;

	@NotNull(message = "Enter duration!")
	@Positive(message = "Duration must be positive!")
	private Integer duration;

	@NotNull(message = "Enter age rating!")
	@Min(value = 0, message = "Age rating cannot be negative!")
	@Max(value = 18, message = "Age rating cannot greater than 18!")
	private Integer ageRating;

	@Size(max = 1000)
	private String synopsis;

	public List<Genre> convertToGenreList() {
		List<Genre> genreList = new ArrayList<>();
		for (String genre : genres)
			genreList.add(new Genre(genre));
		return genreList;
	}
}
