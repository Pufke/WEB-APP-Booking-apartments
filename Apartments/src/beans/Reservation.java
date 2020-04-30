package beans;

public class Reservation {
	private Apartment reservedApartment;
	private String dateOfReservation;
	private String numberOfNights; // Broj nocenja
	private Integer totalPrice;
	private String messageForHost; //Poruka pri rezervaciji;
	private Guest guest;
	private String statusOfReservation;//(Kreirana, Odbijena, Odustanak, Prihvacena, Zavrsena)
	
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
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
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
	
	
}
