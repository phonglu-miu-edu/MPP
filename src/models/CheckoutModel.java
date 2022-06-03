package models;

public final class CheckoutModel {
	private String isbnNumber;
	private int checkoutDateLength;
	
	public CheckoutModel(String isbnNumber, int checkoutDateLength) {
		this.isbnNumber = isbnNumber;
		this.checkoutDateLength = checkoutDateLength;
	}
	
	public String getIsbnNumber() {
		return this.isbnNumber;
	}
	
	public int getCheckoutLength() {
		return this.checkoutDateLength;
	}
}
