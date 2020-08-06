package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class InfoMappingTesoreriaTO  implements java.io.Serializable {

    public ManageFlussoTO manageFlusso;
    public Long id;
    public Date dtCreazione;
    public Date dtUltimaModifica;
    public Integer posCodBolletta;
    public Integer posDeAnnoBolletta;
    public Integer posDeCausale;
    public Integer posDeDenominazione;
    public Integer posDtContabile;
    public Integer posDtValuta;
    public Integer posNumImporto;

    public InfoMappingTesoreriaTO() {
    }

    public InfoMappingTesoreriaTO(ManageFlussoTO manageFlusso, Long id, Date dtCreazione, Date dtUltimaModifica, Integer posCodBolletta, Integer posDeAnnoBolletta, Integer posDeCausale, Integer posDeDenominazione, Integer posDtContabile, Integer posDtValuta, Integer posNumImporto) {
        super();
        this.manageFlusso = manageFlusso;
        this.id = id;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.posCodBolletta = posCodBolletta;
        this.posDeAnnoBolletta = posDeAnnoBolletta;
        this.posDeCausale = posDeCausale;
        this.posDeDenominazione = posDeDenominazione;
        this.posDtContabile = posDtContabile;
        this.posDtValuta = posDtValuta;
        this.posNumImporto = posNumImporto;
    }

    public static InfoMappingTesoreriaTO copyOf(InfoMappingTesoreriaTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).manageFlusso),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).posCodBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).posDeAnnoBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).posDeCausale,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).posDeDenominazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).posDtContabile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).posDtValuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO) o).posNumImporto
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

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public Integer getPosCodBolletta() {
        return posCodBolletta;
    }

    public void setPosCodBolletta(Integer posCodBolletta) {
        this.posCodBolletta = posCodBolletta;
    }

    public Integer getPosDeAnnoBolletta() {
        return posDeAnnoBolletta;
    }

    public void setPosDeAnnoBolletta(Integer posDeAnnoBolletta) {
        this.posDeAnnoBolletta = posDeAnnoBolletta;
    }

    public Integer getPosDeCausale() {
        return posDeCausale;
    }

    public void setPosDeCausale(Integer posDeCausale) {
        this.posDeCausale = posDeCausale;
    }

    public Integer getPosDeDenominazione() {
        return posDeDenominazione;
    }

    public void setPosDeDenominazione(Integer posDeDenominazione) {
        this.posDeDenominazione = posDeDenominazione;
    }

    public Integer getPosDtContabile() {
        return posDtContabile;
    }

    public void setPosDtContabile(Integer posDtContabile) {
        this.posDtContabile = posDtContabile;
    }

    public Integer getPosDtValuta() {
        return posDtValuta;
    }

    public void setPosDtValuta(Integer posDtValuta) {
        this.posDtValuta = posDtValuta;
    }

    public Integer getPosNumImporto() {
        return posNumImporto;
    }

    public void setPosNumImporto(Integer posNumImporto) {
        this.posNumImporto = posNumImporto;
    }

    public InfoMappingTesoreriaTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.InfoMappingTesoreriaTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "InfoMappingTesoreriaTO["
            + manageFlusso
            + ", "
            + id
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + ", "
            + posCodBolletta
            + ", "
            + posDeAnnoBolletta
            + ", "
            + posDeCausale
            + ", "
            + posDeDenominazione
            + ", "
            + posDtContabile
            + ", "
            + posDtValuta
            + ", "
            + posNumImporto
            + "]";
    }

}
