/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivotsb.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.regioneveneto.mygov.payment.mypivot.controller.command.CruscottoIncassiAccertamentiCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.CruscottoIncassiCapitoliCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAccertamentiInputDto;

/**
 * Classe per la validazione dei campi dei form delle Statistiche.
 * 
 * @author Marianna Memoli
 */
@Component
public class CruscottoIncassiValidator implements Validator {

	public CruscottoIncassiValidator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CruscottoIncassiAccertamentiCommand.class) || clazz.equals(CruscottoIncassiCapitoliCommand.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		/**
		 * Form Statistica: Totali per Accertamenti
		 */
		if(target instanceof CruscottoIncassiAccertamentiInputDto){
			/* */
			CruscottoIncassiAccertamentiInputDto command = (CruscottoIncassiAccertamentiInputDto) target;
		
			/**
			 * Codice Tipo Dovuto --> Campo Obbligatorio
			 */
			if(!StringUtils.hasText(command.getCodiceTipoDovuto())) 
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceTipoDovuto", "mypivot.statistiche.validator.requiredField");
			
			/**
			 * Codice Ufficio --> Campo Obbligatorio
			 */
			if(StringUtils.hasText(command.getCodiceTipoDovuto()) && !StringUtils.hasText(command.getCodiceUfficio())) 
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceUfficio", "mypivot.statistiche.validator.requiredField");
			
			/**
			 * Codice Capitolo --> Campo Obbligatorio
			 */
			if(StringUtils.hasText(command.getCodiceTipoDovuto()) && StringUtils.hasText(command.getCodiceUfficio()) && !StringUtils.hasText(command.getCodiceCapitolo())) 
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceCapitolo", "mypivot.statistiche.validator.requiredField");
		}
		
		/**
		 * Form Statistica: Totali per Capitoli
		 */
		if(target instanceof CruscottoIncassiCapitoliCommand){
			/* */
			CruscottoIncassiCapitoliCommand command = (CruscottoIncassiCapitoliCommand) target;
			/**
			 * Dovuto e Ufficio --> Campi Obbligatori
			 */
			if(!StringUtils.hasText(command.getCodiceTipoDovuto()) && !StringUtils.hasText(command.getCodiceUfficio()))
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceTipoDovuto", "mypivot.statistiche.validator.requiredFields");
		}
	}
}