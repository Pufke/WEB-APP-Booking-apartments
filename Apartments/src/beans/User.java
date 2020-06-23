package beans;

import java.util.List;

public class User {

	private String id;
	private String userName;
	private String password;

	private String name;
	private String surname;

	private String role;
	
	private String gender;
	private List<Apartment> apartmentsForRentingHOST;	// apartmani za izdavanje
	private List<Apartment> rentingApartmentsGUEST;		// iznajmljeni apartmani
	private List<Reservation> listOfReservationsGUEST;	// lista rezervacija
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;

		this.name = "";
		this.surname = "";
		this.role = "GUEST";
	}

	public User(String userName, String password, String name, String surname) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
	}

	public User(String userName, String password, String name, String surname, String role) {
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.role = role;
	}

	
	

	public User(String id, String userName, String password, String name, String surname, String role, String gender,
			List<Apartment> apartmentsForRentingHOST, List<Apartment> rentingApartmentsGUEST,
			List<Reservation> listOfReservationsGUEST) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.role = role;
		this.gender = gender;
		this.apartmentsForRentingHOST = apartmentsForRentingHOST;
		this.rentingApartmentsGUEST = rentingApartmentsGUEST;
		this.listOfReservationsGUEST = listOfReservationsGUEST;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Apartment> getApartmentsForRentingHOST() {
		return apartmentsForRentingHOST;
	}

	public void setApartmentsForRentingHOST(List<Apartment> apartmentsForRentingHOST) {
		this.apartmentsForRentingHOST = apartmentsForRentingHOST;
	}

	public List<Apartment> getRentingApartmentsGUEST() {
		return rentingApartmentsGUEST;
	}

	public void setRentingApartmentsGUEST(List<Apartment> rentingApartmentsGUEST) {
		this.rentingApartmentsGUEST = rentingApartmentsGUEST;
	}

	public List<Reservation> getListOfReservationsGUEST() {
		return listOfReservationsGUEST;
	}

	public void setListOfReservationsGUEST(List<Reservation> listOfReservationsGUEST) {
		this.listOfReservationsGUEST = listOfReservationsGUEST;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}
