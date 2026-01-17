package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Genre;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	Optional<Genre> findBySlug(String slug);

	boolean existsByName(String name);
}
