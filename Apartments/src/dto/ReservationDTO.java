package dto;

import beans.Apartment;
import beans.Guest;

public class ReservationDTO {
	private Apartment reservedApartment;
	private String dateOfReservation;
	private String numberOfNights; // Broj nocenja
	private Long totalPrice;
	private String messageForHost; //Poruka pri rezervaciji;
	private Guest guest; //Guest koji je napravio rezervaciju
	private String statusOfReservation;//(Kreirana, Odbijena, Odustanak, Prihvacena, Zavrsena)
}
