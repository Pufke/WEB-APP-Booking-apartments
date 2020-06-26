package beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * Class which encapsulate dates range.
 */
public class DateRange {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fromDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date toDate;

	public DateRange() {
		
	}

	public DateRange(Date fromDate, Date toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	

}
