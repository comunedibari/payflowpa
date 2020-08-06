/**
 * 
 */
package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.EnumErrorMessage;
import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;

/**
 * @author PazziK
 *
 */
public enum EnumDAOErrorCodes implements EnumErrorMessage{
	
	DAOERR_DUPL_KEY("DAOERR_DUPL_KEY","Violazione del vincolo di unicita' della chiave","it.tasgroup.iris.dao.exception.duplicatekey", EnumSeverityLevel.USERINFO),
	DAOERR_OPT_LOCK("DAOERR_OPT_LOCK","Violazione del locking ottimistico","it.tasgroup.iris.dao.exception.optimisticlock", EnumSeverityLevel.USERINFO);
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private EnumSeverityLevel severityLevel;
	
	EnumDAOErrorCodes(String chiave, String descrizione, String chiaveBundle, EnumSeverityLevel severityLevel){
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.severityLevel = severityLevel;
		
	}	
	

	@Override
	public String getChiave() {
		
		return this.chiave;
	}

	@Override
	public String getDescrizione() {
		
		return this.descrizione;
	}

	@Override
	public String getChiaveBundle() {
		
		return this.chiaveBundle;
	}


	@Override
	public EnumSeverityLevel getSeverityLevel() {
		
		return this.severityLevel;
	}


	@Override
	public void setSeverityLevel(EnumSeverityLevel severityLevel) {
		
		this.severityLevel = severityLevel;
		
	}

}
