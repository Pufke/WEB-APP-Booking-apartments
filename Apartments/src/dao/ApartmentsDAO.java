package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Address;
import beans.Apartment;
import beans.Location;
import beans.User;
import dto.ApartmentChangeDTO;
import dto.ApartmentDTOJSON;
import dto.ApartmentsDTO;

public class ApartmentsDAO {

	private ArrayList<Apartment> apartments;
	private String path;

	public ApartmentsDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator
				+ "apartments.json";
		this.apartments = new ArrayList<Apartment>();
		
		System.out.println(this.path);
	}

	/**
	 * Read the apartments from the file.
	 */
	public void readApartments() {
		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File(this.path);

		List<Apartment> loadedApartments = new ArrayList<Apartment>();
		try {

			loadedApartments = objectMapper.readValue(file, new TypeReference<List<Apartment>>() {
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
		for (Apartment a : loadedApartments) {
			System.out.println("ID APARTMANA: " + a.getIdentificator());
			apartments.add(a);
		}
		System.out.println("\n\n");
	}


	public void saveApartmentsJSON() {
		
		// Get all apartments
		List<Apartment> allApartments = new ArrayList<Apartment>();
		for(Apartment a: getValues()) {
			allApartments.add(a);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allApartments);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Boolean changeApartment(ApartmentsDTO updateApartment) {

		for (Apartment apartment : apartments) {
			if (apartment.getIdentificator() == updateApartment.identificator) {
				System.out.println(
						"NASAO SAM APARTMAN " + updateApartment.identificator + " i sad cu mu izmeniti podatke");
				apartment.setReservedStatus("Rezervisano");
				saveApartmentsJSON();
				return true;
			}
		}

		return false;
	}

	// TODO: proveriti da li je potreban gornji changeAPartment
	/*
	 * Izmena podataka prosledjenog apartmana.
	 */
	public Boolean changeApartment(ApartmentChangeDTO updatedApartment) {

		for (Apartment apartment : apartments) {
			if (apartment.getIdentificator().equals(updatedApartment.identificator)) {
				System.out.println(
						"NASAO SAM APARTMAN " + updatedApartment.identificator + " i sad cu mu izmeniti podatke");
				apartment.setPricePerNight(updatedApartment.pricePerNight);
				apartment.setTimeForCheckIn(updatedApartment.timeForCheckIn);
				apartment.setTimeForCheckOut(updatedApartment.timeForCheckOut);
				apartment.setNumberOfRooms(updatedApartment.numberOfRooms);
				apartment.setNumberOfGuests(updatedApartment.numberOfGuests);
				saveApartmentsJSON();
				return true;
			}
		}

		return false;
	}
	
	public void activateApartment(long identificator) {
		for (Apartment apartment : apartments) {
			if (apartment.getIdentificator().equals(identificator)) {
				apartment.setStatus("ACTIVE");
				saveApartmentsJSON();
				return;
			}
		}
		
	}

	public Boolean deleteReservation(ApartmentsDTO updateApartment) {

		for (Apartment apartment : apartments) {
			if (apartment.getIdentificator() == updateApartment.identificator) {
				System.out.println(
						"NASAO SAM APARTMAN " + updateApartment.identificator + " i sad cu mu izmeniti podatke");
				apartment.setReservedStatus("Nije rezervisano");
				saveApartmentsJSON();
				return true;
			}
		}

		return false;
	}

	public ArrayList<Apartment> getValues() {
		return apartments;
	}

	public void deleteApartment(int identificator) {

		for (Apartment apartment : apartments) {
			if (apartment.getIdentificator().equals(identificator)) {
				apartments.remove(apartment);
				saveApartmentsJSON();
				return;
			}
		}
		return;

	}

	public void addNewApartments(ApartmentDTOJSON newItem) {

		Apartment apartment = new Apartment();
		apartment.setStatus(newItem.addedApartment.getStatus());
		apartment.setTypeOfApartment(newItem.addedApartment.getTypeOfApartment());
		apartment.setPricePerNight(newItem.addedApartment.getPricePerNight());
		apartment.setTimeForCheckIn(newItem.addedApartment.getTimeForCheckIn());
		apartment.setTimeForCheckOut(newItem.addedApartment.getTimeForCheckOut());
		apartment.setNumberOfRooms(newItem.addedApartment.getNumberOfRooms());
		apartment.setNumberOfGuests(newItem.addedApartment.getNumberOfGuests());
		apartment.setLocation(newItem.addedApartment.getLocation());

		apartments.add(apartment);
		saveApartmentsJSON();
	}



}
