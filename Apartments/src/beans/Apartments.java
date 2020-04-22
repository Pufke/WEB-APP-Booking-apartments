package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class which represent list of all apartments.
 * 
 * @author Vaxi
 *
 */
public class Apartments {

	private HashMap<String, Apartment> apartments = new HashMap<String, Apartment>();
	private ArrayList<Apartment> apartmentList = new ArrayList<Apartment>();

	public Apartments() {
		this("C:\\Users\\Vaxi\\Desktop\\6-semestar\\WEB\\WEB-Projekat\\Apartments\\WebContent");
	}
	
	
	//TODO: Mozda, ovo prebaciti u novi paket pod nacivamo DAO koji ce vrsiti samo interakciju sa skladistem podataka.
	
	/**
	 * From path we read the data.
	 * @param path
	 */
	public Apartments(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/apartments.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readApartments(in);
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


	/**
	 * Read apartment from file and put him in list of apartments. Key is id of
	 * apartment.
	 */
	private void readApartments(BufferedReader in) {
		String line, id = "", name = "", price = "";
		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					name = st.nextToken().trim();
					price = st.nextToken().trim();
				}
				Apartment product = new Apartment(id, name, Double.parseDouble(price));
				apartments.put(id, product);
				apartmentList.add(product);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/** Return collection of apartments */
	public Collection<Apartment> values() {
		return apartments.values();
	}

	/** Return collection of apartments */
	public Collection<Apartment> getValues() {
		return apartments.values();
	}

	/** Return apartment who have these id */
	public Apartment getApartment(String id) {
		return apartments.get(id);
	}

	/** Return list of apartments */
	public ArrayList<Apartment> getApartmentList() {
		return apartmentList;
	}

}
