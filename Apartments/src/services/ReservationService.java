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
import beans.Holliday;
import beans.Reservation;
import beans.User;
import dao.ApartmentsDAO;
import dao.CommentsDAO;
import dao.HolidaysDAO;
import dao.ReservationDAO;
import dao.UsersDAO;
import dto.CommentDTO;
import dto.DeleteReservationDTO;
import dto.HollidaysItemAddDTO;
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
	public Response getJustApartments() {
		
		if(isUserAdmin() || isUserGuest()) {
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS GET")
					.entity(getReservations().getValues())
					.build();
		}	
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@GET
	@Path("/getHollidays")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHolidays() {
		
		ArrayList<java.sql.Date> listaDatuma = new ArrayList<java.sql.Date>();
		
		
		
		for (Holliday h : getHollidays().getValues()) {
			java.sql.Date sd = new java.sql.Date(h.getHoliday().getTime());
			listaDatuma.add(sd);
		}
		
		if(isUserAdmin()) {
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS GET")
					.entity(listaDatuma)
					.build();
		}	
		
		
		
		
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}


	@POST
	@Path("/addHolliday")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addHolliday(HollidaysItemAddDTO newItem){
		
		if(isUserAdmin()) {
			
			HolidaysDAO hollidays = getHollidays();
			hollidays.addItem(newItem);
			
			ArrayList<java.sql.Date> listaDatuma = new ArrayList<java.sql.Date>();
			
			
			
			for (Holliday h : getHollidays().getValues()) {
				java.sql.Date sd = new java.sql.Date(h.getHoliday().getTime());
				listaDatuma.add(sd);
			}
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
					.entity(listaDatuma)
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/acceptReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptReservation(ReservationDTOJSON param){
		
		if(isUserHost()) {
			ReservationDAO reservationsDAO = getReservations();
			reservationsDAO.acceptReservation(param.reservation);
			
			UsersDAO usersDAO = getUsers();
			User user = (User) request.getSession().getAttribute("loginUser");
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS ACCEPTED")
					.entity(usersDAO.getReservationsOfHost(user, reservationsDAO.getValues()))
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	@POST
	@Path("/declineReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response declineReservation(ReservationDTOJSON param){
		
		if(isUserHost()) {
			ReservationDAO reservationsDAO = getReservations();
			reservationsDAO.declineReservation(param.reservation);
			
			UsersDAO usersDAO = getUsers();
			User user = (User) request.getSession().getAttribute("loginUser");
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS DECLINED")
					.entity(usersDAO.getReservationsOfHost(user, reservationsDAO.getValues()))
					.build();
			
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/endReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response endReservation(ReservationDTOJSON param){
		if(isUserHost()) {
			ReservationDAO reservationsDAO = getReservations();
			reservationsDAO.endReservation(param.reservation);
			
			UsersDAO usersDAO = getUsers();
	
			User user = (User) request.getSession().getAttribute("loginUser");
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS ENDED")
					.entity(usersDAO.getReservationsOfHost(user, reservationsDAO.getValues()))
					.build();
			
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	
	@POST
	@Path("/makeReservations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeReservations(ReservationDTO reservationData) {
		
		if(isUserGuest()) {
			ReservationDAO reservationsCTX = getReservations();
			ApartmentsDAO apartmentsCTX = getApartments();
			HolidaysDAO holidaysCTX = getHollidays();
	
			ArrayList<Apartment> apartments = apartmentsCTX.getValues();
			ArrayList<Reservation> reservations = reservationsCTX.getValues();
			ArrayList<Holliday> holidays = holidaysCTX.getValues();
	
			Apartment apartment = new Apartment();
	
			for (Apartment a : apartments) {
				if (a.getID().equals((reservationData.apartmentIdentificator).intValue())) {	
					apartment = a;
					break;
				}
			}
			
			//Convert java.util.Date to java.Sql.date, reason why i do this i beacuse frontend doesnt format well java.util.Date format 
			java.sql.Date sd = new java.sql.Date(reservationData.dateOfReservation.getTime());
	
			ArrayList<Date> listaSlobodnihDatuma = apartment.getAvailableDates();
			ArrayList<Date> listaPraznika = new ArrayList<Date>();
			
			for (Holliday h : holidays) {
				listaPraznika.add(h.getHoliday());
			}
			
			double totalPrice = 0;
			
			for(int i = 0; i < reservationData.numberOfNights; i++) {
			
					if(isContains(listaSlobodnihDatuma, reservationData.dateOfReservation)){
						listaSlobodnihDatuma = removeDateFromList(listaSlobodnihDatuma, reservationData.dateOfReservation);
						
						if(isContains(listaPraznika, reservationData.dateOfReservation)) {
							//Ako je praznik uvecaj cenu za 5%
							System.out.println("PRAZNIKKK ");
							totalPrice = totalPrice +  ( apartment.getPricePerNight() * 1.05);
						}else if(isHoliday(reservationData.dateOfReservation)) {
						
							totalPrice = totalPrice +  ( apartment.getPricePerNight() * 0.9);
						}
						else {
							totalPrice = totalPrice  +  apartment.getPricePerNight();
						}
						
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(reservationData.dateOfReservation);
						calendar.add(Calendar.DATE, 1);
						reservationData.dateOfReservation =  calendar.getTime();
							
					}else {
						return Response.status(Response.Status.EXPECTATION_FAILED).entity("APARTMENT IS NOT FREE").build();
					}
				
			}
			
			//double totalPrice =  reservationData.numberOfNights * apartment.getPricePerNight();
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

			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS RESERVED APARTMENT")				
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	private boolean isHoliday(Date dateOfReservation) {
		
		if(dateOfReservation.toString().substring(0, 3).equals("Sun") || dateOfReservation.toString().substring(0, 3).equals("Fri") || dateOfReservation.toString().substring(0, 3).equals("Sat")) {
			return true;
		}
		return false;
	}

	@POST
	@Path("/deleteReservations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReservations(DeleteReservationDTO reservationData) {
		
		if(isUserGuest()) {
			
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
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS RESERVED APARTMENT")				
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	@POST
	@Path("/cancelReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelReservation(DeleteReservationDTO reservationData) {
		
		if(isUserGuest()) {
			
			ReservationDAO reservationsCTX = getReservations();
			ArrayList<Reservation> reservations = reservationsCTX.getValues();
	
			Reservation reservation = new Reservation();
			for (Reservation r : reservations) {
				if (r.getID().equals(Integer.parseInt(reservationData.reservationID))) {
					reservation = r;
					break;
				}
			}

			reservationsCTX.cancelReservation(reservation);
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS RESERVED APARTMENT")				
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/makeComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeComment(CommentDTO commentData) {
	
		if(isUserGuest()) {
			
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
	
			Integer CommentUniqueID = comments.size() + 1;
			
			Comment comment = new Comment(CommentUniqueID, 0, 0, user.getID(), apartment.getID(),
					commentData.txtOfComment, commentData.ratingForApartment);
	
			comments.add(comment);
	
			apartmentsCTX.saveApartmentsJSON();
			commentsCTX.saveCommentJSON();
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS COMMENTED")				
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();

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
	
	
	private HolidaysDAO getHollidays() {
		
		HolidaysDAO hollidays = (HolidaysDAO) ctx.getAttribute("hollidays");

		if (hollidays == null) {
			hollidays = new HolidaysDAO();
			hollidays.readHolidays();

			ctx.setAttribute("hollidays", hollidays);
		}

		return hollidays;

	}


	// I made this two functions beacuse i have problem with comparing and removing dates after deserialization from JSON, date time is not same
	private boolean isContains(ArrayList<Date> listaDatuma, Date datum) {
		for(Date d : listaDatuma) {
	
			if(d.toString().substring(0, 10).equals(datum.toString().substring(0, 10))) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Date> removeDateFromList(ArrayList<Date> listaDatuma, Date datum) {
		Date dateForDelete = null;
		for(Date d : listaDatuma) {
			if(d.toString().substring(0, 10).equals(datum.toString().substring(0, 10))) {
				dateForDelete = d;
				
			}
		}
		listaDatuma.remove(dateForDelete);
		return listaDatuma;
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
	