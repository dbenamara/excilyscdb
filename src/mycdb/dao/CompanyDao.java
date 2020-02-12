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
public final class CompanyDao extends Dao {
	//private Connection conn;
	private static volatile CompanyDao instance = null;

	private CompanyDao(Connection conn) {
		super(conn);
	}
	
	public final static CompanyDao getInstance(Connection conn) {

		if (CompanyDao.instance == null) {

			synchronized (CompanyDao.class) {
				if (CompanyDao.instance == null) {
					CompanyDao.instance = new CompanyDao(conn);
				}
			}
		}
		return CompanyDao.instance;

	}

	
	
	public boolean create(int id, String name) throws SQLException {
		String query = "INSERT INTO computer VALUES (?,?);";
		boolean res=false;
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.executeUpdate();
			res=true;
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean delete() {
		return false;
	}
	   
	public boolean update() {
		return false;
	}
	
	public List readAll() {
		List<Company> list = new ArrayList<>();
		String query = "SELECT * FROM company";
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			ResultSet result = preparedStatement.executeQuery();
		    while(result.next()) {
		    	Company tmp = new Company(result.getInt("id"),result.getString("name"));
		    	list.add(tmp);
		    	
		    	
		    }        
		} catch (SQLException e) {
		      e.printStackTrace();
		    }
		
		return list;
	}

}
