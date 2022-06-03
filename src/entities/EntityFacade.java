package entities;

public class EntityFacade {
	private static EntityFacade instance = new EntityFacade();
	
	public static EntityFacade getInstance() {
		return instance;
	}
	
	public CheckoutRecord createCheckoutRecord(String memberId) {
		CheckoutRecord checkoutRecord = new CheckoutRecord(memberId);
		
		return checkoutRecord;
	}
}
