package services;

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

import beans.Apartment;
import beans.Guest;
import beans.Reservation;
import beans.User;
import dao.ApartmentsDAO;
import dao.ReservationDAO;
import dao.UsersDAO;
import dto.ReservationDTO;

@Path("/reservation")
public class ReservationService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/getReservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getJustApartments(){
		System.out.println("CALLED GET JUST RESERVATIONS");
		return getReservations().getValues();
	}
	
	private ReservationDAO getReservations() {
		ReservationDAO reservations = (ReservationDAO) ctx.getAttribute("reservations");
		
		if(reservations == null) {
			reservations = new ReservationDAO();
			reservations.readReservations();
			ctx.setAttribute("reservations", reservations);
		}
		return reservations;
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

	private ApartmentsDAO getApartments() {
		ApartmentsDAO apartments = (ApartmentsDAO) ctx.getAttribute("apartments");
		
		if(apartments == null) {
			apartments = new ApartmentsDAO();
			apartments.readApartments();
			
			ctx.setAttribute("apartments", apartments);
		}
		
		return apartments;
		
	}
	
	
	@POST
	@Path("/makeReservations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeReservations(ReservationDTO reservationData) {
		ReservationDAO reservationsCTX = getReservations();
		UsersDAO usersCTX = getUsers();
		ApartmentsDAO apartmentsCTX = getApartments();
		
		ArrayList<Apartment> apartments = apartmentsCTX.getValues();
		Collection<User> users = usersCTX.getValues();
		ArrayList<Reservation> reservations = reservationsCTX.getValues();
		
		Apartment apartment = new Apartment();
		User user = new User();
		
		for (Apartment a : apartments) {
			if(a.getIdentificator() == reservationData.apartmentIdentificator) {
				a.setReservedStatus("Rezervisano");
				apartment = a;
				break;
			}
		}
		for (User u: users) {
			if(u.getUserName().equals(reservationData.guestUserName)) {
				user = u;
				break;
			}
		}
		
		Reservation reservation = new Reservation(null, apartment, reservationData.dateOfReservation, reservationData.numberOfNights, (long) 1600, reservationData.messageForHost, (Guest) user, reservationData.statusOfReservation);
		reservations.add(reservation);
		
		reservationsCTX.saveReservationsJSON();
		apartmentsCTX.saveApartmentsJSON();
		return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();

	}
	
	
}
//reservation/makeReservations