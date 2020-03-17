package dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import mapper.ComputerMapper;
import model.Computer;

/**
 * @author Djamel
 *
 */
@Repository
public class ComputerDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private ComputerMapper computerMapper = new ComputerMapper();
	
	
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id)"
			+ " VALUES (:computerName, :introduced, :discontinued, :companyId);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = :id ;";
	private static final String DELETE_COMPUTER_SELECTED = "DELETE FROM computer WHERE id = :id;";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name= :name, introduced= :introduced, discontinued= :discontinued, company_id= :company_id WHERE id= :id;";
	private static final String GET_ALL_COMPUTER = "SELECT computer.id , computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id;";
	private static final String GET_PAGE_COMPUTER = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY ";
	private static final String GET_COMPUTER_BY_NAME = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE :like LIMIT :offset :number ORDER BY ";
	private static final String GET_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = :computer.id;";
	protected static final String DELETE_COMPUTER_FROM_COMPANY = "DELETE FROM computer WHERE company_id= :id;";

	
	
	public ComputerDao(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public void create(Computer computer) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("computerName", computer.getName())
				.addValue("introduced", computer.getIntroduced())
				.addValue("discontinued", computer.getDiscontinued())
				.addValue("companyId", computer.getCompany().getId());
		this.namedParameterJdbcTemplate.update(CREATE_COMPUTER, namedParameters);
	}
	
	public List<Computer> readAll() {
		return this.namedParameterJdbcTemplate.query(GET_ALL_COMPUTER, this.computerMapper);
	}
	
	public void delete(int id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
		this.namedParameterJdbcTemplate.update(DELETE_COMPUTER, namedParameters);
	}
	
	public void deleteAllComputerSelected(List<Integer> idList) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", idList);
		this.namedParameterJdbcTemplate.update(DELETE_COMPUTER_SELECTED, namedParameters);
	}
	
	public Optional<Computer> find(int id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("computer.id", id);
		return Optional.of(this.namedParameterJdbcTemplate.queryForObject(GET_COMPUTER_BY_ID, namedParameters,this.computerMapper));

	}
	
	public void update(Computer computer) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("id", computer.getId())
				.addValue("name", computer.getName())
				.addValue("introduced", computer.getIntroduced())
				.addValue("discontinued", computer.getDiscontinued())
				.addValue("company_id", computer.getCompany().getId());
		this.namedParameterJdbcTemplate.update(UPDATE_COMPUTER, namedParameters);
	}
	
	public  List<Computer> getPageComputer(int offset, int number, String orderBy) {
		orderBy = GET_PAGE_COMPUTER + orderBy;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("offset", offset).addValue("number", number);
		return this.namedParameterJdbcTemplate.query(orderBy, namedParameters, this.computerMapper);

	}
	
	public List<Computer> findName(String name, int offset,int number, String orderBy) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("like", name)
				.addValue("number", number).addValue("offset", offset);
		return this.namedParameterJdbcTemplate.query(GET_COMPUTER_BY_NAME, namedParameters, this.computerMapper);

	}
	

	public void deleteComputerFromCompany(int id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
		this.namedParameterJdbcTemplate.update(DELETE_COMPUTER_FROM_COMPANY, namedParameters);
	}

}

