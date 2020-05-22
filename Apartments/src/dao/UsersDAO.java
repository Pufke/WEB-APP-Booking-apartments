package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import beans.Administrator;
import beans.Guest;
import beans.Host;
import beans.User;
import dto.UserDTO;

public class UsersDAO {

	private static LinkedHashMap<String, User> users;
	private String path;

	public UsersDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "users.json";
		UsersDAO.users = new LinkedHashMap<String, User>();
	}

	/**
	 * Read the data from the path.
	 * 
	 * @param path
	 */
	@SuppressWarnings("unchecked")
	public void readUsers() {
		BufferedReader in = null;
		try {
			File file = new File(this.path);
			if (!file.exists()) {
				file = new File(this.path);
			}	
			JSONParser jsonParser = new JSONParser();
	         
	        try (FileReader reader = new FileReader(path))
	        {
	        	Object obj = jsonParser.parse(reader);
	        	JSONArray users = (JSONArray) obj;
				System.out.println(users);
				users.forEach( u -> parseUsersObject( (JSONObject) u ) );
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
	
	 private void parseUsersObject(JSONObject user) 
	    {
	      
	         
	        String userName = (String) user.get("userName");    
	        String password = (String) user.get("password");
	        
			/* byte[] decodedBytes = Base64.getDecoder().decode(password); */
			/* password = new String(decodedBytes); */
			
			String name = (String) user.get("name");
			String surname = (String) user.get("surname");
			String role = (String) user.get("role");
	        
			
			if(role.equals("ADMINISTRATOR")) {
				
				Administrator admin = new Administrator(userName,password,name,surname);
				users.put(admin.getUserName(), admin);
				
			}else if(role.equals("GUEST")) {
				
				Guest guest = new Guest(userName, password, name, surname);
				users.put(guest.getUserName(), guest);
				
			}else if(role.equals("HOST")) {
				
				Host host = new Host(userName, password, name, surname);
				users.put(host.getUserName(), host);
			}
	
	    }
	

	public void addUser(User user) {
		if(!users.containsValue(user)) {
			// TODO: Proveriti da li stvarno dobro proverava
			System.out.println("DODAO SAM: "+ user.getUserName());
			users.put(user.getUserName(),user);
		}
	}
	
	
	public Boolean changeUser(UserDTO updatedUser) {
		
		// Find user with that name, and change his data.
		for (User user : users.values()) {
			if(user.getUserName().equals(updatedUser.username)) {
				System.out.println("NASAO SAM " + user.getUserName() + " i sad cu mu izmeniti podatke");
				System.out.println("NJEGOVA ROLA JE TRENUTNO: " + user.getRole());
				System.out.println("A NOVA JE: " + updatedUser.role);
				
				user.setName(updatedUser.name);
				user.setPassword(updatedUser.password);
				user.setSurname(updatedUser.surname);
				user.setRole(updatedUser.role);
				
				
				saveUsersJSON();
				
				return true;
			}
		}
		return false;
	}


	@SuppressWarnings("unchecked")
	public void saveUsersJSON() {
		
		JSONArray usersList = new JSONArray();
		
		for (User u : users.values()) {
			JSONObject user = new JSONObject();
			user.put("userName", u.getUserName());
			user.put("password", u.getPassword());
			user.put("name", u.getName());
			user.put("surname", u.getSurname());
			user.put("role", u.getRole());
			
			//Add User to list
			usersList.add(user);
		}
		//Write JSON file
       try (FileWriter file = new FileWriter(path)) {
           file.write(usersList.toJSONString());
           file.flush();

       } catch (IOException e) {
           e.printStackTrace();
       }
		
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

	public static Collection<User> getValues() {
		return users.values();
	}

	public User getUser(String username) {
		if(users.containsKey(username)) {
			return users.get(username);
		}
		
		return null;
	}

	
}
