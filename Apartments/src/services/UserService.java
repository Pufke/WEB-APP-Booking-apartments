package services;

import java.io.IOException;
import java.util.ArrayList;
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
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import beans.Administrator;
import beans.Guest;
import beans.Host;
import beans.User;
import dao.UsersDAO;
import dto.UserDTO;
import dto.UserLoginDTO;


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
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(UserLoginDTO user) {
		UsersDAO users = getUsers();
		
		User userForLogin = users.getUser(user.username);
		
		if(userForLogin == null) {
			System.out.println("Nema takvog usera");
			return Response.status(Response.Status.BAD_REQUEST).entity("Password or username are incorrect, try again").build();
		}else if(!userForLogin.getPassword().equals(user.password)) {
			System.out.println("SIFRE NISU JEDNAKE");
			return Response.status(Response.Status.BAD_REQUEST).entity("Password or username are incorrect, try again").build();
		}
		
		request.getSession().setAttribute("loginUser", userForLogin);			// we give him a session
		
		
		
		// We know this, because in users we have 3 types of instances[Administrator, Guest, Host]
		if(userForLogin.getRole().equals("ADMINISTRATOR")) {
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/administratorDashboard.html").build();
			
		}else if(userForLogin.getRole().equals("GUEST")) {
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/guestDashboard.html").build();
			
		}else if(userForLogin.getRole().equals("HOST")) {
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/hostDashboard.html").build();
			
		}
		
		return Response.status(Response.Status.ACCEPTED).entity("/Apartments/#/loginaaa").build();		//redirect to login when is login accepted
		//return Response.ok().build();
		
	}
	
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(UserDTO user) {
		System.out.println("DODAJEM USERA"+user.username+"\nSa sifrom: "+ user.password +"OVDE ZAPRAVO");
		System.out.println("Imena: " + user.name +"\nPrezimena: " + user.surname);
		
		UsersDAO users = getUsers();

		/* If we have already that user, we can't register him */
		if(users.getUser(user.username) != null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("We have alredy user with same username. Please try another one").build();
		}	
		
		User newUser = new User(user.username, user.password, user.name, user.surname, user.role);
		users.addUser(newUser);
		users.saveUsersJSON();
		
		System.out.println("\n\n\t\t USPESNO \n\n");
		return Response.status(Response.Status.ACCEPTED).entity("/Apartments/#/login").build();		//redirect to login when is registration accepted
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
	
	
	@POST
	@Path("/getSearchedUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getSearchedUsers(UserDTO userSearchParam){
		System.out.println("\n\n\t\t PRETRAGA KORISNIKA \n\n");
		
		Collection<User> allUsers = getUsers().getValues();
		ArrayList<User> searchedUsers = new ArrayList<User>();
		
		for (User user : allUsers) {
			if(
					(userSearchParam.username.equals("") ? true : user.getUserName().equals(userSearchParam.username)) &&
					(userSearchParam.surname.equals("") ? true : user.getSurname().equals(userSearchParam.surname)) &&
					(userSearchParam.name.equals("") ? true : user.getName().equals(userSearchParam.name)) &&
					(userSearchParam.role.equals("") ? true : user.getRole().equals(userSearchParam.role)) 
					) {
				System.out.println("DODAJEM");
				searchedUsers.add(user);
			}else {
				System.out.println("NISAM DODAO: " + user.getName() +"\n");
			}
		}
		
		return searchedUsers;
	}
	
	private UsersDAO getUsers(){
		UsersDAO users = (UsersDAO) ctx.getAttribute("users");
		if(users == null) {
			users = new UsersDAO();
			users.readUsers();
			ctx.setAttribute("users", users);
			
		}
		
		return users;
	}
	
}
