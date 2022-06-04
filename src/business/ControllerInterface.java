package business;

import entities.Book;
import entities.CheckoutRecord;
import entities.LibraryMember;
import models.CheckoutModel;
import models.LoginException;
import models.ResponseModel;
import dataaccess.User;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.List;

public interface ControllerInterface {
	public User login(String id, String password) throws LoginException;
	void doChangeScene(User loginedUser) throws IOException;
	void logout();
	public List<String> allMemberIds();
	public List<LibraryMember> allMembers();
	public List<String> allBookIds();
	public List<Book> allBooks();
	public ResponseModel<CheckoutRecord> checkout(String memberId, List<CheckoutModel> checkoutModels);
	public void addNewBook(Book book);
}
