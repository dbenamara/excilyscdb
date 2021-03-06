package exceptions;


import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Djamel
 *
 */
public class Logging {
	
	private static Logger log = LoggerFactory.getLogger(Logging.class);
	
	public Logging() {
	}
	
	public static void printError(String message) {
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jFile.properties"));

		log.error(message);
	}
	
	public static void printInfo(String message) {
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4jFile.properties"));


		log.info(message);
	}
}
