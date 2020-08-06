package it.tasgroup.addon.api.validation.constraints.impl;

import it.tasgroup.addon.api.validation.Amount;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountValidator implements ConstraintValidator<Amount, BigDecimal> {

	@Override
	public void initialize(Amount a) {
	
		
	}

	@Override
	public boolean isValid(BigDecimal amount, ConstraintValidatorContext context) {
		if (amount != null)
			return amount.compareTo(BigDecimal.ZERO) > 0;
		else 
			return true;
				
	}


	

}
