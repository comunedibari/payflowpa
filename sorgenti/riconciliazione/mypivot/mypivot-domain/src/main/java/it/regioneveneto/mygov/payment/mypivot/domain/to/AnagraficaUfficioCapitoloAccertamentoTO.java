package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class AnagraficaUfficioCapitoloAccertamentoTO  implements java.io.Serializable {

    public EnteTO ente;
    public Long id;
    public String codAccertamento;
    public String codCapitolo;
    public String codTipoDovuto;
    public String codUfficio;
    public String deAccertamento;
    public String deAnnoEsercizio;
    public String deCapitolo;
    public String deUfficio;
    public Date dtCreazione;
    public Date dtUltimaModifica;
    public boolean flgAttivo;

    public AnagraficaUfficioCapitoloAccertamentoTO() {
    }

    public AnagraficaUfficioCapitoloAccertamentoTO(EnteTO ente, Long id, String codAccertamento, String codCapitolo, String codTipoDovuto, String codUfficio, String deAccertamento, String deAnnoEsercizio, String deCapitolo, String deUfficio, Date dtCreazione, Date dtUltimaModifica, boolean flgAttivo) {
        super();
        this.ente = ente;
        this.id = id;
        this.codAccertamento = codAccertamento;
        this.codCapitolo = codCapitolo;
        this.codTipoDovuto = codTipoDovuto;
        this.codUfficio = codUfficio;
        this.deAccertamento = deAccertamento;
        this.deAnnoEsercizio = deAnnoEsercizio;
        this.deCapitolo = deCapitolo;
        this.deUfficio = deUfficio;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.flgAttivo = flgAttivo;
    }

    public static AnagraficaUfficioCapitoloAccertamentoTO copyOf(AnagraficaUfficioCapitoloAccertamentoTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).ente),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codAccertamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codCapitolo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codTipoDovuto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).codUfficio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deAccertamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deAnnoEsercizio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deCapitolo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).deUfficio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO) o).flgAttivo
                );
    }

    public EnteTO getEnte() {
        return ente;
    }

    public void setEnte(EnteTO ente) {
        this.ente = ente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodAccertamento() {
        return codAccertamento;
    }

    public void setCodAccertamento(String codAccertamento) {
        this.codAccertamento = codAccertamento;
    }

    public String getCodCapitolo() {
        return codCapitolo;
    }

    public void setCodCapitolo(String codCapitolo) {
        this.codCapitolo = codCapitolo;
    }

    public String getCodTipoDovuto() {
        return codTipoDovuto;
    }

    public void setCodTipoDovuto(String codTipoDovuto) {
        this.codTipoDovuto = codTipoDovuto;
    }

    public String getCodUfficio() {
        return codUfficio;
    }

    public void setCodUfficio(String codUfficio) {
        this.codUfficio = codUfficio;
    }

    public String getDeAccertamento() {
        return deAccertamento;
    }

    public void setDeAccertamento(String deAccertamento) {
        this.deAccertamento = deAccertamento;
    }

    public String getDeAnnoEsercizio() {
        return deAnnoEsercizio;
    }

    public void setDeAnnoEsercizio(String deAnnoEsercizio) {
        this.deAnnoEsercizio = deAnnoEsercizio;
    }

    public String getDeCapitolo() {
        return deCapitolo;
    }

    public void setDeCapitolo(String deCapitolo) {
        this.deCapitolo = deCapitolo;
    }

    public String getDeUfficio() {
        return deUfficio;
    }

    public void setDeUfficio(String deUfficio) {
        this.deUfficio = deUfficio;
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

    public boolean getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    public AnagraficaUfficioCapitoloAccertamentoTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "AnagraficaUfficioCapitoloAccertamentoTO["
            + ente
            + ", "
            + id
            + ", "
            + codAccertamento
            + ", "
            + codCapitolo
            + ", "
            + codTipoDovuto
            + ", "
            + codUfficio
            + ", "
            + deAccertamento
            + ", "
            + deAnnoEsercizio
            + ", "
            + deCapitolo
            + ", "
            + deUfficio
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + ", "
            + flgAttivo
            + "]";
    }

}
