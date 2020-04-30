package beans;

import java.util.ArrayList;

public class Apartment {

	private String typeOfApartment; // it can be STANDARD or ROOM
	private Integer numberOfRooms;
	private Integer numberOfGuests;
	
	private Integer locationID;
	private Location location; //Pocetna ideja je da lokacija ima svoj idnetifikator,
	
	//private String location; // TODO: Trenutno je string, ali kasnije treba prebaciti u klasu instancu klase
								// Location, KOJU TREBA NAPRAVITI

	
	
	// TODO : ALOOOOOOOOOOOOOOOOOOOO, RESITI OVO, datumi za izdavanje i dostupnost
	// po datumima.
	private ArrayList<String> datesForHosting;// TODO: Trenutno je ovako, razmisliti oko tipa i hendlovanja oko datuma
												// za izdavanje(koje zadaje domacin)

	private Host host;
	private ArrayList<String> comments; // TODO: Prebaciti u listu komentara kada napravimo klasu komentara
	private String images; // TODO: Videti sta cemo za slike

	private double pricePerNight;

	private String timeForCheckIn; // TODO: Proveriti kako cemo i ovo cuvati
	private String timeForCheckOut;
	private String status; // ACTIVE ili INACTIVE

	private ArrayList<String> amentities; // TODO: Srediti ovo kada napravimo klasu sadrzaja apartmana
	private ArrayList<String> reservations; // TODO: a i ovo kad napravimo klasu rezervvacija.

	private Integer identificator;
	private String reservedStatus;
	
	public Apartment(String typeOfA, Integer numberOfRooms, double pricePerNight, String status) {

		this.typeOfApartment = typeOfA;
		this.numberOfRooms = numberOfRooms;
		this.pricePerNight = pricePerNight;
		this.status = status;
	}

	public Apartment(Integer identificator, String typeOfA,Integer numberOfRooms, Integer numberOfGuests, Location location, double pricePerNight,
			String timeForCheckIn, String timeForCheckOut, String status,String reservedStatus, Integer locationID) {

		this.typeOfApartment = typeOfA;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.pricePerNight = pricePerNight;
		this.timeForCheckIn = timeForCheckIn;
		this.timeForCheckOut = timeForCheckOut;
		this.status = status;
		this.identificator = identificator;
		this.reservedStatus = reservedStatus;
		this.locationID = locationID;
		this.location = location;
	}

	// TODO: Srediti SVE getere i setere kad konacno sredimo atribute, POSTO JE OVO
	// SAMO NESTO
	// sto mi je trebalo u tom trenutku

	
	


	public Integer getLocationID() {
		return locationID;
	}

	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
	}

	public ArrayList<String> getDatesForHosting() {
		return datesForHosting;
	}

	public void setDatesForHosting(ArrayList<String> datesForHosting) {
		this.datesForHosting = datesForHosting;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public ArrayList<String> getAmentities() {
		return amentities;
	}

	public void setAmentities(ArrayList<String> amentities) {
		this.amentities = amentities;
	}

	public ArrayList<String> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<String> reservations) {
		this.reservations = reservations;
	}

	public Integer getNumberOfGuests() {
		return numberOfGuests;
	}

	public Integer getIdentificator() {
		return identificator;
	}

	public void setIdentificator(Integer identificator) {
		this.identificator = identificator;
	}

	public String getReservedStatus() {
		return reservedStatus;
	}

	public void setReservedStatus(String reservedStatus) {
		this.reservedStatus = reservedStatus;
	}

	public void setNumberOfGuests(Integer numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
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

	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
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

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
