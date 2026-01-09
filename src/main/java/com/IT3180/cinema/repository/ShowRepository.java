package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Auditorium;
import com.IT3180.cinema.model.Show;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
	boolean existsByMovie_MovieID(Integer movieID);

	boolean existsByAuditorium_AuditoriumID(Integer auditoriumID);

	boolean existsByAuditoriumAndShowTime(Auditorium auditorium, LocalDateTime showTime);

	List<Show> findByMovie_MovieID(Integer movieID);

	List<Show> findByAuditorium_AuditoriumIDOrderByShowTimeAsc(Integer auditoriumId);
}
