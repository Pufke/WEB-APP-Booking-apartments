package beans;

import enums.TypeOfUser;


//TODO: Dopuniti gosta sa potrebnim atributima.
public class Guest extends User {

	public Guest(String userName, String password, String name, String surname) {
		super(userName, password, name, surname, TypeOfUser.GUEST);
		
	}

}
