package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.AmenitiesItem;
import beans.Apartment;
import beans.User;
import dao.AmenitiesDAO;
import dao.ApartmentsDAO;
import dao.UsersDAO;
import dto.AmenitiesItemAddDTO;
import dto.ApartmentChangeDTO;
import dto.ApartmentDTOJSON;
import dto.ApartmentsDTO;

//apartments/getApartments
@Path("/apartments")
public class ApartmentService {

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
	@Path("/addNewApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> addItem(ApartmentDTOJSON newItem){
		System.out.println("\n stigao je NOVI APARTMAN sa statusom: " + newItem.addedApartment.getStatus() );
		
		ApartmentsDAO apartmentsDAO = getApartments();
		apartmentsDAO.addNewApartments(newItem);
		
		// With this, we get user who is loged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");
		UsersDAO allUsersDAO = getUsers();

		// Add that apartment in list of hosts apartments
		allUsersDAO.addHostApartments(user, newItem);
		
		return user.getApartmentsForRentingHOST();
	}
	
	@GET
	@Path("/getMyApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustMyApartments() {

		// With this, we get user who is loged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		System.out.println("\n\n\n DOBAVLJANJE SAMO APARTMANA DOMACINA: " + user.getUserName());
		// TODO: Ovde se menja ako budemo presli samo na ID-eve.

		return user.getApartmentsForRentingHOST();
	}

	@POST
	@Path("/activateApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> activateApartment(ApartmentDTOJSON newItem){
		
		long idOfApartmentForActivation = newItem.addedApartment.getIdentificator();
		// With this, we get user who is loged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");
		UsersDAO allUsersDAO = getUsers();
		// Update that apartment in list of hosts apartments
		allUsersDAO.activateApartmentOfHost(user, idOfApartmentForActivation);
		
		
		// Update that apartment in list of all apartments
		ApartmentsDAO apartments = getApartments();
		apartments.activateApartment(idOfApartmentForActivation);
		
		return user.getApartmentsForRentingHOST();
		
	}
	
	@POST
	@Path("/changeMyApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> changeMyApartment(ApartmentChangeDTO updatedApartment) {

		// With this, we get user who is loged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.identificator + " i cenom"
				+ updatedApartment.pricePerNight + "\n\n");

		// Update that apartment in list of all apartments
		ApartmentsDAO apartments = getApartments();
		apartments.changeApartment(updatedApartment);

		UsersDAO allUsersDAO = getUsers();

		// Update that apartment in list of hosts apartments
		allUsersDAO.changeHostApartments(user, updatedApartment);

		return user.getApartmentsForRentingHOST();
	}

	@GET
	@Path("/getApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustApartments() {
		System.out.println("CALLED GET JUST APARTMENTS");
		return getApartments().getValues();
	}

	@POST
	@Path("/changeApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> changeApartment(ApartmentChangeDTO updatedApartment) {
		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.identificator + " i cenom"
				+ updatedApartment.pricePerNight + "\n\n");

		ApartmentsDAO apartments = getApartments();

		apartments.changeApartment(updatedApartment);

		return getApartments().getValues();
	}

	@DELETE
	@Path("/deleteApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> deleteApartment(ApartmentsDTO updatedApartment) {
		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.identificator + "\n\n");

		ApartmentsDAO apartments = getApartments();

		apartments.deleteApartment(updatedApartment.identificator);

		return getApartments().getValues();
	}

	@POST
	@Path("/makeReseervation")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveApartment(ApartmentsDTO updateApartment) {
		ApartmentsDAO apartments = getApartments();
		if (apartments.changeApartment(updateApartment)) {
			System.out.println(updateApartment.identificator);
			return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("ERROR DURING CHANGES").build();
		}

	}

	@POST
	@Path("/deleteReservation")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteReservation(ApartmentsDTO updateApartment) {
		ApartmentsDAO apartments = getApartments();
		if (apartments.deleteReservation(updateApartment)) {
			System.out.println(updateApartment.identificator);
			return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("ERROR DURING CHANGES").build();
		}

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

	private UsersDAO getUsers() {
		UsersDAO users = (UsersDAO) ctx.getAttribute("users");
		if (users == null) {
			users = new UsersDAO();
			users.readUsers();
			ctx.setAttribute("users", users);

		}

		return users;
	}

}