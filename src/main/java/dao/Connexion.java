package dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import exceptions.Logging;


/**
 * @author djamel
 *
 */
@Component
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
	
	public Connexion() {}
	
	@Bean
	public static DataSource hikariDataSource() {
		config = new HikariConfig("/Connexion.properties");
		ds = new HikariDataSource(config);
		return ds;
	}
	
	
	

	
	public Connection getConn() {
		try {
			conn = hikariDataSource().getConnection();
		} catch(SQLException sqle) {
			Logging.printError(sqle.getMessage());
		}
		return conn;
	}
}
