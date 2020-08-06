package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class SegnalazioneTO  implements java.io.Serializable {

    public EnteTO ente;
    public UtenteTO utente;
    public Long id;
    public String classificazioneCompletezza;
    public String codIud;
    public String codIuf;
    public String codIuv;
    public String deNota;
    public Date dtCreazione;
    public Date dtUltimaModifica;
    public Boolean flgAttivo;
    public boolean flgNascosto;
    public int version;

    public SegnalazioneTO() {
    }

    public SegnalazioneTO(EnteTO ente, UtenteTO utente, Long id, String classificazioneCompletezza, String codIud, String codIuf, String codIuv, String deNota, Date dtCreazione, Date dtUltimaModifica, Boolean flgAttivo, boolean flgNascosto, int version) {
        super();
        this.ente = ente;
        this.utente = utente;
        this.id = id;
        this.classificazioneCompletezza = classificazioneCompletezza;
        this.codIud = codIud;
        this.codIuf = codIuf;
        this.codIuv = codIuv;
        this.deNota = deNota;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.flgAttivo = flgAttivo;
        this.flgNascosto = flgNascosto;
        this.version = version;
    }

    public static SegnalazioneTO copyOf(SegnalazioneTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).ente),
                it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).utente),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).classificazioneCompletezza,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).codIud,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).codIuf,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).codIuv,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).deNota,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).flgAttivo,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).flgNascosto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).version
                );
    }

    public EnteTO getEnte() {
        return ente;
    }

    public void setEnte(EnteTO ente) {
        this.ente = ente;
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

    public String getClassificazioneCompletezza() {
        return classificazioneCompletezza;
    }

    public void setClassificazioneCompletezza(String classificazioneCompletezza) {
        this.classificazioneCompletezza = classificazioneCompletezza;
    }

    public String getCodIud() {
        return codIud;
    }

    public void setCodIud(String codIud) {
        this.codIud = codIud;
    }

    public String getCodIuf() {
        return codIuf;
    }

    public void setCodIuf(String codIuf) {
        this.codIuf = codIuf;
    }

    public String getCodIuv() {
        return codIuv;
    }

    public void setCodIuv(String codIuv) {
        this.codIuv = codIuv;
    }

    public String getDeNota() {
        return deNota;
    }

    public void setDeNota(String deNota) {
        this.deNota = deNota;
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

    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    public boolean getFlgNascosto() {
        return flgNascosto;
    }

    public void setFlgNascosto(boolean flgNascosto) {
        this.flgNascosto = flgNascosto;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public SegnalazioneTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "SegnalazioneTO["
            + ente
            + ", "
            + utente
            + ", "
            + id
            + ", "
            + classificazioneCompletezza
            + ", "
            + codIud
            + ", "
            + codIuf
            + ", "
            + codIuv
            + ", "
            + deNota
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + ", "
            + flgAttivo
            + ", "
            + flgNascosto
            + ", "
            + version
            + "]";
    }

}
