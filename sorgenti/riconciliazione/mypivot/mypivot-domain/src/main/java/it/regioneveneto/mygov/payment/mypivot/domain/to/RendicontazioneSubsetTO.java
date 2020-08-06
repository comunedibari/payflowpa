package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class RendicontazioneSubsetTO  implements java.io.Serializable {

    public RendicontazioneSubsetIdTO id;
    public String bilancio;
    public String classificazioneCompletezza;
    public String codBolletta;
    public String codDocumento;
    public String codIdDominio;
    public String codIufKey;
    public String codProvvisorio;
    public String dataOraFlussoRendicontazione;
    public String deAnnoBolletta;
    public String deAnnoDocumento;
    public String deAnnoProvvisorio;
    public String deDataRegolamento;
    public String deDataRicezione;
    public String deDataUltimoAggiornamento;
    public Date dtDataRegolamento;
    public Date dtDataUltimoAggiornamento;
    public Date dtRicezione;
    public String identificativoUnivocoRegolamento;
    public String importoTotalePagamenti;
    public Integer indiceDatiSingoloPagamento;
    public String singoloImportoCommissioneCaricoPa;

    public RendicontazioneSubsetTO() {
    }

    public RendicontazioneSubsetTO(RendicontazioneSubsetIdTO id, String bilancio, String classificazioneCompletezza, String codBolletta, String codDocumento, String codIdDominio, String codIufKey, String codProvvisorio, String dataOraFlussoRendicontazione, String deAnnoBolletta, String deAnnoDocumento, String deAnnoProvvisorio, String deDataRegolamento, String deDataRicezione, String deDataUltimoAggiornamento, Date dtDataRegolamento, Date dtDataUltimoAggiornamento, Date dtRicezione, String identificativoUnivocoRegolamento, String importoTotalePagamenti, Integer indiceDatiSingoloPagamento, String singoloImportoCommissioneCaricoPa) {
        super();
        this.id = id;
        this.bilancio = bilancio;
        this.classificazioneCompletezza = classificazioneCompletezza;
        this.codBolletta = codBolletta;
        this.codDocumento = codDocumento;
        this.codIdDominio = codIdDominio;
        this.codIufKey = codIufKey;
        this.codProvvisorio = codProvvisorio;
        this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
        this.deAnnoBolletta = deAnnoBolletta;
        this.deAnnoDocumento = deAnnoDocumento;
        this.deAnnoProvvisorio = deAnnoProvvisorio;
        this.deDataRegolamento = deDataRegolamento;
        this.deDataRicezione = deDataRicezione;
        this.deDataUltimoAggiornamento = deDataUltimoAggiornamento;
        this.dtDataRegolamento = dtDataRegolamento;
        this.dtDataUltimoAggiornamento = dtDataUltimoAggiornamento;
        this.dtRicezione = dtRicezione;
        this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
        this.importoTotalePagamenti = importoTotalePagamenti;
        this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
        this.singoloImportoCommissioneCaricoPa = singoloImportoCommissioneCaricoPa;
    }

    public static RendicontazioneSubsetTO copyOf(RendicontazioneSubsetTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetIdTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).id),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).bilancio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).classificazioneCompletezza,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).codBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).codDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).codIdDominio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).codIufKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).codProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).dataOraFlussoRendicontazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).deAnnoBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).deAnnoDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).deAnnoProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).deDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).deDataRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).deDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).dtDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).dtDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).dtRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).identificativoUnivocoRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).importoTotalePagamenti,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).indiceDatiSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO) o).singoloImportoCommissioneCaricoPa
                );
    }

    public RendicontazioneSubsetIdTO getId() {
        return id;
    }

    public void setId(RendicontazioneSubsetIdTO id) {
        this.id = id;
    }

    public String getBilancio() {
        return bilancio;
    }

    public void setBilancio(String bilancio) {
        this.bilancio = bilancio;
    }

    public String getClassificazioneCompletezza() {
        return classificazioneCompletezza;
    }

    public void setClassificazioneCompletezza(String classificazioneCompletezza) {
        this.classificazioneCompletezza = classificazioneCompletezza;
    }

    public String getCodBolletta() {
        return codBolletta;
    }

    public void setCodBolletta(String codBolletta) {
        this.codBolletta = codBolletta;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getCodIdDominio() {
        return codIdDominio;
    }

    public void setCodIdDominio(String codIdDominio) {
        this.codIdDominio = codIdDominio;
    }

    public String getCodIufKey() {
        return codIufKey;
    }

    public void setCodIufKey(String codIufKey) {
        this.codIufKey = codIufKey;
    }

    public String getCodProvvisorio() {
        return codProvvisorio;
    }

    public void setCodProvvisorio(String codProvvisorio) {
        this.codProvvisorio = codProvvisorio;
    }

    public String getDataOraFlussoRendicontazione() {
        return dataOraFlussoRendicontazione;
    }

    public void setDataOraFlussoRendicontazione(String dataOraFlussoRendicontazione) {
        this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
    }

    public String getDeAnnoBolletta() {
        return deAnnoBolletta;
    }

    public void setDeAnnoBolletta(String deAnnoBolletta) {
        this.deAnnoBolletta = deAnnoBolletta;
    }

    public String getDeAnnoDocumento() {
        return deAnnoDocumento;
    }

    public void setDeAnnoDocumento(String deAnnoDocumento) {
        this.deAnnoDocumento = deAnnoDocumento;
    }

    public String getDeAnnoProvvisorio() {
        return deAnnoProvvisorio;
    }

    public void setDeAnnoProvvisorio(String deAnnoProvvisorio) {
        this.deAnnoProvvisorio = deAnnoProvvisorio;
    }

    public String getDeDataRegolamento() {
        return deDataRegolamento;
    }

    public void setDeDataRegolamento(String deDataRegolamento) {
        this.deDataRegolamento = deDataRegolamento;
    }

    public String getDeDataRicezione() {
        return deDataRicezione;
    }

    public void setDeDataRicezione(String deDataRicezione) {
        this.deDataRicezione = deDataRicezione;
    }

    public String getDeDataUltimoAggiornamento() {
        return deDataUltimoAggiornamento;
    }

    public void setDeDataUltimoAggiornamento(String deDataUltimoAggiornamento) {
        this.deDataUltimoAggiornamento = deDataUltimoAggiornamento;
    }

    public Date getDtDataRegolamento() {
        return dtDataRegolamento;
    }

    public void setDtDataRegolamento(Date dtDataRegolamento) {
        this.dtDataRegolamento = dtDataRegolamento;
    }

    public Date getDtDataUltimoAggiornamento() {
        return dtDataUltimoAggiornamento;
    }

    public void setDtDataUltimoAggiornamento(Date dtDataUltimoAggiornamento) {
        this.dtDataUltimoAggiornamento = dtDataUltimoAggiornamento;
    }

    public Date getDtRicezione() {
        return dtRicezione;
    }

    public void setDtRicezione(Date dtRicezione) {
        this.dtRicezione = dtRicezione;
    }

    public String getIdentificativoUnivocoRegolamento() {
        return identificativoUnivocoRegolamento;
    }

    public void setIdentificativoUnivocoRegolamento(String identificativoUnivocoRegolamento) {
        this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
    }

    public String getImportoTotalePagamenti() {
        return importoTotalePagamenti;
    }

    public void setImportoTotalePagamenti(String importoTotalePagamenti) {
        this.importoTotalePagamenti = importoTotalePagamenti;
    }

    public Integer getIndiceDatiSingoloPagamento() {
        return indiceDatiSingoloPagamento;
    }

    public void setIndiceDatiSingoloPagamento(Integer indiceDatiSingoloPagamento) {
        this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
    }

    public String getSingoloImportoCommissioneCaricoPa() {
        return singoloImportoCommissioneCaricoPa;
    }

    public void setSingoloImportoCommissioneCaricoPa(String singoloImportoCommissioneCaricoPa) {
        this.singoloImportoCommissioneCaricoPa = singoloImportoCommissioneCaricoPa;
    }

    public RendicontazioneSubsetTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "RendicontazioneSubsetTO["
            + id
            + ", "
            + bilancio
            + ", "
            + classificazioneCompletezza
            + ", "
            + codBolletta
            + ", "
            + codDocumento
            + ", "
            + codIdDominio
            + ", "
            + codIufKey
            + ", "
            + codProvvisorio
            + ", "
            + dataOraFlussoRendicontazione
            + ", "
            + deAnnoBolletta
            + ", "
            + deAnnoDocumento
            + ", "
            + deAnnoProvvisorio
            + ", "
            + deDataRegolamento
            + ", "
            + deDataRicezione
            + ", "
            + deDataUltimoAggiornamento
            + ", "
            + dtDataRegolamento
            + ", "
            + dtDataUltimoAggiornamento
            + ", "
            + dtRicezione
            + ", "
            + identificativoUnivocoRegolamento
            + ", "
            + importoTotalePagamenti
            + ", "
            + indiceDatiSingoloPagamento
            + ", "
            + singoloImportoCommissioneCaricoPa
            + "]";
    }

}
