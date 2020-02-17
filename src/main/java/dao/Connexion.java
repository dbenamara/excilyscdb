
package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author djamel
 *
 */
public class Connexion {
private static Connection conn;
	
	private static  Connexion instance = null;
	
	private Connexion() {}
	
	public  static Connexion getInstance() {
        if (Connexion.instance == null) {
           synchronized(Connexion.class) {
             if (Connexion.instance == null) {
            	 Connexion.instance = new Connexion();
             }
           }
        }
        return Connexion.instance;
	}
	
	public void connect() {
		String url = "jdbc:mysql://localhost:3306/computer-database-db";
	    String user = "admincdb";
	    String passwd = "qwerty1234";
	    
        try{
        	Class.forName("com.mysql.cj.jdbc.Driver");
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
