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

		
	
		
		
		/**
		 * If if paramater default value, then it's true and we do not look for that param in search
		 * but if he is not default value, then apartman with which we are checking must have same value.
		 * 
		 * And if have same value, then it's true.
		 * 
		 * If apartment have all true value he is appropriate one for search.
		 * 
		 * author: Vaxi
		 */
		// TODO: Mozda videti da ubacimo neki regex, da ne bude bas ovaj HC equals
		for (Apartment apartment : searchedApartments) {
			if(
					(searchParam.location.equals("") ? true : apartment.getLocation().equals(searchParam.location)) &&
					(searchParam.checkIn.equals("") ? true : apartment.getTimeForCheckIn().equals(searchParam.checkIn)) &&
					(searchParam.checkOut.equals("") ? true : apartment.getTimeForCheckOut().equals(searchParam.checkOut)) &&
					((searchParam.price == 0.0) ? true : ((double) apartment.getPricePerNight() == searchParam.price)) &&
					((searchParam.rooms == 0) ? true : ((double) apartment.getNumberOfRooms() == searchParam.rooms)) &&
					((searchParam.maxGuests == 0) ? true : ((double) apartment.getNumberOfGuests() == searchParam.maxGuests))
							
					) {
				System.out.println("DODAJEM");
				goodApartments.add(apartment);
			}else {
				System.out.println(searchParam.price);
				System.out.println(apartment.getPricePerNight());
				
				System.out.println("NISAM DODAO");
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
