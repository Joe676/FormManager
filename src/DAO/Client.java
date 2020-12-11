package DAO;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private long ID;
	private String name;
	private String password;
	
	public Client(long id, String name, String password) {
		setID(id);
		setName(name);
		this.password = password;
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean checkPassword(char[] password2) {
		return password.equals(new String(password2));
	}

	public List<String> toData() {
		List<String> data = new ArrayList<String>();
		data.add(String.valueOf(ID));
		data.add(name);
		data.add(password);
		return data;
	}
}
