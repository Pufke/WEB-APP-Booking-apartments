package beans;

import java.util.ArrayList;

public class Apartment {

	private String typeOfApartment; // it can be STANDARD or ROOM
	private Long numberOfRooms;
	private Long numberOfGuests;
	private Location location;
	
	// TODO : ALOOOOOOOOOOOOOOOOOOOO, RESITI OVO, datumi za izdavanje i dostupnost
	// po datumima.
	private ArrayList<String> datesForHosting;// TODO: Trenutno je ovako, razmisliti oko tipa i hendlovanja oko datuma
												// za izdavanje(koje zadaje domacin)

	private Host host; //Host koji izdajes
	private ArrayList<String> comments; // TODO: Prebaciti u listu komentara kada napravimo klasu komentara
	private String images; // TODO: Videti sta cemo za slike

	private Long pricePerNight;

	private String timeForCheckIn; // TODO: Proveriti kako cemo i ovo cuvati
	private String timeForCheckOut;
	private String status; // ACTIVE ili INACTIVE

	private ArrayList<String> amentities; // TODO: Srediti ovo kada napravimo klasu sadrzaja apartmana
	private ArrayList<String> reservations; // TODO: a i ovo kad napravimo klasu rezervvacija.

	private Long identificator;
	private String reservedStatus;
	
	public Apartment(String typeOfA, Long numberOfRooms, Long pricePerNight, String status) {

		this.typeOfApartment = typeOfA;
		this.numberOfRooms = numberOfRooms;
		this.pricePerNight = pricePerNight;
		this.status = status;
	}

	public Apartment(Long identificator2, String typeOfA,Long numberOfRooms2, Long numberOfGuests2, Location location, Long pricePerNight2,
			String timeForCheckIn, String timeForCheckOut, String status, String reservedStatus) {

		this.typeOfApartment = typeOfA;
		this.numberOfRooms = numberOfRooms2;
		this.numberOfGuests = numberOfGuests2;
		this.pricePerNight = pricePerNight2;
		this.timeForCheckIn = timeForCheckIn;
		this.timeForCheckOut = timeForCheckOut;
		this.status = status;
		this.identificator = identificator2;
		this.reservedStatus = reservedStatus;
		this.location = location;
	}

	// TODO: Srediti SVE getere i setere kad konacno sredimo atribute, POSTO JE OVO
	// SAMO NESTO
	// sto mi je trebalo u tom trenutku



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

	public Long getNumberOfGuests() {
		return numberOfGuests;
	}

	public Long getIdentificator() {
		return identificator;
	}

	public void setIdentificator(Long identificator) {
		this.identificator = identificator;
	}

	public String getReservedStatus() {
		return reservedStatus;
	}

	public void setReservedStatus(String reservedStatus) {
		this.reservedStatus = reservedStatus;
	}

	public void setNumberOfGuests(Long numberOfGuests) {
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

	public Long getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(Long numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public Long getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(Long pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
