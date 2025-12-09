package com.IT3180.cinema.dto;

import lombok.Data;

@Data
public class PasswordChangeRequest {
	private String oldPassword;
	private String newPassword;
}
