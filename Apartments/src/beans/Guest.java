package beans;

import enums.TypeOfUser;


//TODO: Dopuniti gosta sa potrebnim atributima.
public class Guest extends User {

	public Guest(String id, String userName, String password, String name, String surname, TypeOfUser role) {
		super(id, userName, password, name, surname, role);
		
	}

}
