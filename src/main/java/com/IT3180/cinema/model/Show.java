package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Show")
@Data
@NoArgsConstructor
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

	@Column(name = "`showDate`", nullable = false)
	private LocalDate showDate;

	@Column(name = "`showTime`", nullable = false)
	private LocalTime showTime;

	@Column(name = "price", nullable = false)
	private int price;

	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Ticket> tickets;

	public Show(Integer showID, Movie movie, Auditorium auditorium, LocalDate showDate, LocalTime showTime, int price) {
		this.showID = showID;
		this.movie = movie;
		this.auditorium = auditorium;
		this.showDate = showDate;
		this.showTime = showTime;
		this.price = price;
	}
}
