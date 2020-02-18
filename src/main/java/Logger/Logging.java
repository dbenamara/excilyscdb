package Logger;


import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;

import dao.ComputerDao;

/**
 * @author Djamel
 *
 */
public class Logging {
	private Logger log;
	
	public Logging() {
		this.log = LoggerFactory.getLogger(Logging.class);
	}
	
	public void printError(String message) {
		
		//PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jConsole.properties"));
		//log.error(message);
		//PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jFile.properties"));
		log.error(message);
	}
	
public void printInfo(String message) {
		
		//PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jConsole.properties"));
		//log.error(message);
		//PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jFile.properties"));
		log.info(message);
	}
}
