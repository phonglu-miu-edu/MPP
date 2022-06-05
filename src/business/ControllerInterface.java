package business;

import dataaccess.User;
import entities.Book;
import entities.CheckoutRecord;
import entities.LibraryMember;
import models.CheckoutModel;
import models.LoginException;
import models.ResponseModel;

import java.util.List;

public interface ControllerInterface {
	User login(String id, String password) throws LoginException;
	void logout();
	List<String> allMemberIds();
	List<LibraryMember> allMembers();
	List<String> allBookIds();
	List<Book> allBooks();
	List<Book> allBooksHasAvailableCopies();
	ResponseModel<CheckoutRecord> checkout(String memberId, List<CheckoutModel> checkoutModels);
	List<CheckoutRecord> getAllCheckoutRecords();
	void addNewBook(Book book);
	void addCopy(List<Book> books);
	List<CheckoutRecord> getCheckoutByMemberId(String memberId);
	void addNewLibraryMember(LibraryMember member);
	boolean addMember(String id, String fname, String lname, String tel, String street, String c, String st, String zip);
}
