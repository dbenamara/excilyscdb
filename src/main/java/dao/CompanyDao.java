package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.Logging;
import mapper.CompanyMapper;
import model.Company;

/**
 * @author djamel
 *
 */
public final class CompanyDao {
	private Connection conn;
	private static volatile CompanyDao instance = null;
	private static final String CREATE_COMPANY = "INSERT INTO company (id,  name) VALUES(?, ?)";
	private static final String GET_ALL_COMPANY = "SELECT * FROM company";
	private static final String SELECT_COMPANY_PAGE = "SELECT * FROM company LIMIT ?,? ";
	private static final String ERROR_ACCESS = "Impossible de se connecter à la bdd";
	private Logging log;
	
	private CompanyDao() {
		this.log = new Logging();
	}
	
	public final static CompanyDao getInstance() {

		if (CompanyDao.instance == null) {

			synchronized (CompanyDao.class) {
				if (CompanyDao.instance == null) {
					CompanyDao.instance = new CompanyDao();
				}
			}
		}
		return CompanyDao.instance;

	}

	
	
	public boolean create(Company company) {
		
		boolean res=false;
		try (Connection connect = Connexion.getInstance().getConn();){
			PreparedStatement preparedStatement = connect.prepareStatement(CREATE_COMPANY);
			preparedStatement.setInt(1, company.getId());
			preparedStatement.setString(2, company.getName());
			preparedStatement.executeUpdate();
			res=true;
			preparedStatement.close();
			
		
		}catch(SQLException e) {
			log.printError(ERROR_ACCESS+e.getMessage());
		}
		return res;
	}

	public boolean delete() {
		return false;
	}
	   
	public boolean update() {
		return false;
	}	
	public List<Company> readAll() {
		
		List<Company> list = new ArrayList<Company>();
		try (Connection connect = Connexion.getInstance().getConn();){
	
			PreparedStatement preparedStatement = connect.prepareStatement(GET_ALL_COMPANY);
			ResultSet result = preparedStatement.executeQuery();
		    while(result.next()) {
		    	Company tmp = new Company(result.getInt("id"),result.getString("name"));
		    	list.add(tmp);
		    	
		    	
		    }
		    preparedStatement.close();
		    result.close();
		}catch (SQLException e) {
			log.printError(ERROR_ACCESS+e.getMessage());
		}
		return list;
	}
	
	public List<Company> getPageCompany(int offset, int number) {

		List<Company> companylist = new ArrayList<Company>();
		try(Connection connect = Connexion.getInstance().getConn();) {
			
			PreparedStatement statementSelectPage = connect.prepareStatement(SELECT_COMPANY_PAGE);
			statementSelectPage.setInt(1, offset);
			statementSelectPage.setInt(2, number);
			ResultSet resListeCompany = statementSelectPage.executeQuery();

			while (resListeCompany.next()) {
				companylist.add(CompanyMapper.getInstance().getCompany(resListeCompany).get());
			}

			statementSelectPage.close();
			resListeCompany.close();

		} catch (SQLException e) {
			log.printError(ERROR_ACCESS+e.getMessage());
		}
		return companylist;
	}

}
