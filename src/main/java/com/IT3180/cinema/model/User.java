package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "`User`")
@Data
@NoArgsConstructor
public class User {

	@Id
	@Column(name = "username", length = 20, nullable = false)
	private String username;

	@Column(name = "password", length = 100, nullable = false)
	@ToString.Exclude
	private String password;

	@Column(name = "`fullName`")
	private String fullName;

	@Column(name = "`dateOfBirth`")
	private LocalDate dateOfBirth;

	@Column(name = "role", nullable = false)
	private String role;

	@Column(name = "email", unique = true)
	@ToString.Exclude
	private String email;

	@Column(name = "tel")
	private String tel;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Book> books;

	public User(String username, String password, String fullName, LocalDate dateOfBirth, String role, String email, String tel) {
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.email = email;
		this.tel = tel;
	}
}
