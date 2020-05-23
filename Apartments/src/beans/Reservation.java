package beans;


public class Reservation {
	
	private String reservationID;
	private Apartment reservedApartment;
	private String dateOfReservation;
	private String numberOfNights; // Broj nocenja
	private Long totalPrice;
	private String messageForHost; //Poruka pri rezervaciji;
	private Guest guest; //Guest koji je napravio rezervaciju
	private String statusOfReservation;//(Kreirana, Odbijena, Odustanak, Prihvacena, Zavrsena)
	
	public Reservation(String reservationID, Apartment reservedApartment, String dateOfReservation,
			String numberOfNights, Long totalPrice,
			String messageForHost, Guest guest, String statusOfReservation) {
		super();
		this.reservationID = reservationID;
		this.reservedApartment = reservedApartment;
		this.dateOfReservation = dateOfReservation;
		this.numberOfNights = numberOfNights;
		this.totalPrice = totalPrice;
		this.messageForHost = messageForHost;
		this.guest = guest;
		this.statusOfReservation = statusOfReservation;
	}
	public Apartment getReservedApartment() {
		return reservedApartment;
	}
	public void setReservedApartment(Apartment reservedApartment) {
		this.reservedApartment = reservedApartment;
	}
	public String getDateOfReservation() {
		return dateOfReservation;
	}
	public void setDateOfReservation(String dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}
	public String getNumberOfNights() {
		return numberOfNights;
	}
	public void setNumberOfNights(String numberOfNights) {
		this.numberOfNights = numberOfNights;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getMessageForHost() {
		return messageForHost;
	}
	public void setMessageForHost(String messageForHost) {
		this.messageForHost = messageForHost;
	}
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	public String getStatusOfReservation() {
		return statusOfReservation;
	}
	public void setStatusOfReservation(String statusOfReservation) {
		this.statusOfReservation = statusOfReservation;
	}
	public String getReservationID() {
		return reservationID;
	}
	public void setReservationID(String reservationID) {
		this.reservationID = reservationID;
	}
	
	
}
