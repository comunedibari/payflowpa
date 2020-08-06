package it.nch.is.fo.web;

/**
 * La classe serve a dare le direttive alle pagine di come deve essere visualizzato lo stato del flusso.
 * 
 * @author Geminiani
 *
 */
public class FlowStatus {
	
	/** 
	 * Descrizione dello stato.<br>
	 * Deve essere una delle costanti presenti in <code>FrontEndConstant</code>.
	 */
	private String descrizione;
	
	/**
	 * Stile (in CSS) della visualizzazione dello stato.  
	 */
	private String style;
	private String styleClass;	
	private String label;
	
	public FlowStatus(String descrizione, String style, String styleClass, String label) {
		super();
		this.style = style;
		this.descrizione = descrizione;
		this.styleClass = styleClass;
		this.label = label;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String style) {
		this.styleClass = style;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
