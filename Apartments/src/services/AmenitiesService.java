package services;

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

import beans.User;
import dao.AmenitiesDAO;
import dto.AmenitiesItemAddDTO;
import dto.AmenitiesItemDTO;

@Path("/amenities")
public class AmenitiesService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getAmenities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJustAmenities() {
		
		if(isUserAdmin() || isUserHost()) {
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
					.entity(getAmenities().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	
	@POST
	@Path("/addItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(AmenitiesItemAddDTO newItem){
		
		if(isUserAdmin()) {
			
			AmenitiesDAO amenties = getAmenities();
			amenties.addItem(newItem);
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
					.entity(getAmenities().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/changeItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeItem(AmenitiesItemDTO updatedItem){
		
		
		if(isUserAdmin()) {
		
			AmenitiesDAO amenties = getAmenities();
			amenties.changeItem(updatedItem);
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
					.entity(getAmenities().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@DELETE
	@Path("/deleteItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(AmenitiesItemDTO deletedItem){
		
		if(isUserAdmin()) {
			
			AmenitiesDAO amenitiesDAO = getAmenities();
			amenitiesDAO.deleteItem(deletedItem.amenitiesID);
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGED")
					.entity(getAmenities().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	private AmenitiesDAO getAmenities() {

		AmenitiesDAO amenities = (AmenitiesDAO) ctx.getAttribute("amenities");

		if (amenities == null) {
			amenities = new AmenitiesDAO();
			amenities.readAmenities();
			ctx.setAttribute("amenities", amenities);
		}

		return amenities;
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
	private boolean isUserHost() {
		User user = (User) request.getSession().getAttribute("loginUser");
		
		if(user!= null) {
			if(user.getRole().equals("HOST")) {	
				return true;
			}
		}	
		return false;
	}
}
