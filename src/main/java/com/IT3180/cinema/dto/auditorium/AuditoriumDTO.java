package com.IT3180.cinema.dto.auditorium;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuditoriumDTO {
	@NotBlank(message = "Enter auditorium's name")
	private String name;
}
