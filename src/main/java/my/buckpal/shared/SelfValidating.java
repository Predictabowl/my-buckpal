package my.buckpal.shared;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public abstract class SelfValidating<T> {
	
	private Validator validator;
	
	public SelfValidating(){
			validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	protected void validateSelf() {
		Set<ConstraintViolation<T>> violations = validator.validate((T) this);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
	}

}
