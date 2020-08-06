package it.tasgroup.iris.dto.flussi;


import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaNDP;

import java.io.Serializable;

public class ListaEsitiNdpDTOLight extends ListaEsitiDTOLight implements Serializable {
	
	public Long id;
	
    public String importoConSegno;
    
    public String importoSenzaSegno;
    
    private String idRiscossione;
    
    private String esitoPagamento;
    
    private EnumTipoAnomaliaNDP tipoAnomalia;
    
    public String getImportoConSegno() {
        return importoConSegno;
    }
    
    public void setImportoConSegno(String importoConSegno) {
        this.importoConSegno = importoConSegno;
    }
    
    public String getIdRiscossione() {
        return idRiscossione;
    }
    
    public void setIdRiscossione(String idRiscossione) {
        this.idRiscossione = idRiscossione;
    }
    
    public String getEsitoPagamento() {
        return esitoPagamento;
    }
    
    public void setEsitoPagamento(String esitoPagamento) {
        this.esitoPagamento = esitoPagamento;
    }

	public EnumTipoAnomaliaNDP getTipoAnomalia() {
		return tipoAnomalia;
	}

	public void setTipoAnomalia(EnumTipoAnomaliaNDP tipoAnomalia) {
		this.tipoAnomalia = tipoAnomalia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImportoSenzaSegno() {
		return importoSenzaSegno;
	}

	public void setImportoSenzaSegno(String importoSenzaSegno) {
		this.importoSenzaSegno = importoSenzaSegno;
	}
    
}
