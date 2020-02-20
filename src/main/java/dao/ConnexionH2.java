package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author Djamel
 *
 */
public class ConnexionH2 {
	private static Connection conn;
	private static String url = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'src/test/resources/h2SQLgeneration.sql'";
	private static String user = "sa";
	private static String passwd = "";
    private static String driverh2 = "org.h2.Driver";
	
	private static  ConnexionH2 instance = null;
	
	private ConnexionH2() {}
	
	public  static ConnexionH2 getInstance() {
        if (ConnexionH2.instance == null) {
           synchronized(ConnexionH2.class) {
             if (ConnexionH2.instance == null) {
            	 ConnexionH2.instance = new ConnexionH2();
             }
           }
        }
        return ConnexionH2.instance;
	}
	
	public void connect() {
		
	    
        try{
        	Class.forName(driverh2);
        	conn = DriverManager.getConnection(url, user, passwd);

        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
	}
	
	public Connection getConn() {
        return conn;
	}
	
	public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}

