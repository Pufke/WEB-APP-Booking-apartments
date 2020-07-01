package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import beans.Comment;
import beans.Reservation;
import beans.User;
import dao.ApartmentsDAO;
import dao.CommentsDAO;
import dao.ReservationDAO;
import dao.UsersDAO;
import dto.CommentDTO;
import dto.DeleteReservationDTO;
import dto.ReservationDTO;
import dto.ReservationDTOJSON;

@Path("/reservation")
public class ReservationService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getReservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getJustApartments() {
		System.out.println("CALLED GET JUST RESERVATIONS");
		return getReservations().getValues();
	}

	@POST
	@Path("/acceptReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> acceptReservation(ReservationDTOJSON param){
		
		ReservationDAO reservationsDAO = getReservations();
		reservationsDAO.acceptReservation(param.reservation);
		
		UsersDAO usersDAO = getUsers();
		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");
		return usersDAO.getReservationsOfHost(user, reservationsDAO.getValues());
	}

	@POST
	@Path("/declineReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> declineReservation(ReservationDTOJSON param){
		
		ReservationDAO reservationsDAO = getReservations();
		reservationsDAO.declineReservation(param.reservation);
		
		UsersDAO usersDAO = getUsers();
		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");
		return usersDAO.getReservationsOfHost(user, reservationsDAO.getValues());
	}
	
	@POST
	@Path("/endReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> endReservation(ReservationDTOJSON param){
		
		ReservationDAO reservationsDAO = getReservations();
		reservationsDAO.endReservation(param.reservation);
		
		UsersDAO usersDAO = getUsers();
		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");
		return usersDAO.getReservationsOfHost(user, reservationsDAO.getValues());
	}
	
	
	//declineReservation
	
	@POST
	@Path("/makeReservations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeReservations(ReservationDTO reservationData) {
		// Prilikom kreiranja rezervacije kreirati rezervaciju dodati u Apartman
		// IdRezervacije
		ReservationDAO reservationsCTX = getReservations();
		UsersDAO usersCTX = getUsers();
		ApartmentsDAO apartmentsCTX = getApartments();

		System.out.println("Kreiranje rezervacijE");

		ArrayList<Apartment> apartments = apartmentsCTX.getValues();
		Collection<User> users = usersCTX.getValues();
		ArrayList<Reservation> reservations = reservationsCTX.getValues();

		Apartment apartment = new Apartment();
		User user = new User();

		for (Apartment a : apartments) {
			if (a.getID().equals((reservationData.apartmentIdentificator).intValue())) {
				// a.setReservedStatus("Rezervisano");
				apartment = a;
				break;
			}
		}
		for (User u : users) {
			if (u.getID() == reservationData.guestID) {
				user = u;
				break;
			}
		}
	//Convert java.util.Date to java.Sql.date, reason why i do this i beacuse frontend doesnt format well 
   // java.util.Date format 
		java.sql.Date sd = new java.sql.Date(reservationData.dateOfReservation.getTime());

		ArrayList<Date> listaSlobodnihDatuma = apartment.getAvailableDates();

		
		for(int i = 0; i < reservationData.numberOfNights; i++) {
			if(listaSlobodnihDatuma.contains(reservationData.dateOfReservation)) {
				listaSlobodnihDatuma.remove(reservationData.dateOfReservation);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(reservationData.dateOfReservation);
				calendar.add(Calendar.DATE, 1);
				reservationData.dateOfReservation =  calendar.getTime();
				
			}else {
				return Response.status(Response.Status.EXPECTATION_FAILED).entity("APARTMENT IS NOT FREE").build();
			}
		}
		
		
		double totalPrice =  reservationData.numberOfNights * apartment.getPricePerNight();
		Integer ReservationUniqueID = reservations.size() + 1;

		
		Reservation newReservation = new Reservation(ReservationUniqueID, 0, reservationData.apartmentIdentificator,
				sd, reservationData.numberOfNights, totalPrice,
				reservationData.messageForHost, reservationData.guestID, reservationData.statusOfReservation);
		
		ArrayList<Integer> reservedApartmentList = apartment.getListOfReservationsIDs();
		reservedApartmentList.add(ReservationUniqueID);
		apartment.setListOfReservationsIDs(reservedApartmentList);
		apartment.setAvailableDates(listaSlobodnihDatuma);
		
		reservations.add(newReservation);

		reservationsCTX.saveReservationsJSON();
		apartmentsCTX.saveApartmentsJSON();
		return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();

	}

	@POST
	@Path("/deleteReservations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReservations(DeleteReservationDTO reservationData) {
		ReservationDAO reservationsCTX = getReservations();
		ApartmentsDAO apartmentsCTX = getApartments();

		ArrayList<Apartment> apartments = apartmentsCTX.getValues();
		ArrayList<Reservation> reservations = reservationsCTX.getValues();

		Reservation reservation = new Reservation();

		System.out.println(reservationData.reservationID);
		for (Reservation r : reservations) {
			if (r.getID().equals(Integer.parseInt(reservationData.reservationID))) {
				reservation = r;
				break;
			}
		}

		for (Apartment a : apartments) {
			if (a.getID().equals((reservationData.apartmentIdentificator).intValue())) {
				ArrayList<Integer> rezervacije = a.getListOfReservationsIDs();
				for (Integer s : rezervacije) {
					if (s.equals(reservationData.reservationID)) {
						rezervacije.remove(s);
						break;
					}

				}
				a.setListOfReservationsIDs(rezervacije);
				break;
			}
		}

		reservations.remove(reservation);

		reservationsCTX.saveReservationsJSON();
		apartmentsCTX.saveApartmentsJSON();
		return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();

	}

	@POST
	@Path("/makeComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeComment(CommentDTO commentData) {
		UsersDAO usersCTX = getUsers();
		ApartmentsDAO apartmentsCTX = getApartments();
		CommentsDAO commentsCTX = getComments();

		ArrayList<Comment> comments = commentsCTX.getValues();
		ArrayList<Apartment> apartments = apartmentsCTX.getValues();
		Collection<User> users = usersCTX.getValues();

		Apartment apartment = new Apartment();
		User user = new User();

		for (Apartment a : apartments) {
			if (a.getID().equals((commentData.apartmentID).intValue())) {
				apartment = a;
				break;
			}
		}
		for (User u : users) {
			if (u.getUserName().equals(commentData.guestUserName)) {
				user = u;
				break;
			}
		}

		Comment comment = new Comment(1000, 0, 0, user.getID(), (apartment.getID()).intValue(),
				commentData.txtOfComment, commentData.ratingForApartment);

		comments.add(comment);

		apartmentsCTX.saveApartmentsJSON();
		commentsCTX.saveCommentJSON();
		return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();

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

	private UsersDAO getUsers() {
		UsersDAO users = (UsersDAO) ctx.getAttribute("users");
		if (users == null) {
			users = new UsersDAO();
			users.readUsers();
			ctx.setAttribute("users", users);

		}

		return users;
	}

	private ApartmentsDAO getApartments() {
		ApartmentsDAO apartments = (ApartmentsDAO) ctx.getAttribute("apartments");

		if (apartments == null) {
			apartments = new ApartmentsDAO();
			apartments.readApartments();

			ctx.setAttribute("apartments", apartments);
		}

		return apartments;

	}

	private CommentsDAO getComments() {
		CommentsDAO comments = (CommentsDAO) ctx.getAttribute("comments");

		if (comments == null) {
			comments = new CommentsDAO();
			comments.readComments();

			ctx.setAttribute("comments", comments);
		}

		return comments;

	}

}
//reservation/makeReservations