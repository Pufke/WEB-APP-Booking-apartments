package beans;


public class Reservation {
	
	private Integer ID;
	//private int logicalDeleted;						// 1 - deleted, 0 - not deleted
	private Integer idOfReservedApartment;
	private String startDateOfReservation;					// date of when reservation is started
	private String numberOfNights; 							// num of nights 
	private Long totalPrice;								// total price for reservation
	private String messageForHost; 							
	private Integer guestID; 								
	private String statusOfReservation;						//(Kreirana, Odbijena, Odustanak, Prihvacena, Zavrsena)
	
	
	public Reservation() {
		
	}
	
	public Reservation(Integer iD, Integer idOfReservedApartment, String startDateOfReservation, String numberOfNights,
			Long totalPrice, String messageForHost, Integer guestID, String statusOfReservation) {
		super();
		ID = iD;
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


	public Integer getIdOfReservedApartment() {
		return idOfReservedApartment;
	}


	public void setIdOfReservedApartment(Integer idOfReservedApartment) {
		this.idOfReservedApartment = idOfReservedApartment;
	}


	public String getStartDateOfReservation() {
		return startDateOfReservation;
	}


	public void setStartDateOfReservation(String startDateOfReservation) {
		this.startDateOfReservation = startDateOfReservation;
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
