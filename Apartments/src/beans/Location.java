package beans;

public class Location {
	private String latitude; //Geografska širina
	private String longitude; //Geografska dužina
	private Address address;
	private int addessID; 
	private int locationID;

	
	public Location(String latitude, String longitude, Address address, int addessID, int locationID) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.addessID = addessID;
		this.locationID = locationID;
	}

	public Integer getLocationID() {
		return locationID;
	}
	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
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
	public Integer getAddessID() {
		return addessID;
	}
	public void setAddessID(Integer addessID) {
		this.addessID = addessID;
	}


}
