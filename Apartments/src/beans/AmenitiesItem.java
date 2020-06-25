package beans;

public class AmenitiesItem {
	private Integer ID;
	private Integer logicalDeleted;						// 1 - deleted, 0 - not deleted
	private String itemName;							// name of amenities item
	
	public AmenitiesItem() {
		
	}
	
	public AmenitiesItem(Integer iD, Integer logicalDeleted, String itemName) {
		super();
		ID = iD;
		this.logicalDeleted = logicalDeleted;
		this.itemName = itemName;
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
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	
	
}
