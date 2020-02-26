
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
	
	private static String urlh2 = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'src/test/resources/h2SQLgeneration.sql'";
	private static String userh2 = "sa";
	private static String passwdh2 = "";
    private static String driverh2 = "org.h2.Driver";
    
    private static String url = "jdbc:mysql://localhost:3306/computer-database-db";
    private static String user = "admincdb";
    private static String passwd = "qwerty1234";
    private static String driver = "com.mysql.jdbc.Driver";
	
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
		
        if(testing(System.getProperty("testState"))) {
        	try{
        		Class.forName(driverh2);
        		conn = DriverManager.getConnection(urlh2, userh2, passwdh2);

        	}catch(SQLException e){
        		e.printStackTrace();
        	} catch (ClassNotFoundException e) {
        		e.printStackTrace();
        	}
        }
        else {
        	try{
        		Class.forName(driver);
        		conn = DriverManager.getConnection(url, user, passwd);

        	}catch(SQLException e){
        		e.printStackTrace();
        	} catch (ClassNotFoundException e) {
        		e.printStackTrace();
        	}
        }
        
	}
	
	public Connection getConn() {
        return conn;
	}
	
	private static boolean testing(String testState) {
		if(testState == null) {
			return false;
		}else if(testState.contentEquals("Running")){
			return true;
		} else {
			return false;
		}
	}
	
	public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
