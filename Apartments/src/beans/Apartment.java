package beans;

/**
 * Test bean class for our project.
 * 
 * @author Vaxi
 *
 */

// TODO: Resolve this to the end.
public class Apartment {

	private String id;
	private String name;
	private int count;

	private double price;

	public Apartment() {
		this.id = "";
		this.name = "";
		this.price = 0.0;
	}

	public Apartment(String id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
