package business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import entities.Book;
import entities.LibraryMember;
import models.LoginException;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	private DataAccessFacade dataAccessFacade = new DataAccessFacade();
	
	//Returns a list of all ids of LibraryMembers whose zipcode contains the digit 3
	public static List<String> allWhoseZipContains3() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		return null;	
	}
	
	//Returns a list of all ids of  LibraryMembers that have an overdue book
	public static List<String> allHavingOverdueBook() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		return null;
	}
	
	//Returns a list of all isbns of  Books that have multiple authors
	public static List<String> allHavingMultipleAuthors() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBooksMap().values();
		List<Book> bs = new ArrayList<>();
		bs.addAll(books);
		//implement
		return null;	
	}
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	@Override
	public HashMap<String, Book> allBooks() {
		DataAccess da = new DataAccessFacade();
		return da.readBooksMap();
	}

	@Override
	public LibraryMember checkout(String memberId, String isbnNumber) {
		boolean isValid = false;
		
		HashMap<String, LibraryMember> members = dataAccessFacade.readMemberMap();
		
		for (Map.Entry<String, LibraryMember> member : members.entrySet()) {
			LibraryMember found = member.getValue();
			
			if (found.getMemberId().equals(memberId)) {
				isValid = true;
			}
		}
		
		if (isValid) {
			isValid = false;
	
			HashMap<String, Book> books = dataAccessFacade.readBooksMap();
			
			for (Map.Entry<String, Book> book : books.entrySet()) {
				Book found = book.getValue();
				
				if (found.getIsbn().equals(isbnNumber)) {
					isValid = true;
				}
			}
		}
		
		if (isValid) {
		}
		
		return null;
	}
	
}
