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
import beans.Guest;
import beans.Location;
import beans.Reservation;


public class ReservationDAO {
	private ArrayList<Reservation> reservations;
	private String path;
	
	public ReservationDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "reservations.json";
		this.reservations = new ArrayList<Reservation>();
	}
	
	@SuppressWarnings("unchecked")
	public void readReservations() {
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
	        	JSONArray reservations = (JSONArray) obj;
				System.out.println(reservations);
				reservations.forEach( reservation -> parseReservationObject( (JSONObject) reservation ) );
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
	
	 private void parseReservationObject(JSONObject reservation)  {
         
		 	String dateOfReservation = (String) reservation.get("dateOfReservation");
			String numberOfNights = (String) reservation.get("numberOfNights");
			Long totalPrice = (Long) reservation.get("totalPrice");
	        String messageForHost = (String) reservation.get("messageForHost");
	        String statusOfReservation = (String) reservation.get("statusOfReservation");
	        
	        JSONObject guest =  (JSONObject) reservation.get("Guest");
	        String userName = (String) guest.get("userName");    
	        String password = (String) guest.get("password");
			String name = (String) guest.get("name");
			String surname = (String) guest.get("surname");
	        
			JSONObject apartment =  (JSONObject) reservation.get("Apartment");
	        String status = (String) apartment.get("status");    
	        String TypeOfApartment = (String) apartment.get("TypeOfApartment"); 
	        Long PricePerNight = (Long) apartment.get("PricePerNight"); 
	        Long NumberOfRooms = (Long) apartment.get("NumberOfRooms"); 
	        Long NumberOfGuests = (Long) apartment.get("NumberOfGuests"); 
	        String TimeForCheckIn = (String) apartment.get("TimeForCheckIn"); 
	        String TimeForCheckOut = (String) apartment.get("TimeForCheckOut"); 
	        String ReservedStatus = (String) apartment.get("ReservedStatus"); 
	        Long Identificator = (Long) apartment.get("Identificator"); 
	        
	        JSONObject location =  (JSONObject) apartment.get("Location");
	        String latitude = (String) location.get("latitude");
	        String longitude = (String) location.get("longitude");
	    
	        JSONObject address =  (JSONObject) location.get("Address");
	        String street = (String) address.get("street");
	        String number = (String) address.get("number");
	        String populatedPlace = (String) address.get("populatedPlace");
	        String zipCode = (String) address.get("zipCode");

			Guest guestObject = new Guest(userName, password, name, surname);
	        Address addressObject = new Address(street, number,populatedPlace, zipCode);
	        Location locationObject  = new Location(latitude, longitude, addressObject);
	        Apartment apartmentObject = new Apartment(Identificator ,TypeOfApartment, NumberOfRooms, NumberOfGuests, locationObject, PricePerNight, TimeForCheckIn, TimeForCheckOut, status, ReservedStatus);	
	        Reservation r = new Reservation(statusOfReservation, apartmentObject, dateOfReservation, numberOfNights, totalPrice, messageForHost, guestObject, statusOfReservation);
	        
	        reservations.add(r);	         
	  }
	 
	 
	 //TODO: Ovo nije koriscena metoda, treba je proveriti, msm da fali
	 // cuvanje info o samoj rezervaciji
	 @SuppressWarnings("unchecked")
		public void saveReservationsJSON() {
			
			JSONArray reservationList = new JSONArray();
			
			for (Reservation r : reservations) {
				
				JSONObject reservation = new JSONObject();
				
				reservation.put("messageForHost", r.getMessageForHost());
				reservation.put("statusOfReservation", r.getStatusOfReservation());
				reservation.put("dateOfReservation", r.getDateOfReservation());
				reservation.put("numberOfNights", r.getNumberOfNights());
				reservation.put("totalPrice", r.getTotalPrice());
				
				JSONObject apartment = new JSONObject();
				Apartment a = r.getReservedApartment();
				
				apartment.put("status", a.getStatus());
				apartment.put("TypeOfApartment", a.getTypeOfApartment());
				apartment.put("PricePerNight", a.getPricePerNight());
				apartment.put("NumberOfRooms", a.getNumberOfRooms());
				apartment.put("NumberOfGuests", a.getNumberOfGuests());
				apartment.put("TimeForCheckIn", a.getTimeForCheckIn());
				apartment.put("TimeForCheckOut", a.getTimeForCheckOut());
				apartment.put("Identificator", a.getIdentificator());
				apartment.put("ReservedStatus", a.getReservedStatus());
					
				JSONObject location = new JSONObject();
				Location l = a.getLocation();
				location.put("latitude", l.getLatitude());
				location.put("longitude", l.getLongitude());
				
				JSONObject address = new JSONObject();
				Address adres = l.getAddress();
				address.put("street", adres.getStreet());
				address.put("number", adres.getNumber());
				address.put("populatedPlace", adres.getPopulatedPlace());
				address.put("zipCode", adres.getZipCode());
				
				location.put("Address", address);
				apartment.put("Location", location);
				
				JSONObject guest = new JSONObject();
				Guest g = r.getGuest();
				guest.put("userName", g.getUserName());
				guest.put("password", g.getPassword());
				guest.put("name", g.getName());
				guest.put("surname", g.getSurname());
				guest.put("role", g.getRole());
		
				reservation.put("Apartment", apartment);
				reservation.put("Guest", guest);
				
				reservationList.add(reservation);
			}
			//Write JSON file
	        try (FileWriter file = new FileWriter(path)) {
	 
	            file.write(reservationList.toJSONString());
	            file.flush();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }			
		}
	 
	 
		public ArrayList<Reservation> getValues() {
			return reservations;
		}
		
}
