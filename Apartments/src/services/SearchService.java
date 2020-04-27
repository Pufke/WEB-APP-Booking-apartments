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
import dao.ApartmentsDAO;
import dto.SearchDTO;


@Path("/search")
public class SearchService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@POST
	@Path("/apartments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getSearchedApartments(SearchDTO searchParam){
		System.out.println("\n\n SEARCH APARTMENTS\n\n");
		
		ArrayList<Apartment> searchedApartments = getApartments().getValues();
		ArrayList<Apartment> goodApartments = new ArrayList<Apartment>();		// apartments which appropriate search
		
		
		// TODO: Mozda videti da ubacimo neki regex, da ne bude bas ovaj HC equals
		if(!searchParam.location.equals("")) {
			for (Apartment apartment : searchedApartments) {
				if(apartment.getLocation().equals(searchParam.location)) {
					goodApartments.add(apartment);
				}
			}
		}
		
		if(!searchParam.checkIn.equals("")) {
			for (Apartment apartment : searchedApartments) {
				
			}
		}
		
		if(!searchParam.checkOut.equals("")) {
			for (Apartment apartment : searchedApartments) {
				
			}
		}
		
		if(searchParam.price != 0.0) {
			for (Apartment apartment : searchedApartments) {
				if(apartment.getPricePerNight() == searchParam.price) {
					goodApartments.add(apartment);
				}
			}
		}
		
		if(searchParam.rooms != 0) {
			for (Apartment apartment : searchedApartments) {
				
			}
		}
		
		if(searchParam.maxGuests != 0) {
			for (Apartment apartment : searchedApartments) {
				
			}
		}
		
		return goodApartments;
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
	
}
