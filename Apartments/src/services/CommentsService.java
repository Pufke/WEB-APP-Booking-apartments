package services;

import java.util.ArrayList;

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

import beans.Comment;
import beans.User;
import dao.CommentsDAO;
import dto.ApartmentCommentJsonDTO;
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
	public Response getJustComments() {
		
		if(isUserAdmin() || isUserHost()) {
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS GET")
					.entity(getComments().getValues())
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	@GET
	@Path("/getMyComments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJustMyComments() {

		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
			CommentsDAO commentsDAO = getComments();
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS GET")
					.entity(commentsDAO.getCommentsForHostApartments(user))
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	@POST
	@Path("/hideComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response hideComment(CommentDTOJSON newItem) {

		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
	
			CommentsDAO commentsDAO = getComments();
			commentsDAO.hideComment(newItem.comment);
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS HIDEN")
					.entity(commentsDAO.getCommentsForHostApartments(user))
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}

	@POST
	@Path("/getCommentsForApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsForApartment(ApartmentCommentJsonDTO apartmentCommentJsonDTO) {
		
		if(isUserGuest()) {
			ArrayList<Comment> commentsForApartment = new ArrayList<Comment>();
			
			CommentsDAO commentsDAO = getComments();
			ArrayList<Comment> allComments = commentsDAO.getValues();
			
			for (Comment c: allComments) {
				if(c.getCommentForApartmentID() == apartmentCommentJsonDTO.apartmentID) {
					commentsForApartment.add(c);
				}
			}
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS GET")
					.entity(commentsForApartment)
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
	}
	
	@POST
	@Path("/showComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response showComment(CommentDTOJSON newItem) {

		if(isUserHost()) {
			User user = (User) request.getSession().getAttribute("loginUser");
	
			CommentsDAO commentsDAO = getComments();
			commentsDAO.showComment(newItem.comment);
			
			return Response
					.status(Response.Status.ACCEPTED).entity("SUCCESS SHOW")
					.entity( commentsDAO.getCommentsForHostApartments(user))
					.build();
		}
		return Response.status(403).type("text/plain")
				.entity("You do not have permission to access!").build();
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
