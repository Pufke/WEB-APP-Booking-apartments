package beans;

import java.time.LocalDate;

public class Reservation {
	
	private Integer ID;
	private Integer logicalDeleted; 							// 1 - deleted, 0 - not deleted
	
	private Integer idOfReservedApartment;
	private LocalDate startDateOfReservation;					// date of when reservation is started
	private Integer numberOfNights; 							// num of nights 
	private Double totalPrice;									// total price for reservation
	private String messageForHost; 							
	private Integer guestID; 								
	private String statusOfReservation;							//(Kreirana, Odbijena, Odustanak, Prihvacena, Zavrsena)
	
	
	public Reservation() {
		
	}

	public Reservation(Integer iD, Integer logicalDeleted, Integer idOfReservedApartment,
			LocalDate startDateOfReservation, Integer numberOfNights, Double totalPrice, String messageForHost,
			Integer guestID, String statusOfReservation) {
		super();
		ID = iD;
		this.logicalDeleted = logicalDeleted;
		this.idOfReservedApartment = idOfReservedApartment;
		this.startDateOfReservation = startDateOfReservation;
		this.numberOfNights = numberOfNights;
		this.totalPrice = totalPrice;
		this.messageForHost = messageForHost;
		this.guestID = guestID;
		this.statusOfReservation = statusOfReservation;
	}

	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public Integer getLogicalDeleted() {
		return logicalDeleted;
	}


	public void setLogicalDeleted(Integer logicalDeleted) {
		this.logicalDeleted = logicalDeleted;
	}


	public Integer getIdOfReservedApartment() {
		return idOfReservedApartment;
	}


	public void setIdOfReservedApartment(Integer idOfReservedApartment) {
		this.idOfReservedApartment = idOfReservedApartment;
	}


	public LocalDate getStartDateOfReservation() {
		return startDateOfReservation;
	}


	public void setStartDateOfReservation(LocalDate startDateOfReservation) {
		this.startDateOfReservation = startDateOfReservation;
	}


	public Integer getNumberOfNights() {
		return numberOfNights;
	}


	public void setNumberOfNights(Integer numberOfNights) {
		this.numberOfNights = numberOfNights;
	}


	public Double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public String getMessageForHost() {
		return messageForHost;
	}


	public void setMessageForHost(String messageForHost) {
		this.messageForHost = messageForHost;
	}


	public Integer getGuestID() {
		return guestID;
	}


	public void setGuestID(Integer guestID) {
		this.guestID = guestID;
	}


	public String getStatusOfReservation() {
		return statusOfReservation;
	}


	public void setStatusOfReservation(String statusOfReservation) {
		this.statusOfReservation = statusOfReservation;
	}

	
	
}
