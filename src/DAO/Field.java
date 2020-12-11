package DAO;


public class Field {
	private String name;
	private String type;
	
	public Field(String n, String t) {
		setName(n);
		setType(t);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return name+"|"+type;
	}
	
}
