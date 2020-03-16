package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mapper.CompanyMapper;
import model.Company;




/**
 * @author djamel
 *
 */
@Repository
public class CompanyDao {
	
	private static final String CREATE_COMPANY = "INSERT INTO company name = :name";
	private static final String GET_ALL_COMPANY = "SELECT * FROM company";
	private static final String SELECT_COMPANY_PAGE = "SELECT * FROM company LIMIT :limit,:offset ";
	private static final String ERROR_ACCESS = "Impossible de se connecter à la bdd";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = :id;";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private ComputerDao computerDao;
	private CompanyMapper companyMapper = new CompanyMapper();
	
	public void SpringCompanyDao(DataSource dataSource,ComputerDao computerDao) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    this.computerDao = computerDao;
	}
	
	
	public void create(Company company) {
		Map<String, String> namedParameters = new HashMap<>();
		namedParameters.put("name", company.getName());
		this.namedParameterJdbcTemplate.update(CREATE_COMPANY, namedParameters);
	}
	
	public List<Company> readAll() {
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		return this.namedParameterJdbcTemplate.query(GET_ALL_COMPANY, namedParameters, this.companyMapper);
	}
	
	public List<Company> getPageCompany(int offset, int number) {

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("limit", number).addValue("offset", offset);
		return this.namedParameterJdbcTemplate.queryForList(SELECT_COMPANY_PAGE, namedParameters, Company.class);
	}
	
	
	@Transactional
	public void deleteCompany(int id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
		computerDao.deleteComputerFromCompany(id);
		this.namedParameterJdbcTemplate.update(DELETE_COMPANY,namedParameters);
	}
	
	
	
}





