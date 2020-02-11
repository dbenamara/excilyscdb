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
public class CompanyDao extends Dao {
	//private Connection conn;

	public CompanyDao(Connection conn) {
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
		List<Company> list = new ArrayList<>();
		try {
			ResultSet result = this.conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM company");
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
