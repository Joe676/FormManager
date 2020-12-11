package DAO;

import java.util.List;

public interface DAO<T> {
	
	void add(T t);
	
	void update(T t);
	
	void delete(T t);
	void delete(long id);
	
	T get(long id);
	
	List<T> getAll();
	
}
