package com.IT3180.cinema.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.Normalizer;
import java.util.List;

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

	@Column(name = "slug", nullable = false, unique = true)
	private String slug;

	@ManyToMany
	@ToString.Exclude
	private List<Movie> movies;

	private static String toSlug(String input) {
		String noAccent = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return noAccent.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
	}

	public Genre(String name) {
		this.name = name;
		this.slug = toSlug(name);
	}
}
