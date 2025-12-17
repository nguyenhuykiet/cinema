package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Movie")
@Data
@NoArgsConstructor
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`movieID`")
	private Integer movieID;

	@Column(name = "title", nullable = false, unique = true)
	private String title;

	@Column(name = "directors")
	private String directors;

	@Column(name = "casts")
	private String casts;

	@ManyToMany
	@JoinTable(name = "`Movie_Genre`", joinColumns = @JoinColumn(name = "`movieID`"), inverseJoinColumns = @JoinColumn(name = "`genreId`"))
	private Set<Genre> genres = new HashSet<>();

	@Column(name = "`openingDay`")
	private LocalDate openingDay;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "`ageRating`")
	private Integer ageRating;

	@Column(name = "synopsis")
	private String synopsis;

	@OneToMany(mappedBy = "movie")
	@ToString.Exclude
	private List<Show> shows;

	public Movie(String title, String directors, String casts, Set<Genre> genres, LocalDate openingDay, Integer duration, Integer ageRating, String synopsis) {
		this.title = title;
		this.directors = directors;
		this.casts = casts;
		this.genres = genres;
		this.openingDay = openingDay;
		this.duration = duration;
		this.ageRating = ageRating;
		this.synopsis = synopsis;
	}
}
