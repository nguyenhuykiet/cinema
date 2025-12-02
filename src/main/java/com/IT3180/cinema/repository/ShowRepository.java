package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Show;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

}
