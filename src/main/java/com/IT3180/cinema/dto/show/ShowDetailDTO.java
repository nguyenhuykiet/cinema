package com.IT3180.cinema.dto.show;

import com.IT3180.cinema.model.Show;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ShowDetailDTO {
	private Integer showId;
	private String movieTitle;
	private String auditoriumName;
	private LocalDateTime showTime;
	private int price;

	public ShowDetailDTO(@NonNull Show show) {
		this.showId = show.getShowID();
		this.movieTitle = show.getMovie().getTitle();
		this.auditoriumName = show.getAuditorium().getName();
		this.showTime = show.getShowTime();
		this.price = show.getPrice();
	}
}
