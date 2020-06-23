package beans;

import java.util.ArrayList;

//TODO: Dopuniti hosta sa potrebim atributima.
public class Host extends User {
	
	private ArrayList<Apartment> myApartments;
	
	public Host(String userName, String password, String name, String surname, ArrayList<Apartment> apartments) {
		super(userName, password, name, surname, "HOST");
		this.myApartments = apartments;
	}

	public ArrayList<Apartment> getMyApartments() {
		return myApartments;
	}

	public void setMyApartments(ArrayList<Apartment> myApartments) {
		this.myApartments = myApartments;
	}
	
	

}
