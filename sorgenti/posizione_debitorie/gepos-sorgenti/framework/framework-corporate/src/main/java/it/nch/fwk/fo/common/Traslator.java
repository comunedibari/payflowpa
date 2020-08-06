/*
 * Creato il 8-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.common;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class Traslator {
	
	/**
	 * Timestamp to String conversion
	 * @param date
	 * @return formatted date (dd/MM/yyyy hh:mm:ss)
	 */
	public static String timestampToString(Timestamp date){
//		Cambiato il formato data che deve arrivare a Frontend. Se la cosa crea problemi ai vostri servizi contattatemi. Anselmo Talotta, 16/06/2006 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (date!=null)?sdf.format(date):"";
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static Timestamp stringToTimestamp(String string) {
		try{
			if ((string==null)||("".equalsIgnoreCase(string))||string.trim().length() == 0) return null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
			Date d = sdf.parse(string);
			
			return new Timestamp(d.getTime());
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Date to String conversion
	 * @param date
	 * @return formatted string (dd/MM/yyyy)
	 */
	public static String dateToString(Date date){
//		Cambiato il formato data che deve arrivare a Frontend 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (date!=null)?sdf.format(date):"";

	}
	/**
	 * 
	 * @param string dd/MM/yyyy
	 * @return
	 * @throws Exception
	 */
	public static Date stringToDate(String string) {
		try{
		if ((string==null)||("".equalsIgnoreCase(string))||string.trim().length() == 0) return null;	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return 	sdf.parse(string);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static String longToString(Long _long){
		if (_long==null) return null; 
		return "" + _long;
	}
	
	public static Long stringToLong(String string){
		if (string==null || string.trim().length()==0) return null; 
		return new Long(string);
	}
	
	public static String integerToString(Integer _integer){
		if (_integer==null) return null; 
		return "" + _integer;
	}
	
	public static Integer stringToInteger(String string){
		if (string==null || string.trim().length()==0) return null; 
		return new Integer(string);
	}
	
	public static String floatToString(Float _float){
		if (_float==null) return null;
		
		DecimalFormat df=null;
		
		try {
			df=(DecimalFormat)NumberFormat.getInstance(Locale.ITALIAN);
		}
		catch (Exception e){
			return null;
		}
		df.applyPattern("#,##0.00;(#,##0.00)");
		return df.format(_float);
	}
	
	public static Float stringToFloat(String string){
		if (string==null || string.length()==0) return null;
		
		Number n=null;
		
		try {
			n=NumberFormat.getInstance().parse(string);
		}
		catch (Exception e){
			return null;
		}
		
		return new Float(n.floatValue());
	}

	public static String bigDecimalToString(BigDecimal _bigDecimal){
		if (_bigDecimal==null) return null; 
		return "" + _bigDecimal;
	}
	
	public static BigDecimal stringToBigDecimal(String string){
		if (string==null || string.length()==0) return null; 
		return new BigDecimal(string);
	}

	public static String doubleToString_OLD(Double _double){
		if (_double==null) return null; 
		return "" + _double;
	}

	/**
	 * Nuova versione, gesisce (arrotondandoli con un criterio molto tartufato)
	 * gli importi che il DB2 mi restituisce con un numero sporpositato di cifre decimali.
	 * In caso di problemi ripristinare la vecchia versione
	 * 
	 * @param _double
	 * @return
	 */	
	public static String doubleToString(Double _double){
		if (_double==null) return null; 
		
		String amount = "" + _double;
		final int MIN = 2;
		final int MAX = 6;
		int numberOfMeaningfulDecimalDigits = MIN; // Mi paro nel caso il blocco seguente vada in eccezione
		try {
			numberOfMeaningfulDecimalDigits = getNumberOfMeaningfulDecimalDigits(amount, MIN, MAX);
		} catch (Exception e) {
			System.err.println("Unable to get # of meaningful decimal digits for number " + _double + ", assuming " + numberOfMeaningfulDecimalDigits);
		}
		
		String rounded = roundAmount(amount, numberOfMeaningfulDecimalDigits);
		return rounded;
	}
	
	private static int getNumberOfMeaningfulDecimalDigits(String _doubleAsString, int MIN, int MAX) {
		//
		//	Voglio sanare i casi in cui la lettura dal DB2 mi d�
		//	un numero esagerato di cifre decimali: ad esempio, salvando
		//	73.20 pu� succedere che una successiva estrazione mi dia
		//	73.19999999999999. Per capire se queste cifre sono effettivamente
		//	significative stabilisco:
		//	* un numero minimo di cifre decimali da visualizzare (MIN)
		//	* un numero massimo di cifre decimali che posso visualizzare,
		//    purch� siano significative (MAX)
		//	Per stabilire se ci sono almeno MAX cifre significative (e stabilire
		//	quante sono) applico il seguente algoritmo: ciclando (i) da MAX e arrivando a MIN,
		//	confronto il numero in input con la sua versione arrotondata alla i-esima cifra decimale,
		//	se la differenza � minore o uguale a 10^(-(i+1)) (ad esempio, il numero 73.199, per i = 3,
		//	deve essere confrontato con il suo arrotondamento alla terza cifra decimale, 73.200)
		//
		//String temp = "" + _double;
		int actual = -1;
		int decimalSeparatorPos = _doubleAsString.indexOf(".");
		if (decimalSeparatorPos >= 0) {
			//
			//	Un double convertito in stringa ha sempre una parte decimale
			//	(anche se � intero viene aggiunto .0)
			//
			if (isSciNotation(_doubleAsString)) {
				//
				//	Se dopo il separatore dei decimali c'� la E della notazione scientifica
				//	lo devo trattare separatamente
				//
				_debug(_doubleAsString + " - Il numero e' in notazione scientifica, lo converto in notazione ordinaria");
				BigDecimal bd = new BigDecimal(_doubleAsString);
				_doubleAsString = bd.toString();
				//	Questo escamotage dovrebbe avere eliminato la notazione scientifica,
				//	trasformandolo in un numero in rappresentazione ordinaria.
				//	Se ora il controllo d� false (per evitare ricorsioni infinite) rientro nel metodo stesso
				if (!isSciNotation(_doubleAsString)) {
					_debug(_doubleAsString + " - Il numero in notazione scientifica e' stato cosi' convertito, rientro nel metodo");
					return getNumberOfMeaningfulDecimalDigits(_doubleAsString, MIN, MAX);
				} else {
					// Getto la spugna
					_debug(_doubleAsString + " - Il numero in notazione scientifica, dopo la conversione in notazione ordinaria, risulta ancora in notazione scientifica");
					actual = MIN;
				}
			} else { // if (isSciNotation(_doubleAsString))
				String decimalPart = _doubleAsString.substring(decimalSeparatorPos + 1);
				//int max = MAX;
				int numberOfDecimalDigits = decimalPart.length();
				if (numberOfDecimalDigits >= MIN && numberOfDecimalDigits <= MAX) {
					//
					//	Il numero di cifre decimali � compreso (estremi inclusi) tra il minimo e il massimo 
					//	(situazione pi� comune, in particolare quella in cui coincide con il minimo)
					//	Considero tutte le cifre che mi arrivano senza fare ulteriori considerazioni
					//
					_debug(_doubleAsString + " - Il numero di cifre decimali (" + numberOfDecimalDigits + ") e' compreso (estremi inclusi) tra il minimo (" + MIN + ") e il massimo (" + MAX + ")");
					actual = numberOfDecimalDigits;
				} else { // if (numberOfDecimalDigits >= MIN && numberOfDecimalDigits <= MAX)
					//
					//	Il numero di cifre decimali NON � compreso tra il minimo e il massimo
					//
					if (numberOfDecimalDigits < MIN) {
						//
						//	Il numero di cifre � minore del minimo (ad esempio numeri interi o comunque con precisione bassa
						//	(minore del minimo). Considero comunque MIN cifre decimali (l'arrotondamento porter� all'aggiunta di zeri
						//	fino ad arrivare a MIN. Questa situazione � abbastanza comune.
						//
						_debug(_doubleAsString + " - Il numero di cifre decimali (" + numberOfDecimalDigits + ") e' minore del minimo (" + MIN + ")");
						actual = MIN;
					} else { // if (numberOfDecimalDigits < MIN
						//
						//	Questo � il caso pi� 'stravagante': ci sono pi� di MAX cifre decimali in input e devo
						//	capire quante sono effettivamente significative 
						//
						
						//
						//	Parto con le migliori intenzioni: ritengo possibile che tutte le cifre fornite (al massimo MAX) siano significative
						//	e vado scalando fino a che trovo la condizione di uscita
						//
						actual = MAX; // valore di default 
						boolean found = false;
						for (int i = MAX; i >= MIN; i--) {
							BigDecimal bd = new BigDecimal(_doubleAsString);		
							BigDecimal bdRounded = new BigDecimal(_doubleAsString);		
							String sRounded = bdRounded.setScale(i, BigDecimal.ROUND_HALF_UP).toString();
							bdRounded = new BigDecimal(sRounded);	
							double diff = bdRounded.subtract(bd).doubleValue();
							diff = Math.abs(diff);
							double compareTo = Math.pow(10, -(i + 1));
							_debug(_doubleAsString + " - Ciclo " + i + ", confronto " + diff + " con " + compareTo);
							if (diff > compareTo) {
								_debug(_doubleAsString + " - Esco dal for perche' " + diff  + " > " + compareTo);
								actual = i + 1;
								if (actual > MAX) {
									//
									//	Questa piccola patch si rende necessaria per gestire il caso (in cui ad esempio MAX = 6)
									//	il numero in input � del tipo 73.68778123414234 (che mi darebbe actual = 7)
									//
									actual = MAX;
								}
								found = true;
								break;
							}
						}
						if (!found) {
							_debug(_doubleAsString + " - Nessuna cifra significativa oltre la " + MIN);
							actual = MIN;
						}
					}
				}
			}
		} else { //if (decimalSeparatorPos >= 0)
			//
			// Non dovrebbe succedere, comunque significherebbe che � un numero intero
			//
			_debug(_doubleAsString + " e' intero");
			actual = MIN;
		}
		_debug(_doubleAsString + " - Il numero di cifre significative dopo la virgola e' " + actual);
		return actual;
	}
	
	private static void _debug(String string) {
		final boolean DEBUG = false;
		if (DEBUG)
			System.out.println(string);
	}

	public static boolean isSciNotation(String _doubleAsString) {
		boolean isSciNotation = false;
		int decimalSeparatorPos = _doubleAsString.indexOf(".");
		if (decimalSeparatorPos >= 0) {
			int posE = _doubleAsString.indexOf("E", decimalSeparatorPos);
			isSciNotation = ( posE >= 0);
		}
		return isSciNotation;
	}

	public static Double stringToDouble(String string){	
		if (string==null || string.length()==0) return null; 
		return new Double(string);
	}
	
	public static String roundAmount(String amount, int numberOfMeaningfulDecimalDigits){
		BigDecimal bd = new BigDecimal(amount);		
		return bd.setScale(numberOfMeaningfulDecimalDigits, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	public static void main(String[] args) {
		try {
			assertRounded("73",					"73.00");
			assertRounded("73.1",				"73.10");
			assertRounded("73.19",				"73.19");
			assertRounded("73.99",				"73.99");
			assertRounded("73.999",				"73.999");
			assertRounded("73.127",				"73.127");
			assertRounded("73.0045",			"73.0045");
			assertRounded("73.00451",			"73.00451");
			assertRounded("73.004519",			"73.004519");
			assertRounded("73.0045199",			"73.00452");
			assertRounded("73.00451999",		"73.00452");
			assertRounded("73.123999",			"73.123999");
			assertRounded("73.1239999",			"73.124");
			assertRounded("73.12399999",		"73.124");
			assertRounded("73.123999999",		"73.124");
			assertRounded("73.1239999999",		"73.124");
			assertRounded("73.19999999999999",	"73.20");
			assertRounded("73.19999799999999",	"73.199998");
			assertRounded("73.1999979999",		"73.199998");
			assertRounded("73.68778123414234",	"73.687781");
			assertRounded("73.68778193414234",	"73.687782");
			assertRounded("5.2E9",				"5200000000.00");
			assertRounded("5.3E8",				"530000000.00");
			assertRounded("6.78E-4",			"0.000678");
			assertRounded("1.2345678E-3",		"0.001235");
			
			System.out.println("Tutti i test case eseguiti con il risultato atteso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void assertRounded(String original, String expectedRounded) throws Exception {
		String lostInTranslation = doubleToString(new Double(original));
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		if (!lostInTranslation.equals(expectedRounded)) {
			throw new Exception("Mi aspettavo che l'arrotondamento di " + original + " desse " + expectedRounded + " e invece ho ottenuto " + lostInTranslation);
		}
	}

	
}