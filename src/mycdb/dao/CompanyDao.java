package mycdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import mycdb.model.Company;

/**
 * @author djamel
 *
 */
public final class CompanyDao {
	private Connexion conn;
	private static volatile CompanyDao instance = null;
	private static final String CREATE_COMPANY = "INSERT INTO company (id,  name) VALUES(?, ?)";
	private static final String GET_ALL_COMPANY = "SELECT * FROM company";

	private CompanyDao() {
		this.conn = Connexion.getInstance();
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

	
	
	public boolean create(Company company) /*throws SQLException*/ {
		
		this.conn = Connexion.getInstance();
		conn.connect();
		boolean res=false;
		try {
			PreparedStatement preparedStatement = this.conn.getConn().prepareStatement(CREATE_COMPANY);
			preparedStatement.setInt(1, company.getId());
			preparedStatement.setString(2, company.getName());
			preparedStatement.executeUpdate();
			res=true;
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return res;
	}

	public boolean delete() {
		return false;
	}
	   
	public boolean update() {
		return false;
	}	
	public List<Company> readAll() {
		conn = Connexion.getInstance();
		conn.connect();
		List<Company> list = new ArrayList<>();
		try {
			System.out.println(conn);
			System.out.println("TOTOTOTOTOTO");	
			//System.out.println(conn.getConn());
			PreparedStatement preparedStatement = conn.getConn().prepareStatement(GET_ALL_COMPANY);
			ResultSet result = preparedStatement.executeQuery();
		    while(result.next()) {
		    	Company tmp = new Company(result.getInt("id"),result.getString("name"));
		    	list.add(tmp);
		    	
		    	
		    }        
		}catch (SQLException e) {
		      e.printStackTrace();
		}
		conn.close();
		return list;
	}

}
