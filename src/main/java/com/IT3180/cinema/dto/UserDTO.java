package com.IT3180.cinema.dto;

import com.IT3180.cinema.model.Book;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDTO {
	private String username;
	private String password;
	private String fullName;
	private LocalDate dateOfBirth;
	private String role;
	private String tel;
	private String email;
	private List<Book> books;
}
