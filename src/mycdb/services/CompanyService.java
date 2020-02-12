package mycdb.services;

import java.sql.Connection;
import java.util.List;

import mycdb.dao.CompanyDao;
import mycdb.model.Company;

/**
 * @author djamel
 *
 */
public class CompanyService {
	private static volatile CompanyService instance = null;
	private Connection conn;
	private CompanyService(Connection conn) {
		this.conn=conn;
	}
	
	public final static CompanyService getInstance(Connection conn) {

		if (CompanyService.instance == null) {

			synchronized (CompanyService.class) {
				if (CompanyService.instance == null) {
					CompanyService.instance = new CompanyService(conn);
				}
			}
		}

		return CompanyService.instance;
	}
	
	public List readAll() {
		return CompanyDao.getInstance(this.conn).readAll();
	}
}
