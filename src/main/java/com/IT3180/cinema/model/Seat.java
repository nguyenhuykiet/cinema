package com.IT3180.cinema.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`seatID`")
	private Integer seatID;

	@Column(name = "`seatName`", length = 10, nullable = false)
	private String seatName;

	@Column(name = "`seatType`", length = 10)
	private String seatType;

	@Column(name = "`isEmpty`")
	private Boolean isEmpty;

	@ManyToOne
	@JoinColumn(name = "`auditoriumName`", referencedColumnName = "`auditoriumName`")
	private Auditorium auditorium;

	@OneToMany(mappedBy = "seat")
	@ToString.Exclude
	private List<Book> books;

}
