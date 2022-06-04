package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public final class CheckoutRecord implements Serializable {
	private static final long serialVersionUID = -8843171457151271994L;

	private String id;
	private String memberId;
	private List<CheckoutRecordEntry> entries;
	
	CheckoutRecord(String memberId) {
		this.memberId = memberId;
		this.entries = new ArrayList<CheckoutRecordEntry>();
	}
	
	public void addBook(String isbnNumber, Date dueDate) {
		CheckoutRecordEntry entry = new CheckoutRecordEntry(isbnNumber, dueDate);		
		this.entries.add(entry);
	}
	
	public String getId() {
		return this.id;
	}

	public String getMemberId() {
		return this.memberId;
	}
}
