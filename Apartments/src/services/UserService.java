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
import dao.ReservationDAO;
import dao.UsersDAO;
import dto.UserDTO;
import dto.UserDTOJSON;
import dto.UserLoginDTO;

@Path("/users")
public class UserService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(UserLoginDTO user) {
		UsersDAO allUsersDAO = getUsers();

		User userForLogin = allUsersDAO.getUserByUsername(user.username);

		if (userForLogin == null) {
			System.out.println("Nema takvog usera");
			return Response.status(Response.Status.BAD_REQUEST).entity("Password or username are incorrect, try again")
					.build();
		}	
		
		
		
		if (!userForLogin.getPassword().equals(user.password)) {
			System.out.println("SIFRE NISU JEDNAKE");
			return Response.status(Response.Status.BAD_REQUEST).entity("Password or username are incorrect, try again")
					.build();
		}
		
		if(allUsersDAO.isBlocked(user.username)) {
			System.out.println("blokiran je");
			return Response.status(Response.Status.BAD_REQUEST).entity("You are blocked from this application!")
					.build();
		}

		request.getSession().setAttribute("loginUser", userForLogin); // we give him a session

		// We know this, because in users we have 3 types of instances[Administrator,
		// Guest, Host]
		if (userForLogin.getRole().equals("ADMINISTRATOR")) {
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/administratorDashboard.html").build();

		} else if (userForLogin.getRole().equals("GUEST")) {
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/guestDashboard.html").build();

		} else if (userForLogin.getRole().equals("HOST")) {
			return Response.status(Response.Status.ACCEPTED).entity("/Apartments/hostDashboard.html").build();

		}

		return Response.status(Response.Status.ACCEPTED).entity("/Apartments/#/loginaaa").build(); // redirect to login
																									// when is login
																									// accepted
		// return Response.ok().build();

	}

	@POST
	@Path("/blockUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response blockUser(UserDTOJSON param){
		
		if(isUserAdmin()) {
			UsersDAO allUsersDAO = getUsers();
			allUsersDAO.blockUserById(param.user.getID());
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS BLOCK")
					.entity(getUsers().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/unblockUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unblockUser(UserDTOJSON param){
		
		if(isUserAdmin()) {
		
			UsersDAO allUsersDAO = getUsers();
			allUsersDAO.unblockUserById(param.user.getID());
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS UNBLOCK")
					.entity(getUsers().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(UserDTO user) {
		
		UsersDAO allUsersDAO = getUsers();

		/* If we have already that user, we can't register him */
		if (allUsersDAO.getUserByUsername(user.username) != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("We have alredy user with same username. Please try another one").build();
		}

		
		allUsersDAO.addNewUser(user);

		return Response.status(Response.Status.ACCEPTED).entity("/Apartments/#/login").build(); // redirect to login
																								// when is registration																							// accepted
	}

	@GET
	@Path("/getJustUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJustUsers() {
		
		if(isUserAdmin()) {
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS SHOW")
					.entity(getUsers().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@GET
	@Path("/getNewUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User getNewUser() {
		User user = new User();
		UsersDAO allUsersDAO = getUsers();
		
		Integer UserUniqueID = allUsersDAO.getValues().size() + 1;
		user.setID(UserUniqueID);
		user.setUserName("Username");
		user.setPassword("password");
		
		return user;

	}

	@GET
	@Path("/getGuestsOfHost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGuestsOfHost() {
		
		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
			UsersDAO allUsersDAO = getUsers();
			
			ReservationDAO reservationDAO = getReservations();
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS SHOW")
					.entity(allUsersDAO.getGuestsOfHost(user, reservationDAO.getValues()))
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@GET
	@Path("/getReservationsOfHost")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservationsOfHost() {
		
		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
			UsersDAO allUsersDAO = getUsers();
			
			ReservationDAO reservationDAO = getReservations();
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS SHOW")
					.entity(allUsersDAO.getReservationsOfHost(user, reservationDAO.getValues()))
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	

	private UsersDAO getUsers() {
	
		UsersDAO users = (UsersDAO) ctx.getAttribute("users");
		
		if (users == null) {
			users = new UsersDAO();
			users.readUsers();
			ctx.setAttribute("users", users);

		}

		return users;
	}
	
	private ReservationDAO getReservations() {
		
		ReservationDAO reservations = (ReservationDAO) ctx.getAttribute("reservations");

		if (reservations == null) {
			reservations = new ReservationDAO();
			reservations.readReservations();
			ctx.setAttribute("reservations", reservations);
		}
		return reservations;
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
	
	@SuppressWarnings("unused")
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
