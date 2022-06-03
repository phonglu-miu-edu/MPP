package business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import entities.Book;
import entities.CheckoutRecord;
import entities.EntityFacade;
import entities.LibraryMember;
import models.CheckoutModel;
import models.LoginException;
import models.ResponseModel;

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
	public List<LibraryMember> allMembers() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();

		return members.stream().toList();
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	@Override
	public List<Book> allBooks() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBooksMap().values();

		return books.stream().toList();
	}

	@Override
	public ResponseModel<CheckoutRecord> checkout(String memberId, List<CheckoutModel> checkoutModels) {
		ResponseModel<CheckoutRecord> response = new ResponseModel<CheckoutRecord>();
		
		LibraryMember member = getMember(memberId);
		
		if (member == null) {
			response.setErrorMessage("Member id '" + memberId + "' not found");
			return response;
		}
		
		for (CheckoutModel checkoutModel : checkoutModels) {
			String isbnNumber = checkoutModel.getIsbnNumber();
			int checkoutLength = checkoutModel.getCheckoutLength();
			
		Book book = getBook(isbnNumber);
		
		if (book == null) {
			response.setErrorMessage("Book isbn number '" + isbnNumber + "' not found");
			return response;
		}
		
		int maxCheckoutLength = book.getMaxCheckoutLength();		
		if (maxCheckoutLength < checkoutLength) {
			response.setErrorMessage("Book isbn number's max checkout length is '" + maxCheckoutLength + "' day(s)");
			return response;
		}

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, checkoutLength);
		Date dueDate = c.getTime();
		
		EntityFacade entityFacade = EntityFacade.getInstance();
		CheckoutRecord checkoutRecord = entityFacade.createCheckoutRecord(memberId);
		checkoutRecord.addBook(isbnNumber, dueDate);
		
			response.setData(checkoutRecord);
		}
		
		return response;
	}
	
	private LibraryMember getMember(String memberId) {
		HashMap<String, LibraryMember> members = dataAccessFacade.readMemberMap();
		
		for (Map.Entry<String, LibraryMember> member : members.entrySet()) {
			LibraryMember found = member.getValue();
			
			if (found.getMemberId().equals(memberId)) {
				return found;
			}
		}
		
		return null;
	}

	private Book getBook(String isbnNumber) {
		HashMap<String, Book> books = dataAccessFacade.readBooksMap();
		
		for (Map.Entry<String, Book> book : books.entrySet()) {
			Book found = book.getValue();
			
			if (found.getIsbn().equals(isbnNumber)) {
				return found;
			}
		}
		
		return null;
	}
	
}
