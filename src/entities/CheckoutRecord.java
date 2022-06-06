package entities;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public final class CheckoutRecord implements Serializable {
	@Serial
	private static final long serialVersionUID = -8843171457151271994L;

	private final String id;

	private LibraryMember member;
	private List<CheckoutRecordEntry> entries;

	public CheckoutRecord(LibraryMember member) {
		this(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()), member);
	}

	public CheckoutRecord(String id, LibraryMember member) {
		this.id = id;
		this.member = member;
		this.entries = new ArrayList<>();
	}
	
	public String getId() {
		return this.id;
	}

	public LibraryMember getMember() {
		return this.member;
	}

	public void setMember(LibraryMember m) {
		this.member = m;
	}

	public List<CheckoutRecordEntry> getEntries(){
		return this.entries;
	}

	public void setEntries(List<CheckoutRecordEntry> entries) {
		this.entries = entries;
	}

	public void addEntry(CheckoutRecordEntry entry){
		if(entry != null)
			this.entries.add(entry);
	}
}
