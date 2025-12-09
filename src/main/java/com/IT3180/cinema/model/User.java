package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Audience")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Column(name = "username", length = 20, nullable = false)
	private String username;

	@Column(name = "password", length = 20, nullable = false)
	@ToString.Exclude
	private String password;

	@Column(name = "`fullName`")
	private String fullName;

	@Column(name = "`dateOfBirth`")
	private LocalDate dateOfBirth;

	@Column(name = "role")
	private String role;

	@Column(name = "tel")
	private String tel;

	@Column(name = "email", unique = true)
	@ToString.Exclude
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Book> books;

}
