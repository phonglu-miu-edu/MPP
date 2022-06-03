package business;

import java.util.List;

import entities.CheckoutRecord;
import models.CheckoutModel;
import models.LoginException;
import models.ResponseModel;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public ResponseModel<CheckoutRecord> checkout(String memberId, List<CheckoutModel> checkoutModels);
}
