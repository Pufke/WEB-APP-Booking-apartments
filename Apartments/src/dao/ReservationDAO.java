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
         
	        String status = (String) reservation.get("status");    
	        String TypeOfApartment = (String) reservation.get("TypeOfApartment"); 
	        Long PricePerNight = (Long) reservation.get("PricePerNight"); 
	        Long NumberOfRooms = (Long) reservation.get("NumberOfRooms"); 
	        Long NumberOfGuests = (Long) reservation.get("NumberOfGuests"); 
	        String TimeForCheckIn = (String) reservation.get("TimeForCheckIn"); 
	        String TimeForCheckOut = (String) reservation.get("TimeForCheckOut"); 
	        String ReservedStatus = (String) reservation.get("ReservedStatus"); 
	        Long Identificator = (Long) reservation.get("Identificator"); 
	        
	        String latitude = (String) reservation.get("latitude");
	        String longitude = (String) reservation.get("longitude");
	    
	        String street = (String) reservation.get("street");
	        String number = (String) reservation.get("number");
	        String populatedPlace = (String) reservation.get("populatedPlace");
	        String zipCode = (String) reservation.get("zipCode");

	        
	        String userName = (String) reservation.get("userName");    
	        String password = (String) reservation.get("password");
			String name = (String) reservation.get("name");
			String surname = (String) reservation.get("surname");
			//String role = (String) reservation.get("role");
			
			String dateOfReservation = (String) reservation.get("dateOfReservation");
			String numberOfNights = (String) reservation.get("numberOfNights");
			Long totalPrice = (Long) reservation.get("totalPrice");
	        String messageForHost = (String) reservation.get("messageForHost");
	        String statusOfReservation = (String) reservation.get("statusOfReservation");
			
			Guest guest = new Guest(userName, password, name, surname);
	        Address address = new Address(street, number,populatedPlace, zipCode);
	        Location location  = new Location(latitude, longitude, address);
	        Apartment apartment = new Apartment(Identificator ,TypeOfApartment, NumberOfRooms, NumberOfGuests, location, PricePerNight, TimeForCheckIn, TimeForCheckOut, status, ReservedStatus);	
	        Reservation r = new Reservation(apartment, dateOfReservation, numberOfNights, totalPrice, messageForHost, guest, statusOfReservation);
	        
	        reservations.add(r);	 
	        System.out.println(reservations);
	        
	  }
	 
	 
	 //TODO: Ovo nije koriscena metoda, treba je proveriti, msm da fali
	 // cuvanje info o samoj rezervaciji
	 @SuppressWarnings("unchecked")
		private void saveReservationsJSON() {
			
			JSONArray reservationList = new JSONArray();
			
			for (Reservation r : reservations) {
				
				JSONObject reservation = new JSONObject();
				
				Apartment a = r.getReservedApartment();
				
				reservation.put("status", a.getStatus());
				reservation.put("TypeOfApartment", a.getTypeOfApartment());
				reservation.put("PricePerNight", a.getPricePerNight());
				reservation.put("NumberOfRooms", a.getNumberOfRooms());
				reservation.put("NumberOfGuests", a.getNumberOfGuests());
				reservation.put("TimeForCheckIn", a.getTimeForCheckIn());
				reservation.put("TimeForCheckOut", a.getTimeForCheckOut());
				reservation.put("Identificator", a.getIdentificator());
				reservation.put("ReservedStatus", a.getReservedStatus());
					
				Location l = a.getLocation();
				reservation.put("latitude", l.getLatitude());
				reservation.put("longitude", l.getLongitude());
				
				Address adres = l.getAddress();
				reservation.put("street", adres.getStreet());
				reservation.put("number", adres.getNumber());
				reservation.put("populatedPlace", adres.getPopulatedPlace());
				reservation.put("zipCode", adres.getZipCode());
				
				Guest guest = r.getGuest();
				reservation.put("userName", guest.getUserName());
				reservation.put("password", guest.getPassword());
				reservation.put("name", guest.getName());
				reservation.put("surname", guest.getSurname());
				reservation.put("role", guest.getRole());
				
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
