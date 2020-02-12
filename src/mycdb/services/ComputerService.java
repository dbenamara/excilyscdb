package mycdb.services;

import java.sql.Connection;
import java.util.List;

import mycdb.dao.ComputerDao;
import mycdb.model.Computer;

/**
 * @author djamel
 *
 */
public class ComputerService {
	private static volatile ComputerService instance = null;
	private Connection conn;
	private ComputerService(Connection conn) {
		this.conn=conn;
	}
	
	public final static ComputerService getInstance(Connection conn) {

		if (ComputerService.instance == null) {

			synchronized (ComputerService.class) {
				if (ComputerService.instance == null) {
					ComputerService.instance = new ComputerService(conn);
				}
			}
		}

		return ComputerService.instance;
	}
	
	public List readAll() {
		return ComputerDao.getInstance(this.conn).readAll();
	}
}


