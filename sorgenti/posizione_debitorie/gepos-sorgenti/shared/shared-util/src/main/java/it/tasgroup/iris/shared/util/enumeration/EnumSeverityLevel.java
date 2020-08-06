/**
 * 
 */
package it.tasgroup.iris.shared.util.enumeration;


/**
 * @author PazziK
 *
 */
public enum EnumSeverityLevel implements MessageDescription{
	
	FATAL("F","Errore Grave","it.tasgroup.iris.exception.severity.fatal", EnumOutcomeStatus.KO),
	ERROR("E","Errore","it.tasgroup.iris.exception.severity.error",  EnumOutcomeStatus.KO),
	WARN("W","Avvertenza","it.tasgroup.iris.exception.severity.warn",  EnumOutcomeStatus.OK),
	INFO("I","Informazione","it.tasgroup.iris.exception.severity.info", EnumOutcomeStatus.OK),
	USERINFO("U","Informazione Utente","it.tasgroup.iris.exception.severity.user.info", EnumOutcomeStatus.OK);
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private EnumOutcomeStatus status;
	
	EnumSeverityLevel(String chiave, String descrizione, String chiaveBundle, EnumOutcomeStatus status){
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.status = status;
	}	

	@Override
	public String getChiave() {
		
		return chiave;
	}

	@Override
	public String getDescrizione() {
		
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		
		return chiaveBundle;
	}

	/**
	 * @return the status
	 */
	public EnumOutcomeStatus getStatus() {
		return status;
	}
	
}
