package com.IT3180.cinema.model;

import jakarta.persistence.*;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Seat", uniqueConstraints = @UniqueConstraint(columnNames = {"`auditoriumID`", "name"}))
@NoArgsConstructor
@Data
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
