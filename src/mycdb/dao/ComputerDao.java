package mycdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mycdb.model.Computer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author djamel
 *
 */
public class ComputerDao extends Dao {
	private static volatile ComputerDao instance = null;
	private ComputerDao(Connection conn) {
		super(conn);
	}
	
	public final static ComputerDao getInstance(Connection conn) {

		if (ComputerDao.instance == null) {

			synchronized (ComputerDao.class) {
				if (ComputerDao.instance == null) {
					ComputerDao.instance = new ComputerDao(conn);
				}
			}
		}

		return ComputerDao.instance;
	}

	
	public boolean create(int id, String name, LocalDateTime introduced, LocalDateTime discontinued, int company_id) {
		String query = "INSERT INTO computer VALUES (?,?,?,?,?);";
		boolean res=false;
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setTimestamp(3, (introduced!=null)?Timestamp.valueOf(introduced):null);
			preparedStatement.setTimestamp(4, (discontinued!=null)?Timestamp.valueOf(discontinued):null);
			preparedStatement.setInt(5, company_id);
			preparedStatement.executeUpdate();
			res=true;
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	public boolean delete(Computer c) {
		boolean res=false;
		String query = "DELETE FROM computer WHERE id=?;";
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setInt(1, c.getId());
			preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	   
	public boolean update(Computer c,int id, String name, LocalDateTime introduced, LocalDateTime discontinued, int company_id) {
		boolean res=false, changes=false;
		int tmpId=c.getId();
		if(id>0) {
			c.setId(id);
			changes=true;
		}
		if(name!=null && name!="") {
			c.setName(name);
			changes=true;
		}
		if(introduced!=null) {
			c.setIntroduced(introduced);
			changes=true;
		}
		if(discontinued!=null) {
			c.setDiscontinued(discontinued);
			changes=true;
		}
		if(company_id>0) {
			c.setManufacturer(company_id);
			changes=true;
		}
		
		if(changes) {
			String query="UPDATE computer SET id=?, name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
			try {
				PreparedStatement preparedStatement = this.conn.prepareStatement(query);
				preparedStatement.setInt(6, tmpId);
				preparedStatement.setInt(1, (id>0)?id:c.getId());
				preparedStatement.setString(2, (name!=null && name!="")?name:c.getName());
				preparedStatement.setTimestamp(3, (introduced!=null)?Timestamp.valueOf(introduced):(c.getIntroduced()==null)?null:Timestamp.valueOf(c.getIntroduced()));
				preparedStatement.setTimestamp(4, (discontinued!=null)?Timestamp.valueOf(discontinued):(c.getDiscontinued()==null)?null:Timestamp.valueOf(c.getDiscontinued()));
				preparedStatement.setInt(5, (company_id>0)?company_id:c.getManufacturer());
				preparedStatement.executeUpdate();
				res=true;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	public List readAll() {
		List<Computer> list = new ArrayList<>();
		String query = "SELECT * FROM computer";
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(query);
			ResultSet result = preparedStatement.executeQuery(); 
		    while(result.next()) {
		    	Computer tmp = new Computer(result.getInt("id"),result.getString("name"),(result.getTimestamp("introduced")==null)?null:result.getTimestamp("introduced").toLocalDateTime(),(result.getTimestamp("discontinued")==null)?null:result.getTimestamp("discontinued").toLocalDateTime(),result.getInt("company_id"));
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
					tmp= new Computer(result.getInt("id"),result.getString("name"),(result.getTimestamp("introduced")==null)?null:result.getTimestamp("introduced").toLocalDateTime(),(result.getTimestamp("discontinued")==null)?null:result.getTimestamp("discontinued").toLocalDateTime(),result.getInt("company_id"));
				}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return tmp;
	}


	
}
