package beans;

public class Address {
	private String street;
	private String number; //Misli se na broj kuce, apartmana , stana
	private String populatedPlace;
	private String zipCode; //npr. Sutjeska 3, Novi Sad 21000
	private Integer addressID; 
	
	public Address(String street, String number, String populatedPlace, String zipCode, Integer addressID) {
		super();
		this.street = street;
		this.number = number;
		this.populatedPlace = populatedPlace;
		this.zipCode = zipCode;
		this.addressID = addressID;
	}
	public Integer getAddressID() {
		return addressID;
	}
	public void setAddressID(Integer addressID) {
		this.addressID = addressID;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPopulatedPlace() {
		return populatedPlace;
	}
	public void setPopulatedPlace(String populatedPlace) {
		this.populatedPlace = populatedPlace;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
