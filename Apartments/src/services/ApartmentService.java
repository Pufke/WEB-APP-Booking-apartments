package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

import beans.Address;
import beans.Apartment;
import beans.ImageOfApartment;
import beans.Location;
import beans.User;

import dao.ApartmentsDAO;
import dao.ImagesDAO;
import dao.UsersDAO;

import dto.ApartmentDTOJSON;
import dto.ApartmentsDTO;
import dto.FreeDatesDTO;

@Path("/apartments")
public class ApartmentService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@POST
	@Path("/addNewApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(ApartmentDTOJSON newItem) {
		
		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
			
			/*
			 * Add image to the images storage.
			 * 
			 * After that we need to set ID of image in apartment attribute imagesPath
			 * from front-end we get imagesPath like Base64 format, and we add that image
			 * to the storage, after that imagesPath in apartment became only ID of that image
			 * for faster and easier use.
			 *  
			 */
			ImagesDAO imagesDAO = getImages();
			ImageOfApartment addedImage = imagesDAO.addNewImage(newItem.addedApartment.getImagesPath());
			newItem.addedApartment.setImagesPath(Integer.toString(addedImage.getID())); 
			
			// Add apartment to the apartments storage
			ApartmentsDAO apartmentsDAO = getApartments();
			apartmentsDAO.addNewApartments(newItem, user.getID());
			
			// Add apartment id to the host list of apartments
			UsersDAO allUsersDAO = getUsers();
			Integer idOfApartment = apartmentsDAO.getValues().size();
			allUsersDAO.addHostApartments(user, idOfApartment);
	
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE")
					.entity(apartmentsDAO.getHostApartments(user))
					.build();
		
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
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
	public Response getJustMyApartments() {
		
		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
			ApartmentsDAO apartmentsDAO = getApartments();
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE")
					.entity(apartmentsDAO.getHostApartments(user))
					.build();
		}
	
		return Response.status(403).type("text/plain")
                .entity("You do not have permission to access!").build();
	}

	@POST
	@Path("/activateApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateApartment(ApartmentDTOJSON newItem) {

		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
			ApartmentsDAO apartmentsDAO = getApartments();
			apartmentsDAO.activateApartment(newItem.addedApartment.getID());
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS ACTIVATED")
					.entity(apartmentsDAO.getHostApartments(user))
					.build();
		}
		
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();

	}
	
	@POST
	@Path("/adminActivationApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adminActivationApartment(ApartmentDTOJSON newItem) {

		if(isUserAdmin()) {	
			ApartmentsDAO apartmentsDAO = getApartments();
			apartmentsDAO.activateApartment(newItem.addedApartment.getID());
	
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS ACTIVATED")
					.entity(getApartments().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
		
	}

	@POST
	@Path("/changeMyApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeMyApartment(ApartmentDTOJSON newItem) {

		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
		
			/*
			 * Add image to the images storage.
			 * 
			 * After that we need to set ID of image in apartment attribute imagesPath
			 * from front-end we get imagesPath like Base64 format, and we add that image
			 * to the storage, after that imagesPath in apartment became only ID of that image
			 * for faster and easier use.
			 *  
			 */
			ImagesDAO imagesDAO = getImages();
			ImageOfApartment addedImage = imagesDAO.addNewImage(newItem.addedApartment.getImagesPath());
			newItem.addedApartment.setImagesPath(Integer.toString(addedImage.getID())); 
			
			
			ApartmentsDAO apartmentsDAO = getApartments();
			apartmentsDAO.changeApartment(newItem.addedApartment, newItem.startDateForReservation, newItem.endDateForReservation);
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
					.entity(apartmentsDAO.getHostApartments(user))
					.build();
		}	
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	
	}

	@GET
	@Path("/getApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustApartments() {
		System.out.println("CALLED GET JUST APARTMENTS");
	
		return getApartments().getValues();
	}
	
	@POST
	@Path("/getApartmentFreeDates")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmentFreeDates(FreeDatesDTO freedatesDTO) {
		
		if(isUserGuest()) {
			ArrayList<java.sql.Date> lsita = new ArrayList<java.sql.Date>();
			for (Apartment ap : getApartments().getValues()) {
				if(ap.getID() == freedatesDTO.apartmentID) {
					for(Date d : ap.getAvailableDates()){
						java.sql.Date sd = new java.sql.Date(d.getTime());
						lsita.add(sd);
					}
				}	
			}
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS")
					.entity(lsita)
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	@POST
	@Path("/changeApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeApartment(ApartmentDTOJSON updatedApartment) {
		
		if(isUserAdmin() || isUserHost()) {
			ApartmentsDAO apartments = getApartments();
			apartments.changeApartment(updatedApartment.addedApartment, updatedApartment.startDateForReservation, updatedApartment.endDateForReservation);
	
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
					.entity(getApartments().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	@DELETE
	@Path("/deleteHostApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteHostApartment(ApartmentsDTO updatedApartment) {
		
		if(isUserHost()) {
				ApartmentsDAO apartmentsDAO = getApartments();
				apartmentsDAO.deleteApartment(updatedApartment.identificator);
		
				User user = (User) request.getSession().getAttribute("loginUser");
				UsersDAO allUsersDAO = getUsers();
				allUsersDAO.deleteHostApartment(user.getID(), updatedApartment.identificator);
				return Response
						.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
						.entity(apartmentsDAO.getHostApartments(user))
						.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
		
	}

	@DELETE
	@Path("/deleteApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteApartment(ApartmentsDTO updatedApartment) {
		if(isUserAdmin()) {
				ApartmentsDAO apartments = getApartments();
				apartments.deleteApartment(updatedApartment.identificator);
				
				UsersDAO allUsersDAO = getUsers();
				allUsersDAO.deleteHostApartment(updatedApartment.hostID, updatedApartment.identificator);
		
				return Response
						.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
						.entity(getApartments().getValues())
						.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	
	private ImagesDAO getImages() {
		ImagesDAO images = (ImagesDAO) ctx.getAttribute("images");
		
		if(images == null) {
			images = new ImagesDAO();
			images.readImagesJSON();
			
			ctx.setAttribute("images", images);
		}
		
		return images;
		
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