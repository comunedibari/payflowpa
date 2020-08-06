package it.tasgroup.addon.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import it.tasgroup.addon.api.validation.constraints.impl.CodiceFiscaleValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CodiceFiscaleValidator.class)
@Documented
public @interface CodiceFiscale {
	
	  String message() default "{it.tasgroup.addon.api.validation.codicefiscale.message}"; 
	  Class<?>[] groups() default {};
	  Class<? extends Payload>[] payload() default {};
	  
	  String messageForLengthValidation() default "{it.tasgroup.addon.api.validation.codicefiscale.length.message}";
	  String messageForPivaValidation() default "{it.tasgroup.addon.api.validation.codicefiscale.piva.message}";
	  String messageForPivaPatternValidation() default "{it.tasgroup.addon.api.validation.codicefiscale.piva.pattern.message}";
	  String messageForCfiscValidation() default "{it.tasgroup.addon.api.validation.codicefiscale.cfisc.message}";
	  String messageForCfiscPatternValidation() default "{it.tasgroup.addon.api.validation.codicefiscale.cfisc.pattern.message}";
	  
}