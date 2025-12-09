package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);

}
