package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	List<Movie> findByTitleContainingIgnoreCase(String title);
	List<Movie> findByGenresContainingIgnoreCase(String genre);
	boolean existsByTitle(String title);

}
