package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class AccertamentoTO  implements java.io.Serializable {

    public EnteTipoDovutoTO enteTipoDovuto;
    public UtenteTO utente;
    public AnagraficaStatoTO anagraficaStato;
    public Long id;
    public String deNomeAccertamento;
    public Date dtCreazione;
    public Date dtUltimaModifica;
    public boolean printed;

    protected AccertamentoTO() {
    }

    public AccertamentoTO(EnteTipoDovutoTO enteTipoDovuto, UtenteTO utente, AnagraficaStatoTO anagraficaStato, Long id, String deNomeAccertamento, Date dtCreazione, Date dtUltimaModifica, boolean printed) {
        super();
        this.enteTipoDovuto = enteTipoDovuto;
        this.utente = utente;
        this.anagraficaStato = anagraficaStato;
        this.id = id;
        this.deNomeAccertamento = deNomeAccertamento;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.printed = printed;
    }

    public static AccertamentoTO copyOf(AccertamentoTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).enteTipoDovuto),
                it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).utente),
                it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).anagraficaStato),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).deNomeAccertamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO) o).printed
                );
    }

    public EnteTipoDovutoTO getEnteTipoDovuto() {
        return enteTipoDovuto;
    }

    public void setEnteTipoDovuto(EnteTipoDovutoTO enteTipoDovuto) {
        this.enteTipoDovuto = enteTipoDovuto;
    }

    public UtenteTO getUtente() {
        return utente;
    }

    public void setUtente(UtenteTO utente) {
        this.utente = utente;
    }

    public AnagraficaStatoTO getAnagraficaStato() {
        return anagraficaStato;
    }

    public void setAnagraficaStato(AnagraficaStatoTO anagraficaStato) {
        this.anagraficaStato = anagraficaStato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeNomeAccertamento() {
        return deNomeAccertamento;
    }

    public void setDeNomeAccertamento(String deNomeAccertamento) {
        this.deNomeAccertamento = deNomeAccertamento;
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

    public boolean getPrinted() {
        return printed;
    }

    public void setPrinted(boolean printed) {
        this.printed = printed;
    }

    public AccertamentoTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "AccertamentoTO["
            + enteTipoDovuto
            + ", "
            + utente
            + ", "
            + anagraficaStato
            + ", "
            + id
            + ", "
            + deNomeAccertamento
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + ", "
            + printed
            + "]";
    }

}
