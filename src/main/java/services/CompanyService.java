package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dao.CompanyDao;
import model.Company;


/**
 * @author djamel
 *
 */
@Service
public class CompanyService {
	private CompanyDao companyDao;
	
	public CompanyService(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	@Transactional
	public List<Company> readAll() {
		return companyDao.readAll();
	}
	@Transactional
	public int getlength(){
		return this.readAll().size();
	}
	
	@Transactional
	public List<Company> getPageCompany(int offset, int number){
		return companyDao.getPageCompany(offset, number);
	}
	
	@Transactional
	public void deleteCompany(int id) {
		companyDao.deleteCompany(id);
	}
}
