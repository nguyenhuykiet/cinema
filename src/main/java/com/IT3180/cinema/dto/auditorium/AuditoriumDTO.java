package com.IT3180.cinema.dto.auditorium;

import com.IT3180.cinema.model.Auditorium;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuditoriumDTO {
	private Integer auditoriumId;

	@NotBlank(message = "Enter auditorium's name")
	private String name;

	public AuditoriumDTO(Auditorium auditorium) {
		this.auditoriumId = auditorium.getAuditoriumID();
		this.name = auditorium.getName();
	}
}
