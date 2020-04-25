package beans;

import enums.TypeOfUser;

//TODO: Dopuniti hosta sa potrebim atributima.
public class Host extends User {

	public Host(String id, String userName, String password, String name, String surname, TypeOfUser role) {
		super(id, userName, password, name, surname, role);
	}

}
