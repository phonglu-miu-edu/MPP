package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public final class CheckoutRecord implements Serializable {
	private static final long serialVersionUID = -8843171457151271994L;

	private int id;
	private int memberId;
	private List<CheckoutRecordEntry> entries;
	
	CheckoutRecord(int memberId) {
		this.memberId = memberId;
		this.entries = new ArrayList<CheckoutRecordEntry>();
	}
	
	public void addBook(String isbnNumber, Date dueDate) {
		CheckoutRecordEntry entry = new CheckoutRecordEntry(isbnNumber, dueDate);		
		this.entries.add(entry);
	}
	
	public int getId() {
		return this.id;
	}
}
