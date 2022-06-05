package business;

import entities.Book;
import entities.CheckoutRecord;
import entities.LibraryMember;
import dataaccess.User;
import models.CheckoutModel;
import models.LoginException;
import models.ResponseModel;

import java.lang.reflect.Member;
import java.util.List;

public interface ControllerInterface {
	public User login(String id, String password) throws LoginException;
	void logout();
	public List<String> allMemberIds();
	public List<LibraryMember> allMembers();
	public List<String> allBookIds();
	public List<Book> allBooks();
	public ResponseModel<CheckoutRecord> checkout(String memberId, List<CheckoutModel> checkoutModels);
	public void addNewBook(Book book);
	public void addCopy(List<Book> books);
	public List<CheckoutRecord> getCheckoutByMemberId(String memberId);
	public void addNewLibraryMember(LibraryMember member);
	boolean addMember(String id, String fname, String lname, String tel, String street, String c, String st, String zip);
}
