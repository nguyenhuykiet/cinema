package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ticket", uniqueConstraints = {@UniqueConstraint(columnNames = {"`showID`", "`seatID`"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`ticketID`")
	private Integer ticketID;

	@ManyToOne
	@JoinColumn(name = "`userID`", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "`showID`", nullable = false)
	private Show show;

	@ManyToOne
	@JoinColumn(name = "`seatID`", nullable = false)
	private Seat seat;
}
