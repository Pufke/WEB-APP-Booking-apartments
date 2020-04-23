package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import beans.User;

public class UsersDAO {

//	private HashMap<String, User> users = new HashMap<String, User>();
//	private ArrayList<User> userList = new ArrayList<User>();

	
	private LinkedHashMap<String, User> users;
	private String path;

	public UsersDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "users.txt";
		this.users = new LinkedHashMap<String, User>();
	}

	/**
	 * Read the data from the path.
	 * 
	 * @param path
	 */
	public void readUsers() {
		System.out.println("\n\n\n\t\t POZVANO READ USERS\n\n\n");
		BufferedReader in = null;
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				saveUsers();
				file = new File(this.path);
			}	
			System.out.println("PUTANJA: "+ this.path);
			in = Files.newBufferedReader(Paths.get(this.path), StandardCharsets.UTF_8);
			StringTokenizer st;
			String line;
			
			String userName="", password="", name="", surname="";
			
			try {
				while ((line = in.readLine()) != null) {
					
					line = line.trim();
					if (line.equals("") || line.indexOf('#') == 0)
						continue;
					st = new StringTokenizer(line, "|");
					while (st.hasMoreTokens()) {
						userName= st.nextToken().trim();
						
						byte[] decodedBytes = Base64.getDecoder().decode(st.nextToken().trim());
						password = new String(decodedBytes);
						
						name = st.nextToken().trim();
						surname = st.nextToken().trim();
						
						
						
						User user = new User(userName, password, name, surname);
						users.put(user.getUserName(), user);
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
	
	

	public void addUser(User user) {
		if(!users.containsValue(user)) {
			// TODO: Proveriti da li stvarno dobro proverava
			System.out.println("DODAO SAM: "+ user.getUserName());
			users.put(user.getUserName(),user);
		}
	}
	

	public void saveUsers() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8);
			for (User u : users.values()) {
				out.write(writeUser(u));
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

	private String writeUser(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append(user.getUserName() +"|");
		
		String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
		sb.append(encodedPassword + "|");
		
		sb.append(user.getName()+"|");
		sb.append(user.getSurname()+"|");
		
		return sb.toString();
	}

	
	
	public LinkedHashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(LinkedHashMap<String, User> users) {
		this.users = users;
	}

	public Collection<User> values() {
		return users.values();
	}

	public Collection<User> getValues() {
		return users.values();
	}
}
