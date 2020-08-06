package it.tasgroup.addon.api.validation.constraints.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.tasgroup.addon.api.validation.CodiceFiscale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodiceFiscaleValidator implements ConstraintValidator<CodiceFiscale, String> {
	
	String msgLengthValidation;

	String msgPivaValidation;
	String msgPivaPatternValidation;

	String msgCfiscValidation;
	String msgCfiscPatternValidation;

	public final static Pattern COD_FISCALE_PATTERN = Pattern.compile("^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})|([0-9]{11})$");

	@Override
	public void initialize(CodiceFiscale codiceFiscale) {
		msgLengthValidation = codiceFiscale.messageForLengthValidation();
		
		msgPivaValidation = codiceFiscale.messageForPivaValidation();
		msgPivaPatternValidation = codiceFiscale.messageForPivaPatternValidation();
		
		msgCfiscValidation = codiceFiscale.messageForCfiscValidation();
		msgCfiscPatternValidation = codiceFiscale.messageForCfiscPatternValidation();
	}

	@Override
	public boolean isValid(String codeString, ConstraintValidatorContext context) {

		if (codeString == null || codeString.isEmpty())
			return true;
		
		// conversione della stringa in caratteri maiuscoli
		codeString = codeString.toUpperCase().trim();

		if (codeString.equals("ANONIMO"))
			return true;
		
		// verifica della lunghezza del codice fiscale
		if (codeString.length() == 16)
			return isValidCodiceFiscale(codeString, context);
			
		if (codeString.length() == 11)
			return isValidPartitaIVA(codeString, context);

		
		replaceDefaultCostraintViolationMessage(context, msgLengthValidation);
		
		return false;
	
	}
	
	private void replaceDefaultCostraintViolationMessage(ConstraintValidatorContext context, String message) {
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation().disableDefaultConstraintViolation();
	}
	
	public static boolean validateCF(String codeString){
		
		// conversione della stringa da esaminare ad una matrice di caratteri
		char[] caratteriCF = codeString.toCharArray();
		int valore = 0;
		for (int i = 0; i < caratteriCF.length - 1; i++) {
			/*
			 * somma delle posizioni pari in base ai valori corrispondenti
			 * contenuti nell'array ValoriPari (tranne l'ultimo carattere che è
			 * quello di controllo)
			 */
			if ((i + 1) % 2 == 0) {
				for (int j = 0; j < caratteri.length; j++) {

					if (caratteriCF[i] == caratteri[j]) {
						valore += valoriPari[j];
					}
				}
				/*
				 * somma delle posizioni dispari in base ai valori
				 * corrispondenti contenuti nell'array ValoriDispari
				 */
			} else {
				for (int j = 0; j < caratteri.length; j++) {
					if (caratteriCF[i] == caratteri[j]) {
						valore += valoriDispari[j];
					}
				}
			}
		}
		/*
		 * ottenimento del resto della divisione per 26 e valutazione del
		 * carattere di controllo (ultimo carattere)
		 */
		valore %= 26;
		for (int i = 0; i < 26; i++) {
			/*
			 * verifica che il valore dell'ultimo carattere corrisponda al
			 * valore ottenuto attraverso l'algoritmo di somma precedente
			 */
			if (caratteriCF[caratteriCF.length - 1] == caratteri[i]) {
				if (valore == i)
					return true;
			}
		}
		
		return false;
		
	}

	private boolean isValidCodiceFiscale(String codeString, ConstraintValidatorContext context) {
		
		// controllo formale
		Matcher m = COD_FISCALE_PATTERN.matcher(codeString);
		if (!m.matches()) {
			replaceDefaultCostraintViolationMessage(context, msgCfiscPatternValidation);
			return false;
		}
		

		if(validateCF(codeString))
			return true;

		replaceDefaultCostraintViolationMessage(context, msgCfiscValidation);
		return false;
	}

	private boolean isInteger(String codeString) {
		try {
			Long.parseLong(codeString);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private boolean isValidPartitaIVA(String codeString, ConstraintValidatorContext context) {

		int sumodd = 0;
		int last = 0;
		int i = 0;

		if (!isInteger(codeString)) {
			replaceDefaultCostraintViolationMessage(context, msgPivaPatternValidation);
			return false;
		}

		// evito il controllo per le partite iva che nelle prime 10
		// posizioni
		// contengono un valore compreso tra 0000001000 e 0273960000, con le
		// ultime tre cifre = 0
		// (per analogia con RBWin SmallTalk)

		// String primi10 = codeString.substring(0, 10);

		// if ((primi10.compareTo("0000001000")>=0) &&
		// (primi10.compareTo("0273960000")<=0) && (primi10.substring(7,
		// 10).equals("000")))
		// return true;

		// else {

		for (i = 0; i < 9; i += 2)
			sumodd += codeString.charAt(i) - '0';

		for (i = 1; i < 10; i += 2) {
			int evendigit = (2 * (codeString.charAt(i) - '0'));
			if (evendigit < 10)
				sumodd += evendigit;
			else
				sumodd += (evendigit - 9);
		}

		last = sumodd % 10;
		if (last != 0)
			last = 10 - last;

		if (last == (codeString.charAt(10) - '0')) {
			return true;
		} else {
			replaceDefaultCostraintViolationMessage(context, msgPivaValidation);
			return false;
		}

	}

	/*
	 * matrice con i caratteri dell'alfabeto
	 */
	private static char[] caratteri = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	/*
	 * creazione della matrice con i valori attribuiti ai caratteri dispari,
	 * corrispondenti alla matrice di caratteri
	 */
	private static int[] valoriDispari = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23, 1, 0, 5, 7, 9, 13, 15,
			17, 19, 21 };
	/*
	 * creazione della matrice con i valori attribuiti ai caratteri pari,
	 * corrispondenti alla matrice di caratteri
	 */
	private static int[] valoriPari = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 0, 1, 2, 3, 4, 5, 6, 7,
			8, 9 };

}
