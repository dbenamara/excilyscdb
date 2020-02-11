
package mycdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import mycdb.dao.CompanyDao;
import mycdb.dao.ComputerDao;

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
			Class.forName("com.mysql.cj.jdbc.Driver");
	         
			String url = "jdbc:mysql://localhost:3306/computer-database-db";
		    String user = "admincdb";
		    String passwd = "qwerty1234";
		         
		    Connection conn = DriverManager.getConnection(url, user, passwd);
		    
		    //Statement state = conn.createStatement();
		    //ResultSet res = state.executeQuery("SELECT * FROM computer");
		    ComputerDao cd = new ComputerDao(conn);
		    CompanyDao cpd = new CompanyDao(conn);
		    cpd.create(44, "companytest");
		    List<Computer> listComputer = cd.readAll();
		    List<Company> listCompany = cpd.readAll();
		    //System.out.print(listComputer);
		    //System.out.print(listCompany);
		    cd.create(600, "PCtest", null, null, 1);
		    
		    Computer c = cd.find(600);
		    System.out.print(c);
		    
		    conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
