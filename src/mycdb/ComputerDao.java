package mycdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author djamel
 *
 */
public class ComputerDao extends Dao {
	
	public ComputerDao(Connection conn) {
		super(conn);
	}
	
	
	public boolean create() {
		return false;
	}

	public boolean delete() {
		return false;
	}
	   
	public boolean update() {
		return false;
	}
	
	public List readAll() {
		List<Computer> list = new ArrayList<>();
		try {
			ResultSet result = this.conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM computer");
		    while(result.next()) {
		    	Computer tmp = new Computer(result.getInt("id"),result.getString("name"),result.getDate("introduced"),result.getDate("discontinued"),result.getInt("company_id"));
		    	list.add(tmp);
		    }        
		} catch (SQLException e) {
		      e.printStackTrace();
		    }
		
		return list;
	}


	
}
