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
	
}
