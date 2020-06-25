package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Apartment;
import beans.Reservation;
import dao.ApartmentsDAO;
import dao.ReservationDAO;
import dto.ReservationAdminSearchDTO;
import dto.SearchDTO;


@Path("/search")
public class SearchService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	// TODO :RESITI OVO
//	@POST
//	@Path("/reservations")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Collection<Reservation> getSearchedReservations(ReservationAdminSearchDTO searchData){
//		System.out.println("\n\n\t\ttrazi se: " + searchData.usernameOfGuest);
//		
//		ArrayList<Reservation> allReservations = getReservations().getValues();
//		ArrayList<Reservation> searchedReservations = new ArrayList<Reservation>();
//		
//		for (Reservation reservation : allReservations) {
//			if(
//					(searchData.usernameOfGuest.equals("") ? true : searchData.usernameOfGuest.equals(reservation.getGuest().getUserName()))
//					) {
//				System.out.println("DODAJEM");
//				searchedReservations.add(reservation);
//			}{
//				System.out.println("nisam dodao jer je username ovog korisnika: "+ reservation.getGuest().getUserName());
//			}
//				
//		}
//		
//		return searchedReservations;
//	}
	
	// TODO: RESITI OVO
//	@POST
//	@Path("/apartments")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Collection<Apartment> getSearchedApartments(SearchDTO searchParam){
//		System.out.println("\n\n SEARCH APARTMENTS\n\n");
//		
//		ArrayList<Apartment> allApartments = getApartments().getValues();
//		ArrayList<Apartment> searchedApartments = new ArrayList<Apartment>();		// apartments which appropriate search
//
//		
//	
//		
//		
//		/**
//		 * If if paramater default value, then it's true and we do not look for that param in search
//		 * but if he is not default value, then apartman with which we are checking must have same value.
//		 * 
//		 * And if have same value, then it's true.
//		 * 
//		 * If apartment have all true value he is appropriate one for search.
//		 * 
//		 * author: Vaxi
//		 */
//		// TODO: Mozda videti da ubacimo neki regex, da ne bude bas ovaj HC equals
//		for (Apartment apartment : allApartments) {
//			if(
//					(searchParam.location.equals("") ? true : apartment.getLocation().equals(searchParam.location)) &&
//					(searchParam.checkIn.equals("") ? true : apartment.getTimeForCheckIn().equals(searchParam.checkIn)) &&
//					(searchParam.checkOut.equals("") ? true : apartment.getTimeForCheckOut().equals(searchParam.checkOut)) &&
//					((searchParam.price == 0.0) ? true : ((double) apartment.getPricePerNight() == searchParam.price)) &&
//					((searchParam.rooms == 0) ? true : ((double) apartment.getNumberOfRooms() == searchParam.rooms)) &&
//					((searchParam.maxGuests == 0) ? true : ((double) apartment.getNumberOfGuests() == searchParam.maxGuests))
//							
//					) {
//				System.out.println("DODAJEM");
//				searchedApartments.add(apartment);
//			}else {
//				System.out.println(searchParam.price);
//				System.out.println(apartment.getPricePerNight());
//				
//				System.out.println("NISAM DODAO");
//			}
//			
//		}
//		
//	
//		return searchedApartments;
//	}
//	
//	@POST
//	@Path("/SearchReservations")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Collection<Reservation> getSearchedReservations(SearchDTO searchParam){
//		System.out.println("\n\n SEARCH RESERVATIONS\n\n");
//		
//		ArrayList<Reservation> allReservations = getReservations().getValues();
//		ArrayList<Reservation> searchedReservations = new ArrayList<Reservation>();		// apartments which appropriate search
//		
//		
//		for (Reservation reservation : allReservations) {
//			if(
//					(searchParam.location.equals("") ? true : reservation.getReservedApartment().getLocation().equals(searchParam.location)) &&
//					(searchParam.checkIn.equals("") ? true :reservation.getReservedApartment().getTimeForCheckIn().equals(searchParam.checkIn)) &&
//					(searchParam.checkOut.equals("") ? true : reservation.getReservedApartment().getTimeForCheckOut().equals(searchParam.checkOut)) &&
//					((searchParam.price == 0.0) ? true : ((double) reservation.getReservedApartment().getPricePerNight() == searchParam.price)) &&
//					((searchParam.rooms == 0) ? true : ((double) reservation.getReservedApartment().getNumberOfRooms() == searchParam.rooms)) &&
//					((searchParam.maxGuests == 0) ? true : ((double) reservation.getReservedApartment().getNumberOfGuests() == searchParam.maxGuests))
//							
//					) {
//				System.out.println("DODAJEM");
//				searchedReservations.add(reservation);
//			}else {
//				System.out.println(searchParam.price);
//				System.out.println("NISAM DODAO");
//			}
//			
//		}
//		
//	
//		return searchedReservations;
//	}
	
	private ReservationDAO getReservations() {
		ReservationDAO reservations = (ReservationDAO) ctx.getAttribute("reservations");
		
		if(reservations == null) {
			reservations = new ReservationDAO();
			reservations.readReservations();
			ctx.setAttribute("reservations", reservations);
		}
		
		return reservations;
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
