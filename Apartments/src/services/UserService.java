package services;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import dao.UsersDAO;
import dto.UserDTO;


@Path("/users")
public class UserService {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Hello Jersey";
	}
	
	
	@POST
	@Path("/registration")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String add(UserDTO user) {
		System.out.println("DODAJEM USERA"+user.username+"\nSa sifrom: "+ user.password +"OVDE ZAPRAVO");
		System.out.println("Imena: " + user.name +"\nPrezimena: " + user.surname);
		
		UsersDAO users = getUsers();
		
		
		User newUser = new User(user.username, user.password, user.name, user.surname);
		users.addUser(newUser);
		users.saveUsers();
		
		return "OK";
	}
	
	@GET
	@Path("/getNewUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User getNewUser() {
		User user = new User("100","noviName","noviSifra");
		return user;
		
		// TODO: promeniti da daje pametniji id useru
	}
	
	@GET
	@Path("/getJustUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getJustUsers(){
		System.out.println("CALLED GET JUST USERS");
		return getUsers().getValues();
	}
	
	
	
	private UsersDAO getUsers() {
		UsersDAO users = (UsersDAO) ctx.getAttribute("users");
		if(users == null) {
			users = new UsersDAO();
			users.readUsers();
			ctx.setAttribute("users", users);
			
		}
		
		return users;
	}
	
}
