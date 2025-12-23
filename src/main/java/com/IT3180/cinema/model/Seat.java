package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Seat", uniqueConstraints = @UniqueConstraint(columnNames = {"`auditoriumID`", "name"}))
@Data
@NoArgsConstructor
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`seatID`")
	private Integer seatID;

	@Column(name = "name", nullable = false, length = 3)
	private String name;

	@ManyToOne
	@JoinColumn(name = "`auditoriumID`", referencedColumnName = "`auditoriumID`", nullable = false)
	private Auditorium auditorium;

	@OneToMany(mappedBy = "seat")
	@ToString.Exclude
	private List<Ticket> tickets;

	public Seat(String name, Auditorium auditorium) {
		this.name = name;
		this.auditorium = auditorium;
	}
}
