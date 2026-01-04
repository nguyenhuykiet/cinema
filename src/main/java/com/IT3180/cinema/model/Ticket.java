package com.IT3180.cinema.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ticket", uniqueConstraints = {@UniqueConstraint(columnNames = {"`showID`", "`seatID`"})})
@NoArgsConstructor
@Data
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`ticketID`")
	private Integer ticketID;

	@Column(name = "`bookingTime`", nullable = false)
	private LocalDateTime bookingTime;

	@ManyToOne
	@JoinColumn(name = "`userID`", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "`showID`", nullable = false)
	private Show show;

	@ManyToOne
	@JoinColumn(name = "`seatID`", nullable = false)
	private Seat seat;

	public Ticket(LocalDateTime bookingTime, User user, Show show, Seat seat) {
		this.bookingTime = bookingTime;
		this.user = user;
		this.show = show;
		this.seat = seat;
	}
}
