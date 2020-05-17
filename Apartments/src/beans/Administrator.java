package beans;

//TODO: Dopuniti administratora sa potrebnim atributima.
public class Administrator extends User {
	
	
	/*
	 * Konstruktor koji poziva konstruktor nadklase i kaze da je
	 * njegova rola ADMINISTRATOR
	 */
	public Administrator(String userName, String password, String name, String surname) {
		super(userName, password, name, surname, "ADMINISTRATOR");
		
	}
	
}
