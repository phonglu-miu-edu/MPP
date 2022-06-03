package business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.DataAccessFacade;

public class Librarian implements Serializable {

	private static final long serialVersionUID = -6232688392326470029L;
	private DataAccessFacade dataAccessFacade = new DataAccessFacade();

	Librarian() {
		
	}
	
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

	public static void main(String[] args) {
		
	}
}
