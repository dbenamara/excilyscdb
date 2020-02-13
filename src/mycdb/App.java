
package mycdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.swing.plaf.synth.SynthOptionPaneUI;

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
		    
		    Cli cli = new Cli();
		    cli.printWelcome();
		    //int action = cli.getEntry(0, 3);
		    
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
		   
		    //Computer c = ComputerDao.getInstance(conn).find(60);
		    //System.out.println(c);
		    //ComputerDao.getInstance(conn).update(c, 60, "toto", c.getIntroduced(), null, 0);
		    //c = cd.find(60);
		    //cd.delete(c);
		    //List<Computer> listComputer = cd.readAll();
		    //System.out.println(listCompany);
		    int action;
		  quit : do {
			action = cli.getEntry(0, 4);  
		  
		    switch(action) {
		    	case 0:
		    		System.out.println(listComputer);
		    		break;
		    	case 1:
		    		System.out.println(listCompany);
		    		break;
		    	case 2:
		    		int idComputer = cli.getComputerId();
		    		System.out.println(ComputerDao.getInstance(conn).find(idComputer));
		    		break;
		    	case 3:
		    		ComputerDao.getInstance(conn).create(602, "PCtest", null, null, 1);
		    		cli.increaseNbComputer();
		    		break;
		    	case 4:
		    		break quit;
		    	default:
		    		System.out.println("Nothing to do");
		    		break;
		    	
		    }
		  }while(true);
		    
		    
		    //System.out.print(listComputer);
		    
		    conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
