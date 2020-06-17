package fr.excilys.validators;
import fr.excilys.exceptions.ValidatorException;
import fr.excilys.model.Computer;

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
