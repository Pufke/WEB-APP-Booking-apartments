package services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Comment;
import dao.CommentsDAO;


@Path("/comments")
public class CommentsService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/getComments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getJustAmenities() {
		System.out.println("\n\n\n\n Pozvano getovanje svih komentara \n\n");
		return getComments().getValues();
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
}
