package entities;

public class EntityFacade {
	private static EntityFacade instance = new EntityFacade();
	
	public static EntityFacade getInstance() {
		return instance;
	}
	
	public CheckoutRecord createCheckoutRecord(LibraryMember member) {
		CheckoutRecord checkoutRecord = new CheckoutRecord(member);
		
		return checkoutRecord;
	}
}
