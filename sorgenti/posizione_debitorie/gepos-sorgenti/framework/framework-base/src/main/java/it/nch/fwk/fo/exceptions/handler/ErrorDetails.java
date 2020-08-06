/*
 * File: ErrorDetails.java
 * Package: com.etnoteam.service.dto
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $
 * Created on: 4-ago-03 - 14.48.04
 * Created by: dcerri (Etnoteam)
 */
package it.nch.fwk.fo.exceptions.handler;

import java.io.Serializable;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * <br>
 * La classe gestisce le informazioni di dettaglio relative ad un codice di
 * errore. L'oggetto e' di tipo "immutable" (non fornisce metodi setter); gli
 * attributi vengono impostati usando lo specifico costruttore.
 *
 * 24/01:LI Siccome sono stati aggiunti (ed utilizzati nel codice) dei metodi
 * setter l'oggetto e' diventato clonabile, in modo da tenere in cache una copia
 * immutable dell'oggetto e utilizzare i metodi setter sui cloni.
 *
 * Solamente nel caso di decodifica errore proveniente dal modello F24 si
 * rimappa la descrizione in base alla risposta di Host sul Dictionary e si
 * aggiorna la descrizione.
 *
 */
public class ErrorDetails
implements Serializable, Cloneable, ErrorDetailInterface{
	
	private static final long	serialVersionUID	= 6457422477219670773L;
	private static final int DEFAULT_BUFFER_SIZE = 100;

	/* codici ActionType */
	public static final int    ERROR_ACTION_TYPE_DISPOSITIVA     = 1;
	public static final int    ERROR_ACTION_TYPE_ENQUIRY         = 2;
	public static final int    ERROR_ACTION_TYPE_NON_DISPOSITIVA = 3;

	/**
	 *  Ordinamento dei livelli di severita' dei codice di errore
	 */
	private static final String[] SEVERITY_ORDER = {
		ERROR_SEVERITY_GENERIC,
		ERROR_SEVERITY_ERROR,
		ERROR_SEVERITY_WARNING,
		ERROR_SEVERITY_INFO,
		ERROR_SEVERITY_EMPTY,
		ERROR_SEVERITY_NO_ERROR,
		ERROR_SEVERITY_SHADY
	};

	private String code = null;
	private String severity = null;
	private String desc = null;
	private String flagType = null;
	private int area ;
	private Map args;

	/** 'true' se l'errore e' stato generato da una funzione dispositiva
	 *  (valore di default 'false')
	 * */
	private boolean disposal = false;

	private boolean contextualized=false;

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Returns the desc.
	 * @return String
	 */
	public String getDesc() {
		return replaceArgs(desc, args);
	}

	/**
	 * Returns the severity.
	 * @return String
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * Returns the flag isDisposal.
	 * @return boolean
	 */
	public boolean isDisposal() {
		return disposal;
	}

	/**
	 * Costruttore pubblico per l'inizializzazione dell'oggetto che contiene i dettagli
	 * dell'errore segnalato
	 * @see java.lang.Object#Object()
	 */
	public ErrorDetails(String code, String severity, String desc, String flagType) {
		this.code = code;
		this.severity = severity;
		this.desc = desc;
		this.flagType = flagType;
	}

	/**
	 * Returns the flagType.
	 * Al momento viene usato per gestire dei casi particolari come il refresh delle
	 * pagine dispositive ("R") e per la gestione particolare di pagine fondi ("F")
	 * @return String
	 */
	public String getFlagType() {
		return flagType;
	}


	/**
	 * Returns the area.
	 * @return int
	 */
	public int getArea() {
		return area;
	}

	/**
	 * Sets the area.
	 * @param area The area to set
	 */
	public void setArea(int area) {
		this.area = area;
	}

	/**
	 * Sets the disposal.
	 * @param disposal The disposal to set
	 */
	public void setDisposal(boolean disposal) {
		this.disposal = disposal;
	}

	/**
	 * Clonazione dell'oggetto.
	 */
	public Object clone() {
		Object ed;
		try {
			ed = super.clone();
		} catch (CloneNotSupportedException e) {
			ed = null;
		}
		return ed;
	}

	/**
	 * Setta gli argomenti da sostituire al messaggio di errore
	 */
	public void setArgs(Map args) {
		this.args = args;
	}

	private static String replaceArgs(String text, Map args) {
		StringTokenizer st;
		StringBuffer sb = new StringBuffer(DEFAULT_BUFFER_SIZE);
		String token="";
		String savedToken = "";
		String arg;
		String resMsg;

		boolean argOpen = false;

		if (args != null && text != null) {
			st = new StringTokenizer(text, "{}", true);

			while (st.hasMoreTokens()) {
				token = st.nextToken();
				if ("{".equals(token)) {
					if (argOpen) {
						sb.append("{");
					}
					argOpen = true;
				} else if ("}".equals(token)) {
					if (argOpen) {
						arg = (String)args.get(savedToken);
						if (arg == null) {
							sb.append("{");
							sb.append(savedToken);
							sb.append("}");
						} else {
							sb.append(arg);
						}
					} else {
						sb.append("}");
					}
					savedToken = "";
					argOpen = false;
				} else if (argOpen) {
					savedToken = token;
				} else {
					sb.append(token);
				}
			}
			resMsg = sb.toString();
		} else {
			resMsg = text;
		}

		return resMsg;
	}

	public boolean isSeverityLessThan(String severityLevel){
		boolean found = false;
		for (int i = 0; i < SEVERITY_ORDER.length; i++) {
			if (SEVERITY_ORDER[i].equals(severityLevel)) {
				break;
			}
			if (SEVERITY_ORDER[i].equals(severity)){
				found = true;
			}
		}
		return !found;
	}

	public boolean isSeverityMoreThan(String severityLevel) {
		boolean found = false;
		for (int i = SEVERITY_ORDER.length - 1; i >= 0; i--) {
			if (SEVERITY_ORDER[i].equals(severityLevel)) {
				break;
			}
			if (SEVERITY_ORDER[i].equals(severity)){
				found = true;
			}
		}
		return !found;
	}

	public static String getMaxSeverity(String sev1, String sev2) {
		String sev = null;
		for (int i = 0; i < SEVERITY_ORDER.length; i++) {
			if (SEVERITY_ORDER[i].equals(sev1)) {
				sev = sev1;
				break;
			}
			if (SEVERITY_ORDER[i].equals(sev2)){
				sev = sev2;
				break;
			}
		}
		return sev;
	}

	/**
	 * Questo metodo deve essere utilizzato esclusivamente per la costruzione dell'errore
	 * proveniente dalla negoziazione titoli.
	 *
	 * @param desc The desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
    /**
     * indica se l'errore deve cambiare il modo in cui visualizzare la pagina d'errore.
     * In particolare, significa che nella pagina di errore bisognera' tenere conto
     * dell'area alla quale questo errore appartiene
     *
     * @return
     */
    public boolean isContextualized() {
        return contextualized;
    }

    /**
     * imposta se l'errore deve cambiare il modo in cui visualizzare la pagina d'errore.
     * In particolare, significa che nella pagina di errore bisognera' tenere conto
     * dell'area alla quale questo errore appartiene
     *
     * @param contextualized se per questo errore bisogna contestualizzare la pagina di errore
     */
    public void setContextualized(boolean contextualized) {
        this.contextualized = contextualized;
    }
}
