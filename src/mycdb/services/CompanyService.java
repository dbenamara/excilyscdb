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
	private CompanyService() {}
	
	public final static CompanyService getInstance() {

		if (CompanyService.instance == null) {

			synchronized (CompanyService.class) {
				if (CompanyService.instance == null) {
					CompanyService.instance = new CompanyService();
				}
			}
		}

		return CompanyService.instance;	
	}
	
	public List readAll() {
		return CompanyDao.getInstance().readAll();
	}
}
