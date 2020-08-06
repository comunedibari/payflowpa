/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoSceltaUfficioCapitoloCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * Classe per la validazione dei campi del form per lo spacchettamento della RT in capitoli/accertamenti.
 * 
 * @author Marianna Memoli
 */
@Component
public class AccertamentoSceltaUfficioCapitoloValidator implements Validator {

	public AccertamentoSceltaUfficioCapitoloValidator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AccertamentoSceltaUfficioCapitoloCommand.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		AccertamentoSceltaUfficioCapitoloCommand command = (AccertamentoSceltaUfficioCapitoloCommand) target;
		
		/**
		 * Codice Ufficio --> Campo Obbligatorio
		 */
		if(!StringUtils.hasText(command.getCodUfficio())) 
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codUfficio", "mypivot.accertamenti.validator.requiredField");
		
		/**
		 * Anno di esercizio --> Campo Obbligatorio
		 */
		if(StringUtils.hasText(command.getCodUfficio()) && !StringUtils.hasText(command.getAnnoEsercizio())) 
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "annoEsercizio", "mypivot.accertamenti.validator.requiredField");
		
		/**
		 * Codice Capitolo --> Campo Obbligatorio
		 */
		if(StringUtils.hasText(command.getCodUfficio()) && StringUtils.hasText(command.getAnnoEsercizio()) && !StringUtils.hasText(command.getCodCapitolo())) 
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codCapitolo", "mypivot.accertamenti.validator.requiredField");

		/**
		 * Importo --> Campo Obbligatorio
		 */
		if(StringUtils.hasText(command.getCodUfficio()) && StringUtils.hasText(command.getAnnoEsercizio()) && StringUtils.hasText(command.getCodCapitolo()) && !StringUtils.hasText(command.getImporto())) 
			errors.rejectValue("importo", "mypivot.accertamenti.validator.requiredField");
		else
			/**
			 * Importo --> deve essere minore o uguale alla differenza tra l'importo totale dei dovuti e l'importo totale assegnato ai capitoli
			 * fino a questo momento.
			 */
			if (StringUtils.hasText(command.getImporto())){
				// calcolo importo rimanente
				BigDecimal diff = command.getTotImportoDovuti().subtract(command.getTotImportoCapitoli());
				try{
					/**
					 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					 */
					if(new BigDecimal(command.getImporto()).compareTo(BigDecimal.ZERO) == 0)
						errors.rejectValue("importo", "mypivot.accertamenti.validator.importo.zero", "mypivot.accertamenti.validator.importo.zero");
					else
						if(new BigDecimal(command.getImporto()).compareTo(diff) > 0)
							errors.rejectValue("importo", "mypivot.accertamenti.validator.importo", new Object[]{Constants.NUMBER_FORMAT_US.format(diff)}, "mypivot.accertamenti.validator.importo");
				
				}catch(NumberFormatException e){
					errors.rejectValue("importo", "mypivot.accertamenti.validator.number");
				}
			}
		 
		/**
		 * Verifico se bloccare l'inserimento di righe duplicate, ossia che hanno le combo valorizzate tutte allo stesso modo. 
		 */
		AccertamentoUfficioCapitoloDto obj = new AccertamentoUfficioCapitoloDto();
		obj.setCodUfficio(command.getCodUfficio());
		obj.setDeAnnoEsercizio(command.getAnnoEsercizio());
		obj.setCodCapitolo(command.getCodCapitolo());
		obj.setCodAccertamento(StringUtils.hasText(command.getCodAccertamento()) ? command.getCodAccertamento() : Constants.CODICE_NOT_AVAILABLE);
		/*
		 */
		if(command.getUfficiTO().contains(obj)) errors.rejectValue("duplicateEntry", "mypivot.accertamenti.validator.duplicateEntry", "mypivot.accertamenti.validator.duplicateEntry");
	}
}
