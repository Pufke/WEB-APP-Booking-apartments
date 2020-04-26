package services;

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

import beans.Guest;
import beans.User;
import dao.UsersDAO;
import dto.UserDTO;

@Path("/edit")
public class PofileService {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	
	@GET
	@Path("/profileGuest")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserInformationForEdit() {
		
		// With this, we get user who is loged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");
		
		if(user instanceof Guest || user.getRole().equals("GUEST")) {
			System.out.println("\n\n\n \t DOBIO SAM " + user.getRole()+"\n\n");
			System.out.println("username: " +user.getUserName() + " password: " + user.getPassword());
		}else {
			System.out.println("NISAM");
		}
		return user;
	}
	
	@POST
	@Path("/saveUserChanges")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveProileChanges(UserDTO updatedUser) {
		UsersDAO users = getUsers();
		User changeUser = users.getUser(updatedUser.username);
		
		
		
		if(users.changeUser(updatedUser)) {

			return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();
			
			
		}else {
			return Response.status(Response.Status.BAD_REQUEST).entity("ERROR DURING CHANGES").build();
		}
		

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
