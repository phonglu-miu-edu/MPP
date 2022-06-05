package dataaccess;

import java.util.HashMap;
import java.util.List;

import entities.Book;
import entities.CheckoutRecord;
import entities.LibraryMember;

public interface DataAccess { 
	public HashMap<String, Book> readBooksMap();
	public HashMap<String, User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, CheckoutRecord> readCheckoutMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewBook(Book book);
	public void updateBooks(List<Book> bookList);
	boolean findMemberByPhone(String phone);
}
