package beans;

public class AmenitiesItem {
	private Long amenitiesItemID;
	private String itemName;				// name of amenities item
	
	public AmenitiesItem() {
		this.amenitiesItemID = 9999L;
		this.itemName = "without name";
	}
	
	public AmenitiesItem(Long amenitiesID, String name) {
		super();
		this.amenitiesItemID = amenitiesID;
		this.itemName = name;
	}
	public Long getAmenitiesID() {
		return amenitiesItemID;
	}
	public void setAmenitiesID(Long amenitiesID) {
		this.amenitiesItemID = amenitiesID;
	}
	public String getName() {
		return itemName;
	}
	public void setName(String name) {
		this.itemName = name;
	}
	
	
}
