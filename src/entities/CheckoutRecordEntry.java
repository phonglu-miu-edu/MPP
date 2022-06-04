package entities;

import java.io.Serializable;
import java.time.LocalDate;


public final class CheckoutRecordEntry implements Serializable {
	private static final long serialVersionUID = 2790400064232857746L;

	private BookCopy bookCopy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	
	public CheckoutRecordEntry(BookCopy bc, LocalDate dueDate) {
		this.bookCopy = bc;
		this.checkoutDate = LocalDate.now();
		this.dueDate = dueDate;
	}
}
