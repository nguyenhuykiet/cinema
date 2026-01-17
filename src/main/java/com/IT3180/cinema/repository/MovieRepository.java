package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	List<Movie> findByTitleContainingIgnoreCase(String title);

	boolean existsByTitle(String title);
}
