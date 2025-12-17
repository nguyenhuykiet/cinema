package com.IT3180.cinema.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "Genre")
@Data
@NoArgsConstructor
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`genreID`")
	private Integer genreID;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@ManyToMany
	@ToString.Exclude
	private Set<Movie> movies;

	public Genre(Integer genreID, String name) {
		this.genreID = genreID;
		this.name = name;
	}
}
