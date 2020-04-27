package beans;

import java.util.ArrayList;

public class Apartment {

	private String typeOfApartment; // it can be STANDARD or ROOM
	private Integer numberOfRooms;
	private Integer numberOfGuests;
	private String location; // TODO: Trenutno je string, ali kasnije treba prebaciti u klasu instancu klase
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

	public Apartment(String typeOfA, Integer numberOfRooms, double pricePerNight, String status) {

		this.typeOfApartment = typeOfA;
		this.numberOfRooms = numberOfRooms;
		this.pricePerNight = pricePerNight;
		this.status = status;
	}

	// TODO: Srediti SVE getere i setere kad konacno sredimo atribute, POSTO JE OVO SAMO NESTO
	// sto mi je trebalo u tom trenutku
	
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
