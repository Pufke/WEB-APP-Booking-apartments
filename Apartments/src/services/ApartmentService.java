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

import beans.Address;
import beans.Apartment;
import beans.Comment;
import beans.Location;
import beans.User;

import dao.ApartmentsDAO;
import dao.CommentsDAO;
import dao.UsersDAO;

import dto.ApartmentChangeDTO;
import dto.ApartmentCommentJsonDTO;
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
	public Collection<Apartment> addItem(ApartmentDTOJSON newItem) {
		// With this, we get user who is loged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		ApartmentsDAO apartmentsDAO = getApartments();
		
		apartmentsDAO.addNewApartments(newItem, user.getID());

		// Add that apartment in list of hosts apartments
		UsersDAO allUsersDAO = getUsers();
		Integer idOfApartment = apartmentsDAO.getValues().size();
		allUsersDAO.addHostApartments(user, idOfApartment);

		return apartmentsDAO.getHostApartments(user);
	}

	
	
	@GET
	@Path("/getDummyApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment getDummyApartments() {

		Apartment retApartment = new Apartment();
		Address address = new Address("", "", "", "");
		retApartment.setLocation(new Location("0", "0", address ));
		
		return retApartment;
	}
	
	@GET
	@Path("/getMyApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustMyApartments() {

		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		System.out.println("\n\n\n DOBAVLJANJE SAMO APARTMANA DOMACINA: " + user.getUserName());

		ApartmentsDAO apartmentsDAO = getApartments();

		return apartmentsDAO.getHostApartments(user);
	}

	@POST
	@Path("/activateApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> activateApartment(ApartmentDTOJSON newItem) {

		System.out.println("\n\n\t\t AKTIVACIJA \n\n");
		
		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		// Update that apartment in list of all apartments
		ApartmentsDAO apartmentsDAO = getApartments();
		apartmentsDAO.activateApartment(newItem.addedApartment.getID());

		return apartmentsDAO.getHostApartments(user);

	}
	
	@POST
	@Path("/adminActivationApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> adminActivationApartment(ApartmentDTOJSON newItem) {

		System.out.println("\n\n\t\t AKTIVACIJA OD STRANE ADMINA \n\n");

		// Update that apartment in list of all apartments
		ApartmentsDAO apartmentsDAO = getApartments();
		apartmentsDAO.activateApartment(newItem.addedApartment.getID());

		return getApartments().getValues();

	}

	@POST
	@Path("/changeMyApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> changeMyApartment(ApartmentDTOJSON newItem) {

		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + newItem.addedApartment.getID() + " i cenom"
				+ newItem.addedApartment.getPricePerNight() + "\n\n");

		// Update that apartment in list of all apartments
		ApartmentsDAO apartmentsDAO = getApartments();
		apartmentsDAO.changeApartment(newItem.addedApartment);

		return apartmentsDAO.getHostApartments(user);
	}

	@GET
	@Path("/getApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustApartments() {
		System.out.println("CALLED GET JUST APARTMENTS");
		for (Apartment ap : getApartments().getValues()) {
			System.out.println("statusa apartman: " + ap.getStatus() + " i ID: " + ap.getID() + "\n");
		}
		return getApartments().getValues();
	}

	@POST
	@Path("/changeApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> changeApartment(ApartmentDTOJSON updatedApartment) {
		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.addedApartment.getID() + " i cenom"
				+ updatedApartment.addedApartment.getPricePerNight() + "\n\n");

		ApartmentsDAO apartments = getApartments();
		apartments.changeApartment(updatedApartment.addedApartment);

		return getApartments().getValues();
	}

	@DELETE
	@Path("/deleteHostApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> deleteHostApartment(ApartmentsDTO updatedApartment) {
		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.identificator + "\n\n");

		ApartmentsDAO apartmentsDAO = getApartments();
		apartmentsDAO.deleteApartment(updatedApartment.identificator);

		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");
		UsersDAO allUsersDAO = getUsers();
		allUsersDAO.deleteHostApartment(user.getID(), updatedApartment.identificator);
		

		return apartmentsDAO.getHostApartments(user);
	}

	@DELETE
	@Path("/deleteApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> deleteApartment(ApartmentsDTO updatedApartment) {
		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.identificator + "\n\n");

		ApartmentsDAO apartments = getApartments();
		apartments.deleteApartment(updatedApartment.identificator);
		
		UsersDAO allUsersDAO = getUsers();
		allUsersDAO.deleteHostApartment(updatedApartment.hostID, updatedApartment.identificator);

		return getApartments().getValues();
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