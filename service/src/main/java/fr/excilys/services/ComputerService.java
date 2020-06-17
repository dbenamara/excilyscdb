package fr.excilys.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.excilys.dao.ComputerDao;
import fr.excilys.model.Computer;
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
	
	
	@Transactional
	public void create(Computer computer) {
		computerDao.create(computer);
	}
	@Transactional
	public void delete(int id) {
		computerDao.delete(id);
	}
	
	@Transactional
	public void update(Computer comp) {
		computerDao.update(comp);
	}
		
	@Transactional
	public List<Computer> readAll() {
		return computerDao.readAll();
	}
	
	@Transactional
	public Computer find(int id) {
		return computerDao.find(id).get();
	}
	
	@Transactional
	public List<Computer> getPageComputer(int offset, int number, String orderBy) {
		return computerDao.getPageComputer(offset, number, orderBy);
	}
	
	@Transactional
	public List<Computer> findName(String name, int offset, int number, String orderBy) {
		return computerDao.findName(name,offset,number,orderBy);
	}
	
	@Transactional
	public void deleteAllComputerSelected(List<Integer> idList) {
		computerDao.deleteAllComputerSelected(idList);
	}
	
	@Transactional
	public int getlength() {
		return this.readAll().size();
}

	
}


