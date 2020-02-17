
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
			
		         
		    
		    Cli cli = new Cli();
		    cli.printWelcome();
		   
		    List<Computer> listComputer = ComputerService.getInstance().readAll();
		    List<Company> listCompany = CompanyService.getInstance().readAll();
		  
		    Company newCompany = new Company(42,"Research In Motion");
		    Computer newComputer = new Computer(575,"tototo",null, null, newCompany);
		    Computer upComputer = new Computer(601,"sixentun",null, null, newCompany);
		    
		    
		    int action;
		  quit : do {
			action = cli.getEntry(0, 6);  
		  
		    switch(action) {
		    	case 0:
		    		System.out.println(listComputer);
		    		break;
		    	case 1:
		    		System.out.println(listCompany);
		    		break;
		    	case 2:
		    		int idComputer = cli.getComputerId();
		    		System.out.println(ComputerService.getInstance().find(idComputer));
		    		break;
		    	case 3:
		    		ComputerService.getInstance().create(newComputer);
		    		cli.increaseNbComputer();
		    		break;
		    	
		    	case 4:
		    		ComputerService.getInstance().delete(601);
		    		break;
		    	case 5:
		    		ComputerService.getInstance().update(upComputer);
		    		break;
		    	case 6:
		    		break quit;
		    	default:
		    		System.out.println("Nothing to do");
		    		break;
		    	
		    }
		  }while(true);
		    
		    
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
