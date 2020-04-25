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
import javax.ws.rs.core.Response;

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
		
		User compareUser = users.getUser(user.username);
		
		if(compareUser == null) {
			System.out.println("Nema takvog usera");
			return Response.status(Response.Status.BAD_REQUEST).entity("Password or username are incorrect, try again").build();
		}else if(!compareUser.getPassword().equals(user.password)) {
			System.out.println("SIFRE NISU JEDNAKE");
			return Response.status(Response.Status.BAD_REQUEST).entity("Password or username are incorrect, try again").build();
		}
		
		
		// We know this, because in users we have 3 types of instances[Administrator, Guest, Host]
		if(compareUser instanceof Administrator) {
			Administrator admin = (Administrator) compareUser;
			request.getSession().setAttribute("loginUser", admin);  							// we give him a session
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/administratorDashboard.html").build();
			
		}else if(compareUser instanceof Guest) {
			Guest guest = (Guest) compareUser;
			request.getSession().setAttribute("loginUser", guest);  							// we give him a session
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/guestDashboard.html").build();
			
		}else if(compareUser instanceof Host) {
			Host host = (Host) compareUser;
			request.getSession().setAttribute("loginUser", host);  							// we give him a session
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/hostDashboard.html").build();
		}
		
		return Response.status(Response.Status.ACCEPTED).entity("/Apartments/#/login").build();		//redirect to login when is login accepted
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
		
		User newUser = new User(user.username, user.password, user.name, user.surname);
		users.addUser(newUser);
		users.saveUsers();
		
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
