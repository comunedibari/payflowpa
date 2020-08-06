package it.tasgroup.ge.pojo;

import it.tasgroup.ge.CfgEventi;

import java.io.Serializable;

public class CommunicationEvent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codiceEvento;
	private String objectId;
	private CfgEventi cfgEvento;
	
	
	public CommunicationEvent(String codiceEvento, String objectId,
			CfgEventi cfgEvento) {
		super();
		this.codiceEvento = codiceEvento;
		this.objectId = objectId;
		this.cfgEvento = cfgEvento;
	}
	public String getCodiceEvento() {
		return codiceEvento;
	}
	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public CfgEventi getCfgEvento() {
		return cfgEvento;
	}
	public void setCfgEvento(CfgEventi cfgEvento) {
		this.cfgEvento = cfgEvento;
	}

	
}
