package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ListaMonitoraggioFlussiFilterDTO implements Serializable {
	
	private String tipoflusso;
	private String stato;
	private String nomeSupporto;
	private Integer dimensioneDa;
	private Integer dimensioneA;
	
	private Timestamp dataCreazioneDa;
	private Timestamp dataCreazioneA;
	
	private BigDecimal importoDa;
	private BigDecimal importoA;
        
    private String mittente;
    private String ricevente;
    
    private List<String> ricercaAnomalie;
    
    private boolean selectNullRicevente = true;
	
	public Integer getDimensioneDa() {
		return dimensioneDa;
	}
	public void setDimensioneDa(Integer dimensioneDa) {
		this.dimensioneDa = dimensioneDa;
	}
	public Integer getDimensioneA() {
		return dimensioneA;
	}
	public void setDimensioneA(Integer dimensioneA) {
		this.dimensioneA = dimensioneA;
	}
	
	public Timestamp getDataCreazioneDa() {
		return dataCreazioneDa;
	}
	public void setDataCreazioneDa(Timestamp dataCreazioneDa) {
		this.dataCreazioneDa = dataCreazioneDa;
	}
	public String getTipoflusso() {
		return tipoflusso;
	}
	public void setTipoflusso(String tipoflusso) {
		this.tipoflusso = tipoflusso;
	}
	public Timestamp getDataCreazioneA() {
		return dataCreazioneA;
	}
	public void setDataCreazioneA(Timestamp dataCreazioneA) {
		this.dataCreazioneA = dataCreazioneA;
	}
	public BigDecimal getImportoDa() {
		return importoDa;
	}
	public void setImportoDa(BigDecimal importoDa) {
		this.importoDa = importoDa;
	}
	public BigDecimal getImportoA() {
		return importoA;
	}
	public void setImportoA(BigDecimal importoA) {
		this.importoA = importoA;
	}

	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getNomeSupporto() {
		return nomeSupporto;
	}
	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}

        public String getMittente() {
            return mittente;
        }
        
        public void setMittente(String mittente) {
            this.mittente = mittente;
        }
        
        public String getRicevente() {
            return ricevente;
        }
        
        public void setRicevente(String ricevente) {
            this.ricevente = ricevente;
        }
        
        public List<String> getRicercaAnomalie() {
            return ricercaAnomalie;
        }
        
        public void setRicercaAnomalie(List<String> ricercaAnomalie) {
            this.ricercaAnomalie = ricercaAnomalie;
        }
		public boolean isSelectNullRicevente() {
			return selectNullRicevente;
		}
		public void setSelectNullRicevente(boolean selectNullRicevente) {
			this.selectNullRicevente = selectNullRicevente;
		}
		
        
}
