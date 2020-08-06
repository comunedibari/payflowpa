package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class EnteTO  implements java.io.Serializable {

    public Long id;
    public String codIpaEnte;
    public String codiceFiscaleEnte;
    public String deNomeEnte;
    public Date dtCreazione;
    public Date dtUltimaModifica;
    public String emailAmministratore;
    public boolean flgPagati;
    public boolean flgTesoreria;
    public String myboxClientKey;
    public String myboxClientSecret;
    public int numGiorniPagamentoPresunti;
    public String deLogoEnte;

    public EnteTO() {
    }

    public EnteTO(Long id, String codIpaEnte, String codiceFiscaleEnte, String deNomeEnte, Date dtCreazione, Date dtUltimaModifica, String emailAmministratore, boolean flgPagati, boolean flgTesoreria, String myboxClientKey, String myboxClientSecret, int numGiorniPagamentoPresunti) {
        super();
        this.id = id;
        this.codIpaEnte = codIpaEnte;
        this.codiceFiscaleEnte = codiceFiscaleEnte;
        this.deNomeEnte = deNomeEnte;
        this.dtCreazione = dtCreazione;
        this.dtUltimaModifica = dtUltimaModifica;
        this.emailAmministratore = emailAmministratore;
        this.flgPagati = flgPagati;
        this.flgTesoreria = flgTesoreria;
        this.myboxClientKey = myboxClientKey;
        this.myboxClientSecret = myboxClientSecret;
        this.numGiorniPagamentoPresunti = numGiorniPagamentoPresunti;
    }

    public static EnteTO copyOf(EnteTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO(
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).codIpaEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).codiceFiscaleEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).deNomeEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).emailAmministratore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).flgPagati,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).flgTesoreria,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).myboxClientKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).myboxClientSecret,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO) o).numGiorniPagamentoPresunti
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodIpaEnte() {
        return codIpaEnte;
    }

    public void setCodIpaEnte(String codIpaEnte) {
        this.codIpaEnte = codIpaEnte;
    }

    public String getCodiceFiscaleEnte() {
        return codiceFiscaleEnte;
    }

    public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
        this.codiceFiscaleEnte = codiceFiscaleEnte;
    }

    public String getDeNomeEnte() {
        return deNomeEnte;
    }

    public void setDeNomeEnte(String deNomeEnte) {
        this.deNomeEnte = deNomeEnte;
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

    public String getEmailAmministratore() {
        return emailAmministratore;
    }

    public void setEmailAmministratore(String emailAmministratore) {
        this.emailAmministratore = emailAmministratore;
    }

    public boolean getFlgPagati() {
        return flgPagati;
    }

    public void setFlgPagati(boolean flgPagati) {
        this.flgPagati = flgPagati;
    }

    public boolean getFlgTesoreria() {
        return flgTesoreria;
    }

    public void setFlgTesoreria(boolean flgTesoreria) {
        this.flgTesoreria = flgTesoreria;
    }

    public String getMyboxClientKey() {
        return myboxClientKey;
    }

    public void setMyboxClientKey(String myboxClientKey) {
        this.myboxClientKey = myboxClientKey;
    }

    public String getMyboxClientSecret() {
        return myboxClientSecret;
    }

    public void setMyboxClientSecret(String myboxClientSecret) {
        this.myboxClientSecret = myboxClientSecret;
    }

    public int getNumGiorniPagamentoPresunti() {
        return numGiorniPagamentoPresunti;
    }

    public void setNumGiorniPagamentoPresunti(int numGiorniPagamentoPresunti) {
        this.numGiorniPagamentoPresunti = numGiorniPagamentoPresunti;
    }
    
    public String getDeLogoEnte() {
        return deLogoEnte;
    }

    public void setDeLogoEnte(String deLogoEnte) {
        this.deLogoEnte = deLogoEnte;
    }

    public EnteTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "EnteTO["
            + id
            + ", "
            + codIpaEnte
            + ", "
            + codiceFiscaleEnte
            + ", "
            + deNomeEnte
            + ", "
            + dtCreazione
            + ", "
            + dtUltimaModifica
            + ", "
            + emailAmministratore
            + ", "
            + flgPagati
            + ", "
            + flgTesoreria
            + ", "
            + myboxClientKey
            + ", "
            + myboxClientSecret
            + ", "
            + numGiorniPagamentoPresunti
            + "]";
    }

}
