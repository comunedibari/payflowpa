/**
 * 
 */
package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author pazzik
 *
 */
public class ListaDistinteRiaccreditoFilterDTO implements Serializable {
	
	private Timestamp dataDisposizione;	
	private Timestamp dataRicezioneEsito;
	private BigDecimal importo;
	private String stato;
	private String ragioneSocialeBeneficiario;
	private String tipoDebito;
	private String idPagamentoEnte;
	private String codicePagamentoIris;
	
	private Timestamp dataDisposizioneDa;
	private Timestamp dataDisposizioneA;
	
	private Timestamp dataRicezioneEsitoDa;
	private Timestamp dataRicezioneEsitoA;
	
	private BigDecimal importoDa;
	private BigDecimal importoA;
	
    public Timestamp getDataDisposizione() {
        return dataDisposizione;
    }
    public void setDataDisposizione(Timestamp dataDisposizione) {
        this.dataDisposizione = dataDisposizione;
    }
    public Timestamp getDataRicezioneEsito() {
        return dataRicezioneEsito;
    }
    public void setDataRicezioneEsito(Timestamp dataRicezioneEsito) {
        this.dataRicezioneEsito = dataRicezioneEsito;
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
    public String getRagioneSocialeBeneficiario() {
        return ragioneSocialeBeneficiario;
    }
    public void setRagioneSocialeBeneficiario(String ragioneSocialeBeneficiario) {
        this.ragioneSocialeBeneficiario = ragioneSocialeBeneficiario;
    }
    public String getTipoDebito() {
        return tipoDebito;
    }
    public void setTipoDebito(String tipoDebito) {
        this.tipoDebito = tipoDebito;
    }
    public String getIdPagamentoEnte() {
        return idPagamentoEnte;
    }
    public void setIdPagamentoEnte(String idPagamentoEnte) {
        this.idPagamentoEnte = idPagamentoEnte;
    }
    public String getCodicePagamentoIris() {
        return codicePagamentoIris;
    }
    public void setCodicePagamentoIris(String codicePagamentoIris) {
        this.codicePagamentoIris = codicePagamentoIris;
    }
    public Timestamp getDataDisposizioneDa() {
        return dataDisposizioneDa;
    }
    public void setDataDisposizioneDa(Timestamp dataDisposizioneDa) {
        this.dataDisposizioneDa = dataDisposizioneDa;
    }
    public Timestamp getDataDisposizioneA() {
        return dataDisposizioneA;
    }
    public void setDataDisposizioneA(Timestamp dataDisposizioneA) {
        this.dataDisposizioneA = dataDisposizioneA;
    }
    public Timestamp getDataRicezioneEsitoDa() {
        return dataRicezioneEsitoDa;
    }
    public void setDataRicezioneEsitoDa(Timestamp dataRicezioneEsitoDa) {
        this.dataRicezioneEsitoDa = dataRicezioneEsitoDa;
    }
    public Timestamp getDataRicezioneEsitoA() {
        return dataRicezioneEsitoA;
    }
    public void setDataRicezioneEsitoA(Timestamp dataRicezioneEsitoA) {
        this.dataRicezioneEsitoA = dataRicezioneEsitoA;
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

	
}
