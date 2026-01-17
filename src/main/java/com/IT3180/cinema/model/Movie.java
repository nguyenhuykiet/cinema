package com.IT3180.cinema.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Movie")
@NoArgsConstructor
@Data
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`movieID`")
	private Integer movieID;

	@Column(name = "title", nullable = false, unique = true, length = 100)
	private String title;

	@Column(name = "directors", length = 100)
	private String directors;

	@Column(name = "casts", length = 200)
	private String casts;

	@ManyToMany
	@JoinTable(name = "`Movie_Genre`", joinColumns = @JoinColumn(name = "`movieID`"), inverseJoinColumns = @JoinColumn(name = "`genreId`"))
	private List<Genre> genres;

	@Column(name = "`openingDay`")
	private LocalDate openingDay;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "`ageRating`")
	private String ageRating;

	@Column(name = "synopsis", length = 1000)
	private String synopsis;

	@Column(name = "`posterUrl`", length = 100)
	private String posterUrl;

	@OneToMany(mappedBy = "movie")
	@ToString.Exclude
	private List<Show> shows;

	public Movie(String title, String directors, String casts, List<Genre> genres, LocalDate openingDay, Integer duration, String ageRating, String synopsis) {
		this.title = title;
		this.directors = directors;
		this.casts = casts;
		this.genres = genres;
		this.openingDay = openingDay;
		this.duration = duration;
		this.ageRating = ageRating;
		this.synopsis = synopsis;
	}

	public List<String> extractGenreNames() {
		return genres.stream().map(Genre::getName).toList();
	}
}
