package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import beans.Address;
import beans.Apartment;
import beans.Location;
import dto.ApartmentChangeDTO;
import dto.ApartmentsDTO;

public class ApartmentsDAO {
	
	private ArrayList<Apartment> apartments;
	private String path;
	
	
	public ApartmentsDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "apartments.json";
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
	         
	        try (FileReader reader = new FileReader(path))
	        {
	        	Object obj = jsonParser.parse(reader);
	        	JSONArray apartments = (JSONArray) obj;
				System.out.println(apartments);
				apartments.forEach( apar -> parseApartmentObject( (JSONObject) apar ) );
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

	 private void parseApartmentObject(JSONObject apartment)  {
	         
	        String status = (String) apartment.get("status");    
	        String TypeOfApartment = (String) apartment.get("TypeOfApartment"); 
	        Long PricePerNight = (Long) apartment.get("PricePerNight"); 
	        Long NumberOfRooms = (Long) apartment.get("NumberOfRooms"); 
	        Long NumberOfGuests = (Long) apartment.get("NumberOfGuests"); 
	        String TimeForCheckIn = (String) apartment.get("TimeForCheckIn"); 
	        String TimeForCheckOut = (String) apartment.get("TimeForCheckOut"); 
	        String ReservedStatus = (String) apartment.get("ReservedStatus"); 
	        Long Identificator = (Long) apartment.get("Identificator"); 
	   
	        
	        String latitude = (String) apartment.get("latitude");
	        String longitude = (String) apartment.get("longitude");
	        System.out.println(latitude);
	    
	        String street = (String) apartment.get("street");
	        String number = (String) apartment.get("number");
	        String populatedPlace = (String) apartment.get("populatedPlace");
	        String zipCode = (String) apartment.get("zipCode");
	        System.out.println(zipCode);
	        Address address = new Address(street, number,populatedPlace, zipCode);
	        Location location  = new Location(latitude, longitude, address);
	        Apartment a = new Apartment(Identificator ,TypeOfApartment, NumberOfRooms, NumberOfGuests, location, PricePerNight, TimeForCheckIn, TimeForCheckOut, status, ReservedStatus);	
			apartments.add(a);
	    }

	
	 @SuppressWarnings("unchecked")
	private void saveApartmentsJSON() {
		
		JSONArray apartmentList = new JSONArray();
		
		for (Apartment a : apartments) {
			
			JSONObject apartment = new JSONObject();
			apartment.put("status", a.getStatus());
			apartment.put("TypeOfApartment", a.getTypeOfApartment());
			apartment.put("PricePerNight", a.getPricePerNight());
			apartment.put("NumberOfRooms", a.getNumberOfRooms());
			apartment.put("NumberOfGuests", a.getNumberOfGuests());
			apartment.put("TimeForCheckIn", a.getTimeForCheckIn());
			apartment.put("TimeForCheckOut", a.getTimeForCheckOut());
			apartment.put("Identificator", a.getIdentificator());
			apartment.put("ReservedStatus", a.getReservedStatus());
			
			Location l = a.getLocation();
			apartment.put("latitude", l.getLatitude());
			apartment.put("longitude", l.getLongitude());
			
			Address adres = l.getAddress();
			apartment.put("street", adres.getStreet());
			apartment.put("number", adres.getNumber());
			apartment.put("populatedPlace", adres.getPopulatedPlace());
			apartment.put("zipCode", adres.getZipCode());
			
			//Add apartments to list
			apartmentList.add(apartment);
		}
		//Write JSON file
        try (FileWriter file = new FileWriter(path)) {
 
            file.write(apartmentList.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public Boolean changeApartment(ApartmentsDTO updateApartment) {
		
		for (Apartment apartment : apartments) {
			if(apartment.getIdentificator() == updateApartment.identificator) {
				System.out.println("NASAO SAM APARTMAN " + updateApartment.identificator + " i sad cu mu izmeniti podatke");
				apartment.setReservedStatus("Rezervisano");
				saveApartmentsJSON();
				return true;
			}
		}
		
		return false;
	}
	
	
	//TODO: proveriti da li je potreban gornji changeAPartment
	/*
	 * Izmena podataka prosledjenog apartmana.
	 */
	public Boolean changeApartment(ApartmentChangeDTO updatedApartment) {
		
		for (Apartment apartment : apartments) {
			if(apartment.getIdentificator() == updatedApartment.identificator) {
				System.out.println("NASAO SAM APARTMAN " + updatedApartment.identificator + " i sad cu mu izmeniti podatke");
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
			if(apartment.getIdentificator() == updateApartment.identificator) {
				System.out.println("NASAO SAM APARTMAN " + updateApartment.identificator + " i sad cu mu izmeniti podatke");
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

	public void deleteApartment(int identificator ) {
		
		
		for (Apartment apartment : apartments) {
			if(apartment.getIdentificator() == identificator) {
				apartments.remove(apartment);
				saveApartmentsJSON();
				return;
			}
		}
		return;
		
	}
	
}
