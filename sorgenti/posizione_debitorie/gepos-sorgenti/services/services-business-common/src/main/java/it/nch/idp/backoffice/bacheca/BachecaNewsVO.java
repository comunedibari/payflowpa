package it.nch.idp.backoffice.bacheca;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BachecaNewsVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1329218593312659685L;
	private Long id;
	private String titolo;
	private Long priorita = 0l;
	private String messaggio;
	private String immagine;
	private Date decorrenza;
	private Date scadenza;
	private String ordinamento;
	private String tipoOrdinamento;
	
	private byte[] imgExtContent;
    private String imgExtFileName;
    
	private byte[] imgIntContent;
    private String imgIntFileName;

    private boolean isNew;
	
	private String opInserimento;
	private String opAggiornamento;
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Long getPriorita() {
		return priorita;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public String getImmagine() {
		return immagine;
	}
	public Date getDecorrenza() {
		return decorrenza;
	}
	public Date getScadenza() {
		return scadenza;
	}
	public void setPriorita(Long priorita) {
		this.priorita = priorita;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	public void setDecorrenza(Date decorrenza) {
		this.decorrenza = decorrenza;
	}
	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
	public String getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}
	public String getTipoOrdinamento() {
		return tipoOrdinamento;
	}
	public void setTipoOrdinamento(String tipoOrdinamento) {
		this.tipoOrdinamento = tipoOrdinamento;
	}
	
	public boolean isNew() {
		return isNew;
	}
	public String getOpInserimento() {
		return opInserimento;
	}
	public String getOpAggiornamento() {
		return opAggiornamento;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	public String getContenutoExcerpt() {
		if (messaggio.length() > 50)
			return messaggio.substring(0, 50) + "[...]";
		else 
			return messaggio;
		
	}
	
	public String getTitoloExcerpt() {
		if (titolo.length() > 50)
			return titolo.substring(0, 50) + "[...]";
		else 
			return titolo;
		
	}
	
	public String getDecorrenzaVideo() {
		return df.format(decorrenza);
	}
	
	public String getScadenzaVideo() {
		return df.format(scadenza);
	}
	public byte[] getImgExtContent() {
		return imgExtContent;
	}
	public void setImgExtContent(byte[] imgExtContent) {
		this.imgExtContent = imgExtContent;
	}
	public String getImgExtFileName() {
		return imgExtFileName;
	}
	public void setImgExtFileName(String imgExtFileName) {
		this.imgExtFileName = imgExtFileName;
	}
	public byte[] getImgIntContent() {
		return imgIntContent;
	}
	public void setImgIntContent(byte[] imgIntContent) {
		this.imgIntContent = imgIntContent;
	}
	public String getImgIntFileName() {
		return imgIntFileName;
	}
	public void setImgIntFileName(String imgIntFileName) {
		this.imgIntFileName = imgIntFileName;
	}
}
