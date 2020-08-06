package it.tasgroup.iris.dto;

import it.tasgroup.addon.api.domain.DettaglioStrutturato;
import it.tasgroup.addon.api.domain.TributoStrutturato;

import java.io.Serializable;

public class SearchTributiRequest<T extends TributoStrutturato> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private T tributoStrutturato;
	private boolean searchHidden = false;
	
	public SearchTributiRequest() {
	}

	public SearchTributiRequest(T tributoStrutturato) {
		this.tributoStrutturato = tributoStrutturato;
	}

	public T getTributoStrutturato() {
		return tributoStrutturato;
	}

	public void setTributoStrutturato(T tributoStrutturato) {
		this.tributoStrutturato = tributoStrutturato;
	}

	public DettaglioStrutturato getDettaglio() {
		return this.getTributoStrutturato().getDettaglioStrutturato();
	}

	public void setDettaglio(DettaglioStrutturato dettaglio) {
		throw new UnsupportedOperationException("Set operation is not supported");
	}

	public boolean isSearchHidden() {
		return searchHidden;
	}

	public void setSearchHidden(boolean searchHidden) {
		this.searchHidden = searchHidden;
	}

	public String getIdEnte() {
		// TODO: adrebbe settato da chi fa la ricerca - dovrebbe gia' essere cosi' ma ...
		return tributoStrutturato.getDettaglioStrutturato().getIdEnte();
	}

	public String getCdTrbEnte() {
		// TODO: adrebbe settato da chi fa la ricerca - per ora non dovrebbe servire ma per i plugin esistenti cdTrbEnte e cdPlugin coincidono
		return tributoStrutturato.getTipoTributo();
	}
}
