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

	private HashMap<String, User> users = new HashMap<String, User>();
	private ArrayList<User> userList = new ArrayList<User>();

	private String path;

	public UsersDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "users.txt";
		this.UsersRead(path);
	}

	/**
	 * Read the data from the path.
	 * 
	 * @param path
	 */
	public void UsersRead(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path);
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readUsers(in);
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

	private void readUsers(BufferedReader in) {
		String line, id = "", userName = "", password = "";
		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					userName = st.nextToken().trim();
					password = st.nextToken().trim();
				}
				User product = new User(id, userName, password);
				users.put(id, product);
				userList.add(product);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addUser(User user) {
		if(!users.containsValue(user)) {
			// TODO: Proveriti da li stvarno dobro proverava
			System.out.println("DODAO SAM: "+ user.getUserName());
			users.put(user.getUserName(),user);
		}
	}
	
	
	
	public Collection<User> values() {
		return users.values();
	}

	public Collection<User> getValues() {
		return users.values();
	}

	public User getUser(String id) {
		return users.get(id);
	}

	public ArrayList<User> getUserList() {
		return userList;
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
		sb.append(user.getId()+"|");
		
		sb.append(user.getUserName() +"|");
		
		String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
		sb.append(encodedPassword + "|");
		
		
		return sb.toString();
	}

}
