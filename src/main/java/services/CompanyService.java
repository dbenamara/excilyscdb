package services;

import java.util.List;

import org.springframework.stereotype.Service;

import dao.CompanyDao;
import model.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author djamel
 *
 */
@Service
public class CompanyService {
	private static volatile CompanyService instance = null;
	private CompanyDao companyDao;
	
	public CompanyService(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	
	public List<Company> readAll() {
		return companyDao.readAll();
	}
	
	public int getlength(){
		return this.readAll().size();
	}
	public List<Company> getPageCompany(int offset, int number){
		return companyDao.getPageCompany(offset, number);
	}
}
