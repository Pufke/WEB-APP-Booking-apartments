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
	}

	@SuppressWarnings("unchecked")
	public void readAmenities() {
		BufferedReader in = null;
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				file = new File(this.path);
			}
			JSONParser jsonParser = new JSONParser();

			try (FileReader reader = new FileReader(path)) {
				Object obj = jsonParser.parse(reader);
				JSONArray amenities = (JSONArray) obj;
				System.out.println(amenities);
				amenities.forEach(amenitiesItem -> parseAmenitiesObject((JSONObject) amenitiesItem));
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

	private void parseAmenitiesObject(JSONObject amenitiesItem) {

		Long ID = (Long) amenitiesItem.get("amenitiesItemID");
		String name = (String) amenitiesItem.get("itemName");

		AmenitiesItem newAmenitiesItem = new AmenitiesItem(ID, name);
		amenities.add(newAmenitiesItem);

		System.out.println("\n\t Sadrzaj apartmana \n");
		System.out.println(newAmenitiesItem.getName());

	}

	@SuppressWarnings({ "unchecked" })
	private void saveAmenitiesJSON() {

		JSONArray amenitiesList = new JSONArray();

		for (AmenitiesItem item : amenities) {

			JSONObject itemToSave = new JSONObject();

			itemToSave.put("amenitiesItemID", item.getAmenitiesID());
			itemToSave.put("itemName", item.getName());

			amenitiesList.add(itemToSave);
		}
		
		// Writing to JSON file
		try (FileWriter file = new FileWriter(path)) {
			file.write(amenitiesList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<AmenitiesItem> getValues() {
		return amenities;
	}

	public Boolean changeItem(AmenitiesItemDTO updatedItem) {
		
		for(AmenitiesItem item : amenities) {
			if(item.getAmenitiesID().equals(updatedItem.amenitiesID)) {
				System.out.println(" nasao sam item sa id-om: " + updatedItem.amenitiesID);
				
				item.setName(updatedItem.name);
				saveAmenitiesJSON();
				return true;
			}
		}
		
		return false;
		
	}

	public void deleteItem(Long amenitiesID) {
		
		for (AmenitiesItem amenitiesItem : amenities) {
			if(amenitiesItem.getAmenitiesID().equals(amenitiesID)) {
				amenities.remove(amenitiesItem);
				saveAmenitiesJSON();
				return;
			}
		}
		return;
		
	}

	public Boolean addItem(AmenitiesItemAddDTO newItem) {
		
		amenities.add(new AmenitiesItem((long) (amenities.size()+1),newItem.newItemName));
		saveAmenitiesJSON();
		
		return true;
		
	}

}
