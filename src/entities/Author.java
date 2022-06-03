package entities;

import java.io.Serializable;

public final class Author extends Person implements Serializable {
	private static final long serialVersionUID = 7508481940058530471L;
	private String bio;
	
	public Author(String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.bio = bio;
	}
	
	public String getBio() {
		return bio;
	}
}
