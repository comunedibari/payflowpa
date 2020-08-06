package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class RendicontazioneTesoreriaSubsetTO  implements java.io.Serializable {

    public RendicontazioneTesoreriaSubsetIdTO id;
    public String classificazioneCompletezza;
    public String codBolletta;
    public String codConto;
    public String codDocumento;
    public String codIdDominio;
    public String codIufKey;
    public String codOr1;
    public String codProvvisorio;
    public String dataOraFlussoRendicontazione;
    public String deAnnoBolletta;
    public String deAnnoDocumento;
    public String deAnnoProvvisorio;
    public String deCausaleT;
    public String deDataContabile;
    public String deDataEsecuzionePagamento;
    public String deDataRegolamento;
    public String deDataRicezione;
    public String deDataUltimoAggiornamento;
    public String deDataValuta;
    public String deImporto;
    public Date dtDataContabile;
    public Date dtDataEsecuzionePagamento;
    public Date dtDataRegolamento;
    public Date dtDataUltimoAggiornamento;
    public Date dtDataValuta;
    public Date dtRicezione;
    public String identificativoUnivocoRegolamento;
    public String importoTotalePagamenti;
    public BigDecimal numImporto;
    public String singoloImportoCommissioneCaricoPa;

    public RendicontazioneTesoreriaSubsetTO() {
    }

    public RendicontazioneTesoreriaSubsetTO(RendicontazioneTesoreriaSubsetIdTO id, String classificazioneCompletezza, String codBolletta, String codConto, String codDocumento, String codIdDominio, String codIufKey, String codOr1, String codProvvisorio, String dataOraFlussoRendicontazione, String deAnnoBolletta, String deAnnoDocumento, String deAnnoProvvisorio, String deCausaleT, String deDataContabile, String deDataEsecuzionePagamento, String deDataRegolamento, String deDataRicezione, String deDataUltimoAggiornamento, String deDataValuta, String deImporto, Date dtDataContabile, Date dtDataEsecuzionePagamento, Date dtDataRegolamento, Date dtDataUltimoAggiornamento, Date dtDataValuta, Date dtRicezione, String identificativoUnivocoRegolamento, String importoTotalePagamenti, BigDecimal numImporto, String singoloImportoCommissioneCaricoPa) {
        super();
        this.id = id;
        this.classificazioneCompletezza = classificazioneCompletezza;
        this.codBolletta = codBolletta;
        this.codConto = codConto;
        this.codDocumento = codDocumento;
        this.codIdDominio = codIdDominio;
        this.codIufKey = codIufKey;
        this.codOr1 = codOr1;
        this.codProvvisorio = codProvvisorio;
        this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
        this.deAnnoBolletta = deAnnoBolletta;
        this.deAnnoDocumento = deAnnoDocumento;
        this.deAnnoProvvisorio = deAnnoProvvisorio;
        this.deCausaleT = deCausaleT;
        this.deDataContabile = deDataContabile;
        this.deDataEsecuzionePagamento = deDataEsecuzionePagamento;
        this.deDataRegolamento = deDataRegolamento;
        this.deDataRicezione = deDataRicezione;
        this.deDataUltimoAggiornamento = deDataUltimoAggiornamento;
        this.deDataValuta = deDataValuta;
        this.deImporto = deImporto;
        this.dtDataContabile = dtDataContabile;
        this.dtDataEsecuzionePagamento = dtDataEsecuzionePagamento;
        this.dtDataRegolamento = dtDataRegolamento;
        this.dtDataUltimoAggiornamento = dtDataUltimoAggiornamento;
        this.dtDataValuta = dtDataValuta;
        this.dtRicezione = dtRicezione;
        this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
        this.importoTotalePagamenti = importoTotalePagamenti;
        this.numImporto = numImporto;
        this.singoloImportoCommissioneCaricoPa = singoloImportoCommissioneCaricoPa;
    }

    public static RendicontazioneTesoreriaSubsetTO copyOf(RendicontazioneTesoreriaSubsetTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetIdTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).id),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).classificazioneCompletezza,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).codBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).codConto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).codDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).codIdDominio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).codIufKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).codOr1,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).codProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).dataOraFlussoRendicontazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deAnnoBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deAnnoDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deAnnoProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deCausaleT,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deDataContabile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deDataEsecuzionePagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deDataRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deDataValuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).deImporto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).dtDataContabile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).dtDataEsecuzionePagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).dtDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).dtDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).dtDataValuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).dtRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).identificativoUnivocoRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).importoTotalePagamenti,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).numImporto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO) o).singoloImportoCommissioneCaricoPa
                );
    }

    public RendicontazioneTesoreriaSubsetIdTO getId() {
        return id;
    }

    public void setId(RendicontazioneTesoreriaSubsetIdTO id) {
        this.id = id;
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

    public String getCodConto() {
        return codConto;
    }

    public void setCodConto(String codConto) {
        this.codConto = codConto;
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

    public String getCodOr1() {
        return codOr1;
    }

    public void setCodOr1(String codOr1) {
        this.codOr1 = codOr1;
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

    public String getDeCausaleT() {
        return deCausaleT;
    }

    public void setDeCausaleT(String deCausaleT) {
        this.deCausaleT = deCausaleT;
    }

    public String getDeDataContabile() {
        return deDataContabile;
    }

    public void setDeDataContabile(String deDataContabile) {
        this.deDataContabile = deDataContabile;
    }

    public String getDeDataEsecuzionePagamento() {
        return deDataEsecuzionePagamento;
    }

    public void setDeDataEsecuzionePagamento(String deDataEsecuzionePagamento) {
        this.deDataEsecuzionePagamento = deDataEsecuzionePagamento;
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

    public String getDeDataValuta() {
        return deDataValuta;
    }

    public void setDeDataValuta(String deDataValuta) {
        this.deDataValuta = deDataValuta;
    }

    public String getDeImporto() {
        return deImporto;
    }

    public void setDeImporto(String deImporto) {
        this.deImporto = deImporto;
    }

    public Date getDtDataContabile() {
        return dtDataContabile;
    }

    public void setDtDataContabile(Date dtDataContabile) {
        this.dtDataContabile = dtDataContabile;
    }

    public Date getDtDataEsecuzionePagamento() {
        return dtDataEsecuzionePagamento;
    }

    public void setDtDataEsecuzionePagamento(Date dtDataEsecuzionePagamento) {
        this.dtDataEsecuzionePagamento = dtDataEsecuzionePagamento;
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

    public Date getDtDataValuta() {
        return dtDataValuta;
    }

    public void setDtDataValuta(Date dtDataValuta) {
        this.dtDataValuta = dtDataValuta;
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

    public BigDecimal getNumImporto() {
        return numImporto;
    }

    public void setNumImporto(BigDecimal numImporto) {
        this.numImporto = numImporto;
    }

    public String getSingoloImportoCommissioneCaricoPa() {
        return singoloImportoCommissioneCaricoPa;
    }

    public void setSingoloImportoCommissioneCaricoPa(String singoloImportoCommissioneCaricoPa) {
        this.singoloImportoCommissioneCaricoPa = singoloImportoCommissioneCaricoPa;
    }

    public RendicontazioneTesoreriaSubsetTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "RendicontazioneTesoreriaSubsetTO["
            + id
            + ", "
            + classificazioneCompletezza
            + ", "
            + codBolletta
            + ", "
            + codConto
            + ", "
            + codDocumento
            + ", "
            + codIdDominio
            + ", "
            + codIufKey
            + ", "
            + codOr1
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
            + deCausaleT
            + ", "
            + deDataContabile
            + ", "
            + deDataEsecuzionePagamento
            + ", "
            + deDataRegolamento
            + ", "
            + deDataRicezione
            + ", "
            + deDataUltimoAggiornamento
            + ", "
            + deDataValuta
            + ", "
            + deImporto
            + ", "
            + dtDataContabile
            + ", "
            + dtDataEsecuzionePagamento
            + ", "
            + dtDataRegolamento
            + ", "
            + dtDataUltimoAggiornamento
            + ", "
            + dtDataValuta
            + ", "
            + dtRicezione
            + ", "
            + identificativoUnivocoRegolamento
            + ", "
            + importoTotalePagamenti
            + ", "
            + numImporto
            + ", "
            + singoloImportoCommissioneCaricoPa
            + "]";
    }

}
