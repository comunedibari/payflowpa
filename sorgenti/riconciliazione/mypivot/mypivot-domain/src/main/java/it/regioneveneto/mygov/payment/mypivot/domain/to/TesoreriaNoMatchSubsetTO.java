package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class TesoreriaNoMatchSubsetTO  implements java.io.Serializable {

    public TesoreriaNoMatchSubsetIdTO id;
    public String bilancio;
    public String classificazioneCompletezza;
    public String codConto;
    public String codDocumento;
    public String codIdDominio;
    public String codIufKey;
    public String codIuvKey;
    public String codOr1;
    public String codProvvisorio;
    public String codiceIuv;
    public String deAnnoDocumento;
    public String deAnnoProvvisorio;
    public String deCausaleT;
    public String deDataContabile;
    public String deDataEsecuzionePagamento;
    public String deDataRicezione;
    public String deDataUltimoAggiornamento;
    public String deDataValuta;
    public String deImporto;
    public Date dtDataContabile;
    public Date dtDataEsecuzionePagamento;
    public Date dtDataUltimoAggiornamento;
    public Date dtDataValuta;
    public Date dtRicezione;
    public String identificativoFlussoRendicontazione;
    public BigDecimal numImporto;
    public String singoloImportoCommissioneCaricoPa;

    protected TesoreriaNoMatchSubsetTO() {
    }

    public TesoreriaNoMatchSubsetTO(TesoreriaNoMatchSubsetIdTO id, String bilancio, String classificazioneCompletezza, String codConto, String codDocumento, String codIdDominio, String codIufKey, String codIuvKey, String codOr1, String codProvvisorio, String codiceIuv, String deAnnoDocumento, String deAnnoProvvisorio, String deCausaleT, String deDataContabile, String deDataEsecuzionePagamento, String deDataRicezione, String deDataUltimoAggiornamento, String deDataValuta, String deImporto, Date dtDataContabile, Date dtDataEsecuzionePagamento, Date dtDataUltimoAggiornamento, Date dtDataValuta, Date dtRicezione, String identificativoFlussoRendicontazione, BigDecimal numImporto, String singoloImportoCommissioneCaricoPa) {
        super();
        this.id = id;
        this.bilancio = bilancio;
        this.classificazioneCompletezza = classificazioneCompletezza;
        this.codConto = codConto;
        this.codDocumento = codDocumento;
        this.codIdDominio = codIdDominio;
        this.codIufKey = codIufKey;
        this.codIuvKey = codIuvKey;
        this.codOr1 = codOr1;
        this.codProvvisorio = codProvvisorio;
        this.codiceIuv = codiceIuv;
        this.deAnnoDocumento = deAnnoDocumento;
        this.deAnnoProvvisorio = deAnnoProvvisorio;
        this.deCausaleT = deCausaleT;
        this.deDataContabile = deDataContabile;
        this.deDataEsecuzionePagamento = deDataEsecuzionePagamento;
        this.deDataRicezione = deDataRicezione;
        this.deDataUltimoAggiornamento = deDataUltimoAggiornamento;
        this.deDataValuta = deDataValuta;
        this.deImporto = deImporto;
        this.dtDataContabile = dtDataContabile;
        this.dtDataEsecuzionePagamento = dtDataEsecuzionePagamento;
        this.dtDataUltimoAggiornamento = dtDataUltimoAggiornamento;
        this.dtDataValuta = dtDataValuta;
        this.dtRicezione = dtRicezione;
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
        this.numImporto = numImporto;
        this.singoloImportoCommissioneCaricoPa = singoloImportoCommissioneCaricoPa;
    }

    public static TesoreriaNoMatchSubsetTO copyOf(TesoreriaNoMatchSubsetTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetIdTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).id),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).bilancio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).classificazioneCompletezza,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codConto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codIdDominio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codIufKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codIuvKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codOr1,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).codiceIuv,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deAnnoDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deAnnoProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deCausaleT,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deDataContabile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deDataEsecuzionePagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deDataRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deDataValuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).deImporto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).dtDataContabile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).dtDataEsecuzionePagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).dtDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).dtDataValuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).dtRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).identificativoFlussoRendicontazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).numImporto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO) o).singoloImportoCommissioneCaricoPa
                );
    }

    public TesoreriaNoMatchSubsetIdTO getId() {
        return id;
    }

    public void setId(TesoreriaNoMatchSubsetIdTO id) {
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

    public String getCodIuvKey() {
        return codIuvKey;
    }

    public void setCodIuvKey(String codIuvKey) {
        this.codIuvKey = codIuvKey;
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

    public String getCodiceIuv() {
        return codiceIuv;
    }

    public void setCodiceIuv(String codiceIuv) {
        this.codiceIuv = codiceIuv;
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

    public String getIdentificativoFlussoRendicontazione() {
        return identificativoFlussoRendicontazione;
    }

    public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
        this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
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

    public TesoreriaNoMatchSubsetTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "TesoreriaNoMatchSubsetTO["
            + id
            + ", "
            + bilancio
            + ", "
            + classificazioneCompletezza
            + ", "
            + codConto
            + ", "
            + codDocumento
            + ", "
            + codIdDominio
            + ", "
            + codIufKey
            + ", "
            + codIuvKey
            + ", "
            + codOr1
            + ", "
            + codProvvisorio
            + ", "
            + codiceIuv
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
            + dtDataUltimoAggiornamento
            + ", "
            + dtDataValuta
            + ", "
            + dtRicezione
            + ", "
            + identificativoFlussoRendicontazione
            + ", "
            + numImporto
            + ", "
            + singoloImportoCommissioneCaricoPa
            + "]";
    }

}
