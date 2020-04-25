package beans;

import enums.TypeOfUser;

//TODO: Dopuniti hosta sa potrebim atributima.
public class Host extends User {

	public Host(String userName, String password, String name, String surname) {
		super(userName, password, name, surname, TypeOfUser.HOST);
	}

}
