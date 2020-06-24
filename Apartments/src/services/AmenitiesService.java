package services;

import java.util.ArrayList;
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

import beans.AmenitiesItem;
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
	public ArrayList<AmenitiesItem> getJustAmenities() {
		System.out.println("\n\n\n\n Pozvano getovanje sadrzaja \n\n");
		return getAmenities().getValues();
	}
	
	
	@POST
	@Path("/addItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AmenitiesItem> addItem(AmenitiesItemAddDTO newItem){
		System.out.println("\n stigao je item " + newItem.newItemName );
		
		AmenitiesDAO amenties = getAmenities();
		
		amenties.addItem(newItem);
		
		return getAmenities().getValues();
	}
	
	@POST
	@Path("/changeItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AmenitiesItem> changeItem(AmenitiesItemDTO updatedItem){
		System.out.println("\n stigao je item " + updatedItem.name + " sa id-om: " + updatedItem.amenitiesID);
		
		AmenitiesDAO amenties = getAmenities();
		
		amenties.changeItem(updatedItem);
		
		return getAmenities().getValues();
	}
	
	@DELETE
	@Path("/deleteItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AmenitiesItem> deleteItem(AmenitiesItemDTO deletedItem){
		AmenitiesDAO amenitiesDAO = getAmenities();
		
		amenitiesDAO.deleteItem(deletedItem.amenitiesID);
		
		return getAmenities().getValues();
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
}
