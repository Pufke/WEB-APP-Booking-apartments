package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Address;
import beans.Administrator;
import beans.Apartment;
import beans.Guest;
import beans.Host;
import beans.Location;
import beans.Reservation;
import beans.User;
import dto.ApartmentChangeDTO;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	public void addUser(User user) {
		if (!users.containsValue(user)) {
			// TODO: Proveriti da li stvarno dobro proverava
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

	public void changeHostApartment(User updatedUser, ApartmentChangeDTO updatedApartment) {
		// Find user with that name, and change his data.
		for (User user : users.values()) {
			if (user.getUserName().equals(updatedUser.getUserName())) {
				
				// find which apartment we need to change for this host
				for(Apartment apartment : user.getApartmentsForRentingHOST()) {
					if (updatedApartment.identificator.equals(apartment.getIdentificator())) {
						apartment.setPricePerNight(updatedApartment.pricePerNight);
						apartment.setTimeForCheckIn(updatedApartment.timeForCheckIn);
						apartment.setTimeForCheckOut(updatedApartment.timeForCheckOut);
						apartment.setNumberOfRooms(updatedApartment.numberOfRooms);
						apartment.setNumberOfGuests(updatedApartment.numberOfGuests);
						saveUsersJSON();
						return;
					}
				}
			}
		}
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

}
