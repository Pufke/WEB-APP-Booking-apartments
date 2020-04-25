package beans;

import enums.TypeOfUser;

//TODO: Dopuniti administratora sa potrebnim atributima.
public class Administrator extends User {

	public Administrator(String id, String userName, String password, String name, String surname, TypeOfUser role) {
		super(id, userName, password, name, surname, role);
		
	}
	
}
