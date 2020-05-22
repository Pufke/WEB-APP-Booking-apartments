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
import javax.ws.rs.core.Response;

import beans.Apartment;
import dao.ApartmentsDAO;
import dto.ApartmentChangeDTO;
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
	
	@GET
	@Path("/getApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustApartments(){
		System.out.println("CALLED GET JUST APARTMENTS");
		return getApartments().getValues();
	}
	
	@POST
	@Path("/changeApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> changeApartment(ApartmentChangeDTO updatedApartment){
		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.identificator + " i cenom" + updatedApartment.pricePerNight + "\n\n");
		
		ApartmentsDAO apartments = getApartments();
		
		apartments.changeApartment(updatedApartment);
		
		return getApartments().getValues();
	}
	
	@DELETE
	@Path("/deleteApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> deleteApartment(ApartmentsDTO updatedApartment){
		System.out.println("\n\n\t\tSTIGAO JE APARTMAN SA ID-om: " + updatedApartment.identificator + "\n\n");
		
		ApartmentsDAO apartments = getApartments();
		
		apartments.deleteApartment(updatedApartment.identificator);
		
		return getApartments().getValues();
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
	@Path("/makeReseervation")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveApartment(ApartmentsDTO updateApartment) {
		ApartmentsDAO apartments = getApartments();
		if(apartments.changeApartment(updateApartment)) {
			System.out.println(updateApartment.identificator);
			return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();
					
		}else {
			return Response.status(Response.Status.BAD_REQUEST).entity("ERROR DURING CHANGES").build();
		}

	}
	
	@POST
	@Path("/deleteReservation")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteReservation(ApartmentsDTO updateApartment) {
		ApartmentsDAO apartments = getApartments();
		if(apartments.deleteReservation(updateApartment)) {
			System.out.println(updateApartment.identificator);
			return Response.status(Response.Status.ACCEPTED).entity("SUCCESS CHANGE").build();
					
		}else {
			return Response.status(Response.Status.BAD_REQUEST).entity("ERROR DURING CHANGES").build();
		}

	}
	
}