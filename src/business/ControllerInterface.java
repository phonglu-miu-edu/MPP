package business;

import java.util.HashMap;
import java.util.List;

import entities.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import entities.LibraryMember;
import models.LoginException;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public HashMap<String, Book> allBooks();
	public LibraryMember checkout(String memberId, String isbnNumber);	
}
