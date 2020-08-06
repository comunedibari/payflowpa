package it.nch.eb.xsqlcmd.model;

import java.io.Serializable;

public class ConfigurazioneTributoEnteModel implements Serializable {
	
	public static final String FLAG_INIZIATIVA_SPONTANEO="Y";
	
	String idTributo;
	String flagIniziativa;
	
	public String getIdTributo() {
		return idTributo;
	}
	
	public String getFlagIniziativa() {
		return flagIniziativa;
	}
	
	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}
	
	public void setFlagIniziativa(String flagIniziativa) {
		this.flagIniziativa = flagIniziativa;
	}
	
}
