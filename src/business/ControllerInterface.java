package business;

import entities.Author;
import entities.Book;
import entities.CheckoutRecord;
import entities.LibraryMember;
import dataaccess.User;
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
	ResponseModel<CheckoutRecord> checkout(String memberId, String bookIsbnNumber);
	List<CheckoutRecord> getAllCheckoutRecords();
	void addNewBook(String isbn, String title, int days, List<Author> authors);
	void addCopy(String isbn);
	List<CheckoutRecord> getCheckoutByMemberId(String memberId);
	void addNewLibraryMember(LibraryMember member);
	boolean addMember(String fname, String lname, String tel, String street, String c, String st, String zip);
	void printCheckoutRecord(String memberId);
}
