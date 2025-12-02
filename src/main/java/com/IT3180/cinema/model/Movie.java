package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`movieID`")
	private Integer movieID;

	@Column(name = "title", unique = true)
	private String title;

	@Column(name = "directors")
	private String directors;

	@Column(name = "casts")
	private String casts;

	@Column(name = "genres")
	private String genres;

	@Column(name = "`openingDay`")
	private LocalDate openingDay;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "`ageRating`")
	private Integer ageRating;

	@Column(name = "synopsis")
	private String synopsis;

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Show> shows;

}
