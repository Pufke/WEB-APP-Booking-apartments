package beans;

//TODO: Dopuniti administratora sa potrebnim atributima.
public class Administrator extends User {

	public Administrator(String userName, String password, String name, String surname) {
		super(userName, password, name, surname, "ADMINISTRATOR");
		
	}
	
}
