/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivotsb.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoNuovoCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoNuovoDto;

/**
 * Classe per la validazione dei campi del form per la creazione di un accertamento.
 * 
 */
@Component
public class AccertamentoValidator implements Validator {

	public AccertamentoValidator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AccertamentoNuovoDto.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		AccertamentoNuovoDto accertamentoNuovoDto = (AccertamentoNuovoDto) target;
		
		/*
		 * Nome accertamento --> Campo Obbligatorio
		 */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deNomeAccertamento", "mypivot.accertamenti.validator.requiredField");
		/*
		 * Tipo dovuto --> Campo Obbligatorio
		 */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codTipoDovuto", "mypivot.accertamenti.validator.requiredField");
		
		/*
		 * Nome accertamento --> Numero massimo caratteri
		 */
		if (StringUtils.hasText(accertamentoNuovoDto.getDeNomeAccertamento()) && accertamentoNuovoDto.getDeNomeAccertamento().length() > 255) {
			errors.rejectValue("deNomeAccertamento", "mypivot.accertamenti.validator.lengthTextTooLong");
		}
	}
}
