package entities;

import java.io.Serializable;
import java.util.Date;


public final class CheckoutRecordEntry implements Serializable {
	private static final long serialVersionUID = 2790400064232857746L;

	private String isbnNumber;
	//private BookCopy bookCopy;
	private Date checkoutDate;
	private Date dueDate;
	
	CheckoutRecordEntry(String isbnNumber, Date dueDate) {
		this.isbnNumber = isbnNumber;
		this.checkoutDate = new Date();
		this.dueDate = dueDate;
	}
}
