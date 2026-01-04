package com.IT3180.cinema.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "`User`")
@NoArgsConstructor
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`userID`")
	private Integer userID;

	@Column(name = "email", unique = true)
	@ToString.Exclude
	private String email;

	@Column(name = "password", nullable = false)
	@ToString.Exclude
	private String password;

	@Column(name = "`fullName`")
	private String fullName;

	@Column(name = "`dateOfBirth`")
	private LocalDate dateOfBirth;

	@Column(name = "tel")
	private String tel;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Ticket> tickets;

	public User(String email, String password, String fullName, LocalDate dateOfBirth, String tel) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.tel = tel;
	}
}
