package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.User;

@Path("/logout")
public class LogoutService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/someone")
	@Produces(MediaType.TEXT_HTML)
	public Response logoutUser() {
		
		if(isUserHost() || isUserAdmin() || isUserGuest()) {
		
			HttpSession session = request.getSession();
			if(session != null && session.getAttribute("loginUser") != null) {
				session.invalidate();
			}
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS LOGOUT")
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
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
