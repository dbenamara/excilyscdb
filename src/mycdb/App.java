
package mycdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * @author djamel
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
	         
			String url = "jdbc:mysql://localhost:3306/computer-database-db";
		    String user = "admincdb";
		    String passwd = "qwerty1234";
		         
		    Connection conn = DriverManager.getConnection(url, user, passwd);
		    System.out.println("TOTOTOTOTOTO");
		    //Statement state = conn.createStatement();
		    //ResultSet res = state.executeQuery("SELECT * FROM computer");
		    ComputerDao cd = new ComputerDao(conn);
		    List<Computer> list = cd.readAll();
		    System.out.print(list);
		    
		    conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
