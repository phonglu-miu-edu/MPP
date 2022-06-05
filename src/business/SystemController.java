package business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import entities.*;
import models.CheckoutModel;
import models.LoginException;
import models.ResponseModel;
import ui.Util;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	private DataAccessFacade dataAccessFacade = new DataAccessFacade();
	
	public User login(String id, String password) throws LoginException {
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
		return map.get(id);
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	public void logout() {
		currentAuth = null;
		Util.showLoginForm();
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
		return new ArrayList<>(da.readBookMap().keySet());
	}
	
	@Override
	public List<Book> allBooks() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBookMap().values();
		return books.stream().toList();
	}

	@Override
	public List<Book> allBooksHasAvailableCopies() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBookMap().values();

		List<Book> result = new ArrayList<>();

		for (Book book : books) {
			int bookCopiesQuantity = book.getNumCopies();
			if (bookCopiesQuantity > 0) {
				result.add(book);
			}
		}

		return result;
	}

	@Override
	public ResponseModel<CheckoutRecord> checkout(String memberId, List<CheckoutModel> checkoutModels) {
		ResponseModel<CheckoutRecord> response = new ResponseModel<>();
		
		LibraryMember member = getMember(memberId);
		
		if (member == null) {
			response.setErrorMessage("Member id '" + memberId + "' not found");
			return response;
		}

		CheckoutRecord checkoutRecord = new CheckoutRecord(member);

		for (CheckoutModel checkoutModel : checkoutModels) {
			String isbnNumber = checkoutModel.getIsbn();
			int checkoutLength = checkoutModel.getCheckoutLength();

			Book book = getBook(isbnNumber);

			if (book == null) {
				response.setErrorMessage("Book isbn number '" + isbnNumber + "' not found");
				return response;
			}

			BookCopy bookCopy = book.getNextAvailableCopy();

			if (bookCopy == null) {
				response.setErrorMessage("There is no book copy of '" + book.getTitle() + "'");
				return response;
			}

			int maxCheckoutLength = book.getMaxCheckoutLength();
			if (maxCheckoutLength < checkoutLength) {
				response.setErrorMessage("Book isbn number's max checkout length is '" + maxCheckoutLength + "' day(s)");
				return response;
			}

			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, checkoutLength);
			LocalDate dueDate = LocalDateTime.ofInstant(c.toInstant(), c.getTimeZone().toZoneId()).toLocalDate();

			checkoutRecord.addEntry(new CheckoutRecordEntry(bookCopy, dueDate));

			bookCopy.changeAvailability();
		}

		response.setData(checkoutRecord);
		dataAccessFacade.saveCheckoutRecord(checkoutRecord);
		
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
		HashMap<String, Book> books = dataAccessFacade.readBookMap();

		for (Map.Entry<String, Book> book : books.entrySet()) {
			Book found = book.getValue();

			if (found.getIsbn().equals(isbnNumber)) {
				return found;
			}
		}

		return null;
	}

	@Override
	public List<CheckoutRecord> getAllCheckoutRecords() {
		HashMap<String, CheckoutRecord> checkoutRecords = dataAccessFacade.readCheckoutRecordMap();
		return new ArrayList<>(checkoutRecords.values());
	}
	
	@Override
	public void addNewBook(Book book) {
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
	}
	
	@Override
	public void addCopy(List<Book> books) {
		DataAccess da = new DataAccessFacade();
		da.updateBooks(books);
	}

	//Only one CheckoutRecord for each member.
	@Override
	public List<CheckoutRecord> getCheckoutByMemberId(String memberId) {
		DataAccess da = new DataAccessFacade();
		Collection<CheckoutRecord> records = da.readCheckoutRecordMap().values();

		List<CheckoutRecord> recordsList = records.stream().toList();
		List<CheckoutRecord> returnValue = new ArrayList<>();
		for(CheckoutRecord record: recordsList) {
			/*if (memberId.equals(record.getMemberId())) {
				returnValue.add(record);
			}*/
		}
		return returnValue;
	}

	public CheckoutRecord getMemberCheckoutRecord(LibraryMember member) {
		//need to improve here
		return new CheckoutRecord(member);
	}
	
	@Override
	public void addNewLibraryMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}

	public boolean addMember(String id, String fname, String lname, String tel, String street, String c, String st, String zip) {
		//search member by phone
		DataAccess da =  new DataAccessFacade();
		boolean isExist = da.findMemberByPhone(tel);
		System.out.println("isExist: "+isExist);
		if(!isExist) {
			Address addr = null;
			if(street.length() > 0 && c.length() > 0 && st.length() > 0 && zip.length() > 0) {
				addr = new Address(street, c, st, zip);
			}

			LibraryMember member = new LibraryMember(id, fname, lname, tel, addr);
			da.saveNewMember(member);
			return true;
		}

		return false;
	}
}
