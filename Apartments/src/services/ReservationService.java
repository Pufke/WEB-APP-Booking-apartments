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
			if (a.getID().equals((reservationData.apartmentIdentificator).intValue())) {
				// a.setReservedStatus("Rezervisano");
				apartment = a;
				break;
			}
		}
		for (User u : users) {
			if (u.getUserName().equals(reservationData.guestUserName)) {
				user = u;
				break;
			}
		}

		Integer uniqueID = 191919;
		ArrayList<Integer> reservedApartmentList = apartment.getListOfReservationsIDs();
		reservedApartmentList.add(uniqueID);

//		apartment.setListOfReservationsIDs(reservedApartmentList);
//		Reservation reservation = new Reservation(uniqueID,0 , apartment.getID(),
//				reservationData.dateOfReservation, reservationData.numberOfNights, 1600.0,
//				reservationData.messageForHost, user.getID(), reservationData.statusOfReservation);

//		for (Reservation r : reservations) {
//			if (r.getGuest().getUserName().equals(reservation.getGuest().getUserName())
//					&& r.getReservedApartment().getID() == reservation.getReservedApartment().getID()) {
//
//				return Response.status(Response.Status.EXPECTATION_FAILED).entity("Ovaj apartman je vec rezervisan")
//						.build();
//
//			}
//		}
//		reservations.add(reservation);

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

		Comment comment = new Comment(1000, 0, user.getID(), (apartment.getID()).intValue(),
				commentData.txtOfComment, commentData.ratingForApartment);

		comments.add(comment);

		apartmentsCTX.saveApartmentsJSON();
		commentsCTX.saveCommentJSON();
		return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();

	}

}
//reservation/makeReservations