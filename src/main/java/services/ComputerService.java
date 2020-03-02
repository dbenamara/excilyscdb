package services;

import java.util.List;

import dao.ComputerDao;
import model.Computer;
/**
 * @author djamel
 *
 */
public class ComputerService {
	private static volatile ComputerService instance = null;
	private ComputerService() {
	}
	
	public final static ComputerService getInstance() {

		if (ComputerService.instance == null) {

			synchronized (ComputerService.class) {
				if (ComputerService.instance == null) {
					ComputerService.instance = new ComputerService();
				}
			}
		}

		return ComputerService.instance;
	}
	
	public void create(Computer computer) {
		ComputerDao.getInstance().create(computer);
	}
	
	public void delete(int id) {
		ComputerDao.getInstance().delete(id);
	}
	
	public Computer update(Computer comp) {
		return ComputerDao.getInstance().update(comp);
	}
		
	public List<Computer> readAll() {
		return ComputerDao.getInstance().readAll();
	}
	
	public Computer find(int id) {
		return ComputerDao.getInstance().find(id).get();
	}
	
	public List<Computer> getPageComputer(int offset, int number) {
		return ComputerDao.getInstance().getPageComputer(offset, number);
	}
	
	public int getlength() {
		return readAll().size();
}

	
}


