package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PendenzaDTO implements Serializable{
	private String idPendenzaEnte;
	private String idEnte;
	private String cdTrbEnte;
	private List<String> condizioni = new ArrayList<String>();
	private List<BigDecimal> importi  = new ArrayList<BigDecimal>();
	private List<String> listaItemFlagOpposizione = new ArrayList<String>();
	
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	public List<String> getCondizioni() {
		return condizioni;
	}
	public void setCondizioni(List<String> condizioni) {
		this.condizioni = condizioni;
	}
	public List<String> getListaItemFlagOpposizione() {
		return listaItemFlagOpposizione;
	}
	public void setListaItemFlagOpposizione(List<String> flags) {
		this.listaItemFlagOpposizione = flags;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	
	public List<BigDecimal> getImporti() {
		return importi;
	}
	
	public void setImporti(List<BigDecimal> importi) {
		this.importi = importi;
	}
	@Override
	public String toString() {
		return "PendenzaDTO [idPendenzaEnte=" + idPendenzaEnte + ", idEnte="
				+ idEnte + ", cdTrbEnte=" + cdTrbEnte + ", condizioni="
				+ condizioni + ", importi=" + importi +  ", listaItemFlagOpposizione=" + listaItemFlagOpposizione + "]";
	}
	
}
