package mycdb.dao;

import java.sql.Connection;
import java.util.List;

/**
 * @author djamel
 *
 */
public abstract class Dao {
	protected Connection conn;
	
	public Dao(Connection conn){
	    this.conn = conn;
	}
	
	//public abstract boolean create();
	
	public abstract List readAll();
	
	public abstract boolean update();
	
	public abstract boolean delete();
	
	
}
