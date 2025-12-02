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
public class Audience {

	@Id
	@Column(name = "username", length = 20)
	private String username;

	@Column(name = "password", length = 20, nullable = false)
	@ToString.Exclude
	private String password;

	@Column(name = "`fullName`")
	private String fullName;

	@Column(name = "`dateOfBirth`")
	private LocalDate dateOfBirth;

	@Column(name = "tel")
	private String tel;

	@Column(name = "email", unique = true)
	private String email;

	@OneToMany(mappedBy = "audience", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Book> books;

}
