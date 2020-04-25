package beans;

import enums.TypeOfUser;

public class User {

	private String id;
	private String userName;
	private String password;

	private String name;
	private String surname;

	private TypeOfUser role;

	public User(String id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;

		this.name = "";
		this.surname = "";
	}

	public User(String userName, String password, String name, String surname) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
	}

	public User(String userName, String password, String name, String surname, TypeOfUser role) {
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.role = role;
	}

	public TypeOfUser getRole() {
		return role;
	}

	public void setRole(TypeOfUser role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}
