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
import beans.Comment;
import beans.Guest;
import beans.Location;


public class CommentsDAO {

	private ArrayList<Comment> comments;
	private String path;

	public CommentsDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "comments.json";
		this.comments = new ArrayList<Comment>();
	}

	@SuppressWarnings("unchecked")
	public void readComments() {
		BufferedReader in = null;
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				file = new File(this.path);
			}
			JSONParser jsonParser = new JSONParser();

			try (FileReader reader = new FileReader(path)) {
				Object obj = jsonParser.parse(reader);
				JSONArray commentsJSON = (JSONArray) obj;
				System.out.println(commentsJSON);
				commentsJSON.forEach(comment -> parseCommentObject((JSONObject) comment));
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

	private void parseCommentObject(JSONObject comment) {

		String status = (String) comment.get("status");    
        String TypeOfApartment = (String) comment.get("TypeOfApartment"); 
        Long PricePerNight = (Long) comment.get("PricePerNight"); 
        Long NumberOfRooms = (Long) comment.get("NumberOfRooms"); 
        Long NumberOfGuests = (Long) comment.get("NumberOfGuests"); 
        String TimeForCheckIn = (String) comment.get("TimeForCheckIn"); 
        String TimeForCheckOut = (String) comment.get("TimeForCheckOut"); 
        String ReservedStatus = (String) comment.get("ReservedStatus"); 
        Long Identificator = (Long) comment.get("Identificator"); 
		
        String userName = (String) comment.get("userName");    
        String password = (String) comment.get("password");
		String name = (String) comment.get("name");
		String surname = (String) comment.get("surname");
        
		String latitude = (String) comment.get("latitude");
        String longitude = (String) comment.get("longitude");
    
        String street = (String) comment.get("street");
        String number = (String) comment.get("number");
        String populatedPlace = (String) comment.get("populatedPlace");
        String zipCode = (String) comment.get("zipCode");
		
		Guest guest = new Guest(userName, password, name, surname);
		Address address = new Address(street, number,populatedPlace, zipCode);
        Location location  = new Location(latitude, longitude, address);
		Apartment apartment = new Apartment(null, Identificator ,TypeOfApartment, NumberOfRooms, NumberOfGuests, location, PricePerNight, TimeForCheckIn, TimeForCheckOut, status, ReservedStatus);
		//TODO PREPRAVITI NULL U Listu apartmana
		String txtOfComment = (String) comment.get("txtOfComment");
		Long ratingForApartment = (Long) comment.get("ratingForApartment");
		
		Comment newComment = new Comment(guest,apartment,txtOfComment,ratingForApartment);
		comments.add(newComment);
		
		System.out.println("Dodao sam novi komentar");
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void saveAmenitiesJSON() {

		JSONArray commentsList = new JSONArray();

		for (Comment comment : comments) {

			JSONObject commentToSave = new JSONObject();
			
			commentToSave.put("txtOfComment", comment.getTxtOfComment());
			commentToSave.put("ratingForApartment", comment.getRatingForApartment());
			
			
			Apartment a = comment.getApartmentComment();
			
			commentToSave.put("status", a.getStatus());
			commentToSave.put("TypeOfApartment", a.getTypeOfApartment());
			commentToSave.put("PricePerNight", a.getPricePerNight());
			commentToSave.put("NumberOfRooms", a.getNumberOfRooms());
			commentToSave.put("NumberOfGuests", a.getNumberOfGuests());
			commentToSave.put("TimeForCheckIn", a.getTimeForCheckIn());
			commentToSave.put("TimeForCheckOut", a.getTimeForCheckOut());
			commentToSave.put("Identificator", a.getIdentificator());
			commentToSave.put("ReservedStatus", a.getReservedStatus());
				
			Location l = a.getLocation();
			commentToSave.put("latitude", l.getLatitude());
			commentToSave.put("longitude", l.getLongitude());
			
			Address adres = l.getAddress();
			commentToSave.put("street", adres.getStreet());
			commentToSave.put("number", adres.getNumber());
			commentToSave.put("populatedPlace", adres.getPopulatedPlace());
			commentToSave.put("zipCode", adres.getZipCode());
			
			Guest guest = comment.getGuestCommentAuthor();
			commentToSave.put("userName", guest.getUserName());
			commentToSave.put("password", guest.getPassword());
			commentToSave.put("name", guest.getName());
			commentToSave.put("surname", guest.getSurname());
			commentToSave.put("role", guest.getRole());
			
			commentsList.add(commentToSave);
			
		}
		
		// Writing to JSON file
		try (FileWriter file = new FileWriter(path)) {
			file.write(commentsList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Comment> getValues() {
		return comments;
	}


	
}
