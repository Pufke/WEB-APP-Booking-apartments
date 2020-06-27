package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Reservation;
import beans.User;

import dto.ApartmentDTOJSON;
import dto.UserDTO;

public class UsersDAO {

	private LinkedHashMap<String, User> users;
	private String path;

	public UsersDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "users.json";
		this.users = new LinkedHashMap<String, User>();
		
		// UNCOMMENT IF YOU WANT TO ADD MOCKUP DATA TO FILE addMockupData();
	}

	/**
	 * Read the data from the path.
	 * 
	 * @param path
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public void readUsers() {
		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File(this.path);

		List<User> loadedUsers = new ArrayList<User>();
		try {

			loadedUsers = objectMapper.readValue(file, new TypeReference<List<User>>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n ucitavam preko object mapera \n\n");
		for (User u : loadedUsers) {
			System.out.println("ime: " + u.getUserName());
			users.put(u.getUserName(), u);
		}
		System.out.println("\n\n");

	}

	public void saveUsersJSON() {

		// Get all users
		List<User> allUsers = new ArrayList<User>();
		for (User u : getValues()) {
			allUsers.add(u);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allUsers);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<User> getGuestsOfHost(User user, ArrayList<Reservation> allReservations) {
		
		
		// Get ID-s of guest which have reservation on Host apartments
		ArrayList<Integer> guestsID = new ArrayList<Integer>();
		for (Integer idOfApartment : user.getApartmentsForRentingHostIDs()) {
			for (Reservation currReservation : allReservations) {
				if(idOfApartment.equals(currReservation.getIdOfReservedApartment())) {
					guestsID.add(currReservation.getGuestID());
					break;
				}
			}
		}
		
		// Get real object of those guests (by id ofc)
		ArrayList<User> guestsOfHost = new ArrayList<User>();
		for (Integer userID : guestsID) {
			if(findUserById(userID) != null) {
				guestsOfHost.add(findUserById(userID));
			}
		}
		
		return guestsOfHost;
	}

	
	public Collection<Reservation> getReservationsOfHost(User user, ArrayList<Reservation> allReservations) {
		
		ArrayList<Reservation> reservationsOfHost = new ArrayList<Reservation>();
		
		for (Integer idOfApartment : user.getApartmentsForRentingHostIDs()) {
			for (Reservation currReservation : allReservations) {
				if(idOfApartment.equals(currReservation.getIdOfReservedApartment())) {
					reservationsOfHost.add(currReservation);
				}
			}
		}
		
		return reservationsOfHost;
	}
	
	public void addUser(User user) {
		if (!users.containsValue(user)) {
			System.out.println("DODAO SAM: " + user.getUserName());
			users.put(user.getUserName(), user);
		}
	}

	public Boolean changeUser(UserDTO updatedUser) {

		// Find user with that name, and change his data.
		for (User user : users.values()) {
			if (user.getUserName().equals(updatedUser.username)) {
				System.out.println("NASAO SAM " + user.getUserName() + " i sad cu mu izmeniti podatke");
				System.out.println("NJEGOVA ROLA JE TRENUTNO: " + user.getRole());
				System.out.println("A NOVA JE: " + updatedUser.role);

				user.setName(updatedUser.name);
				user.setPassword(updatedUser.password);
				user.setSurname(updatedUser.surname);
				user.setRole(updatedUser.role);

				saveUsersJSON();

				return true;
			}
		}
		return false;
	}

	public void addHostApartments(User updatedUser, Integer idOfApartment) {
		// Find user with that name, and change his data.
		for (User user : users.values()) {
			if (user.getUserName().equals(updatedUser.getUserName())) {

				// If we are here, then this user does not have this apartman so wee need to add
				// it
				user.getApartmentsForRentingHostIDs().add(idOfApartment);
				saveUsersJSON();
				return;
			}
		}

	}
	
	public void deleteHostApartment(Integer hostID, Integer apartmentID) {

		System.out.println("\n\n hostu sa id-em: " + hostID + " smo obrisali apartman sa id-em: " + apartmentID);
		User host = findUserById(hostID);
		List<Integer> apartmentsOfHostIDs = host.getApartmentsForRentingHostIDs();
		apartmentsOfHostIDs.remove(apartmentID);
		saveUsersJSON();

	}

	public User findUserById(Integer ID) {
		for (User currentUser : getValues()) {
			if(currentUser.getID().equals(ID))
				return currentUser;
		}
		
		return null;
	}
	
	public LinkedHashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(LinkedHashMap<String, User> users) {
		this.users = users;
	}

	public Collection<User> values() {
		return users.values();
	}

	public Collection<User> getValues() {
		return users.values();
	}

	public User getUser(String username) {
		if (users.containsKey(username)) {
			return users.get(username);
		}

		return null;
	}

	/**
	 * Method for adding dummy data to JSON file of users
	 */
	@SuppressWarnings("unused")
	private void addMockupData() {

		// Make all users
		List<User> allUsers = new ArrayList<User>();

		List<Integer> apartmentsForRentingHostIDs = new ArrayList<Integer>(); // apartmani za izdavanje
		apartmentsForRentingHostIDs.add(1);
		apartmentsForRentingHostIDs.add(2);

		List<Integer> rentedApartmentsOfGuestIDs = new ArrayList<Integer>(); // iznajmljeni apartmani

		List<Integer> listOfReservationsGuestIDs = new ArrayList<Integer>(); // lista rezervacija
		
		allUsers.add(new User(1, 0, 0, "dule", "12345", "Dule", "Maksimovic", "ADMINISTRATOR", "Male",
				new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>()));
		
		allUsers.add(new User(2, 0, 0, "vaxi", "12345", "Vladislav", "Maksimovic", "HOST", "Male",
				apartmentsForRentingHostIDs, new ArrayList<Integer>(), new ArrayList<Integer>()));
		
		
		rentedApartmentsOfGuestIDs.add(1); rentedApartmentsOfGuestIDs.add(2);
		listOfReservationsGuestIDs.add(10); listOfReservationsGuestIDs.add(20);
		allUsers.add(new User(3, 0, 0, "pufke", "12345", "Nemanja", "Pualic", "GUEST", "Male",
				new ArrayList<Integer>(), rentedApartmentsOfGuestIDs, listOfReservationsGuestIDs));
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allUsers);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

	

	

}
