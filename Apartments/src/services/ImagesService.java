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

import beans.ImageOfApartment;
import dao.ImagesDAO;


@Path("/images")
public class ImagesService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/getImages")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ImageOfApartment> getAllImages(){
		System.out.println("\n\n\t\t\t ucitavanje slika \n\n");
		return getImages().getValues();
	}
	
	
	
	private ImagesDAO getImages() {
		ImagesDAO images = (ImagesDAO) ctx.getAttribute("images");
		
		if(images == null) {
			images = new ImagesDAO();
			images.readImagesJSON();
			
			ctx.setAttribute("images", images);
		}
		
		return images;
		
	}
}
