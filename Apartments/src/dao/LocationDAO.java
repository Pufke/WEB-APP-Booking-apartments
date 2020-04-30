package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

import beans.Address;
import beans.Location;

public class LocationDAO {
	private ArrayList<Location> locations;
	private String path;
	
	public LocationDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "locations.txt";
		this.locations = new ArrayList<Location>();
		
	}
	

	/**
	 * Read the locations from the file.
	 */
	public void readLocations() {
		System.out.println("\n\n\n\t\t POZVANO READ LOCATIONS\n\n\n");
		BufferedReader in = null;
		
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				saveLocations();
				file = new File(this.path);
			}	
			System.out.println("PUTANJA: "+ this.path);
			in = Files.newBufferedReader(Paths.get(this.path), StandardCharsets.UTF_8);
			StringTokenizer st;
			String line;
			 
			String locationID="", latitude="", longitude="", addessID="";
			
			AddressDAO address= new AddressDAO();
			address.readAddresses();
			ArrayList<Address> addresses = address.getAddresses();
			
			try {  
				while ((line = in.readLine()) != null) {
					
					line = line.trim();
					if (line.equals("") || line.indexOf('#') == 0)
						continue;
					st = new StringTokenizer(line, "|");
					while (st.hasMoreTokens()) {
						
						locationID = st.nextToken().trim();
						latitude = st.nextToken().trim();
						longitude = st.nextToken().trim();
						addessID = st.nextToken().trim();
						
						
						for (Address a : addresses) {
							if(a.getAddressID() == Integer.parseInt(addessID)) {
								Location location = new Location(latitude,longitude,a, Integer.parseInt(addessID), Integer.parseInt(locationID));
								locations.add(location);
							}
						}
						
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
	


	private void saveLocations() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8);
			for (Location l : locations) {
				out.write(writeLocation(l));
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
	
	private String writeLocation(Location location) {
		StringBuilder sb = new StringBuilder();
		
		
		sb.append(location.getLocationID() + "|");
		sb.append(location.getLatitude()+ "|");
		sb.append(location.getLongitude() + "|");
		sb.append(location.getAddessID() + "|");
		
		return sb.toString();
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

}
