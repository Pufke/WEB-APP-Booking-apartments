package dao;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Comment;
import beans.User;

public class CommentsDAO {

	private ArrayList<Comment> comments;
	private String path;

	public CommentsDAO() {
		File podaciDir = new File(System.getProperty("catalina.base") + File.separator + "podaci");
		if (!podaciDir.exists()) {
			podaciDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "podaci" + File.separator + "comments.json";
		this.comments = new ArrayList<Comment>();

		// UNCOMMENT IF WANT TO PUT DUMMY DATA IN FILE
		addMockupData();
	}

	public void readComments() {
		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File(this.path);

		List<Comment> loadedComments = new ArrayList<Comment>();
		try {

			loadedComments = objectMapper.readValue(file, new TypeReference<List<Comment>>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n\n ucitavam preko object mapera \n\n");
		for (Comment a : loadedComments) {
			System.out.println("TXT KOMENTARA: " + a.getTxtOfComment());
			comments.add(a);
		}
		System.out.println("\n\n");
	}

	public void saveCommentJSON() {

		// Get all comments
		List<Comment> allComments = new ArrayList<Comment>();
		for (Comment a : getValues()) {
			allComments.add(a);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allComments);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Comment> getValues() {
		return comments;
	}

	public ArrayList<Comment> getCommentsForHostApartments(User user) {

		ArrayList<Comment> hostsApartments = new ArrayList<Comment>();

		for (Integer idOfApartments : user.getApartmentsForRentingHostIDs()) {
			if (getCommentById(idOfApartments) != null) {
				hostsApartments.add(getCommentById(idOfApartments));
			}
		}

		return hostsApartments;
	}

	public Comment getCommentById(Integer id) {
		for (Comment com : comments) {
			if (com.getID().equals(id)) {
				return com;
			}
		}
		return null;
	}

	public void hideComment(Comment comment) {

		for (Comment curComment : comments) {
			if (curComment.getID().equals(comment.getID())) {
				curComment.setIsAvailableToSee(0);
				saveCommentJSON();
				return;
			}
		}

	}

	public void showComment(Comment comment) {

		for (Comment curComment : comments) {
			if (curComment.getID().equals(comment.getID())) {
				curComment.setIsAvailableToSee(1);
				saveCommentJSON();
				return;
			}
		}

	}

	/**
	 * Method for adding dummy data to JSON file of comments
	 */
	@SuppressWarnings("unused")
	private void addMockupData() {

		// Make all comments
		List<Comment> allComments = new ArrayList<Comment>();
		allComments.add(new Comment(1, 0, 1, 10, 1, "Jako dobar i lep apartman", "10"));
		allComments.add(new Comment(2, 0, 1, 20, 2, "Gori apartman koliko je dobar", "10"));
		allComments.add(new Comment(3, 0, 0, 30, 3, "A nije lose", "7"));

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Write them to the file
			objectMapper.writeValue(new FileOutputStream(this.path), allComments);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
