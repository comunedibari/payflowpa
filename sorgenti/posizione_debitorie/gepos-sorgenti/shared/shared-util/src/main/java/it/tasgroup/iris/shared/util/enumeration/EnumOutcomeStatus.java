/**
 * 
 */
package it.tasgroup.iris.shared.util.enumeration;


/**
 * @author pazzik
 *
 */
public enum EnumOutcomeStatus implements MessageDescription {
	
	OK("OK", "OK", "OK"), 
 	KO("KO", "KO", "KO");
	
	private String chiave;
	
	private String descrizione;
	
	private String chiaveBundle;
	
	EnumOutcomeStatus(String chiave, String descrizione, String chiaveBundle){
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}	

	/**
	 * @return the chiave
	 */
	public String getChiave() {
		return chiave;
	}

	/**
	 * @param chiave the chiave to set
	 */
	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * @return the chiaveBundle
	 */
	public String getChiaveBundle() {
		return chiaveBundle;
	}

	/**
	 * @param chiaveBundle the chiaveBundle to set
	 */
	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}

	

}
