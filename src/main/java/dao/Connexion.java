package dao;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import exceptions.Logging;


/**
 * @author djamel
 *
 */
public class Connexion {
	private static Connection conn;
	
    
    private static String url;
    private static String user;
    private static String passwd;
    private static String driver;
    
    private static HikariConfig config;
	private static HikariDataSource ds;
    
    private static Properties properties;
	
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
	
	/*public void connect() {
		properties = new Properties();
		
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Connexion.properties"));
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			passwd = properties.getProperty("passwd");
			driver = properties.getProperty("driver");
			
    		Class.forName(driver);
    		conn = DriverManager.getConnection(url, user, passwd);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(SQLException e){
    		e.printStackTrace();
		} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
		
        
        


	}*/
	
	public Connection getConn() {
		config = new HikariConfig("/Connexion.properties");
		ds = new HikariDataSource( config );
			
		try {
			return ds.getConnection();
		}catch(SQLException e) {
			Logging.printError(e.getMessage());
		}
		return null;
	}
	
	/*public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}*/
}
