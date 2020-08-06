package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class ManageFlussoTO  implements java.io.Serializable {

    public AnagraficaStatoTO anagraficaStato;
    public EnteTO ente;
    public TipoFlussoTO tipoFlusso;
    public UtenteTO utente;
    public Long id;
    public String codIdentificativoFlusso;
    public String codProvenienzaFile;
    public String codRequestToken;
    public String deNomeFile;
    public String dePercorsoFile;
    public Date dtCreazione;
    public Date dtDataOraFlusso;
    public Date dtUltimaModifica;
    public Long idChiaveMultitabella;
    public String identificativoPsp;
    public Long numDimensioneFileScaricato;
    public int version;

    public ManageFlussoTO() {
    }

    public ManageFlussoTO(AnagraficaStatoTO anagraficaStato, EnteTO ente, TipoFlussoTO tipoFlusso, UtenteTO utente, Long id, String codIdentificativoFlusso, String codProvenienzaFile, String codRequestToken, String deNomeFile, String dePercorsoFile, Date dtCreazione, Date dtDataOraFlusso, Date dtUltimaModifica, Long idChiaveMultitabella, String identificativoPsp, Long numDimensioneFileScaricato, int version) {
        super();
        this.anagraficaStato = anagraficaStato;
        this.ente = ente;
        this.tipoFlusso = tipoFlusso;
        this.utente = utente;
        this.id = id;
        this.codIdentificativoFlusso = codIdentificativoFlusso;
        this.codProvenienzaFile = codProvenienzaFile;
        this.codRequestToken = codRequestToken;
        this.deNomeFile = deNomeFile;
        this.dePercorsoFile = dePercorsoFile;
        this.dtCreazione = dtCreazione;
        this.dtDataOraFlusso = dtDataOraFlusso;
        this.dtUltimaModifica = dtUltimaModifica;
        this.idChiaveMultitabella = idChiaveMultitabella;
        this.identificativoPsp = identificativoPsp;
        this.numDimensioneFileScaricato = numDimensioneFileScaricato;
        this.version = version;
    }

    public static ManageFlussoTO copyOf(ManageFlussoTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).anagraficaStato),
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).ente),
                it.regioneveneto.mygov.payment.mypivot.domain.to.TipoFlussoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).tipoFlusso),
                it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).utente),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).codIdentificativoFlusso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).codProvenienzaFile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).codRequestToken,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).deNomeFile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).dePercorsoFile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).dtDataOraFlusso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).idChiaveMultitabella,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).identificativoPsp,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).numDimensioneFileScaricato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO) o).version
                );
    }

    public AnagraficaStatoTO getAnagraficaStato() {
        return anagraficaStato;
    }

    public void setAnagraficaStato(AnagraficaStatoTO anagraficaStato) {
        this.anagraficaStato = anagraficaStato;
    }

    public EnteTO getEnte() {
        return ente;
    }

    public void setEnte(EnteTO ente) {
        this.ente = ente;
    }

    public TipoFlussoTO getTipoFlusso() {
        return tipoFlusso;
    }

    public void setTipoFlusso(TipoFlussoTO tipoFlusso) {
        this.tipoFlusso = tipoFlusso;
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

    public String getCodIdentificativoFlusso() {
        return codIdentificativoFlusso;
    }

    public void setCodIdentificativoFlusso(String codIdentificativoFlusso) {
        this.codIdentificativoFlusso = codIdentificativoFlusso;
    }

    public String getCodProvenienzaFile() {
        return codProvenienzaFile;
    }

    public void setCodProvenienzaFile(String codProvenienzaFile) {
        this.codProvenienzaFile = codProvenienzaFile;
    }

    public String getCodRequestToken() {
        return codRequestToken;
    }

    public void setCodRequestToken(String codRequestToken) {
        this.codRequestToken = codRequestToken;
    }

    public String getDeNomeFile() {
        return deNomeFile;
    }

    public void setDeNomeFile(String deNomeFile) {
        this.deNomeFile = deNomeFile;
    }

    public String getDePercorsoFile() {
        return dePercorsoFile;
    }

    public void setDePercorsoFile(String dePercorsoFile) {
        this.dePercorsoFile = dePercorsoFile;
    }

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }

    public Date getDtDataOraFlusso() {
        return dtDataOraFlusso;
    }

    public void setDtDataOraFlusso(Date dtDataOraFlusso) {
        this.dtDataOraFlusso = dtDataOraFlusso;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public Long getIdChiaveMultitabella() {
        return idChiaveMultitabella;
    }

    public void setIdChiaveMultitabella(Long idChiaveMultitabella) {
        this.idChiaveMultitabella = idChiaveMultitabella;
    }

    public String getIdentificativoPsp() {
        return identificativoPsp;
    }

    public void setIdentificativoPsp(String identificativoPsp) {
        this.identificativoPsp = identificativoPsp;
    }

    public Long getNumDimensioneFileScaricato() {
        return numDimensioneFileScaricato;
    }

    public void setNumDimensioneFileScaricato(Long numDimensioneFileScaricato) {
        this.numDimensioneFileScaricato = numDimensioneFileScaricato;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ManageFlussoTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "ManageFlussoTO["
            + anagraficaStato
            + ", "
            + ente
            + ", "
            + tipoFlusso
            + ", "
            + utente
            + ", "
            + id
            + ", "
            + codIdentificativoFlusso
            + ", "
            + codProvenienzaFile
            + ", "
            + codRequestToken
            + ", "
            + deNomeFile
            + ", "
            + dePercorsoFile
            + ", "
            + dtCreazione
            + ", "
            + dtDataOraFlusso
            + ", "
            + dtUltimaModifica
            + ", "
            + idChiaveMultitabella
            + ", "
            + identificativoPsp
            + ", "
            + numDimensioneFileScaricato
            + ", "
            + version
            + "]";
    }

}
