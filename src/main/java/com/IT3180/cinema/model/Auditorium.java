package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Auditorium")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auditorium {

	@Id
	@Column(name = "`auditoriumName`", length = 3)
	private String auditoriumName;

	@Column(name = "capacity")
	private int capacity;

	@Column(name = "status")
	private String status;

	@OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Seat> seats;

	@OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Show> shows;

}
