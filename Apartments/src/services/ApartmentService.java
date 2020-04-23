package services;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Apartment;
import dao.ApartmentsDAO;
import dto.ApartmentToAdd;
import beans.Reservation;
import beans.ReservationCart;

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
	@Path("/getJustApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getJustApartments() {
		return getApartments().getValues();
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String add(ApartmentToAdd p) {
		
		getReservationCart().addItem(getApartments().getApartment(p.id), p.count);
		System.out.println("Product " + getApartments().getApartment(p.id)
				+ " added with count: " + p.count);

		return "OK";
	}
	
	@GET
	@Path("/getJustRc")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reservation> getJustRc() {
		return getReservationCart().getItems();
	}

	@GET
	@Path("/getTotal")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTotal() {
		return "" + getReservationCart().getTotal();
	}

	@POST
	@Path("/clearRc")
	@Produces(MediaType.APPLICATION_JSON)
	public String clearRc() {
		getReservationCart().getItems().clear();
		return "OK";
	}

	/**
	 * Get apartments from application. If it is for first time, we tie apartments
	 * to application.
	 * 
	 * @return apartments
	 */
	private ApartmentsDAO getApartments() {
		ApartmentsDAO apartments = (ApartmentsDAO) ctx.getAttribute("apartments");
		if (apartments == null) {
			apartments = new ApartmentsDAO();
			ctx.setAttribute("apartments", apartments);
		}

		return apartments;
	}

	/**
	 * Get reservationCart from application. If it is for first time, we tie
	 * reservationCart to application.
	 * 
	 * @return reservationCart
	 */
	private ReservationCart getReservationCart() {
		ReservationCart rc = (ReservationCart) request.getSession().getAttribute("reservationCart");
		if (rc == null) {
			rc = new ReservationCart();
			request.getSession().setAttribute("reservationCart", rc);
		}
		return rc;
	}
}
