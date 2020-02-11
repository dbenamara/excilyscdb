package mycdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mycdb.Computer;

/**
 * @author djamel
 *
 */
public class ComputerDao extends Dao {
	
	public ComputerDao(Connection conn) {
		super(conn);
	}
	
	
	public boolean create(int id, String name, Timestamp introduced, Timestamp discontinued, int company_id) {
		String query = "INSERT INTO computer VALUES (?,?,?,?,?);";
		boolean res=false;
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setTimestamp(3, introduced);
			preparedStatement.setTimestamp(4, discontinued);
			preparedStatement.setInt(5, company_id);
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
		boolean res=false;
		
		
		return res;
	}
	
	public List readAll() {
		List<Computer> list = new ArrayList<>();
		String query = "SELECT * FROM computer";
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			ResultSet result = preparedStatement.executeQuery(); 
		    while(result.next()) {
		    	Computer tmp = new Computer(result.getInt("id"),result.getString("name"),result.getDate("introduced"),result.getDate("discontinued"),result.getInt("company_id"));
		    	list.add(tmp);
		    }        
		} catch (SQLException e) {
		      e.printStackTrace();
		    }
		
		return list;
	}
	
	public Computer find(int id) {
		Computer tmp = null;
		String query = "SELECT * FROM computer WHERE id=?";
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
				if(result.first()) {
					tmp= new Computer(result.getInt("id"),result.getString("name"),result.getDate("introduced"),result.getDate("discontinued"),result.getInt("company_id"));
				}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return tmp;
	}


	
}
