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



public class AddressDAO {
	private ArrayList<Address> addresses;
	private String path;

	
	public AddressDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "addresses.txt";
		this.addresses = new ArrayList<Address>();
		
	}
	
	/**
	 * Read the addresses from the file.
	 */
	public void readAddresses() {
		System.out.println("\n\n\n\t\t POZVANO READ ADDRESSES\n\n\n");
		BufferedReader in = null;
		
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				saveAddresses();
				file = new File(this.path);
			}	
			System.out.println("PUTANJA: "+ this.path);
			in = Files.newBufferedReader(Paths.get(this.path), StandardCharsets.UTF_8);
			StringTokenizer st;
			String line;
			 
			String addressID="", street="", number="", populatedPlace="", zipCode="";

			try {
				while ((line = in.readLine()) != null) {
					
					line = line.trim();
					if (line.equals("") || line.indexOf('#') == 0)
						continue;
					st = new StringTokenizer(line, "|");
					while (st.hasMoreTokens()) {
						
						addressID = st.nextToken().trim();
						street = st.nextToken().trim();
						number = st.nextToken().trim();
						populatedPlace = st.nextToken().trim();
						zipCode = st.nextToken().trim();
						
						Address address = new Address( street, number, populatedPlace, zipCode,Integer.parseInt(addressID));
						addresses.add(address);
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
	

	private void saveAddresses() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8);
			for (Address a : addresses) {
				out.write(writeAddresses(a));
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
	
	private String writeAddresses(Address address) {
		StringBuilder sb = new StringBuilder();
		
		
		sb.append(address.getAddressID() + "|");
		sb.append(address.getStreet()+ "|");
		sb.append(address.getNumber() + "|");
		sb.append(address.getPopulatedPlace() + "|");
		sb.append(address.getZipCode() + "|");
		
		return sb.toString();
	}
	
	public ArrayList<Address> getAddresses() {
		return addresses;
	}

}

