package dto;

import beans.Apartment;
import beans.Guest;

public class ReservationDTO {
	
	public Long apartmentIdentificator;
	public String dateOfReservation;
	public String numberOfNights; // Broj nocenja
	public String messageForHost; //Poruka pri rezervaciji;
	public String guestUserName; //Guest koji je napravio rezervaciju
	public String statusOfReservation;//(Kreirana, Odbijena, Odustanak, Prihvacena, Zavrsena)
}
