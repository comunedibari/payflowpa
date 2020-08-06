package it.tasgroup.iris.dto.flussi;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ListaCasellarioDTOLight {
    private Long id;
    private Timestamp dataCreazione;
    private String tipoFlusso;
    private String mittente;
    private String ricevente;
    private BigDecimal importo;
    private String stato;
    private String nomeSupporto;
    private Integer dimensione;
    private Integer numeroRecord;
	private Timestamp dataRicezione;
    private Timestamp dataSpedizione;
    private Long idRendicontazione;
    private Long idDistintaPagamento;
    private Long idDistintaRiaccredito;
    private String nomeFile;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Timestamp getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    public String getTipoFlusso() {
        return tipoFlusso;
    }
    public void setTipoFlusso(String tipoFlusso) {
        this.tipoFlusso = tipoFlusso;
    }
    public BigDecimal getImporto() {
        return importo;
    }
    public void setImporto(BigDecimal importo) {
        this.importo = importo;
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
    public Integer getDimensione() {
        return dimensione;
    }
    public void setDimensione(Integer dimensione) {
        this.dimensione = dimensione;
    }
    public Timestamp getDataRicezione() {
        return dataRicezione;
    }
    public void setDataRicezione(Timestamp dataRicezione) {
        this.dataRicezione = dataRicezione;
    }
    public Timestamp getDataSpedizione() {
        return dataSpedizione;
    }
    public void setDataSpedizione(Timestamp dataSpedizione) {
        this.dataSpedizione = dataSpedizione;
    }
    public Long getIdRendicontazione() {
        return idRendicontazione;
    }
    public void setIdRendicontazione(Long idRendicontazione) {
        this.idRendicontazione = idRendicontazione;
    }
    public Long getIdDistintaPagamento() {
        return idDistintaPagamento;
    }
    public void setIdDistintaPagamento(Long idDistintaPagamento) {
        this.idDistintaPagamento = idDistintaPagamento;
    }
    public Long getIdDistintaRiaccredito() {
        return idDistintaRiaccredito;
    }
    public void setIdDistintaRiaccredito(Long idDistintaRiaccredito) {
        this.idDistintaRiaccredito = idDistintaRiaccredito;
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
    
    public Integer getNumeroRecord() {
		return numeroRecord;
	}
	public void setNumeroRecord(Integer numeroRecord) {
		this.numeroRecord = numeroRecord;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}


}
