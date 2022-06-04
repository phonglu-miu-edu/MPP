package entities;

import java.io.Serializable;

public final class LibraryMember extends Person implements Serializable {
	private static final long serialVersionUID = -2226197306790714013L;
	
	private String memberId;

	private CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
		this.memberId = memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
		return this.checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecord cr) {
		this.checkoutRecord = cr;
	}
	
	public String getMemberId() {
		return memberId;
	}
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", " + getTelephone() + " " + getAddress();
	}
}
