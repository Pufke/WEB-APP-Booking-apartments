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

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Address;
import beans.Apartment;
import beans.Location;
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
	}

	/**
	 * Read the apartments from the file.
	 */
	@SuppressWarnings("unchecked")
	public void readApartments() {
		BufferedReader in = null;
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				file = new File(this.path);
			}
			System.out.println(file);
			JSONParser jsonParser = new JSONParser();

			try (FileReader reader = new FileReader(path)) {
				Object obj = jsonParser.parse(reader);
				JSONArray apartments = (JSONArray) obj;
				System.out.println(apartments);
				apartments.forEach(apar -> parseApartmentObject((JSONObject) apar));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private void parseApartmentObject(JSONObject apartment) {
		String status = (String) apartment.get("status");
		String TypeOfApartment = (String) apartment.get("TypeOfApartment");
		Long PricePerNight = (Long) apartment.get("PricePerNight");
		Long NumberOfRooms = (Long) apartment.get("NumberOfRooms");
		Long NumberOfGuests = (Long) apartment.get("NumberOfGuests");
		String TimeForCheckIn = (String) apartment.get("TimeForCheckIn");
		String TimeForCheckOut = (String) apartment.get("TimeForCheckOut");
		String ReservedStatus = (String) apartment.get("ReservedStatus");
		Long Identificator = (Long) apartment.get("Identificator");

		JSONArray reservedApartmentListJSON = (JSONArray) apartment.get("reservedApartmentList");
		ArrayList<String> reservedApartmentList = new ArrayList<String>();

		if (reservedApartmentListJSON != null) {
			for (int i = 0; i < reservedApartmentListJSON.size(); i++) {
				reservedApartmentList.add((String) reservedApartmentListJSON.get(i));
			}
		}

		JSONObject l = (JSONObject) apartment.get("Location");
		JSONObject a = (JSONObject) l.get("Address");

		String latitude = (String) l.get("latitude");
		String longitude = (String) l.get("longitude");

		String street = (String) a.get("street");
		String number = (String) a.get("number");
		String populatedPlace = (String) a.get("populatedPlace");
		String zipCode = (String) a.get("zipCode");

		Address address = new Address(street, number, populatedPlace, zipCode);
		Location location = new Location(latitude, longitude, address);
		Apartment ap = new Apartment(reservedApartmentList, Identificator, TypeOfApartment, NumberOfRooms,
				NumberOfGuests, location, PricePerNight, TimeForCheckIn, TimeForCheckOut, status, ReservedStatus);
		apartments.add(ap);
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
//	public void saveApartmentsJSON() {
//		
//		JSONArray apartmentList = new JSONArray();
//		
//		for (Apartment a : apartments) {
//			
//			JSONObject apartment = new JSONObject();
//			apartment.put("status", a.getStatus());
//			apartment.put("TypeOfApartment", a.getTypeOfApartment());
//			apartment.put("PricePerNight", a.getPricePerNight());
//			apartment.put("NumberOfRooms", a.getNumberOfRooms());
//			apartment.put("NumberOfGuests", a.getNumberOfGuests());
//			apartment.put("TimeForCheckIn", a.getTimeForCheckIn());
//			apartment.put("TimeForCheckOut", a.getTimeForCheckOut());
//			apartment.put("Identificator", a.getIdentificator());
//			apartment.put("ReservedStatus", a.getReservedStatus());
//			
//			JSONArray reservedApartmentListJSON = new JSONArray();
//			ArrayList<String> reservedApartmentList = a.getReservedApartmentList();
//			
//			//if(reservedApartmentList != null) {
//				for (String s : reservedApartmentList) {
//					reservedApartmentListJSON.add(s);
//				}
//			//}
//			
//			apartment.put("reservedApartmentList", reservedApartmentListJSON);
//			
//			JSONObject location = new JSONObject();
//			Location l = a.getLocation();
//			location.put("latitude", l.getLatitude());
//			location.put("longitude", l.getLongitude());
//			
//			JSONObject address = new JSONObject();
//			Address adres = l.getAddress();
//			address.put("street", adres.getStreet());
//			address.put("number", adres.getNumber());
//			address.put("populatedPlace", adres.getPopulatedPlace());
//			address.put("zipCode", adres.getZipCode());
//			
//			location.put("Address", address);
//			apartment.put("Location", location);
//			//Add apartments to list
//			apartmentList.add(apartment);
//		}
//		//Write JSON file
//        try (FileWriter file = new FileWriter(path)) {
// 
//            file.write(apartmentList.toJSONString());
//            file.flush();
// 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//		
//	}

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
