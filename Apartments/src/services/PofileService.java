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
	@Path("/profileUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInformationForEdit() {
		
		if(isUserHost() || isUserAdmin() || isUserGuest()) {
	
			User user = (User) request.getSession().getAttribute("loginUser");		

			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS SHOW")
					.entity( user)
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/saveUserChanges")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveProileChanges(UserDTO updatedUser) {
		
		if(isUserHost() || isUserAdmin() || isUserGuest()) {
			
			UsersDAO users = getUsers();
			users.changeUser(updatedUser);
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE")
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();

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
	
	private boolean isUserHost() {
		User user = (User) request.getSession().getAttribute("loginUser");
		
		if(user!= null) {
			if(user.getRole().equals("HOST")) {	
				return true;
			}
		}	
		return false;
	}
	
	private boolean isUserAdmin() {
		User user = (User) request.getSession().getAttribute("loginUser");
		
		if(user!= null) {
			if(user.getRole().equals("ADMINISTRATOR")) {
				return true;
			}
		}	
		return false;
	}
	
	private boolean isUserGuest() {
		User user = (User) request.getSession().getAttribute("loginUser");
		
		if(user!= null) {
			if(user.getRole().equals("GUEST")) {
				return true;
			}
		}	
		return false;
	}
}
