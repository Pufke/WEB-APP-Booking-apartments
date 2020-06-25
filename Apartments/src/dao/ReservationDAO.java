package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Reservation;

public class ReservationDAO {
	private ArrayList<Reservation> reservations;
	private String path;

	public ReservationDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator
				+ "reservations.json";
		this.reservations = new ArrayList<Reservation>();
		
		// UNCOMMENT IF YOU WANT TO SAVE MOCKUP DATA DO FILE 
		addMockupData();
	}

	public void readReservations() {
		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File(this.path);

		List<Reservation> loadedReservations = new ArrayList<Reservation>();
		try {

			loadedReservations = objectMapper.readValue(file, new TypeReference<List<Reservation>>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n ucitavam preko object mapera \n\n");
		for (Reservation a : loadedReservations) {
			System.out.println("rezervacija ID: " + a.getID());
			reservations.add(a);
		}
		System.out.println("\n\n");
	}

	public void saveReservationsJSON() {

		// Get all reservations
		List<Reservation> allReservations = new ArrayList<Reservation>();
		for (Reservation a : getValues()) {
			allReservations.add(a);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allReservations);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Reservation> getValues() {
		return reservations;
	}

	/**
	 * Method for adding dummy data to JSON file of comments
	 */
	@SuppressWarnings("unused")
	private void addMockupData() {

		// Make all reservations
		List<Reservation> allReservations = new ArrayList<Reservation>();
		allReservations.add(new Reservation(1,0, 1, new Date(), 3, 100.0, "Brate, da me klima ceka, nemoj se igras sa vatrom", 2, "Kreirana"));
		allReservations.add(new Reservation(2,0, 2, new Date(), 2, 99.0, "Ako moze samo da me docekaju otvoreni prozori", 2, "Kreirana"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allReservations);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
