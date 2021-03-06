package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ComputerDao;
import model.Computer;
/**
 * @author djamel
 *
 */

@Service
public class ComputerService {
	private static volatile ComputerService instance = null;
	
	private ComputerDao computerDao;
	
	
	public ComputerService(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}
	
	
	
	public void create(Computer computer) {
		computerDao.create(computer);
	}
	
	public void delete(int id) {
		computerDao.delete(id);
	}
	
	public void update(Computer comp) {
		computerDao.update(comp);
	}
		
	public List<Computer> readAll() {
		return computerDao.readAll();
	}
	
	public Computer find(int id) {
		return computerDao.find(id).get();
	}
	
	public List<Computer> getPageComputer(int offset, int number, String orderBy) {
		return computerDao.getPageComputer(offset, number, orderBy);
	}
	
	public List<Computer> findName(String name, int offset, int number, String orderBy) {
		return computerDao.findName(name,offset,number,orderBy);
	}
	
	public void deleteAllComputerSelected(List<Integer> idList) {
		computerDao.deleteAllComputerSelected(idList);
	}
	
	public int getlength() {
		return this.readAll().size();
}

	
}


