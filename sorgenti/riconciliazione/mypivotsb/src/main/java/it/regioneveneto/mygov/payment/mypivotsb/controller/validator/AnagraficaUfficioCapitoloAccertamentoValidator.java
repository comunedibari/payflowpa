package it.regioneveneto.mygov.payment.mypivotsb.controller.validator;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaUfficioCapitoloAccertamentoRepositoryDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;

/**
 * Classe per la validazione dei campi per anagraficaUfficioCapitoloAccertamento
 * 
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
		return clazz.equals(AnagraficaUfficioCapitoloAccertamentoDto.class);
	}

	
	@Override
	public void validate(Object target, Errors errors) {
		
		AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto = (AnagraficaUfficioCapitoloAccertamentoDto) target;		
		
		String codTipoDovuto = anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto();
		if (codTipoDovuto != null && !codTipoDovuto.equals("null") && !codTipoDovuto.equals(""))
			anagraficaUfficioCapitoloAccertamentoDto.setCodTipoDovuto(codTipoDovuto);
		
		//DATI OBBLIGATORI
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codTipoDovuto", "mypivot.anagrafica.validator.requiredField");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codCapitolo", "mypivot.anagrafica.validator.requiredField");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deCapitolo", "mypivot.anagrafica.validator.requiredField");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deAnnoEsercizio", "mypivot.anagrafica.validator.requiredField");
		
		//LUNGHEZZA MASSIMA
		
		/**
		 * Codice Ufficio
		 */
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio()) && anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio().length() > 64)
			errors.rejectValue("codiceUfficio", "mypivot.anagrafica.validator.lengthTextTooLong64");
		/**
		 * Denominazione Ufficio
		 */
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio()) && anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio().length() > 512)
			errors.rejectValue("denominazioneUfficio", "mypivot.anagrafica.validator.lengthTextTooLong512");
		
		/**
		 * Codice Capitolo
		 */
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo()) && anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo().length() > 64)
			errors.rejectValue("codiceCapitolo", "mypivot.anagrafica.validator.lengthTextTooLong64");
		
		/**
		 * Denominazione Capitolo
		 */
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo()) && anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo().length() > 512)
			errors.rejectValue("denominazioneCapitolo", "mypivot.anagrafica.validator.lengthTextTooLong512");
		
		/**
		 * Anno Esercizio
		 */
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio()) && anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio().length() != 4)
			errors.rejectValue("annoEsercizio", "mypivot.anagrafica.validator.lengthTextDifferent4");
		
		/**
		 * Codice Accertamento
		 */
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento()) && anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().length() > 64)
			errors.rejectValue("codiceAccertamento", "mypivot.anagrafica.validator.lengthTextTooLong64");
		
		/**
		 * Denominazione Accertamento
		 */
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento()) && anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento().length() > 512)
			errors.rejectValue("denominazioneAccertamento", "mypivot.anagrafica.validator.lengthTextTooLong512");
		
		//SOLO NUMERICO
		if(StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio())){
			try{
				int iCheck = Integer.parseInt(anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio());
			}catch(NumberFormatException e) 
				{ errors.rejectValue("annoEsercizio", "mypivot.anagrafica.validator.onlyNumber"); }
		}
		
		
		//CARATTERI SPECIALI
		/**
		 * Codice Ufficio
		 */
		if (StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio()) ){
			String codUff = anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio();
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
		if (StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio()) ){
			String deUff = anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio();
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
		if (StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo()) ){
			String codCap = anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo();
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
		if (StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo()) ){
			String deCap = anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo();
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
		if (StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento()) ){
			String codAcc = anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento();
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
		if (StringUtils.hasText(anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento()) ){
			String deAcc = anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento();
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
		if(anagraficaUfficioCapitoloAccertamentoDto.getId() == null){
			
//			Long enteId = SecurityContext.getEnte().getId();
			Long enteId = anagraficaUfficioCapitoloAccertamentoDto.getEnte().getId();
			String codiceUfficio = anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio();
			String codiceCapitolo = anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo();
			String codiceAccertamento = anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento();
			
			if(StringUtils.hasText(codiceUfficio)){
				String denominazioneUfficio = anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio();
				String denPre = repositoryDao.getDeUfficioByIdEnteAndCodUfficio(enteId, codiceUfficio);
				if(denPre!= null && !(denPre).equals(denominazioneUfficio))
					errors.rejectValue("denominazioneUfficio", "mypivot.anagrafica.validator.incoerenzaDenominazione");
				if(StringUtils.hasText(codiceCapitolo)){
					String denominazioneCapitolo = anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo();
					denPre = repositoryDao.getDeCapitoloByIdEnteAndCodUfficioAndCodCapitolo(enteId, codiceUfficio, codiceCapitolo);
					if(denPre!= null && !(denPre).equals(denominazioneCapitolo))
						errors.rejectValue("denominazioneCapitolo", "mypivot.anagrafica.validator.incoerenzaDenominazione");
					if(StringUtils.hasText(codiceAccertamento)){
						String denominazioneAccertamento = anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento();
						denPre = repositoryDao.getDeAccertamentoByIdEnteAndCodUfficioAndCodCapitoloAndCodAccertamento(enteId, codiceUfficio, codiceCapitolo, codiceAccertamento);
						if(denPre!= null && !(denPre).equals(denominazioneAccertamento))
							errors.rejectValue("denominazioneAccertamento", "mypivot.anagrafica.validator.incoerenzaDenominazione");
					}
				}
			}
		}
	}
}
