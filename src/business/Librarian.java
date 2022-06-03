package business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.DataAccessFacade;

public class Librarian implements Serializable {

	private static final long serialVersionUID = -6232688392326470029L;
	private DataAccessFacade _dataAccessFacade = new DataAccessFacade();

	Librarian() {
		
	}
	
	LibraryMember checkout(String memberId, String isbnNumber) {
		HashMap<String, LibraryMember> members = _dataAccessFacade.readMemberMap();
		
		for (Map.Entry<String, LibraryMember> member : members.entrySet()) {
			LibraryMember found = member.getValue();
			
			if (found.getMemberId().equals(memberId)) {
				return found;
			}
		}
		
		HashMap<String, Book> books = _dataAccessFacade.readBooksMap();
		
		for (Map.Entry<String, Book> book : books.entrySet()) {
			Book found = book.getValue();
			
			if (found.getIsbn().equals(isbnNumber)) {
				return null;
			}
		}
		
		return null;
	}

	public static void main(String[] args) {
		
	}
}
