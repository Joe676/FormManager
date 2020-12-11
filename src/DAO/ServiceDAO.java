package DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDAO implements DAO<Service>, DataReader, DataWriter {

	private Map<Long, Service> buffer = new HashMap<Long, Service>();
	private long nextID = 1;
	private int quantity = 0;
	private String fileName = "Services.csv";
	private FormDAO fd;
	
	public ServiceDAO(FormDAO f) {
		fd = f;
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
		for(Service service: buffer.values()) {
			data.add(service.toData());
		}
		this.overwriteFile(fileName_, data);
	}

	private Map<Long, Service> fileToBuffer(String fileName_) {
		List<List<String>> tmp = this.getFile(fileName_);
		Map<Long, Service> out	= new HashMap<Long, Service>();
		if(tmp==null)return out;
		nextID = Long.parseLong(tmp.get(0).get(0));
		quantity = Integer.parseInt(tmp.get(0).get(1));
		for(int i = 1; i<quantity+1; i++) {
			List<String> line = tmp.get(i);
			List<Form> f = new ArrayList<Form>();
			if(line.size()==3) {
				for(String form:line.get(2).split(":")) {
					if(fd.get(Long.parseLong(form))!=null) {
						f.add(fd.get(Long.parseLong(form)));
					}
				}
			}
			out.put(Long.parseLong(line.get(0)), new Service(Long.parseLong(line.get(0)), line.get(1), f));
		}
		return out;
	}

	@Override
	public void add(Service t) {
		buffer.put(t.getID(), t);
		quantity++;
		updateFile(fileName);
	}

	@Override
	public void update(Service t) {
		buffer.replace(t.getID(), t);
		updateFile(fileName);
	}

	@Override
	public void delete(Service t) {
		delete(t.getID());
	}

	@Override
	public void delete(long id) {
		buffer.remove(id);
		quantity--;
		updateFile(fileName);
	}

	@Override
	public Service get(long id) {
		updateBuffer();
		return buffer.get(id);
	}

	@Override
	public List<Service> getAll() {
		updateBuffer();
		return new ArrayList<Service>(buffer.values());
	}
	
	public long getNextID() {
		return nextID++;
	}
	
	public int getQuantity() {
		return quantity;
	}
}
