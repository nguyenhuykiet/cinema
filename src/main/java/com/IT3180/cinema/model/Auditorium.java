package com.IT3180.cinema.model;

import jakarta.persistence.*;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Auditorium")
@NoArgsConstructor
@Data
public class Auditorium {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`auditoriumID`")
	private Integer auditoriumID;

	@Column(name = "name", nullable = false, unique = true, length = 10)
	private String name;

	@OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<Seat> seats;

	@OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Show> shows;

	public Auditorium(String name) {
		this.name = name;
	}
}
