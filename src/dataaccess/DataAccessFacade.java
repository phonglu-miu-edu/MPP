package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;

import entities.*;

public class DataAccessFacade implements DataAccess {
	enum StorageType {
		BOOKS, MEMBERS, USERS, CHECKOUT_RECORDS;
	}

	private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
	
	public static final String OUTPUT_DIR = System.getProperty("user.dir") + SEPARATOR + "src" + SEPARATOR + "dataaccess" + SEPARATOR + "storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	//implement: other save operations
	public void saveNewMember(LibraryMember member) {
		System.out.println("Adding new member " + member.getMemberId());
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);	
	}

	public void saveCheckoutRecord(CheckoutRecord checkoutRecord) {
		HashMap<String, CheckoutRecord> checkoutRecords = readCheckoutRecordMap();
		String id = checkoutRecord.getId();

		if (id.isEmpty()) {
			long unixTime = Instant.now().getEpochSecond();
			id = String.valueOf(unixTime);
		}

		checkoutRecords.put(id, checkoutRecord);
		saveToStorage(StorageType.CHECKOUT_RECORDS, checkoutRecords);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBookMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);

		if (books == null) {
			books = new HashMap<String, Book>();
		}

		return books;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		HashMap<String, LibraryMember> members = (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);

		if (members == null) {
			members = new HashMap<String, LibraryMember>();
		}

		return members;
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}
	
	/////load methods - these place test data into the storage area
	///// - used just once at startup
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}
 
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	static void loadCheckoutRecordMap(List<CheckoutRecord> checkoutRecordList) {
		HashMap<String, CheckoutRecord> checkoutRecords = new HashMap<String, CheckoutRecord>();
		checkoutRecordList.forEach(checkoutRecord -> checkoutRecords.put(checkoutRecord.getId(), checkoutRecord));
		saveToStorage(StorageType.CHECKOUT_RECORDS, checkoutRecords);
	}
	
	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}
	
	
	static final class Pair<S,T> implements Serializable{
		private static final long serialVersionUID = 1L;
		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override 
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}
		
		@Override 
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

	}
	
	@Override
	public void updateBooks(List<Book> bookList) {
		System.out.println(bookList.get(2).getNumCopies());
		loadBookMap(bookList);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		HashMap<String, CheckoutRecord> records = (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.CHECKOUT_RECORDS);

		if (records == null) {
			records = new HashMap<String, CheckoutRecord>();
		}

		return records;
	}

	public boolean findMemberByPhone(String phone) {
		HashMap<String, LibraryMember> mems = readMemberMap();

		for(Entry<String, LibraryMember> m : mems.entrySet()) {
			if(m.getValue().getTelephone().equals(phone)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, CheckoutRecord> readCheckoutMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		HashMap<String, CheckoutRecord> records = (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.CHECKOUT_RECORDS);

		if (records == null) {
			records = new HashMap<String, CheckoutRecord>();
		}

		return records;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);

		if (books == null) {
			books = new HashMap<String, Book>();
		}

		return books;
	}
	
	//implement: other save operations
	public void saveNewBook(String isbn, String title, int days, List<Author> authors) {
		HashMap<String, Book> books = readBooksMap();
		//Increase id for a new book
		int maxID = 0;
		for(Book book: books.values()) {
			if (Integer.valueOf(book.getId()) > maxID) {
				maxID = Integer.valueOf(book.getId());
			}
		}
		maxID++;
		Book book = new Book(String.valueOf(maxID), isbn, title, days, authors);
		books.put(isbn, book);
		System.out.println("A new book with ID is added " + maxID);
		saveToStorage(StorageType.BOOKS, books);	
	}

	public void changeBookCopyAvailability(String isbn, int bookCopyId, boolean availability) {
		HashMap<String, Book> books = readBooksMap();
		Book book = books.get(isbn);
		if (book != null) {
			for (BookCopy bookCopy : book.getCopies()) {
				if (bookCopy.getCopyNum() == bookCopyId) {
					bookCopy.changeAvailability(availability);
					break;
				}
			}

			saveToStorage(StorageType.BOOKS, books);
		}
	}

	@Override
	public void addBookCopy(String isbn) {
		HashMap<String, Book> books = readBooksMap();
		Book book = books.get(isbn);
		if (book != null) {
			System.out.println(isbn + " book is added a book copy");
			book.addCopy();
			saveToStorage(StorageType.BOOKS, books);
		}
	}
	
	@Override
	public String getNewMemberID() {
		HashMap<String, LibraryMember> mems = readMemberMap();
		int maxID = 0;
		for(String id: mems.keySet()) {
			if (Integer.valueOf(id) > maxID) {
				maxID = Integer.valueOf(id);
			}
		}
		return String.valueOf(maxID + 1);
	}
	
	@Override
	public List<CheckoutRecord> getCheckoutByMemberId(String memberID) {
		HashMap<String, CheckoutRecord> records = readCheckoutMap();
		List<CheckoutRecord> values = new ArrayList<>();
		for(CheckoutRecord record: records.values()) {
			if (record.getMember() != null && memberID.equals(record.getMember().getMemberId())) {
				values.add(record);
			}
		}
		return values;
	}
}
