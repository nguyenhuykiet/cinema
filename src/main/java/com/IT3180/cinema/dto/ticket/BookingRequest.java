package com.IT3180.cinema.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookingRequest {
	private Integer showId;
	private Integer seatId;
}
