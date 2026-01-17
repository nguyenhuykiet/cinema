package com.IT3180.cinema.dto.movie;

import com.IT3180.cinema.model.Movie;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.jspecify.annotations.NonNull;

@NoArgsConstructor
@Data
public class MovieSearchDTO {
	private Integer movieID;
	private String title;
	private String posterUrl;

	public MovieSearchDTO(@NonNull Movie movie) {
		this.movieID = movie.getMovieID();
		this.title = movie.getTitle();
		this.posterUrl = movie.getPosterUrl();
	}
}
