package DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormDAO implements DAO<Form>, DataReader, DataWriter {
	
	private Map<Long, Form> buffer = new HashMap<Long, Form>();
	private long nextID = 1;
	private int quantity = 0;
	private String fileName = "Forms.csv";
	
	public FormDAO() {
		updateBuffer();
	}
	
	private void updateBuffer() {
		buffer = fileToBuffer(fileName);
		updateFile(fileName);
	}

	private void updateFile(String fileName_) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(new ArrayList<String>());
		data.get(0).add(String.valueOf(nextID));
		data.get(0).add(String.valueOf(quantity));
		for(Form form: buffer.values()) {
			data.add(form.toData());
		}
		this.overwriteFile(fileName_, data);
	}

	private Map<Long, Form> fileToBuffer(String fileName_) {
		List<List<String>> tmp = this.getFile(fileName_);
		Map<Long, Form> out = new HashMap<Long, Form>();
		if(tmp == null)return out;
		nextID = Long.parseLong(tmp.get(0).get(0));
		quantity = Integer.parseInt(tmp.get(0).get(1));
		for(int i = 1; i < quantity+1; i++) {
			List<String> line = tmp.get(i);
			List<Field> f = new ArrayList<Field>();
			if(line.size()==3) {
				for(String field:line.get(2).split(":")) {
					f.add(new Field(field.split("\\|")[0], field.split("\\|")[1]));
				}
			}
			out.put(Long.parseLong(line.get(0)), new Form(Long.parseLong(line.get(0)), line.get(1), f));
		}
		return out;
	}

	@Override
	public void add(Form t) {
		buffer.put(t.getID(), t);
		quantity++;
		updateFile(fileName);
	}

	@Override
	public void update(Form t) {
		buffer.replace(t.getID(), t);
		updateFile(fileName);
	}

	@Override
	public void delete(Form t) {
		delete(t.getID());
	}

	@Override
	public void delete(long id) {
		buffer.remove(id);
		quantity--;
		updateFile(fileName);
	}

	@Override
	public Form get(long id) {
		updateBuffer();
		return buffer.get(id);
	}
	
	public List<Form> get(List<Long> ids){
		updateBuffer();
		List<Form> out = new ArrayList<Form>();
		for(long id: ids) {
			out.add(buffer.get(id));
		}
		return out;
	}

	@Override
	public List<Form> getAll() {
		updateBuffer();
		return new ArrayList<Form>(buffer.values());
	}
	
	public long getNextID() {
		return nextID++;
	}

}
