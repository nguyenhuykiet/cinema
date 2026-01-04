package com.IT3180.cinema.dto.seat;

import lombok.Data;

@Data
public class SeatDTO {
	private String name;
	private boolean booked;

	public SeatDTO(String name, boolean booked) {
		this.name = name;
		this.booked = booked;
	}
}
