package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import exceptions.Logging;
import mapper.CompanyMapper;
import model.Company;

/**
 * @author djamel
 *
 */
@Repository
public final class CompanyDao {
	private Connexion conn;
	private static volatile CompanyDao instance = null;
	private static final String CREATE_COMPANY = "INSERT INTO company (id,  name) VALUES(?, ?)";
	private static final String GET_ALL_COMPANY = "SELECT * FROM company";
	private static final String SELECT_COMPANY_PAGE = "SELECT * FROM company LIMIT ?,? ";
	private static final String ERROR_ACCESS = "Impossible de se connecter à la bdd";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?;";
	
	private Logging log;
	
	
	public CompanyDao(Connexion conn) {
		this.conn = conn;
	}
	
	
	public boolean create(Company company) {
		
		boolean res=false;
		try (Connection connect = conn.getConn()){
			PreparedStatement preparedStatement = connect.prepareStatement(CREATE_COMPANY);
			preparedStatement.setInt(1, company.getId());
			preparedStatement.setString(2, company.getName());
			preparedStatement.executeUpdate();
			res=true;
			preparedStatement.close();
			
		
		}catch(SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return res;
	}

	public List<Company> readAll() {
		
		List<Company> list = new ArrayList<Company>();
		try (Connection connect = conn.getConn()){
	
			PreparedStatement preparedStatement = connect.prepareStatement(GET_ALL_COMPANY);
			ResultSet result = preparedStatement.executeQuery();
		    while(result.next()) {
		    	Company tmp = new Company(result.getInt("id"),result.getString("name"));
		    	list.add(tmp);
		    	
		    	
		    }
		    preparedStatement.close();
		    result.close();
		}catch (SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return list;
	}
	
	public List<Company> getPageCompany(int offset, int number) {

		List<Company> companylist = new ArrayList<Company>();
		try(Connection connect = conn.getConn()) {
			
			PreparedStatement statementSelectPage = connect.prepareStatement(SELECT_COMPANY_PAGE);
			statementSelectPage.setInt(1, offset);
			statementSelectPage.setInt(2, number);
			ResultSet resListeCompany = statementSelectPage.executeQuery();

			while (resListeCompany.next()) {
				companylist.add(new CompanyMapper().getCompany(resListeCompany).get());
			}

			statementSelectPage.close();
			resListeCompany.close();

		} catch (SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return companylist;
	}

	public void deleteCompany(int id) {
		ResultSet result;
		Connection connect=null;
		try {
			connect = conn.getConn();
			connect.setAutoCommit(false);
			PreparedStatement preparedStatementComputer = connect.prepareStatement(ComputerDao.DELETE_COMPUTER_FROM_COMPANY);
			PreparedStatement preparedStatementCompany = connect.prepareStatement(DELETE_COMPANY);
			preparedStatementComputer.setInt(1, id);
			preparedStatementCompany.setInt(1, id);

			preparedStatementComputer.executeUpdate();
			preparedStatementCompany.executeUpdate();
			connect.commit();
			preparedStatementComputer.close();
			preparedStatementCompany.close();
			connect.setAutoCommit(true);
			connect.close();
		}catch(SQLException sqle) {
			Logging.printError(ERROR_ACCESS+" dans deleteCompany "+ sqle.getMessage());
			try {
				if(connect!=null) {
					connect.rollback();
				}
			}catch(SQLException sqle2) {
				Logging.printError(ERROR_ACCESS+" dans deleteCompany "+ sqle2.getMessage());
			}
		}
	}

}
