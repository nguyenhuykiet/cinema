package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Auditorium;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Integer> {
	boolean existsByName(String name);
}
