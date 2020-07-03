package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Address;
import beans.Apartment;
import beans.DateRange;
import beans.Location;
import beans.User;
import dto.ApartmentChangeDTO;
import dto.ApartmentDTOJSON;

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

		// UNCOMENT IF WANT TO ADD DUMMY DATA TO FILE 
		//addMockupData();

		System.out.println(this.path);
	}

	/**
	 * Read the apartments from the file.
	 */
	public void readApartments() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(dateFormat);
		File file = new File(this.path);

		List<Apartment> loadedApartments = new ArrayList<Apartment>();
		try {

			loadedApartments = objectMapper.readValue(file, new TypeReference<List<Apartment>>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n ucitavam preko object mapera \n\n");
		for (Apartment a : loadedApartments) {
			/*
			 * System.out.println("ID APARTMANA: " + a.getID()); for (DateRange dateRange :
			 * a.getDatesForHosting()) { System.out.println("from date: "+
			 * dateRange.getFromDate()); System.out.println("to date: " +
			 * dateRange.getToDate()); }
			 */
			//System.out.println("\n\n code slike: " + a.getImagesPath() + "\n\n");
		
			
			apartments.add(a);
		}
		System.out.println("\n\n");
	}

	public void saveApartmentsJSON() {

		// Get all apartments
		List<Apartment> allApartments = new ArrayList<Apartment>();
		for (Apartment a : getValues()) {
			allApartments.add(a);
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(dateFormat);
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allApartments);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Izmena podataka prosledjenog apartmana.
	 */

	public Boolean changeApartment(Apartment updatedApartment, Date startDateForReservation, Date endDateForReservation) {

		for (Apartment apartment : apartments) {
			if (apartment.getID().equals((updatedApartment.getID()).intValue())) {
				
				apartment.setTypeOfApartment(updatedApartment.getTypeOfApartment());		
				apartment.setPricePerNight((updatedApartment.getPricePerNight()).doubleValue());
				apartment.setTimeForCheckIn(updatedApartment.getTimeForCheckIn());
				apartment.setTimeForCheckOut(updatedApartment.getTimeForCheckOut());
				apartment.setNumberOfRooms(updatedApartment.getNumberOfRooms());
				apartment.setNumberOfGuests(updatedApartment.getNumberOfGuests());
				apartment.setApartmentAmentitiesIDs(updatedApartment.getApartmentAmentitiesIDs());
				apartment.setLocation(updatedApartment.getLocation());
				apartment.setImagesPath(updatedApartment.getImagesPath());
				
				ArrayList<Date> dataRangeForHosting = new ArrayList<Date>();
				
				while (startDateForReservation.before(endDateForReservation)) {  
				    	dataRangeForHosting.add(startDateForReservation);
				        Calendar calendar = Calendar.getInstance();
				        calendar.setTime(startDateForReservation);
				        calendar.add(Calendar.DATE, 1);
				        startDateForReservation =  calendar.getTime();
			  }		
			  apartment.setDatesForHosting(dataRangeForHosting);
			  ArrayList<Date> freeData = new ArrayList<Date>();
				for(Date d : dataRangeForHosting) {
					freeData.add(d);
				}
			  apartment.setAvailableDates(freeData);
				
				saveApartmentsJSON();
				return true;
			}
		}

		return false;
	}

	public void activateApartment(int identificator) {
		for (Apartment apartment : apartments) {
			if (apartment.getID().equals(identificator)) {
				apartment.setStatus("ACTIVE");
				saveApartmentsJSON();
				return;
			}
		}

	}

	public ArrayList<Apartment> getValues() {
		return apartments;
	}

	/**
	 * Logical delete of apartment with forwarded ID.
	 * 
	 * @param identificator : unique represent of apartment
	 */
	public void deleteApartment(Integer identificator) {

		for (Apartment apartment : apartments) {
			if (apartment.getID().equals(identificator)) {
				apartment.setLogicalDeleted(1);				// 1- logical deleted, 0 - not logical deleted
				System.out.println("\n\n\t\tapartman sa id-em: " + apartment.getID() + " logicki obrisan: " + apartment.getLogicalDeleted());
				saveApartmentsJSON();
				return;
			}
		}
		return;

	}

	/**
	 * Add apartment to file & save it.
	 * 
	 * @param newItem
	 * @param hostID : need this parameter to set ID of host which is this apartment 
	 */
	public void addNewApartments(ApartmentDTOJSON newItem,Integer hostID) {

		Apartment apartment = new Apartment();
		apartment.setID( apartments.size() + 1);
		apartment.setHostID(hostID);
		apartment.setLogicalDeleted(0);
		
		
		apartment.setStatus("INACTIVE");
		apartment.setTypeOfApartment(newItem.addedApartment.getTypeOfApartment());
		apartment.setPricePerNight(newItem.addedApartment.getPricePerNight());
		apartment.setTimeForCheckIn(newItem.addedApartment.getTimeForCheckIn());
		apartment.setTimeForCheckOut(newItem.addedApartment.getTimeForCheckOut());
		apartment.setNumberOfRooms(newItem.addedApartment.getNumberOfRooms());
		apartment.setNumberOfGuests(newItem.addedApartment.getNumberOfGuests());
		apartment.setLocation(newItem.addedApartment.getLocation());
		apartment.setApartmentAmentitiesIDs(newItem.addedApartment.getApartmentAmentitiesIDs());
		apartment.setImagesPath(newItem.addedApartment.getImagesPath());
		//System.out.println("\n\n\t\t code od slike je: " + apartment.getImagesPath());
		//System.out.println("\n\n kraj coda");
		// init to empty, because null is not allowed for deserialization
		ArrayList<Date> dataRangeForHosting = new ArrayList<Date>();
		
		
	    while (newItem.startDateForReservation.before(newItem.endDateForReservation)) {
	       // processDate(newItem.startDateForReservation);
	    	dataRangeForHosting.add(newItem.startDateForReservation);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(newItem.startDateForReservation);
	        calendar.add(Calendar.DATE, 1);
	        newItem.startDateForReservation =  calendar.getTime();
	    }
		
		apartment.setDatesForHosting(dataRangeForHosting);
		ArrayList<Date> freeData = new ArrayList<Date>();
		for(Date d : dataRangeForHosting) {
			freeData.add(d);
		}
		apartment.setAvailableDates(freeData);
		
		newItem.addedApartment.getLocation().getAddress().setZipCode("");
		//apartment.setImagesPath("withoutPath");
		apartment.setListOfReservationsIDs(new ArrayList<Integer>());
		
		apartments.add(apartment);
		saveApartmentsJSON();
	}


	public Collection<Apartment> getHostApartments(User user) {

		List<Apartment> hostApartments = new ArrayList<Apartment>();

		for (int id : user.getApartmentsForRentingHostIDs()) {
			if(getApartmentById(id) != null) {
				hostApartments.add(getApartmentById(id));
			}
			
		}

		return hostApartments;
	}

	public Apartment getApartmentById(Integer id) {
		for (Apartment ap : apartments) {
			if (ap.getID().equals(id)) {
				return ap;
			}
		}
		return null;
	}

	/**
	 * Method for adding dummy data to JSON file of apartments
	 */
	private void addMockupData() {
		// Make list for writing
		List<Apartment> allApartments = new ArrayList<Apartment>();

		Integer ID = 1;
		Integer logicalDeleted = 0; // 1 - deleted, 0 - not deleted
		String typeOfApartment = "STANDARD"; // it can be STANDARD or ROOM
		Integer numberOfRooms = 11;
		Integer numberOfGuests = 5;
		Location location = new Location("41", "42", new Address("Danila Kisa", "33", "Novi Sad", "21000"));

		ArrayList<DateRange> datesForHosting = new ArrayList<DateRange>();
		
		 datesForHosting.add(new DateRange( new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
		 datesForHosting.add(new DateRange(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
		

		ArrayList<DateRange> availableDates = new ArrayList<DateRange>();
		  availableDates.add(new DateRange(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
		  availableDates.add(new DateRange(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
		 

		Integer hostID = 1;
		ArrayList<Integer> apartmentCommentsIDs = new ArrayList<Integer>();
		apartmentCommentsIDs.add(1);
		apartmentCommentsIDs.add(2);

		String images = "empty";

		Double pricePerNight = 100.0;

		//Date timeForCheckIn = new Date(System.currentTimeMillis());
		//Date timeForCheckOut = new Date(System.currentTimeMillis());
		String status = "ACTIVE";

		ArrayList<Integer> apartmentAmentitiesIDs = new ArrayList<Integer>();
		apartmentAmentitiesIDs.add(1);
		apartmentAmentitiesIDs.add(2);

		ArrayList<Integer> listOfReservationsIDs = new ArrayList<Integer>();
		listOfReservationsIDs.add(1);
		listOfReservationsIDs.add(2);
		/*
		 * Apartment a1 = new Apartment(ID, logicalDeleted, typeOfApartment,
		 * numberOfRooms, numberOfGuests, location, datesForHosting, availableDates,
		 * hostID, apartmentCommentsIDs, images, pricePerNight, "14:00", "10:00",
		 * status, apartmentAmentitiesIDs, listOfReservationsIDs); Apartment a2 = new
		 * Apartment(2, logicalDeleted, typeOfApartment, numberOfRooms, numberOfGuests,
		 * location, datesForHosting, availableDates, hostID, apartmentCommentsIDs,
		 * images, pricePerNight, "14:00", "10:00", status, apartmentAmentitiesIDs,
		 * listOfReservationsIDs);
		 */

		/*
		 * allApartments.add(a1); allApartments.add(a2);
		 */

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(dateFormat);
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allApartments);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
