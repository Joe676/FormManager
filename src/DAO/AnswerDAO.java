package DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerDAO implements DAO<Answer>, DataReader, DataWriter {
	
	private Map<Long, Answer> buffer = new HashMap<Long, Answer>();
	private long nextID = 1;
	private int quantity = 0;
	private String fileName = "Answers.csv";
	private ServiceDAO sd;
	private FormDAO fd;
	private ClientDAO cd;
	
	public AnswerDAO(ServiceDAO s, FormDAO f, ClientDAO c) {
		sd = s;
		fd = f;
		cd = c;
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
		if(buffer!=null) {
			for(Answer ans: buffer.values()) {
				data.add(ans.toData());
			}
		}
		this.overwriteFile(fileName_, data);
	}

	private Map<Long, Answer> fileToBuffer(String fileName_) {
		List<List<String>> tmp = this.getFile(fileName_);
		Map<Long, Answer> out = new HashMap<Long, Answer>();
		if(tmp==null)return out;
		nextID = Long.parseLong(tmp.get(0).get(0));
		quantity = Integer.parseInt(tmp.get(0).get(1));
		for(int i = 1; i < quantity+1; i++) {
			List<String> line = tmp.get(i);
			if(sd.get(Long.parseLong(line.get(1)))!=null) {
				if(fd.get(Long.parseLong(line.get(2)))!=null) {
					if(cd.get(Long.parseLong(line.get(3))) != null) {
						List <String> a = null;
						if(line.size()==6) a = Arrays.asList(line.get(5).split(":"));
						out.put(Long.parseLong(line.get(0)), new Answer(Long.parseLong(line.get(0)), sd.get(Long.parseLong(line.get(1))), fd.get(Long.parseLong(line.get(2))), cd.get(Long.parseLong(line.get(3))), line.get(4), a));
					}
				}
				
			}
		}
		return out;
	}

	@Override
	public void add(Answer t) {
		buffer.put(t.getID(), t);
		quantity++;
		updateFile(fileName);
	}

	@Override
	public void update(Answer t) {
		buffer.replace(t.getID(), t);
		updateFile(fileName);
	}

	@Override
	public void delete(Answer t) {
		delete(t.getID());
	}

	@Override
	public void delete(long id) {
		buffer.remove(id);
		quantity--;
		updateFile(fileName);
	}

	@Override
	public Answer get(long id) {
		updateBuffer();
		return buffer.get(id);
	}

	@Override
	public List<Answer> getAll() {
		updateBuffer();
		return new ArrayList<Answer>(buffer.values());
	}
	
	public long getNextID() {
		return nextID++;
	}
	
	public List<Answer> filter(Service s, Form f){//Get a list of answers to a form about service. (for visualisation)
		List<Answer> out = new ArrayList<Answer>();
		for(Answer a: buffer.values()) {
			if(a.getService().getID() == s.getID() && a.getForm().getID() == f.getID()) {
				out.add(a);
			}
		}
		return out;
	}
}
