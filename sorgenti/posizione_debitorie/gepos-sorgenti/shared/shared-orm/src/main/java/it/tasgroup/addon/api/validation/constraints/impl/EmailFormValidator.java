package it.tasgroup.addon.api.validation.constraints.impl;

import it.tasgroup.addon.api.validation.EmailForm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.validator.FacesValidator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



@FacesValidator("it.tasgroup.addon.api.validation.constraints.impl.EmailFormValidator")
public class EmailFormValidator implements ConstraintValidator<EmailForm, String> {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;
	
	@Override
	public void initialize(EmailForm arg0) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		
	}

	@Override
	public boolean isValid(String emailValue, ConstraintValidatorContext arg1) {
		if(emailValue != null && !emailValue.isEmpty()) {
			matcher = pattern.matcher(emailValue);
			return matcher.matches();
		} else {
			return true;
		}
	}


	

}
