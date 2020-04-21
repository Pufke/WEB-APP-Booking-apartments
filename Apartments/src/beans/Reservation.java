package beans;

/**
 * Class which represent one reservation.
 * @author Vaxi
 *
 */
public class Reservation {

	private Apartment apartment;
	private int count;

	public Reservation(Apartment apartment, int count) {
		this.apartment = apartment;
		this.count = count;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	/** Get total price for reservation. */
	public double getTotal() {
		return apartment.getPrice() * count;
	}
}
