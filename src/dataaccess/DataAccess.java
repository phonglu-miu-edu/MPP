package dataaccess;

import java.util.HashMap;
import java.util.List;

import entities.Author;
import entities.Book;
import entities.CheckoutRecord;
import entities.LibraryMember;

public interface DataAccess { 
	public HashMap<String, Book> readBookMap();
	public HashMap<String, User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewBook(String isbn, String title, int days, List<Author> authors);
	public void updateBooks(List<Book> bookList);
	boolean findMemberByPhone(String phone);
	public void updateCopyBook(String isbn);
	public String getNewMemberID();
	public List<CheckoutRecord> getCheckoutByMemberId(String memberID);
}
