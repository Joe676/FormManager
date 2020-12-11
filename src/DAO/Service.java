package DAO;

import java.util.ArrayList;
import java.util.List;

public class Service {
	private long ID;
	private String name;
	private List<Form> forms;
	
	public Service(long id, String name, List<Form> forms) {
		setID(id);
		setName(name);
		setForms(forms);
		if(forms == null)this.forms = new ArrayList<Form>();
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
	public List<Form> getForms() {
		return forms;
	}
	public void setForms(List<Form> forms) {
		this.forms = forms;
	}
	
	public List<String> toData(){
		List<String> out = new ArrayList<String>();
		out.add(String.valueOf(ID));
		out.add(name);
		String tmp = "";
		if(forms!=null) {
			for(int i = 0; i < forms.size(); i++) {
				tmp += (String.valueOf(forms.get(i).getID()));
				if(i<forms.size()-1) {
					tmp += (":");
				}
			}
			out.add(tmp);
		}
		return out;
	}
	
	public String toString() {
		String out = ID + ";" +name;// + ";"; 
//		for(int i = 0; i < forms.size(); i++) {
//			out += forms.get(i).getID();
//			if(i<forms.size()-1) {
//				out += ":";
//			}
//		}
		
		return out;
	}
}
