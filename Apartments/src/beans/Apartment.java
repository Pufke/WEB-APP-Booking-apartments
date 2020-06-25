package beans;

import java.util.ArrayList;

public class Apartment {
	

	private Long ID;
	private int logicalDeleted;						// 1 - deleted, 0 - not deleted
	private String typeOfApartment; 				// it can be STANDARD or ROOM
	private Long numberOfRooms;
	private Long numberOfGuests;
	private Location location;
													// TODO : ALOOOOOOOOOOOOOOOOOOOO, RESITI OVO, datumi za izdavanje i dostupnost
													// po datumima.
	private ArrayList<String> datesForHosting;		// TODO: Trenutno je ovako, razmisliti oko tipa i hendlovanja oko datuma
													// za izdavanje(koje zadaje domacin)

	private Integer hostID; 
	private ArrayList<Integer> apartmentCommentsIDs; 
	private String images; 							// TODO: Videti sta cemo za slike

	private Long pricePerNight;

	private String timeForCheckIn; 
	private String timeForCheckOut;
	private String status; 							// ACTIVE or INACTIVE

	private ArrayList<Integer> apartmentAmentitiesIDs; 
	private ArrayList<String> listOfReservationsIDs; 

	
	public Apartment() {
		
	}


	public Apartment(Long iD, int logicalDeleted, String typeOfApartment, Long numberOfRooms, Long numberOfGuests,
			Location location, ArrayList<String> datesForHosting, Integer hostID,
			ArrayList<Integer> apartmentCommentsIDs, String images, Long pricePerNight, String timeForCheckIn,
			String timeForCheckOut, String status, ArrayList<Integer> apartmentAmentitiesIDs,
			ArrayList<String> listOfReservationsIDs) {
		super();
		ID = iD;
		this.logicalDeleted = logicalDeleted;
		this.typeOfApartment = typeOfApartment;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.datesForHosting = datesForHosting;
		this.hostID = hostID;
		this.apartmentCommentsIDs = apartmentCommentsIDs;
		this.images = images;
		this.pricePerNight = pricePerNight;
		this.timeForCheckIn = timeForCheckIn;
		this.timeForCheckOut = timeForCheckOut;
		this.status = status;
		this.apartmentAmentitiesIDs = apartmentAmentitiesIDs;
		this.listOfReservationsIDs = listOfReservationsIDs;
	}


	public Long getID() {
		return ID;
	}


	public void setID(Long iD) {
		ID = iD;
	}


	public int getLogicalDeleted() {
		return logicalDeleted;
	}


	public void setLogicalDeleted(int logicalDeleted) {
		this.logicalDeleted = logicalDeleted;
	}


	public String getTypeOfApartment() {
		return typeOfApartment;
	}


	public void setTypeOfApartment(String typeOfApartment) {
		this.typeOfApartment = typeOfApartment;
	}


	public Long getNumberOfRooms() {
		return numberOfRooms;
	}


	public void setNumberOfRooms(Long numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}


	public Long getNumberOfGuests() {
		return numberOfGuests;
	}


	public void setNumberOfGuests(Long numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public ArrayList<String> getDatesForHosting() {
		return datesForHosting;
	}


	public void setDatesForHosting(ArrayList<String> datesForHosting) {
		this.datesForHosting = datesForHosting;
	}


	public Integer getHostID() {
		return hostID;
	}


	public void setHostID(Integer hostID) {
		this.hostID = hostID;
	}


	public ArrayList<Integer> getApartmentCommentsIDs() {
		return apartmentCommentsIDs;
	}


	public void setApartmentCommentsIDs(ArrayList<Integer> apartmentCommentsIDs) {
		this.apartmentCommentsIDs = apartmentCommentsIDs;
	}


	public String getImages() {
		return images;
	}


	public void setImages(String images) {
		this.images = images;
	}


	public Long getPricePerNight() {
		return pricePerNight;
	}


	public void setPricePerNight(Long pricePerNight) {
		this.pricePerNight = pricePerNight;
	}


	public String getTimeForCheckIn() {
		return timeForCheckIn;
	}


	public void setTimeForCheckIn(String timeForCheckIn) {
		this.timeForCheckIn = timeForCheckIn;
	}


	public String getTimeForCheckOut() {
		return timeForCheckOut;
	}


	public void setTimeForCheckOut(String timeForCheckOut) {
		this.timeForCheckOut = timeForCheckOut;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public ArrayList<Integer> getApartmentAmentitiesIDs() {
		return apartmentAmentitiesIDs;
	}


	public void setApartmentAmentitiesIDs(ArrayList<Integer> apartmentAmentitiesIDs) {
		this.apartmentAmentitiesIDs = apartmentAmentitiesIDs;
	}


	public ArrayList<String> getListOfReservationsIDs() {
		return listOfReservationsIDs;
	}


	public void setListOfReservationsIDs(ArrayList<String> listOfReservationsIDs) {
		this.listOfReservationsIDs = listOfReservationsIDs;
	}


	
	
	
	
	
	
	
	

}
