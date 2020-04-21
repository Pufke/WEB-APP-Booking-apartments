package beans;

import java.util.ArrayList;

/**
 * Class which represent our ReservationCart, and it's list of reservations.
 * 
 * @author Vaxi
 *
 */
public class ReservationCart {
	
	private ArrayList<Reservation> reservations;
	
	
	public ReservationCart() {
		reservations = new ArrayList<Reservation>();
	}

	public void addItem(Apartment product, int count) {
		reservations.add(new Reservation(product, count));
	}

	public ArrayList<Reservation> getItems() {
		return reservations;
	}
	
	public double getTotal() {
		double retVal = 0;
		for (Reservation res : reservations) {
			retVal += res.getTotal();
		}
		return retVal;
	}
	
}
