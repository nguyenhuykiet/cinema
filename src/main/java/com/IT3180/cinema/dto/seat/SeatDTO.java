package com.IT3180.cinema.dto.seat;

import lombok.Data;

@Data
public class SeatDTO {
	private Integer seatId;
	private String name;
	private boolean booked;

	public SeatDTO(Integer seatId, String name, boolean booked) {
		this.seatId = seatId;
		this.name = name;
		this.booked = booked;
	}
}
