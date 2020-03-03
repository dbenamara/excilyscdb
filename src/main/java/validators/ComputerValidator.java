package validators;

import exceptions.ValidatorException;
import model.Computer;

/**
 * @author Djamel
 *
 */
public class ComputerValidator {
	public  void validateComputer(Computer computer) {
		validateDates(computer);
		validateComputerName(computer);
	}
	
	public void validateDates(Computer computer) {
		if(computer.getIntroduced()!=null && computer.getDiscontinued()!=null) {
			if(computer.getIntroduced().isAfter(computer.getDiscontinued())) {
				throw new ValidatorException.DateValidator("ValidatorException: Introduced date should be before discontinued date");
			}
		}
	}
	
	private void validateComputerName(Computer computer){
		if(computer.getName()==null || computer.getName().equals("")) {
			throw new ValidatorException.NameValidator("ValidatorException: Computer Name is required");
		}
	}
}
