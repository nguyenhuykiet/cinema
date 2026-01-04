package com.IT3180.cinema.dto.ticket;

import com.IT3180.cinema.model.Ticket;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.jspecify.annotations.NonNull;

@NoArgsConstructor
@Data
public class TicketDTO {
	private Integer ticketId;
	private Integer showID;
	private String title;
	private LocalDateTime showTime;
	private String seatName;

	public TicketDTO(@NonNull Ticket ticket) {
		this.ticketId = ticket.getTicketID();
		this.showID = ticket.getShow().getShowID();
		this.title = ticket.getShow().getMovie().getTitle();
		this.showTime = ticket.getShow().getShowTime();
		this.seatName = ticket.getSeat().getName();
	}
}
