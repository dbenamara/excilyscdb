package exceptions;

/**
 * @author Djamel
 *
 */
public class ValidatorException extends RuntimeException {
	public ValidatorException() {
		super();
	}
	
	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ValidatorException(String message) {
		super(message);
	}
	
	public ValidatorException(Throwable cause) {
		super(cause);
	}
	
	public static class NameValidator extends ValidatorException{

		public NameValidator() {
			super();
		}

		public NameValidator(String message, Throwable cause) {
			super(message, cause);
		}

		public NameValidator(String message) {
			super(message);
		}

		public NameValidator(Throwable cause) {
			super(cause);
		}
		
	}
	
	public static class DateValidator extends ValidatorException{

		public DateValidator() {
			super();
		}

		public DateValidator(String message, Throwable cause) {
			super(message, cause);
		}

		public DateValidator(String message) {
			super(message);
			
		}

		public DateValidator(Throwable cause) {
			super(cause);
			
		}
		
	}
}
