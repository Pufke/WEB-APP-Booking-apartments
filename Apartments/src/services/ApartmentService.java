package services;


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

import beans.Apartment;
import beans.User;

import dao.ApartmentsDAO;
import dao.UsersDAO;

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
		Integer uniqID = apartmentsDAO.getValues().size() + 1;
		allUsersDAO.addHostApartments(user, uniqID);
		
		
		
		return apartmentsDAO.getHostApartments(user);
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

		ApartmentsDAO apartmentsDAO = getApartments();


		return apartmentsDAO.getHostApartments(user);
	}

	@POST
	@Path("/activateApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> activateApartment(ApartmentDTOJSON newItem){
		
		long idOfApartmentForActivation = newItem.addedApartment.getID();
		// With this, we get user who is loged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");		
		
		// Update that apartment in list of all apartments
		ApartmentsDAO apartmentsDAO = getApartments();
		apartmentsDAO.activateApartment((int) idOfApartmentForActivation);
		
		return apartmentsDAO.getHostApartments(user);
		
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
		ApartmentsDAO apartmentsDAO = getApartments();
		apartmentsDAO.changeApartment(updatedApartment);


		return apartmentsDAO.getHostApartments(user);
	}

	@GET
	@Path("/getApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustApartments() {
		System.out.println("CALLED GET JUST APARTMENTS");
		for(Apartment ap : getApartments().getValues()) {
			System.out.println("statusa apartman: " + ap.getStatus() + " i ID: " + ap.getID() + "\n");
		}
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