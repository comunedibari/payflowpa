package it.tasgroup.iris.dto.flussi;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DistinteRiaccreditoDTOLight implements Serializable {

    private Long id;	

    private Timestamp dataCreazione;
    private Double importo;
    private String stato;

    //dataDisposizione
    private Timestamp dataSpedizione;
    //dataRicezioneEsito
    private Timestamp tsInserimento;
    //codice banca
    private String codiceRiferimento;
    //ragioneSocialeBeneficiario
    private String ragSocBenefic;
    private String ibanBenefic;
    //idPagamentoEnte
    private String idPagCondEnte;
    //tipoDebito
    private String deTrb;
    //dataPagamento
    private Timestamp tsDecorrenza;
    //codicePagamentoIris
    private String codPagamento;
    private String causale;

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
    public Double getImporto() {
        return importo;
    }
    public void setImporto(Double importo) {
        this.importo = importo;
    }
    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }
    public Timestamp getDataSpedizione() {
        return dataSpedizione;
    }
    public void setDataSpedizione(Timestamp dataSpedizione) {
        this.dataSpedizione = dataSpedizione;
    }
    public Timestamp getTsInserimento() {
        return tsInserimento;
    }
    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }
    public String getCodiceRiferimento() {
        return codiceRiferimento;
    }
    public void setCodiceRiferimento(String codiceRiferimento) {
        this.codiceRiferimento = codiceRiferimento;
    }
    public String getIbanBenefic() {
        return ibanBenefic;
    }
    public void setIbanBenefic(String ibanBeneficiario) {
        this.ibanBenefic = ibanBeneficiario;
    }
    public String getIdPagCondEnte() {
        return idPagCondEnte;
    }
    public void setIdPagCondEnte(String idPagCondEnte) {
        this.idPagCondEnte = idPagCondEnte;
    }
    public String getDeTrb() {
        return deTrb;
    }
    public void setDeTrb(String deTrb) {
        this.deTrb = deTrb;
    }
    public Timestamp getTsDecorrenza() {
        return tsDecorrenza;
    }
    public void setTsDecorrenza(Timestamp tsDecorrenza) {
        this.tsDecorrenza = tsDecorrenza;
    }
    public String getCodPagamento() {
        return codPagamento;
    }
    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }
    public String getCausale() {
        return causale;
    }
    public void setCausale(String causale) {
        this.causale = causale;
    }
	public String getRagSocBenefic() {
		return ragSocBenefic;
	}
	public void setRagSocBenefic(String ragSocBenefic) {
		this.ragSocBenefic = ragSocBenefic;
	}
    
}
