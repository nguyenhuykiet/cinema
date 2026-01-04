package com.IT3180.cinema.repository;

import com.IT3180.cinema.model.Ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	boolean existsByShow_ShowID(Integer showID);

	boolean existsByShow_ShowIDAndSeat_SeatID(Integer showId, Integer seatId);

	List<Ticket> findByUser_UserID(Integer userId);
}
