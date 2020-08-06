package it.regioneveneto.mygov.payment.mypivot.controller.validator;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand;
import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaUfficioCapitoloAccertamentoRepositoryDao;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * Classe per la validazione dei campi per anagraficaUfficioCapitoloAccertamento
 * 
 * @author Alessandro Paolillo
 *
 */
@Component
public class AnagraficaUfficioCapitoloAccertamentoValidator implements Validator{
	
	@Autowired
	AnagraficaUfficioCapitoloAccertamentoRepositoryDao repositoryDao;
	
	public AnagraficaUfficioCapitoloAccertamentoValidator(){
		super();
	}

	/**
	 * 
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AnagraficaUfficioCapitoloAccertamentoCommand.class);
	}

	
	@Override
	public void validate(Object target, Errors errors) {
		
		AnagraficaUfficioCapitoloAccertamentoCommand anagraficaCommand = (AnagraficaUfficioCapitoloAccertamentoCommand) target;
		
		String codTipoDovuto = anagraficaCommand.getCodTipoDovutoDto();
		if (codTipoDovuto != null && !codTipoDovuto.equals("null") && !codTipoDovuto.equals(""))
			anagraficaCommand.setCodTipoDovuto(codTipoDovuto);
		
		//DATI OBBLIGATORI
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codTipoDovuto", "mypivot.anagrafica.validator.requiredField");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceCapitolo", "mypivot.anagrafica.validator.requiredField");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "denominazioneCapitolo", "mypivot.anagrafica.validator.requiredField");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "annoEsercizio", "mypivot.anagrafica.validator.requiredField");
		
		//LUNGHEZZA MASSIMA
		
		/**
		 * Codice Ufficio
		 */
		if(StringUtils.hasText(anagraficaCommand.getCodiceUfficio()) && anagraficaCommand.getCodiceUfficio().length() > 64)
			errors.rejectValue("codiceUfficio", "mypivot.anagrafica.validator.lengthTextTooLong64");
		/**
		 * Denominazione Ufficio
		 */
		if(StringUtils.hasText(anagraficaCommand.getDenominazioneUfficio()) && anagraficaCommand.getDenominazioneUfficio().length() > 512)
			errors.rejectValue("denominazioneUfficio", "mypivot.anagrafica.validator.lengthTextTooLong512");
		
		/**
		 * Codice Capitolo
		 */
		if(StringUtils.hasText(anagraficaCommand.getCodiceCapitolo()) && anagraficaCommand.getCodiceCapitolo().length() > 64)
			errors.rejectValue("codiceCapitolo", "mypivot.anagrafica.validator.lengthTextTooLong64");
		
		/**
		 * Denominazione Capitolo
		 */
		if(StringUtils.hasText(anagraficaCommand.getDenominazioneCapitolo()) && anagraficaCommand.getDenominazioneCapitolo().length() > 512)
			errors.rejectValue("denominazioneCapitolo", "mypivot.anagrafica.validator.lengthTextTooLong512");
		
		/**
		 * Anno Esercizio
		 */
		if(StringUtils.hasText(anagraficaCommand.getAnnoEsercizio()) && anagraficaCommand.getAnnoEsercizio().length() != 4)
			errors.rejectValue("annoEsercizio", "mypivot.anagrafica.validator.lengthTextDifferent4");
		
		/**
		 * Codice Accertamento
		 */
		if(StringUtils.hasText(anagraficaCommand.getCodiceAccertamento()) && anagraficaCommand.getCodiceAccertamento().length() > 64)
			errors.rejectValue("codiceAccertamento", "mypivot.anagrafica.validator.lengthTextTooLong64");
		
		/**
		 * Denominazione Accertamento
		 */
		if(StringUtils.hasText(anagraficaCommand.getDenominazioneAccertamento()) && anagraficaCommand.getDenominazioneAccertamento().length() > 512)
			errors.rejectValue("denominazioneAccertamento", "mypivot.anagrafica.validator.lengthTextTooLong512");
		
		//SOLO NUMERICO
		if(StringUtils.hasText(anagraficaCommand.getAnnoEsercizio())){
			try{
				int iCheck = Integer.parseInt(anagraficaCommand.getAnnoEsercizio());
			}catch(NumberFormatException e) 
				{ errors.rejectValue("annoEsercizio", "mypivot.anagrafica.validator.onlyNumber"); }
		}
		
		
		//CARATTERI SPECIALI
		/**
		 * Codice Ufficio
		 */
		if (StringUtils.hasText(anagraficaCommand.getCodiceUfficio()) ){
			String codUff = anagraficaCommand.getCodiceUfficio();
			String temp="";
			try {
				temp = new String(codUff.getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				errors.rejectValue("codiceUfficio", "mypivot.anagrafica.validator.errorCaracter");
			}
			if (!temp.equals(codUff))
				errors.rejectValue("codiceUfficio", "mypivot.anagrafica.validator.errorCaracter");
		}
		
		/**
		 * Denominazione Ufficio
		 */
		if (StringUtils.hasText(anagraficaCommand.getDenominazioneUfficio()) ){
			String deUff = anagraficaCommand.getDenominazioneUfficio();
			String temp="";
			try {
				temp = new String(deUff.getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				errors.rejectValue("denominazioneUfficio", "mypivot.anagrafica.validator.errorCaracter");
			}
			if (!temp.equals(deUff))
				errors.rejectValue("denominazioneUfficio", "mypivot.anagrafica.validator.errorCaracter");
		}
		
		/**
		 * Codice Capitolo
		 */
		if (StringUtils.hasText(anagraficaCommand.getCodiceCapitolo()) ){
			String codCap = anagraficaCommand.getCodiceCapitolo();
			String temp="";
			try {
				temp = new String(codCap.getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				errors.rejectValue("codiceCapitolo", "mypivot.anagrafica.validator.errorCaracter");
			}
			if (!temp.equals(codCap))
				errors.rejectValue("codiceCapitolo", "mypivot.anagrafica.validator.errorCaracter");
		}
		
		/**
		 * Denominazione Capitolo
		 */
		if (StringUtils.hasText(anagraficaCommand.getDenominazioneCapitolo()) ){
			String deCap = anagraficaCommand.getDenominazioneCapitolo();
			String temp="";
			try {
				temp = new String(deCap.getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				errors.rejectValue("denominazioneCapitolo", "mypivot.anagrafica.validator.errorCaracter");
			}
			if (!temp.equals(deCap))
				errors.rejectValue("denominazioneCapitolo", "mypivot.anagrafica.validator.errorCaracter");
		}
		
		/**
		 * Codice Accertamento
		 */
		if (StringUtils.hasText(anagraficaCommand.getCodiceAccertamento()) ){
			String codAcc = anagraficaCommand.getCodiceAccertamento();
			String temp="";
			try {
				temp = new String(codAcc.getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				errors.rejectValue("codiceAccertamento", "mypivot.anagrafica.validator.errorCaracter");
			}
			if (!temp.equals(codAcc))
				errors.rejectValue("codiceAccertamento", "mypivot.anagrafica.validator.errorCaracter");
		}
		
		/**
		 * Denominazione Accertamento
		 */
		if (StringUtils.hasText(anagraficaCommand.getDenominazioneAccertamento()) ){
			String deAcc = anagraficaCommand.getDenominazioneAccertamento();
			String temp="";
			try {
				temp = new String(deAcc.getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				errors.rejectValue("denominazioneAccertamento", "mypivot.anagrafica.validator.errorCaracter");
			}
			if (!temp.equals(deAcc))
				errors.rejectValue("denominazioneAccertamento", "mypivot.anagrafica.validator.errorCaracter");
		}
		
		//CONTROLLI DENOMINAZIONI COERENTI
		if(anagraficaCommand.getAnagraficaUffCapAccId() == null){
			
			Long enteId = SecurityContext.getEnte().getId();
			String codiceUfficio = anagraficaCommand.getCodiceUfficio();
			String codiceCapitolo = anagraficaCommand.getCodiceCapitolo();
			String codiceAccertamento = anagraficaCommand.getCodiceAccertamento();
			
			if(StringUtils.hasText(codiceUfficio)){
				String denominazioneUfficio = anagraficaCommand.getDenominazioneUfficio();
				String denPre = repositoryDao.getDeUfficioByIdEnteAndCodUfficio(enteId, codiceUfficio);
				if(denPre!= null && !(denPre).equals(denominazioneUfficio))
					errors.rejectValue("denominazioneUfficio", "mypivot.anagrafica.validator.incoerenzaDenominazione");
				if(StringUtils.hasText(codiceCapitolo)){
					String denominazioneCapitolo = anagraficaCommand.getDenominazioneCapitolo();
					denPre = repositoryDao.getDeCapitoloByIdEnteAndCodUfficioAndCodCapitolo(enteId, codiceUfficio, codiceCapitolo);
					if(denPre!= null && !(denPre).equals(denominazioneCapitolo))
						errors.rejectValue("denominazioneCapitolo", "mypivot.anagrafica.validator.incoerenzaDenominazione");
					if(StringUtils.hasText(codiceAccertamento)){
						String denominazioneAccertamento = anagraficaCommand.getDenominazioneAccertamento();
						denPre = repositoryDao.getDeAccertamentoByIdEnteAndCodUfficioAndCodCapitoloAndCodAccertamento(enteId, codiceUfficio, codiceCapitolo, codiceAccertamento);
						if(denPre!= null && !(denPre).equals(denominazioneAccertamento))
							errors.rejectValue("denominazioneAccertamento", "mypivot.anagrafica.validator.incoerenzaDenominazione");
					}
				}
			}
		}
	}
}
