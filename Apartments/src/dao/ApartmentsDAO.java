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

	 private void parseApartmentObject(JSONObject apartment) 
	    {
	        JSONObject apartmentObject = (JSONObject) apartment.get("apartment");
	         
	        String status = (String) apartmentObject.get("status");    
	        String TypeOfApartment = (String) apartmentObject.get("TypeOfApartment"); 
	        Long PricePerNight = (Long) apartmentObject.get("PricePerNight"); 
	        Long NumberOfRooms = (Long) apartmentObject.get("NumberOfRooms"); 
	        Long NumberOfGuests = (Long) apartmentObject.get("NumberOfGuests"); 
	        String TimeForCheckIn = (String) apartmentObject.get("TimeForCheckIn"); 
	        String TimeForCheckOut = (String) apartmentObject.get("TimeForCheckOut"); 
	        String ReservedStatus = (String) apartmentObject.get("ReservedStatus"); 
	        Long Identificator = (Long) apartmentObject.get("Identificator"); 
	        
	        JSONObject locationJSONObject = (JSONObject) apartmentObject.get("location"); 
	        String latitude = (String) locationJSONObject.get("latitude");
	        String longitude = (String) locationJSONObject.get("longitude");
	        
	        JSONObject addressObject = (JSONObject) locationJSONObject.get("address"); 
	        String street = (String) addressObject.get("street");
	        String number = (String) addressObject.get("number");
	        String populatedPlace = (String) addressObject.get("populatedPlace");
	        String zipCode = (String) addressObject.get("zipCode");
	      
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
			
			Location l = a.getLocation();
			JSONObject location = new JSONObject();
			location.put("latitude", l.getLatitude());
			location.put("longitude", l.getLongitude());
			
			Address adres = l.getAddress();
			JSONObject address = new JSONObject();
			address.put("street", adres.getStreet());
			address.put("number", adres.getNumber());
			address.put("populatedPlace", adres.getPopulatedPlace());
			address.put("zipCode", adres.getZipCode());
			
			//Add apartments to list
			apartmentList.add(apartment);
			apartmentList.add(apartment);
		}
		//Write JSON file
        try (FileWriter file = new FileWriter("path2")) {
 
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
	
}
