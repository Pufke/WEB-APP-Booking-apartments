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

import beans.Apartment;
import beans.Comment;
import beans.User;
import dao.ApartmentsDAO;
import dao.CommentsDAO;
import dto.CommentDTOJSON;

@Path("/comments")
public class CommentsService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getComments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getJustComments() {
		System.out.println("\n\n\n\n Pozvano getovanje svih komentara \n\n");
		return getComments().getValues();
	}

	@GET
	@Path("/getMyComments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getJustMyComments() {

		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		System.out.println("\n\n\n DOBAVLJANJE SAMO APARTMANA DOMACINA: " + user.getUserName());

		CommentsDAO commentsDAO = getComments();

		return commentsDAO.getCommentsForHostApartments(user);
	}

	@POST
	@Path("/hideComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> hideComment(CommentDTOJSON newItem) {

		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		System.out.println("\n\n\n DOBAVLJANJE SAMO APARTMANA DOMACINA: " + user.getUserName());

		CommentsDAO commentsDAO = getComments();
		commentsDAO.hideComment(newItem.comment);
		
		return commentsDAO.getCommentsForHostApartments(user);
	}

	@POST
	@Path("/showComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> showComment(CommentDTOJSON newItem) {

		// With this, we get user who is logged in.
		// We are in UserService method login() tie user for session.
		// And now we can get him.
		User user = (User) request.getSession().getAttribute("loginUser");

		System.out.println("\n\n\n DOBAVLJANJE SAMO APARTMANA DOMACINA: " + user.getUserName());

		CommentsDAO commentsDAO = getComments();
		commentsDAO.showComment(newItem.comment);
		
		return commentsDAO.getCommentsForHostApartments(user);
	}
	private CommentsDAO getComments() {

		CommentsDAO commentsDAO = (CommentsDAO) ctx.getAttribute("comments");

		if (commentsDAO == null) {
			commentsDAO = new CommentsDAO();
			commentsDAO.readComments();
			ctx.setAttribute("comments", commentsDAO);
		}

		return commentsDAO;
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
}
