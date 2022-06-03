package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private static final long serialVersionUID = 7508481940058530471L;
	private String id;
	private String bio;
	private String credentials;

	public String getId() { return id; }
	public String getBio() {
		return bio;
	}
	public String getCredentials() { return credentials; }
	
	public Author(String id, String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.bio = bio;
		this.id = id;
	}

}
