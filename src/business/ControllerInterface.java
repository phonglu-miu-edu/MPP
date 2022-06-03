package business;

import java.util.HashMap;
import java.util.List;

import entities.LibraryMember;
import models.LoginException;
import models.ResponseModel;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public ResponseModel<LibraryMember> checkout(int memberId, String isbnNumber, int dueDate);
	public HashMap<String, Book> allBooks();
	public LibraryMember checkout(String memberId, String isbnNumber);
}
