package models;

public final class ComboBoxItem {
	private String id;
	private String description;
    
    public ComboBoxItem(String id, String description) {
    	this.id = id;
    	this.description = description;
    }
	
    public String getId() {
    	return this.id;
    }
	
    public String toString() {
        return description;
    }
}
