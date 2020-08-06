package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class AnagraficaStatoTO  implements java.io.Serializable {

    public Long id;
    public String codStato;
    public String deStato;
    public String deTipoStato;
    public Date dtCreazione;
    public Date dtUltimaModifica;

    public AnagraficaStatoTO() {
    }

    public AnagraficaStatoTO(Long id, String codStato, String deStato, String deTipoStato, Date dtCreazione, Date dtUltimaModifica) {
        super();
        this.id = id;
        this.codStato = codStato;
        this.deStato = deStato;
        this.deTipoStato = deTipoStato;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public static AnagraficaStatoTO copyOf(AnagraficaStatoTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO) o).codStato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO) o).deStato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO) o).deTipoStato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO) o).dtUltimaModifica
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodStato() {
        return codStato;
    }

    public void setCodStato(String codStato) {
        this.codStato = codStato;
    }

    public String getDeStato() {
        return deStato;
    }

    public void setDeStato(String deStato) {
        this.deStato = deStato;
    }

    public String getDeTipoStato() {
        return deTipoStato;
    }

    public void setDeTipoStato(String deTipoStato) {
        this.deTipoStato = deTipoStato;
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

    public AnagraficaStatoTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "AnagraficaStatoTO["
            + id
            + ", "
            + codStato
            + ", "
            + deStato
            + ", "
            + deTipoStato
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + "]";
    }

}
