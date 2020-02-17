package mycdb.services;

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
	
	public List<Company> readAll() {
		return CompanyDao.getInstance().readAll();
	}
	
	public int getlength(){
		return readAll().size();
	}
	public List<Company> getPageCompany(int offset, int number){
		return CompanyDao.getInstance().getPageCompany(offset, number);
	}
}
