package com.IT3180.cinema.model;

import jakarta.persistence.*;

import java.text.Normalizer;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Genre")
@NoArgsConstructor
@Data
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`genreID`")
	private Integer genreID;

	@Column(name = "name", nullable = false, unique = true, length = 50)
	private String name;

	@Column(name = "slug", nullable = false, unique = true, length = 50)
	private String slug;

	@ManyToMany(mappedBy = "genres")
	@ToString.Exclude
	private List<Movie> movies;

	public Genre(String name) {
		this.name = name;
		this.slug = toSlug(name);
	}

	private static String toSlug(String input) {
		String normalized = input.replace('Đ', 'D').replace('đ', 'd');
		String noAccent = Normalizer.normalize(normalized, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return noAccent.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
	}
}
