package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class InfoFlussoPosteWebTO  implements java.io.Serializable {

    public ManageFlussoTO manageFlusso;
    public Long id;
    public String codIdentificativoUnivocoRegolamento;
    public Date dtCreazione;
    public Date dtDataRegolamento;
    public Date dtUltimaModifica;
    public BigDecimal numImportoTotalePagamenti;
    public int version;

    public InfoFlussoPosteWebTO() {
    }

    public InfoFlussoPosteWebTO(ManageFlussoTO manageFlusso, Long id, String codIdentificativoUnivocoRegolamento, Date dtCreazione, Date dtDataRegolamento, Date dtUltimaModifica, BigDecimal numImportoTotalePagamenti, int version) {
        super();
        this.manageFlusso = manageFlusso;
        this.id = id;
        this.codIdentificativoUnivocoRegolamento = codIdentificativoUnivocoRegolamento;
        this.dtCreazione = dtCreazione;
        this.dtDataRegolamento = dtDataRegolamento;
        this.dtUltimaModifica = dtUltimaModifica;
        this.numImportoTotalePagamenti = numImportoTotalePagamenti;
        this.version = version;
    }

    public static InfoFlussoPosteWebTO copyOf(InfoFlussoPosteWebTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).manageFlusso),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).codIdentificativoUnivocoRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).dtDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).numImportoTotalePagamenti,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO) o).version
                );
    }

    public ManageFlussoTO getManageFlusso() {
        return manageFlusso;
    }

    public void setManageFlusso(ManageFlussoTO manageFlusso) {
        this.manageFlusso = manageFlusso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodIdentificativoUnivocoRegolamento() {
        return codIdentificativoUnivocoRegolamento;
    }

    public void setCodIdentificativoUnivocoRegolamento(String codIdentificativoUnivocoRegolamento) {
        this.codIdentificativoUnivocoRegolamento = codIdentificativoUnivocoRegolamento;
    }

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }

    public Date getDtDataRegolamento() {
        return dtDataRegolamento;
    }

    public void setDtDataRegolamento(Date dtDataRegolamento) {
        this.dtDataRegolamento = dtDataRegolamento;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public BigDecimal getNumImportoTotalePagamenti() {
        return numImportoTotalePagamenti;
    }

    public void setNumImportoTotalePagamenti(BigDecimal numImportoTotalePagamenti) {
        this.numImportoTotalePagamenti = numImportoTotalePagamenti;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public InfoFlussoPosteWebTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.InfoFlussoPosteWebTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "InfoFlussoPosteWebTO["
            + manageFlusso
            + ", "
            + id
            + ", "
            + codIdentificativoUnivocoRegolamento
            + ", "
            + dtCreazione
            + ", "
            + dtDataRegolamento
            + ", "
            + dtUltimaModifica
            + ", "
            + numImportoTotalePagamenti
            + ", "
            + version
            + "]";
    }

}
