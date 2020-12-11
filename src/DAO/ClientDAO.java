package DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientDAO implements DAO<Client>, DataReader, DataWriter {
	
	private Map<Long, Client> buffer = new HashMap<Long, Client>();
	private long nextID = 1;
	private int quantity = 0;
	private String fileName = "Clients.csv";
	
	public ClientDAO() {
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
		for(Client client: buffer.values()) {
			data.add(client.toData());
		}
		this.overwriteFile(fileName_, data);
	}


	private Map<Long, Client> fileToBuffer(String fileName_) {
		List<List<String>> tmp = this.getFile(fileName_);
		Map<Long, Client> out = new HashMap<Long, Client>();
		if(tmp == null)return out;
		nextID = Long.parseLong(tmp.get(0).get(0));
		quantity = Integer.parseInt(tmp.get(0).get(1));
		for(int i = 1; i<quantity+1; i++) {
			List<String> line = tmp.get(i);
			out.put(Long.parseLong(line.get(0)), new Client(Long.parseLong(line.get(0)), line.get(1), line.get(2)));
		}
		return out;
	}


	@Override
	public void add(Client t) {
		buffer.put(t.getID(), t);
		quantity++;
		updateFile(fileName);
	}

	@Override
	public void update(Client t) {
		buffer.replace(t.getID(), t);
		updateFile(fileName);
	}

	@Override
	public void delete(Client t) {
		delete(t.getID());
	}

	@Override
	public void delete(long id) {
		buffer.remove(id);
		quantity--;
		updateFile(fileName);		
	}

	@Override
	public Client get(long id) {
		updateBuffer();
		return buffer.get(id);
	}
	
	public Client authorise(String login, char[] password) {
		for(Client client: buffer.values()) {
			if(client.getName().equals(login) && client.checkPassword(password)) {
				return client;
			}
		}
		return null;
	}

	@Override
	public List<Client> getAll() {
		updateBuffer();
		return new ArrayList<Client>(buffer.values());
	}

	public long getNextID() {
		return nextID++;
	}


	public boolean loginAvailable(String text) {
		for(Client client: buffer.values()) {
			if(client.getName().equals(text))return false;
		}
		return true;
	}
}
