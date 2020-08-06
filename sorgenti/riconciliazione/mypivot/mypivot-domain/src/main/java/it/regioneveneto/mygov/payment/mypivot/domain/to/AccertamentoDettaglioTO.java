package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class AccertamentoDettaglioTO  implements java.io.Serializable {

    public AccertamentoTO accertamento;
    public UtenteTO utente;
    public Long id;
    public String codAccertamento;
    public String codCapitolo;
    public String codIpaEnte;
    public String codIud;
    public String codIuv;
    public String codTipoDovuto;
    public String codUfficio;
    public Date dtDataInserimento;
    public Date dtUltimaModifica;
    public boolean flgImportoInserito;
    public BigDecimal numImporto;

    protected AccertamentoDettaglioTO() {
    }

    public AccertamentoDettaglioTO(AccertamentoTO accertamento, UtenteTO utente, Long id, String codAccertamento, String codCapitolo, String codIpaEnte, String codIud, String codIuv, String codTipoDovuto, String codUfficio, Date dtDataInserimento, Date dtUltimaModifica, boolean flgImportoInserito, BigDecimal numImporto) {
        super();
        this.accertamento = accertamento;
        this.utente = utente;
        this.id = id;
        this.codAccertamento = codAccertamento;
        this.codCapitolo = codCapitolo;
        this.codIpaEnte = codIpaEnte;
        this.codIud = codIud;
        this.codIuv = codIuv;
        this.codTipoDovuto = codTipoDovuto;
        this.codUfficio = codUfficio;
        this.dtDataInserimento = dtDataInserimento;
        this.dtUltimaModifica = dtUltimaModifica;
        this.flgImportoInserito = flgImportoInserito;
        this.numImporto = numImporto;
    }

    public static AccertamentoDettaglioTO copyOf(AccertamentoDettaglioTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).accertamento),
                it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).utente),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).codAccertamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).codCapitolo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).codIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).codIud,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).codIuv,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).codTipoDovuto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).codUfficio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).dtDataInserimento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).flgImportoInserito,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO) o).numImporto
                );
    }

    public AccertamentoTO getAccertamento() {
        return accertamento;
    }

    public void setAccertamento(AccertamentoTO accertamento) {
        this.accertamento = accertamento;
    }

    public UtenteTO getUtente() {
        return utente;
    }

    public void setUtente(UtenteTO utente) {
        this.utente = utente;
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

    public String getCodIpaEnte() {
        return codIpaEnte;
    }

    public void setCodIpaEnte(String codIpaEnte) {
        this.codIpaEnte = codIpaEnte;
    }

    public String getCodIud() {
        return codIud;
    }

    public void setCodIud(String codIud) {
        this.codIud = codIud;
    }

    public String getCodIuv() {
        return codIuv;
    }

    public void setCodIuv(String codIuv) {
        this.codIuv = codIuv;
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

    public Date getDtDataInserimento() {
        return dtDataInserimento;
    }

    public void setDtDataInserimento(Date dtDataInserimento) {
        this.dtDataInserimento = dtDataInserimento;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public boolean getFlgImportoInserito() {
        return flgImportoInserito;
    }

    public void setFlgImportoInserito(boolean flgImportoInserito) {
        this.flgImportoInserito = flgImportoInserito;
    }

    public BigDecimal getNumImporto() {
        return numImporto;
    }

    public void setNumImporto(BigDecimal numImporto) {
        this.numImporto = numImporto;
    }

    public AccertamentoDettaglioTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.AccertamentoDettaglioTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "AccertamentoDettaglioTO["
            + accertamento
            + ", "
            + utente
            + ", "
            + id
            + ", "
            + codAccertamento
            + ", "
            + codCapitolo
            + ", "
            + codIpaEnte
            + ", "
            + codIud
            + ", "
            + codIuv
            + ", "
            + codTipoDovuto
            + ", "
            + codUfficio
            + ", "
            + dtDataInserimento
            + ", "
            + dtUltimaModifica
            + ", "
            + flgImportoInserito
            + ", "
            + numImporto
            + "]";
    }

}
