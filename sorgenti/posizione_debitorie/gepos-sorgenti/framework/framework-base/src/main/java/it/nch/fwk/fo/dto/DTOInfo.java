/*
 * Creato il 30-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author sberisso
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class DTOInfo implements Serializable {

	/**
	 * Costanti che descrivono il tipo di informazione 
	 * Inizio di implementazione di simulazione questa sezione 
	 * andra' a sparire aloorquando il framework verra' portato sull'implementazione
	 * del BASE_NIB
	 */
	public static final short INFO = 1;
	public static final String INFO_TEXT = "info";
	public static final short WARNING = 2;
	public static final String WARNING_TEXT = "warning";
	public static final short ERROR = 3;
	public static final String ERROR_TEXT = "error";
	
	private static final short MIN_INFO = INFO;
	private static final short MAX_INFO = ERROR;
	
	private short infoType;
	private String description;
	
	/**
	 * @param code
	 * @param message
	 * @param severity
	 */
	public DTOInfo(String code, String message, int severity) {
		
		this.code = code;
		this.message = message;
		this.severity = severity;
	}
	/**
	 * @deprecated
	 * Costruttore 
	 */
	public DTOInfo(short infoType) {
		this.infoType = infoType;
		description = "";
	}
	
	public DTOInfo() {
		severity = DTOInfoInterface.SEVERITY_INFO;
		description = "";
	}
	
	/**
	 * @deprecated
	 * @return Restituisce il valore description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @deprecated
	 * @param description Il valore description da impostare.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @deprecated
	 * @return Restituisce il valore infoType.
	 */
	public short getInfoType() {
		return infoType;
	}
	/**
	 * @deprecated
	 * @param infoType Il valore infoType da impostare.
	 */
	public void setInfoType(short infoType) {
		if ((infoType >= MIN_INFO) && (infoType <= MAX_INFO))
			this.infoType = infoType;
	}
	
	/**
	 * @deprecated
	 * @return
	 * 
	 */
	public String getInfoTypeText() {
		String infoTypeText = "";
		
		switch (infoType) {
			case INFO:
				infoTypeText = INFO_TEXT;
				break;
			case WARNING:
				infoTypeText = WARNING_TEXT;
				break;
			case ERROR:
				infoTypeText = ERROR_TEXT;
				break;
			default:
				infoTypeText = "no value";
				break;
		}
		return infoTypeText;
	}
	
	/**
	 * @return
	 */
	public boolean hasInfo() {
		return (this.severity == DTOInfoInterface.SEVERITY_INFO);
	}
	
	public boolean hasWarning() {
		return (this.severity == DTOInfoInterface.SEVERITY_WARNING);
	}
	
	public boolean hasError() {
		return (this.severity == DTOInfoInterface.SEVERITY_ERROR);
	}
	
	
	/***
	 * INIZIO IMPLEMENTAZIONE BASE_NIB
	 * 
	 * @author EE10057
	 *
	 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
	 * Finestra - Preferenze - Java - Stile codice - Modelli codice
	 */
	private String code;
    private String message;
    private int severity;
    private Map args;
    
    public DTOInfo(int severity) {
		this.severity = severity;
		message = "";
		code = "";
	}
    
    public DTOInfo(int severity,String code,String message) {
		this.severity = severity;
		message = "";
		code = "";
	}
    
    public String getCode() {
        return code;
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.dto.DTOInfoInterface#getMessage()
     */
    public String getMessage() {
        return replaceArgs(message, args);
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.dto.DTOInfoInterface#getSeverity()
     */
    public int getSeverity() {
        return severity;
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.dto.DTOInfoInterface#setArgs(java.util.Map)
     */
    public Object[] getArgs() {
    	if(args!=null){
    		Object[] obj = new Object[args.size()];
    		Iterator it = args.values().iterator();
    		
    		int i = 0;
    		while(it.hasNext()){
    			obj[i]=it.next();
    			i++;
    		}
    		return obj;
    	}    		
        return null;
    }

    
    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.dto.DTOInfoInterface#setArgs(java.util.Map)
     */
    public void setArgs(Map args) {
        this.args=args;
    }

	private static String replaceArgs(String text, Map args) {
		StringTokenizer st;
		String token="";
		String savedToken = "";
		String arg;
		String resMsg;
		
		boolean argOpen = false;
		
		if (args != null && text != null) {
			StringBuffer sb = new StringBuffer(text.length());
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
    
    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.dto.DTOInfoInterface#setDesc(java.lang.String)
     */
    public void setMessage(String msg) {
        this.message=msg;
    }
	
	/**
	 * @param code Il valore code da impostare.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
}
