package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Auditorium;

import com.IT3180.cinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, String> {

	Auditorium findByAuditoriumName(String name);
	boolean existsByAuditoriumName(String name);

}
