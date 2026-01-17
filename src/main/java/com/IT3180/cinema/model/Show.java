package com.IT3180.cinema.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Show",
		uniqueConstraints = @UniqueConstraint(
				columnNames = {"`showTime`", "`auditoriumID`"}
		))
@NoArgsConstructor
@Data
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`showID`")
	private Integer showID;

	@ManyToOne
	@JoinColumn(name = "`movieID`", referencedColumnName = "`movieID`")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "`auditoriumID`", referencedColumnName = "`auditoriumID`")
	private Auditorium auditorium;

	@Column(name = "`showTime`", nullable = false)
	private LocalDateTime showTime;

	@Column(name = "price", nullable = false)
	private int price;

	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Ticket> tickets;

	public Show(Movie movie, Auditorium auditorium, LocalDateTime showTime, int price) {
		this.movie = movie;
		this.auditorium = auditorium;
		this.showTime = showTime;
		this.price = price;
	}
}
