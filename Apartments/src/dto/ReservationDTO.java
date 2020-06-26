package dto;

import java.util.Date;

public class ReservationDTO {
	
	public Integer apartmentIdentificator;
	public Date dateOfReservation;
	public Integer numberOfNights; // Broj nocenja
	public String messageForHost; //Poruka pri rezervaciji;
	public Integer guestID; //Guest koji je napravio rezervaciju
	public String statusOfReservation;//(Kreirana, Odbijena, Odustanak, Prihvacena, Zavrsena)
	public Date fromDate;
	public Date toDate;
}
