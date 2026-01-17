package com.IT3180.cinema.dto.show;

import com.IT3180.cinema.model.Show;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.jspecify.annotations.NonNull;

@NoArgsConstructor
@Data
public class ShowConstructDTO {
	@NotNull
	private Integer movieId;

	@NotNull
	private Integer auditoriumId;

	@NotNull
	@Future
	private LocalDateTime showTime;

	@Positive
	private int price;

	public ShowConstructDTO(@NonNull Show show) {
		this.movieId = show.getMovie().getMovieID();
		this.auditoriumId = show.getAuditorium().getAuditoriumID();
		this.showTime = show.getShowTime();
		this.price = show.getPrice();
	}
}
