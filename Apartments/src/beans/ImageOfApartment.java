package beans;

/**
 * Idea with this class is encapsulation  of
 * images in one separate storage.
 * 
 * Apartment will have ID reference to his
 * image.
 *
 */
public class ImageOfApartment {

	private Integer ID;
	private String code64ForImage;
	
	
	public ImageOfApartment(Integer iD, String code64ForImage) {
		super();
		ID = iD;
		this.code64ForImage = code64ForImage;
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getCode64ForImage() {
		return code64ForImage;
	}
	public void setCode64ForImage(String code64ForImage) {
		this.code64ForImage = code64ForImage;
	}
	
	
}
