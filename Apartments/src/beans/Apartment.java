package beans;


import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Apartment {

	private Integer ID;
	private Integer logicalDeleted; // 1 - deleted, 0 - not deleted
	private String typeOfApartment; // it can be STANDARD or ROOM
	private Integer numberOfRooms;
	private Integer numberOfGuests;
	private Location location;

	private ArrayList<Date> datesForHosting; // host set those dates
	private ArrayList<Date> availableDates;

	private Integer hostID;
	private ArrayList<Integer> apartmentCommentsIDs;
	private String imagesPath; // TODO: Videti sta cemo za slike

	private Double pricePerNight;

	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
	private String timeForCheckIn;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
	private String timeForCheckOut;
	
	private String status; // ACTIVE or INACTIVE

	private ArrayList<Integer> apartmentAmentitiesIDs;
	private ArrayList<Integer> listOfReservationsIDs;

	public Apartment() {

	}
	
	public Apartment(Integer iD, Integer logicalDeleted, String typeOfApartment, Integer numberOfRooms,
			Integer numberOfGuests, Location location, ArrayList<Date> datesForHosting,
			ArrayList<Date> availableDates, Integer hostID, ArrayList<Integer> apartmentCommentsIDs,
			String imagesPath, Double pricePerNight, String timeForCheckIn, String timeForCheckOut, String status,
			ArrayList<Integer> apartmentAmentitiesIDs, ArrayList<Integer> listOfReservationsIDs) {
		super();
		ID = iD;
		this.logicalDeleted = logicalDeleted;
		this.typeOfApartment = typeOfApartment;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.datesForHosting = datesForHosting;
		this.availableDates = availableDates;
		this.hostID = hostID;
		this.apartmentCommentsIDs = apartmentCommentsIDs;
		this.imagesPath = imagesPath;
		this.pricePerNight = pricePerNight;
		this.timeForCheckIn = timeForCheckIn;
		this.timeForCheckOut = timeForCheckOut;
		this.status = status;
		this.apartmentAmentitiesIDs = apartmentAmentitiesIDs;
		this.listOfReservationsIDs = listOfReservationsIDs;
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

	public String getTypeOfApartment() {
		return typeOfApartment;
	}

	public void setTypeOfApartment(String typeOfApartment) {
		this.typeOfApartment = typeOfApartment;
	}

	public Integer getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(Integer numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public Integer getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(Integer numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ArrayList<Date> getDatesForHosting() {
		return datesForHosting;
	}

	public void setDatesForHosting(ArrayList<Date> datesForHosting) {
		this.datesForHosting = datesForHosting;
	}

	public ArrayList<Date> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(ArrayList<Date> availableDates) {
		this.availableDates = availableDates;
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

	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}

	public Double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(Double pricePerNight) {
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

	public ArrayList<Integer> getListOfReservationsIDs() {
		return listOfReservationsIDs;
	}

	public void setListOfReservationsIDs(ArrayList<Integer> listOfReservationsIDs) {
		this.listOfReservationsIDs = listOfReservationsIDs;
	}

	
	

}
