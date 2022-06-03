package models;

public final class CheckoutModel {
	private final String isbnNumber;
	private final String title;
	private final int checkoutDateLength;
	
	public CheckoutModel(String isbnNumber, String title, int checkoutDateLength) {
		this.isbnNumber = isbnNumber;
		this.title = title;
		this.checkoutDateLength = checkoutDateLength;
	}
	
	public String getIsbn() {
		return this.isbnNumber;
	}

	public String getTitle() {
		return this.title;
	}
	
	public int getCheckoutLength() {
		return this.checkoutDateLength;
	}
}
