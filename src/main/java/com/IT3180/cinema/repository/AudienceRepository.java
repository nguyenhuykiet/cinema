package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Audience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, String> {

	Audience findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);

}
