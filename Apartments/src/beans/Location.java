package beans;

public class Location {
	private String latitude; //Geografska širina
	private String longitude; //Geografska dužina
	private Address address;

	
	public Location(String latitude, String longitude, Address address) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}


	public String getLatitude() {
		return latitude;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



}
