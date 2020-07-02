package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.AmenitiesItem;
import dto.AmenitiesItemAddDTO;
import dto.AmenitiesItemDTO;

public class AmenitiesDAO {
	private ArrayList<AmenitiesItem> amenities;
	private String path;

	public AmenitiesDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "amenities.json";
		this.amenities = new ArrayList<AmenitiesItem>();
		
		// UNCOMMENT IF WANT TO PUT DUMMY DATA IN FILE 
		// addMockupData();
	}

	public void readAmenities() {
		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File(this.path);

		List<AmenitiesItem> loadedAmenities = new ArrayList<AmenitiesItem>();
		try {

			loadedAmenities = objectMapper.readValue(file, new TypeReference<List<AmenitiesItem>>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n ucitavam preko object mapera \n\n");
		for (AmenitiesItem a : loadedAmenities) {
			System.out.println("IME SADRZAJA: " + a.getItemName());
			amenities.add(a);
		}
		System.out.println("\n\n");
	}

	private void saveAmenitiesJSON() {

		// Get all amenities
		List<AmenitiesItem> allAmenities = new ArrayList<AmenitiesItem>();
		for (AmenitiesItem a : getValues()) {
			allAmenities.add(a);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allAmenities);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<AmenitiesItem> getValues() {
		return amenities;
	}

	public Boolean changeItem(AmenitiesItemDTO updatedItem) {

		for (AmenitiesItem item : amenities) {
			System.out.println("UPOREDJUJEM I MENJAM " + item.getID() + " I " + updatedItem.amenitiesID);
			if (item.getID() == updatedItem.amenitiesID ) {
				item.setItemName(updatedItem.name);
				saveAmenitiesJSON();
				return true;
			}
		}

		return false;

	}

	public void deleteItem(Integer amenitiesID) {

		for (AmenitiesItem amenitiesItem : amenities) {
		
			if (amenitiesItem.getID() == amenitiesID) {
				amenities.remove(amenitiesItem);
				saveAmenitiesJSON();
				return;
			}
		}
		return;

	}

	public Boolean addItem(AmenitiesItemAddDTO newItem) {

		amenities.add(new AmenitiesItem(amenities.size() + 1, 0, newItem.newItemName));
		saveAmenitiesJSON();

		return true;

	}

	/**
	 * Method for adding dummy data to JSON file of comments
	 */
	@SuppressWarnings("unused")
	private void addMockupData() {
		
		// Make all amenities
		List<AmenitiesItem> allAmenities = new ArrayList<AmenitiesItem>();
		
		allAmenities.add(new AmenitiesItem(1, 0, "Tus kabina"));
		allAmenities.add(new AmenitiesItem(2, 0, "Kuhinja"));
		allAmenities.add(new AmenitiesItem(3, 0, "Terasa"));

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allAmenities);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
