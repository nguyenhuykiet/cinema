package com.IT3180.cinema.dto.user;

import com.IT3180.cinema.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.jspecify.annotations.NonNull;

@NoArgsConstructor
@Data
public class UserProfileDTO {
	private String fullName;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	private String email;
	private String tel;
	private String role;

	public UserProfileDTO(@NonNull User user) {
		this.fullName = user.getFullName();
		this.dateOfBirth = user.getDateOfBirth();
		this.email = user.getEmail();
		this.tel = user.getTel();
		this.role = user.getRole() == null ? null : user.getRole().name();
	}
}
