
package mycdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import mycdb.dao.ComputerDao;
import mycdb.model.Company;
import mycdb.model.Computer;
import mycdb.services.CompanyService;
import mycdb.services.ComputerService;

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
		    //ComputerDao cd = new ComputerDao(conn);
		    //CompanyDao cpd = new CompanyDao(conn);
		    //cpd.create(44, "companytest");
		    List<Computer> listComputer = ComputerService.getInstance(conn).readAll();
		    List<Company> listCompany = CompanyService.getInstance(conn).readAll();
		    //System.out.print(listComputer);
		    //System.out.print(listCompany);
		    //cd.create(600, "PCtest", null, null, 1);
		   
		    Computer c = ComputerDao.getInstance(conn).find(60);
		    System.out.println(c);
		    ComputerDao.getInstance(conn).update(c, 60, "toto", c.getIntroduced(), null, 0);
		    //c = cd.find(60);
		    //cd.delete(c);
		    //List<Computer> listComputer = cd.readAll();
		    System.out.print(listComputer);
		    
		    conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
