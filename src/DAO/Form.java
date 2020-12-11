package DAO;

import java.util.ArrayList;
import java.util.List;

public class Form {
	private long ID;
	private String name;
	private List<Field> fields;
	
	public Form(long id, String name, List<Field> f) {
		setID(id);
		setName(name);
		setFields(f);
		if(f==null)fields = new ArrayList<Field>();
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		String out = ID + ";" + name;// + ";";
		
//		for(int i = 0; i < fields.size(); i++) {
//			out += fields.get(i);
//			if(i<fields.size()-1) {
//				out += ":";
//			}
//		}
		return out;
	}
	
	public List<String> toData(){
		List<String> out = new ArrayList<String>();
		out.add(String.valueOf(ID));
		out.add(name);
		String h = "";
		if(fields!=null) {
			for(int i = 0; i<fields.size(); i++) {
				h += fields.get(i).toString();
				if(i<fields.size()-1) {
					h += ":";
				}
			}	
		}
		out.add(h);
		return out;
	}
}
