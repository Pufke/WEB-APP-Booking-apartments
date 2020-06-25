package beans;

import java.util.List;

public class User {

	private Integer ID;
	private Integer logicalDeleted;						// 1 - deleted, 0 - not deleted
	private Integer blocked;							// 1 - blocked, 0 - not blocked
	
	private String userName;
	private String password;
	private String name;
	private String surname;
	private String role;
	private String gender;
	
	private List<Integer> apartmentsForRentingHostIDs;			// apartmani za izdavanje
	private List<Integer> rentedApartmentsOfGuestIDs;			// iznajmljeni apartmani
	private List<Integer> listOfReservationsGuestIDs;			// lista rezervacija
	
	
	public User() {
		
	}


	public User(Integer iD, Integer logicalDeleted, Integer blocked, String userName, String password, String name,
			String surname, String role, String gender, List<Integer> apartmentsForRentingHostIDs,
			List<Integer> rentedApartmentsOfGuestIDs, List<Integer> listOfReservationsGuestIDs) {
		super();
		ID = iD;
		this.logicalDeleted = logicalDeleted;
		this.blocked = blocked;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.role = role;
		this.gender = gender;
		this.apartmentsForRentingHostIDs = apartmentsForRentingHostIDs;
		this.rentedApartmentsOfGuestIDs = rentedApartmentsOfGuestIDs;
		this.listOfReservationsGuestIDs = listOfReservationsGuestIDs;
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


	public Integer getBlocked() {
		return blocked;
	}


	public void setBlocked(Integer blocked) {
		this.blocked = blocked;
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


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public List<Integer> getApartmentsForRentingHostIDs() {
		return apartmentsForRentingHostIDs;
	}


	public void setApartmentsForRentingHostIDs(List<Integer> apartmentsForRentingHostIDs) {
		this.apartmentsForRentingHostIDs = apartmentsForRentingHostIDs;
	}


	public List<Integer> getRentedApartmentsOfGuestIDs() {
		return rentedApartmentsOfGuestIDs;
	}


	public void setRentedApartmentsOfGuestIDs(List<Integer> rentedApartmentsOfGuestIDs) {
		this.rentedApartmentsOfGuestIDs = rentedApartmentsOfGuestIDs;
	}


	public List<Integer> getListOfReservationsGuestIDs() {
		return listOfReservationsGuestIDs;
	}


	public void setListOfReservationsGuestIDs(List<Integer> listOfReservationsGuestIDs) {
		this.listOfReservationsGuestIDs = listOfReservationsGuestIDs;
	}

	
	

}
