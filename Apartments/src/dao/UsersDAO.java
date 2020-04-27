package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import beans.Administrator;
import beans.Guest;
import beans.Host;
import beans.User;
import dto.UserDTO;

public class UsersDAO {

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
			
			String userName="", password="", name="", surname="", role="";
			
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
						role = st.nextToken().trim();
						
						
						/* Because of this, we will know after, which roles have user */
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
				
				
				saveUsers();
				
				return true;
			}
		}
		return false;
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
		sb.append(user.getRole()+"|");
		
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

	public User getUser(String username) {
		if(users.containsKey(username)) {
			return users.get(username);
		}
		
		return null;
	}

	
}
