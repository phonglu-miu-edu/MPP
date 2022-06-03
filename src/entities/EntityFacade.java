package entities;

public class EntityFacade {
	private static EntityFacade instance = new EntityFacade();
	
	public static EntityFacade getInstance() {
		return instance;
	}
	
	public CheckoutRecord createCheckoutRecord(int memberID) {
		CheckoutRecord checkoutRecord = new CheckoutRecord(memberID);
		
		return checkoutRecord;
	}
}
