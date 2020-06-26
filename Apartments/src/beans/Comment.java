package beans;

public class Comment {
	
	private Integer ID;
	private Integer logicalDeleted;						// 1 - deleted, 0 - not deleted
	private Integer isAvailableToSee;					// 1 - guest can see, 0 - guest can not see
	
	private Integer guestAuthorOfCommentID; 			// Gost koji je ostavio komentar
	private Integer commentForApartmentID; 				// Apartman na koji se odnosi komentar
	private String txtOfComment;
	private String ratingForApartment;
	
	
	public Comment() {
		
	}

	public Comment(Integer iD, Integer logicalDeleted, Integer isAvailableToSee, Integer guestAuthorOfCommentID,
			Integer commentForApartmentID, String txtOfComment, String ratingForApartment) {
		super();
		ID = iD;
		this.logicalDeleted = logicalDeleted;
		this.isAvailableToSee = isAvailableToSee;
		this.guestAuthorOfCommentID = guestAuthorOfCommentID;
		this.commentForApartmentID = commentForApartmentID;
		this.txtOfComment = txtOfComment;
		this.ratingForApartment = ratingForApartment;
	}

	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public Integer getLogicalDeleted() {
		return logicalDeleted;
	}


	public void setLogicalDeleted(Integer logicalDeleted) {
		this.logicalDeleted = logicalDeleted;
	}


	public Integer getIsAvailableToSee() {
		return isAvailableToSee;
	}


	public void setIsAvailableToSee(Integer isAvailableToSee) {
		this.isAvailableToSee = isAvailableToSee;
	}


	public Integer getGuestAuthorOfCommentID() {
		return guestAuthorOfCommentID;
	}


	public void setGuestAuthorOfCommentID(Integer guestAuthorOfCommentID) {
		this.guestAuthorOfCommentID = guestAuthorOfCommentID;
	}


	public Integer getCommentForApartmentID() {
		return commentForApartmentID;
	}


	public void setCommentForApartmentID(Integer commentForApartmentID) {
		this.commentForApartmentID = commentForApartmentID;
	}


	public String getTxtOfComment() {
		return txtOfComment;
	}


	public void setTxtOfComment(String txtOfComment) {
		this.txtOfComment = txtOfComment;
	}


	public String getRatingForApartment() {
		return ratingForApartment;
	}


	public void setRatingForApartment(String ratingForApartment) {
		this.ratingForApartment = ratingForApartment;
	}
	
	
}
