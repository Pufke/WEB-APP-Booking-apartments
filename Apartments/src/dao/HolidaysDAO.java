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

import beans.Holliday;
import dto.HollidaysItemAddDTO;

public class HolidaysDAO {
	private ArrayList<Holliday> hollidays;
	private String path;

	public HolidaysDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator
				+ "hollidays.json";
		this.hollidays = new ArrayList<Holliday>();

	}
	
	public void readHolidays() {
		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File(this.path);

		 ArrayList<Holliday> loadedHollidays = new  ArrayList<Holliday>();
		
		try {
			loadedHollidays = objectMapper.readValue(file, new TypeReference<ArrayList<Holliday>>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		hollidays = loadedHollidays;
	}

	public void saveHolidaysJSON() {

		List<Holliday> allHollidays = new  ArrayList<Holliday>();
		
		for (Holliday h : getValues()) {
			allHollidays.add(h);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allHollidays);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean addItem(HollidaysItemAddDTO newHolliday) {

		Holliday holliday = new Holliday();
		holliday.setHoliday(newHolliday.dateForAdd);
		hollidays.add(holliday);
		saveHolidaysJSON();

		return true;

	}
	
	public ArrayList<Holliday> getValues() {
		return hollidays;
	}
}
