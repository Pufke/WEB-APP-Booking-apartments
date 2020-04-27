package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import beans.Administrator;
import beans.Apartment;
import beans.Guest;
import beans.Host;
import beans.User;

public class ApartmentsDAO {
	
	private ArrayList<Apartment> apartments;
	private String path;
	
	
	public ApartmentsDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "apartments.txt";
		this.apartments = new ArrayList<Apartment>();
		
	}
	
	
	/**
	 * Read the apartments from the file.
	 */
	public void readApartments() {
		System.out.println("\n\n\n\t\t POZVANO READ APARTMENTS\n\n\n");
		BufferedReader in = null;
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				saveApartments();
				file = new File(this.path);
			}	
			System.out.println("PUTANJA: "+ this.path);
			in = Files.newBufferedReader(Paths.get(this.path), StandardCharsets.UTF_8);
			StringTokenizer st;
			String line;
			
			String status="", typeOfApartment="", pricePerNight="", numberOfRooms="";
			
			try {
				while ((line = in.readLine()) != null) {
					
					line = line.trim();
					if (line.equals("") || line.indexOf('#') == 0)
						continue;
					st = new StringTokenizer(line, "|");
					while (st.hasMoreTokens()) {
						status = st.nextToken().trim();
						if(!status.equals("ACTIVE"))	// Do not read inactive apartments
							continue;			
						typeOfApartment = st.nextToken().trim();
						pricePerNight = st.nextToken().trim();
						numberOfRooms = st.nextToken().trim();
						
						Apartment apartment = new Apartment(typeOfApartment, Integer.parseInt(numberOfRooms), Double.parseDouble(pricePerNight), status);
						apartments.add(apartment);
						
					}
					
					
				}
			}catch (Exception ex) {
				ex.printStackTrace();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}


	private void saveApartments() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8);
			for (Apartment a : apartments) {
				out.write(writeApartment(a));
				out.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
				}
			}
		}
		
	}


	private String writeApartment(Apartment apartment) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(apartment.getStatus() + "|");
		sb.append(apartment.getTypeOfApartment() + "|");
		sb.append(apartment.getPricePerNight() + "|");
		sb.append(apartment.getNumberOfRooms() + "|");
		
		
		return sb.toString();
	}


	public ArrayList<Apartment> getValues() {
		return apartments;
	}
	
}
