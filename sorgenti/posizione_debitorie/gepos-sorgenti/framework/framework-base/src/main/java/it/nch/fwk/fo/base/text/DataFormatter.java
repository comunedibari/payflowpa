/*
 * File: DataFormatter.java
 * Package: com.etnoteam.service.text
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 16-lug-03 - 19.25.56
 * Created by: dcerri (Etnoteam)
 */

package it.nch.fwk.fo.base.text;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author dcerri
 *
 * <br>
 * Classe padre dei formatter specifici per LINGUA e CANALE.
 * Visto che is prevede un formattatore per sessione utente, nelle implementazioni
 * dei metodi &egrave necessario prevedere una sincronizzazione.
 * 
**/
public abstract class DataFormatter implements Serializable, DataFormatterInterface {
	
	/** Logger privato alla classe. */
    private static Logger logger = Logger.getLogger(DataFormatter.class);
	
	/* hashtable contenente i parametri di configurazione */
	private static Map formatterConfiguration = new Hashtable();
	
	protected String bank;
	protected String channel;
	protected String language;
	
	/* prefisso di ricerca nella struttura hash */
	private String prefix = null;
	/* Locale corrente */
	private Locale currentLocale = null;
	
	/* costruttore protetto */
	protected DataFormatter(String prefix, Locale loc) {
		this.prefix = prefix;
		this.currentLocale = loc;
	}	

	/**
	 * Formattazione quantita' intera (implementazione base, senza pattern di formattazione con separatore di migliaia)
	 * @param value valore 
	 * @return String quantita' formattata
	 * @deprecated utilizzare formatQtyInteger
	 */
	public synchronized String formatIntAmount(int value) {
		return String.valueOf(value);
	}

	/**
	 * Formattazione quantita' intera (implementazione base)
	 * @param value valore 
	 * @return String quantita' formattata
	 * @deprecated utilizzare formatQtyInteger
	 */
	public synchronized String formatIntAmount(Integer value) {
		return formatIntAmount(value.intValue());
	}

	
	/**
	 * Parsing del valore intero specificato (implementazione base)
	 * (la stringa deve rappresentare un numero privo di formattazione funzione di uno specifico Locale)
	 * @param value valore
	 * @return int valore parsato
	 * @throws Exception
	 */
	public synchronized int parseInt(String value) throws Exception {
		return Integer.parseInt(value);
	}
	
	/**
	 * Parsing del valore decimale specificato (implementazione base)
	 * (la stringa deve rappresentare un numero privo di formattazione funzione di uno specifico Locale)
	 * @param value valore
	 * @return BigDecimal valore parsato
	 * @throws Exception
	 */
	public abstract BigDecimal parseBigDecimal(String value) throws Exception;

	/**
	 * Parsing del valore decimale specificato (implementazione base)
	 * (la stringa deve rappresentare un numero privo di formattazione funzione di uno specifico Locale)
	 * @param value valore
	 * @param defaultValue valore restituito nel caso di errori di parsing
	 * @return BigDecimal valore parsato
	 */
	public abstract BigDecimal parseBigDecimal(String value,BigDecimal defaultValue);

	/**
     * Formattazione quantita' percentuale (segno, due decimali e simbolo '%')
     * pattern di definizione nel file XML: PERCENTAGE
     * @param value valore 
     * @return String quantita' formattata
     */
    public  abstract String formatPercentage(BigDecimal value);

	/**
     * Formattazione quantita' percentuale positiva (senza segno, due decimali e simbolo '%')
     * pattern di definizione nel file XML: PERCENTAGE
     * @param value valore 
     * @return String quantita' formattata
     */
    public  abstract String formatPositivePercentage(BigDecimal value);

	/**
     * Formattazione quantita' percentuale (segno, due decimali e simbolo '%')
     * La differenza rispetto a formatPercentage e' che qui il dato viene considerato
     * unitario ( 1 = "100%" ).
     * pattern di definizione nel file XML: PERCUNIT
     * @param value valore 
     * @return String quantita' formattata
     */
    public  abstract String formatUnitPercentage(BigDecimal value);
    
	/**
     * Formattazione quantita' percentuale (segno, due decimali e simbolo '%')
     * La differenza rispetto a formatPercentage e' che qui il dato viene considerato
     * unitario ( 1 = "100%" ).
     * pattern di definizione nel file XML: PERCUNIT
     * @param value valore 
     * @return String quantita' formattata
     */
    public  abstract String formatUnitPercentage(String value);

	/**
     * Formattazione quantita' percentuale (segno, due decimali e simbolo '%')
     * La differenza rispetto a formatPercentage e' che qui il dato viene considerato
     * unitario ( 1 = "100%" ).
     * pattern di definizione nel file XML: PERCUNIT
     * @param value valore 
     * @param dec sub_type (e' possibile personalizzare il numero di decimali da XML)
	 * @return String quantita' formattata
     */
    public  abstract String formatUnitPercentage(BigDecimal value, String dec);

	/**
     * Formattazione quantita' percentuale (segno, due decimali e simbolo '%')
     * La differenza rispetto a formatPercentage e' che qui il dato viene considerato
     * unitario ( 1 = "100%" ).
     * pattern di definizione nel file XML: PERCUNIT
     * @param value valore 
     * @param dec sub_type (e' possibile personalizzare il numero di decimali da XML)
	 * @return String quantita' formattata
     */
    public  abstract String formatUnitPercentage(String value, String dec);
    
     /**
     * Formattazione quantita' percentuale (segno, due decimali e simbolo '%')
     * pattern di definizione nel file XML: PERCENTAGE
     * @param value valore 
     * @return String quantita' formattata
     */
    public  abstract String formatPercentage(String value);

    
    /**
	 * Formattazione quantita' decimale
	 * @param value valore 
	 * @return String quantita' formattata
	 */
	public  abstract String formatDecimalAmount(BigDecimal value);

	/**
	 * Formattazione quantita' decimale
	 * pattern di definizione nel file XML: DECIMAL
	 * @param value valore 
	 * @return String quantita' formattata
	 */
	public abstract String formatDecimalAmount(String value);
	
	
	/**
	 * Formattazione quantita' decimale
	 * pattern di definizione nel file XML: DECIMAL
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatDecimalAmount(double value);
	

    /**
	 * Formattazione quantita' decimale con tre cifre decimali
	 * pattern di definizione nel file XML: DECIMAL3
	 * @param value valore 
	 * @return String quantita' formattata
	 */
	public abstract String formatDecimal3Amount(BigDecimal value);


	/**
	 * Formattazione quantita' decimale con tre cifre decimali
	 * pattern di definizione nel file XML: DECIMAL3
	 * @param value valore 
	 * @return String quantita' formattata
	 */
	public abstract String formatDecimal3Amount(String value);
	

	/**
	 * Formattazione quantita' decimale con quatro cifre decimali
	 * @param value valore 
	 * @return String quantita' formattata
	 */
	public abstract String formatDecimal4Amount(String value);

    /**
     * Formattazione quantita' decimale con quatro cifre decimali
     * @param value valore 
     * @return String quantita' formattata
     */
    public abstract String formatDecimal4Amount(BigDecimal value);

    /**
     * Formattazione quantita' decimale con due cifre decimali
     * @param value valore 
     * @return String quantita' formattata
     */
    public abstract String formatDecimal2Amount(String value);


    /**
     * Formattazione quantita' decimale con due cifre decimali
     * @param value valore 
     * @return String quantita' formattata
     */
    public abstract String formatDecimal2Amount(BigDecimal value);

	
	/**
	 * Formattazione quantita' decimale con tre cifre decimali
	 * pattern di definizione nel file XML: DECIMAL3
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatDecimal3Amount(double value);
	
	
	/**
	 * Formattazione valore decimale (credito), segno stampato come prefisso.
	 * pattern di definizione nel file XML: CREDIT
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatCredit(BigDecimal value);
	
	
	/**
	 * Formattazione valore decimale (credito),segno stampato come prefisso.
	 * pattern di definizione nel file XML: CREDIT
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatCredit(String value);
	
	
	/**
	 * Formattazione valore decimale (credito),segno stampato come prefisso.
	 * pattern di definizione nel file XML: CREDIT
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatCredit(double value);
	
	
	/**
	 * Formattazione valore decimale (debito),segno stampato come postfisso.
	 * pattern di definizione nel file XML: DEBIT
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatDebit(double value);
	
		
	/**
	 * Formattazione valore decimale (debito),segno stampato come postfisso.
	 * pattern di definizione nel file XML: DEBIT
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatDebit(String value);
	
	
	/**
	 * Formattazione valore decimale (debito),segno stampato come postfisso.
	 * pattern di definizione nel file XML: DEBIT
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatDebit(BigDecimal value);
	
	
	
	/**
	 * Formattazione di un prezzo, per la divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * pattern di definizione nel file XML: CCY (specifico per una divisa)
	 * @param value valore
	 * @param ccy_code codice ISO divisa
	 * @return String prezzo formattato
	 */
	public abstract String formatPrice(BigDecimal value, String ccy_code);
	
	/**
	 * Formattazione a tronkamento di un prezzo, per la divisa specificata
	 * pattern di definizione nel file XML: TRUNC_PRICE
	 * pattern di definizione nel file XML: CCY (specifico per una divisa)
	 * @param value valore
	 * @param ccy_code codice ISO divisa
	 * @return String prezzo formattato
	 */
	public abstract String formatPriceTruncated(BigDecimal value, String ccy_code);
		
	/**
	 * Formattazione di un prezzo, per la divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * pattern di definizione nel file XML: CCY (specifico per una divisa)
	 * @param value valore
	 * @param ccy_code codice ISO divisa
	 * @return String prezzo formattato
	 */
	public abstract String formatPrice(String value, String ccy_code);
	
	/**
	 * Method formatPrice, per la divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * pattern di definizione nel file XML: CCY (specifico per una divisa)
	 * @param value valore 
	 * @param ccy_code codice ISO divisa
	 * @return String prezzo formattato
	 */
	public abstract String formatPrice(double value, String ccy_code);
	
	/**
	 * Formattazione di un prezzo, senza divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * @param value valore
	 * @return String prezzo formattato
	 */
	public abstract String formatPrice(String value);
	
	/**
	 * Formattazione di un prezzo,senza divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * @param value valore
	 * @return String prezzo formattato
	 */
	public abstract String formatPrice(BigDecimal value);
	
	/**
	 * Formattazione di un prezzo, per la divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * @param value valore
	 * @return String prezzo formattato - divisa
	 */
	public  abstract String formatPriceAndCcy(BigDecimal value, String Ccy);

	/**
	 * Formattazione di un prezzo, per la divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * @param value valore
	 * @return String prezzo formattato - divisa
	 */
	public abstract String formatPriceAndCcy(String value, String Ccy);
	
	
	/**
	 * Formattazione di un prezzo, senza divisa specificata
	 * pattern di definizione nel file XML: PRICE (default)
	 * @param value valore
	 * @return String prezzo formattato
	 */
	public abstract String formatPrice(double value);
		
	/**
	 * Formattazione di una data
	 * pattern di definizione nel file XML: DATE 
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDate(Date value);
		
	/**
	 * Formattazione di una data
	 * pattern di definizione nel file XML: DATE
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDate(String value) throws Exception;
	
	/**
	 * Formattazione di una data
	 * pattern di definizione nel file XML: DATEYYYY
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDateYYYY(Date value);
	
	/**
	 * Formattazione di una data
	 * pattern di definizione nel file XML: DATEYYYY
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDateYYYY(String value) throws Exception;
	
	/**
	 * Formattazione di un'ora
	 * pattern di definizione nel file XML: TIME
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatTime(Date value);

	/**
	 * Formattazione di un'ora
	 * pattern di definizione nel file XML: TIME
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatTime(String value) throws Exception;
	
    /**
     * Formattazione di una data in modo letterale
     * pattern di definizione nel file XML: LITERALDATE
     * @param value valore
     * @return String data formattata
     */
    public abstract String formatLiteralDate(Date value);

	/**
	 * Formattazione di una data + ora
	 * pattern di definizione nel file XML: DATETIME
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDateTime(Date value);

	/**
	 * Formattazione di una data + ora
	 * pattern di definizione nel file XML: DATETIME
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDateTime(String value) throws Exception;
	
	/**
	 * Formattazione di una data in formato DDMMYYYY
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDateDDMMYYYY(Date value);

	/**
	 * Formattazione di una data in formato YYYYMMDD
	 * @param value valore
	 * @return String data formattata
	 */
	public abstract String formatDateYYYYMMDD(Date value);
	
	 /**
     * Formattazione di una data mese in modo letterale
     * pattern di definizione nel file XML: MONTHNAME
     * @param value valore
     * @return String data formattata
     */
    public abstract String formatNameMonthDate(Date value);

	 /**
     * Formattazione di una data in formato DD MMMM
     * pattern di definizione nel file XML: DAYMONTH
     * @param value valore
     * @return String data formattata
     */
    public abstract String formatDateDDMMMM(Date value);
    
	/**
     * Formattazione di una data in formato MMM YY
     * pattern di definizione nel file XML: MONTHYEAR
     * @param value valore
     * @return String data formattata
	 */
	public abstract String formatDateMMMYY(Date value);

    /**
     * Formattazione di una data mese in modo letterale
     * pattern di definizione nel file XML: MONTHNAME
     * @param value valore
     * @return String data formattata
     */
    public abstract String formatNameMonthDate(String value) throws Exception;
	
	
	/**************************************************************************/
	/**
	 * Formattazione di un tasso di cambio (4 cifre decimali)
	 * pattern di definizione nel file XML: EXCHANGERATE
	 * @param value valore
	 * @return String tasso di cambio formattato
	 */
	public abstract String formatExchangeRate(BigDecimal value);
	
	/**
	 * Restituisce il numero del giorno (all'interno del mese)
	 * @param value valore
	 * @return String giorno del mese
	 */
	public abstract String formatDay(Date value);
	
	
	/**
	 * Restituisce il numero del mese (all'interno dell'anno)
	 * @param value valore
	 * @return String mese dell'anno
	 */
	public abstract String formatMonth(Date value);
	
	
	/**
	 * Restituisce l'anno della data specificata
	 * @param value valore
	 * @return String anno
	 */
	public abstract String formatYear(Date value);
	
	
	/**
	 * Formattazione standard per prezziTitolo in EUR (quattro cifre decimali)
	 * pattern di definizione nel file XML: STOCKPRICE, CCY = EUR
	 * @param value valore
	 * @return String valore con formattazione stile EURO
	 */
	public abstract String formatEURCcyStockPrice(BigDecimal value);
	
	/**
	 * Formattazione standard per prezziFondo in EUR (tre cifre decimali)
	 * pattern di definizione nel file XML: FUNDPRICE, CCY = EUR
	 * @param value valore
	 * @return String valore con formattazione stile EURO
	 */
	public abstract String formatEURCcyFundPrice(BigDecimal value);
	
	/**
	 * Formattazione standard per ammontari in EUR (due cifre decimali)
	 * pattern di definizione nel file XML: PRICE, CCY = EUR
	 * @param value valore
	 * @return String valore con formattazione stile EURO
	 */
	public abstract String formatEURCcyAmount(BigDecimal value);
	
	/**
     * Formattazione standard per ammontari a seconda della divisa
     * Pattern di definizione nel file XML: PRICE
	 * @param value
	 * @param ccy
	 * @return String
	 */
    public abstract String formatCcyAmount(BigDecimal value, String ccy);
        
	/**
	 * Formattazione standard per ammontari espressi in divise NON EUR (tre cifre decimali)
	 * pattern di definizione nel file XML: PRICE, CCY di default
	 * @param value valore
	 * @return String valore con formattazione stile divisa estera
	 */
	public abstract String formatForeignCcyAmount(BigDecimal value);
	
	/**
	 * Formattazione standard per quantita' decimali
	 * con decimali opzionali
	 * pattern di definizione nel file XML: QTY
	 * @param value valore
	 * @return String valore formattato
	 */
	public abstract String formatQty(BigDecimal value);
	
	/**
	 * Formattazione standard per quantita' decimali
	 * pattern di definizione nel file XML: QTY_DEC
	 * @param value valore
	 * @return String valore formattato
	 */
	public abstract String formatQtyDecimal(BigDecimal value);
	
	/**
	 * Formattazione standard per quantita' intere
	 * pattern di definizione nel file XML: QTY_INT
	 * @param value valore
	 * @return String valore formattato
	 */
	public abstract String formatQtyInteger(Integer value);

    /**
     * Formattazione standard per quantita' intere
     * pattern di definizione nel file XML: QTY_INT
     * Arrotonda il valore decimale in ingresso
     * @param value valore
     * @return String valore formattato
     */
    public abstract String formatQtyInteger(BigDecimal value);
    
    /**
     * Formattazione standard per importo intero
     * pattern di definizione nel file XML: QTY_INT
     * @param value valore
     * @return String valore formattato
     */
    public abstract String formatAmountInteger(BigDecimal value);

	/**
	 * Formattazione standard per quantita' intere
	 * pattern di definizione nel file XML: QTY_INT
	 * @param value valore
	 * @return String valore formattato
	 */
	public abstract String formatQtyInteger(int value);
	
	
	/**
	 * Formattazione valore decimale, segno sempre presente  stampato come prefisso.
	 * (utilizzato per totali con segno)
	 * pattern di definizione nel file XML: TOTAL_SIGNED
	 * @param value valore
	 * @return String quantita' formattata
	 */
	public abstract String formatTotalSigned(BigDecimal value);
				
	/***************************************************************************/
		

	/**
	 *  Parsing della data specificata
	 * @param date valore
	 * @return Date valore parsato
	 * @throws Exception
	 */
	public abstract Date parseDate(String date) throws Exception;
	
	/**
	 *  Parsing della data specificata
	 * @param date valore
	 * @return Date valore parsato
	 * @throws Exception
	 */
	public abstract Date parseDateYYYY(String date) throws Exception;
	
	/**
	 *  Parsing della data specificata
	 * @param date valore
	 * @return Date valore parsato
	 * @throws Exception
	 */
    public abstract Date toDate(String date) throws Exception;
	

	/**
	 *  Parsing dell'ora specificata
	 * @param date valore
	 * @return Date valore parsato
	 * @throws Exception
	 */
	public abstract Date parseTime(String date) throws Exception;

	/**
	 *  Parsing della data/ora specificata
	 * @param date valore
	 * @return Date valore parsato
	 * @throws Exception
	 */
	public abstract Date parseDateTime(String date) throws Exception;
			
	/**
	 * Method getInstance.
	 * @param lang codice ISO locale di riferimento utente
	 * @param ch codice canale 
	 * @param bank codice banca
	 * @return DataFormatter Istanza dell'oggetto di classe specifica
	 * @throws Exception Sollevata in cui non vi e' una istanza di formattatore per i parametri specificati
	 */
	public static DataFormatter getInstance(
		String bank,
		String ch,
		String lang)
		throws Exception {
		
        StringBuffer prefix = new StringBuffer();
        prefix.append(bank.toUpperCase());
		prefix.append(SEP);
		prefix.append(ch.toUpperCase());
		prefix.append(SEP);
		prefix.append(lang.toUpperCase());
		prefix.append(SEP);		
								
		Class classFactory = null;
		Constructor constructor;
		Locale loc = null;
		DataFormatter df = null;

		synchronized (formatterConfiguration) {
			if (formatterConfiguration.isEmpty()) {
				try {
					formatterConfiguration = DataFormatterHandler.reload();	
				} catch (Exception e) {
					logger.fatal("Parsing error:" + e.getMessage(), e);
					/* in caso di eccezione l'oggetto rimane comunque inizializzato
					formatterConfiguration = null;
					*/
				}
			}
		}

		/* controlla se e' configurata una factory per i parametri specificati */			
		if(formatterConfiguration.containsKey(prefix.toString())) {
			
			/* get factory */												
			classFactory = (Class)getConfValue(prefix.toString(),TAG_CLASS,ATTRIB_TYPE);
			loc = (Locale) getConfValue(prefix.toString(),TAG_CLASS,KEY_LOCALE);
			
			/* invocazione metodo di costruzione istanza della classe */
			
			
			constructor = classFactory.getDeclaredConstructor(
				new Class[] {String.class, Locale.class, String.class, String.class, String.class });
			
			df = (DataFormatter)constructor.newInstance(
				new Object[] {prefix.toString(), loc, bank, ch, lang});			
		} else {
			/* ECCEZIONE: parametri di Locale/Canale non supportati */
			logger.fatal("Unable to build DataFormatter for LANG:"+ lang+ " CHANNEL:"+ ch+ " BANK:"+ bank);
			throw new Exception("Unable to build DataFormatter for LANG:"+ lang+ " CHANNEL:"+ ch+ " BANK:"+ bank);
		}
		
		return df;
	}
	
	/* funzione chiamata dal metodo di factory per operazioni di inizializzazione */
	protected abstract void initializeFormatter();
	
	/* impostazione parametri formattatore decimale */
	protected static DecimalFormat decimalAmountFactory(
		String format_pattern,
		Locale format_local,
		boolean grouping,
		boolean decimal_separator) {
			
		DecimalFormat formatter = new DecimalFormat(format_pattern,new DecimalFormatSymbols(format_local));		
		formatter.setGroupingUsed(grouping);
		formatter.setDecimalSeparatorAlwaysShown(decimal_separator);

		return formatter;
	}				
	
	/* recupero prefisso ricerca */
	protected synchronized String getFormatterPrefix() {
		return this.prefix;
	}
	
	/* recupero oggetto Locale associato al formattatore */
	protected synchronized Locale getFormatterLocale() {
		return this.currentLocale;
	}
							
	/* recupero valore da hash di condifurazione */
	protected static Object getConfValue(String prefix, String tag, String attribute) {
		StringBuffer key = new StringBuffer();
		key.append(prefix);
		key.append(tag);
		key.append(SEP);
		key.append(attribute);
		return formatterConfiguration.get(key.toString());
	}
	
	/* recupero pattern di formattazione della valuta specificata */
	protected static String getCcyPattern(String prefix,String ccy) {
		StringBuffer key = new StringBuffer();
		key.append(prefix);
		key.append(TAG_CCY);
		key.append(SEP);
		key.append(ccy);
		return (String)formatterConfiguration.get(key.toString());
	}
	
	/* recupero attributi di formattazione della valuta specificata */
	protected static String getCcyAttributes(String prefix, String ccy, String attributes){
		StringBuffer key = new StringBuffer();
		key.append(prefix);
		key.append(TAG_CCY);
		key.append(SEP);
		key.append(ccy);
		key.append(SEP);
		if(ATTRIB_ROUNDING.equals(attributes)) {
		    key.append(ATTRIB_ROUNDING);
		}
		if(ATTRIB_DECIMALS.equals(attributes)) {
		    key.append(ATTRIB_DECIMALS);
		}
		return (String) formatterConfiguration.get(key.toString());
	}
	 

	/**
	 * Legge la chiave dal dizionario per il tipo specificato.
	 * Se la chiave non esiste restituisce NOT_AVAILABLE
	 * 
	 * @param codeType  - il tipo di chiave richiesta
	 * @param codeValue - la chiave da decodificare
	 * @return String   - la decidifica della chiave.
	 */
	public abstract String decode(String codeType, String codeValue) ;
	
}