package DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Answer {
	private long ID;
	private Service service;
	private Form form;
	private Client client;
	private String date;
	private List<String> answers;
	
	
	public Answer(long id, Service service, Form form, Client client, List<String> answers) {
		ID = id;
		this.service = service;
		this.form = form;
		this.answers = answers;
		this.client = client;
		setDate((new SimpleDateFormat("dd.MM.yyyy")).format(new Date()));
	}
	
	public Answer(long id, Service service, Form form, Client client, String date, List<String> answers) {
		ID = id;
		this.service = service;
		this.form = form;
		this.answers = answers;
		this.client = client;
		this.date = date;
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	
	public String toString() {
		String out = "";
		out.concat(ID+";"+service.getID()+";"+form.getID()+";"+client.getID()+";"+date+";");
		if(answers!=null) {
				for(int i = 0; i < answers.size(); i++) {
				out.concat(answers.get(i));
				if(i < answers.size()-1) out.concat(":");
			}
		}
		return out;
	}
	
	public List<String> toData(){
		List<String> out = new ArrayList<String>();
		out.add(String.valueOf(ID));
		out.add(String.valueOf(service.getID()));
		out.add(String.valueOf(form.getID()));
		out.add(String.valueOf(client.getID()));
		out.add(date);
		String a = "";
		if(answers!=null) {
			for(int i = 0; i < answers.size(); i++) {
				a += (answers.get(i));
				if(i < answers.size()-1) a += (":");
			}
		}
		out.add(a);
		return out;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
