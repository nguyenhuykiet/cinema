package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Show")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Show {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`showID`")
	private Integer showID;

	@Column(name = "`showTime`")
	private LocalTime showTime;

	@ManyToOne
	@JoinColumn(name = "`movieID`")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "`auditoriumName`")
	private Auditorium auditorium;

	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Book> books;

}
