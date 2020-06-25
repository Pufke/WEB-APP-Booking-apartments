package beans;

import java.time.LocalDate;

/**
 * Class which encapsulate dates range.
 */
public class DateRange {
	
	private LocalDate fromDate;
	private LocalDate toDate;

	public DateRange(LocalDate fromDate, LocalDate toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

}
