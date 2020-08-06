package it.nch.idp.backoffice.tavolooperativo;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * TODO PAZZIK: I18N
 *
 */
public class PendenzaErrataVO implements Serializable{

	private static final long serialVersionUID = 325128601217161954L;
	private String e2emsgid;
	private String senderId;
	private String senderSys;
	private String idEsitoPendenza;
	private String idPendenzaEnte;
	private String codice;
	private String descrizioneCodice;
	private String descrizioneErrore;
	
	private static final String defaultBundleName = "err_mon.error";

    private static final Locale defaultLocale = Locale.ITALY;
	
    private ResourceBundle descriptionBundle;
    	
	public PendenzaErrataVO() {
		descriptionBundle = ResourceBundle.getBundle(defaultBundleName, defaultLocale);
	}

	public PendenzaErrataVO(Locale locale) {
		descriptionBundle = ResourceBundle.getBundle(defaultBundleName, locale);
	}

	public void setLocale(Locale locale) {
		descriptionBundle = ResourceBundle.getBundle(defaultBundleName, locale);
	}
   
	public String getE2emsgid() {
		return e2emsgid;
	}

	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderSys() {
		return senderSys;
	}

	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
	}


	public String getIdEsitoPendenza() {
		return idEsitoPendenza;
	}

	public void setIdEsitoPendenza(String idEsitoPendenza) {
		this.idEsitoPendenza = idEsitoPendenza;
	}

	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	/**
	 * Decodifica il codice di errore trovato su DB attraverso un file di properties
	 * a doppio livello condiviso con EBMonitor.
	 *
	 * @return la decodifica del codice di errore trovato su DB
	 */
	public String getErrorDescription(){
		
		String secondKey = "";
		
		String thirdKey = "";
		
		try{
			if (descriptionBundle == null)
				return "";
			
			secondKey = descriptionBundle.getString(codice.trim());
			
			if (secondKey != null)
				thirdKey= descriptionBundle.getString(secondKey.trim());
			
			else thirdKey = codice+":"+descrizioneErrore;
			
			if (thirdKey==null) {
				thirdKey = codice+":"+descrizioneErrore;
			}

		} catch (MissingResourceException e){
			e.printStackTrace();
		}
		
		return thirdKey;
	}

	public String getDescrizioneCodice() {
		if (((descrizioneCodice==null)||(descrizioneCodice.equals("")))&&((!(codice==null))||(!codice.equals(""))))
			return getErrorDescription();
		
		return descrizioneCodice;
	}

	public void setDescrizioneCodice(String descrizioneCodice) {
		this.descrizioneCodice = descrizioneCodice;
	}
	
	public static void main(String[] args) {
		PendenzaErrataVO vo = new PendenzaErrataVO();
		
		vo.setCodice("");
	}

	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}

	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	
}