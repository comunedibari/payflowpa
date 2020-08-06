package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.util.Date;

@javax.annotation.Generated("dtonator")
public class PrenotazioneFlussoRiconciliazioneTO  implements java.io.Serializable {

    public AnagraficaStatoTO anagraficaStato;
    public EnteTO ente;
    public UtenteTO utente;
    public Long id;
    public String codCodiceClassificazione;
    public String codContoTesoreria;
    public String codIdRegolamento;
    public String codIdUnivocoDovuto;
    public String codIdUnivocoPagatore;
    public String codIdUnivocoRendicontazione;
    public String codIdUnivocoRiscossione;
    public String codIdUnivocoVersamento;
    public String codIdUnivocoVersante;
    public String codRequestToken;
    public String deAnagraficaPagatore;
    public String deAnagraficaVersante;
    public String deCausale;
    public String deDenominazioneAttestante;
    public String deImportoTesoreria;
    public String deNomeFileGenerato;
    public String deOrdinante;
    public String deTipoDovuto;
    public Date dtCreazione;
    public Date dtDataContabileA;
    public Date dtDataContabileDa;
    public Date dtDataEsecuzioneA;
    public Date dtDataEsecuzioneDa;
    public Date dtDataEsitoA;
    public Date dtDataEsitoDa;
    public Date dtDataRegolamentoA;
    public Date dtDataRegolamentoDa;
    public Date dtDataUltimoAggiornamentoA;
    public Date dtDataUltimoAggiornamentoDa;
    public Date dtDataValutaA;
    public Date dtDataValutaDa;
    public Date dtUltimaModifica;
    public Long numDimensioneFileGenerato;
    public int version;
    public String versioneTracciato;

    public PrenotazioneFlussoRiconciliazioneTO() {
    }

    public PrenotazioneFlussoRiconciliazioneTO(AnagraficaStatoTO anagraficaStato, EnteTO ente, UtenteTO utente, Long id, String codCodiceClassificazione, String codContoTesoreria, String codIdRegolamento, String codIdUnivocoDovuto, String codIdUnivocoPagatore, String codIdUnivocoRendicontazione, String codIdUnivocoRiscossione, String codIdUnivocoVersamento, String codIdUnivocoVersante, String codRequestToken, String deAnagraficaPagatore, String deAnagraficaVersante, String deCausale, String deDenominazioneAttestante, String deImportoTesoreria, String deNomeFileGenerato, String deOrdinante, String deTipoDovuto, Date dtCreazione, Date dtDataContabileA, Date dtDataContabileDa, Date dtDataEsecuzioneA, Date dtDataEsecuzioneDa, Date dtDataEsitoA, Date dtDataEsitoDa, Date dtDataRegolamentoA, Date dtDataRegolamentoDa, Date dtDataUltimoAggiornamentoA, Date dtDataUltimoAggiornamentoDa, Date dtDataValutaA, Date dtDataValutaDa, Date dtUltimaModifica, Long numDimensioneFileGenerato, int version, String versioneTracciato) {
        super();
        this.anagraficaStato = anagraficaStato;
        this.ente = ente;
        this.utente = utente;
        this.id = id;
        this.codCodiceClassificazione = codCodiceClassificazione;
        this.codContoTesoreria = codContoTesoreria;
        this.codIdRegolamento = codIdRegolamento;
        this.codIdUnivocoDovuto = codIdUnivocoDovuto;
        this.codIdUnivocoPagatore = codIdUnivocoPagatore;
        this.codIdUnivocoRendicontazione = codIdUnivocoRendicontazione;
        this.codIdUnivocoRiscossione = codIdUnivocoRiscossione;
        this.codIdUnivocoVersamento = codIdUnivocoVersamento;
        this.codIdUnivocoVersante = codIdUnivocoVersante;
        this.codRequestToken = codRequestToken;
        this.deAnagraficaPagatore = deAnagraficaPagatore;
        this.deAnagraficaVersante = deAnagraficaVersante;
        this.deCausale = deCausale;
        this.deDenominazioneAttestante = deDenominazioneAttestante;
        this.deImportoTesoreria = deImportoTesoreria;
        this.deNomeFileGenerato = deNomeFileGenerato;
        this.deOrdinante = deOrdinante;
        this.deTipoDovuto = deTipoDovuto;
        this.dtCreazione = dtCreazione;
        this.dtDataContabileA = dtDataContabileA;
        this.dtDataContabileDa = dtDataContabileDa;
        this.dtDataEsecuzioneA = dtDataEsecuzioneA;
        this.dtDataEsecuzioneDa = dtDataEsecuzioneDa;
        this.dtDataEsitoA = dtDataEsitoA;
        this.dtDataEsitoDa = dtDataEsitoDa;
        this.dtDataRegolamentoA = dtDataRegolamentoA;
        this.dtDataRegolamentoDa = dtDataRegolamentoDa;
        this.dtDataUltimoAggiornamentoA = dtDataUltimoAggiornamentoA;
        this.dtDataUltimoAggiornamentoDa = dtDataUltimoAggiornamentoDa;
        this.dtDataValutaA = dtDataValutaA;
        this.dtDataValutaDa = dtDataValutaDa;
        this.dtUltimaModifica = dtUltimaModifica;
        this.numDimensioneFileGenerato = numDimensioneFileGenerato;
        this.version = version;
        this.versioneTracciato = versioneTracciato;
    }

    public static PrenotazioneFlussoRiconciliazioneTO copyOf(PrenotazioneFlussoRiconciliazioneTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).anagraficaStato),
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).ente),
                it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).utente),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).id,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codCodiceClassificazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codContoTesoreria,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codIdRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codIdUnivocoDovuto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codIdUnivocoPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codIdUnivocoRendicontazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codIdUnivocoRiscossione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codIdUnivocoVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codIdUnivocoVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).codRequestToken,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deAnagraficaPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deAnagraficaVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deCausale,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deDenominazioneAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deImportoTesoreria,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deNomeFileGenerato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deOrdinante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).deTipoDovuto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataContabileA,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataContabileDa,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataEsecuzioneA,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataEsecuzioneDa,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataEsitoA,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataEsitoDa,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataRegolamentoA,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataRegolamentoDa,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataUltimoAggiornamentoA,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataUltimoAggiornamentoDa,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataValutaA,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtDataValutaDa,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).numDimensioneFileGenerato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).version,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO) o).versioneTracciato
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

    public String getCodCodiceClassificazione() {
        return codCodiceClassificazione;
    }

    public void setCodCodiceClassificazione(String codCodiceClassificazione) {
        this.codCodiceClassificazione = codCodiceClassificazione;
    }

    public String getCodContoTesoreria() {
        return codContoTesoreria;
    }

    public void setCodContoTesoreria(String codContoTesoreria) {
        this.codContoTesoreria = codContoTesoreria;
    }

    public String getCodIdRegolamento() {
        return codIdRegolamento;
    }

    public void setCodIdRegolamento(String codIdRegolamento) {
        this.codIdRegolamento = codIdRegolamento;
    }

    public String getCodIdUnivocoDovuto() {
        return codIdUnivocoDovuto;
    }

    public void setCodIdUnivocoDovuto(String codIdUnivocoDovuto) {
        this.codIdUnivocoDovuto = codIdUnivocoDovuto;
    }

    public String getCodIdUnivocoPagatore() {
        return codIdUnivocoPagatore;
    }

    public void setCodIdUnivocoPagatore(String codIdUnivocoPagatore) {
        this.codIdUnivocoPagatore = codIdUnivocoPagatore;
    }

    public String getCodIdUnivocoRendicontazione() {
        return codIdUnivocoRendicontazione;
    }

    public void setCodIdUnivocoRendicontazione(String codIdUnivocoRendicontazione) {
        this.codIdUnivocoRendicontazione = codIdUnivocoRendicontazione;
    }

    public String getCodIdUnivocoRiscossione() {
        return codIdUnivocoRiscossione;
    }

    public void setCodIdUnivocoRiscossione(String codIdUnivocoRiscossione) {
        this.codIdUnivocoRiscossione = codIdUnivocoRiscossione;
    }

    public String getCodIdUnivocoVersamento() {
        return codIdUnivocoVersamento;
    }

    public void setCodIdUnivocoVersamento(String codIdUnivocoVersamento) {
        this.codIdUnivocoVersamento = codIdUnivocoVersamento;
    }

    public String getCodIdUnivocoVersante() {
        return codIdUnivocoVersante;
    }

    public void setCodIdUnivocoVersante(String codIdUnivocoVersante) {
        this.codIdUnivocoVersante = codIdUnivocoVersante;
    }

    public String getCodRequestToken() {
        return codRequestToken;
    }

    public void setCodRequestToken(String codRequestToken) {
        this.codRequestToken = codRequestToken;
    }

    public String getDeAnagraficaPagatore() {
        return deAnagraficaPagatore;
    }

    public void setDeAnagraficaPagatore(String deAnagraficaPagatore) {
        this.deAnagraficaPagatore = deAnagraficaPagatore;
    }

    public String getDeAnagraficaVersante() {
        return deAnagraficaVersante;
    }

    public void setDeAnagraficaVersante(String deAnagraficaVersante) {
        this.deAnagraficaVersante = deAnagraficaVersante;
    }

    public String getDeCausale() {
        return deCausale;
    }

    public void setDeCausale(String deCausale) {
        this.deCausale = deCausale;
    }

    public String getDeDenominazioneAttestante() {
        return deDenominazioneAttestante;
    }

    public void setDeDenominazioneAttestante(String deDenominazioneAttestante) {
        this.deDenominazioneAttestante = deDenominazioneAttestante;
    }

    public String getDeImportoTesoreria() {
        return deImportoTesoreria;
    }

    public void setDeImportoTesoreria(String deImportoTesoreria) {
        this.deImportoTesoreria = deImportoTesoreria;
    }

    public String getDeNomeFileGenerato() {
        return deNomeFileGenerato;
    }

    public void setDeNomeFileGenerato(String deNomeFileGenerato) {
        this.deNomeFileGenerato = deNomeFileGenerato;
    }

    public String getDeOrdinante() {
        return deOrdinante;
    }

    public void setDeOrdinante(String deOrdinante) {
        this.deOrdinante = deOrdinante;
    }

    public String getDeTipoDovuto() {
        return deTipoDovuto;
    }

    public void setDeTipoDovuto(String deTipoDovuto) {
        this.deTipoDovuto = deTipoDovuto;
    }

    public Date getDtCreazione() {
        return dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
        this.dtCreazione = dtCreazione;
    }

    public Date getDtDataContabileA() {
        return dtDataContabileA;
    }

    public void setDtDataContabileA(Date dtDataContabileA) {
        this.dtDataContabileA = dtDataContabileA;
    }

    public Date getDtDataContabileDa() {
        return dtDataContabileDa;
    }

    public void setDtDataContabileDa(Date dtDataContabileDa) {
        this.dtDataContabileDa = dtDataContabileDa;
    }

    public Date getDtDataEsecuzioneA() {
        return dtDataEsecuzioneA;
    }

    public void setDtDataEsecuzioneA(Date dtDataEsecuzioneA) {
        this.dtDataEsecuzioneA = dtDataEsecuzioneA;
    }

    public Date getDtDataEsecuzioneDa() {
        return dtDataEsecuzioneDa;
    }

    public void setDtDataEsecuzioneDa(Date dtDataEsecuzioneDa) {
        this.dtDataEsecuzioneDa = dtDataEsecuzioneDa;
    }

    public Date getDtDataEsitoA() {
        return dtDataEsitoA;
    }

    public void setDtDataEsitoA(Date dtDataEsitoA) {
        this.dtDataEsitoA = dtDataEsitoA;
    }

    public Date getDtDataEsitoDa() {
        return dtDataEsitoDa;
    }

    public void setDtDataEsitoDa(Date dtDataEsitoDa) {
        this.dtDataEsitoDa = dtDataEsitoDa;
    }

    public Date getDtDataRegolamentoA() {
        return dtDataRegolamentoA;
    }

    public void setDtDataRegolamentoA(Date dtDataRegolamentoA) {
        this.dtDataRegolamentoA = dtDataRegolamentoA;
    }

    public Date getDtDataRegolamentoDa() {
        return dtDataRegolamentoDa;
    }

    public void setDtDataRegolamentoDa(Date dtDataRegolamentoDa) {
        this.dtDataRegolamentoDa = dtDataRegolamentoDa;
    }

    public Date getDtDataUltimoAggiornamentoA() {
        return dtDataUltimoAggiornamentoA;
    }

    public void setDtDataUltimoAggiornamentoA(Date dtDataUltimoAggiornamentoA) {
        this.dtDataUltimoAggiornamentoA = dtDataUltimoAggiornamentoA;
    }

    public Date getDtDataUltimoAggiornamentoDa() {
        return dtDataUltimoAggiornamentoDa;
    }

    public void setDtDataUltimoAggiornamentoDa(Date dtDataUltimoAggiornamentoDa) {
        this.dtDataUltimoAggiornamentoDa = dtDataUltimoAggiornamentoDa;
    }

    public Date getDtDataValutaA() {
        return dtDataValutaA;
    }

    public void setDtDataValutaA(Date dtDataValutaA) {
        this.dtDataValutaA = dtDataValutaA;
    }

    public Date getDtDataValutaDa() {
        return dtDataValutaDa;
    }

    public void setDtDataValutaDa(Date dtDataValutaDa) {
        this.dtDataValutaDa = dtDataValutaDa;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public Long getNumDimensioneFileGenerato() {
        return numDimensioneFileGenerato;
    }

    public void setNumDimensioneFileGenerato(Long numDimensioneFileGenerato) {
        this.numDimensioneFileGenerato = numDimensioneFileGenerato;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersioneTracciato() {
        return versioneTracciato;
    }

    public void setVersioneTracciato(String versioneTracciato) {
        this.versioneTracciato = versioneTracciato;
    }

    public PrenotazioneFlussoRiconciliazioneTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "PrenotazioneFlussoRiconciliazioneTO["
            + anagraficaStato
            + ", "
            + ente
            + ", "
            + utente
            + ", "
            + id
            + ", "
            + codCodiceClassificazione
            + ", "
            + codContoTesoreria
            + ", "
            + codIdRegolamento
            + ", "
            + codIdUnivocoDovuto
            + ", "
            + codIdUnivocoPagatore
            + ", "
            + codIdUnivocoRendicontazione
            + ", "
            + codIdUnivocoRiscossione
            + ", "
            + codIdUnivocoVersamento
            + ", "
            + codIdUnivocoVersante
            + ", "
            + codRequestToken
            + ", "
            + deAnagraficaPagatore
            + ", "
            + deAnagraficaVersante
            + ", "
            + deCausale
            + ", "
            + deDenominazioneAttestante
            + ", "
            + deImportoTesoreria
            + ", "
            + deNomeFileGenerato
            + ", "
            + deOrdinante
            + ", "
            + deTipoDovuto
            + ", "
            + dtCreazione
            + ", "
            + dtDataContabileA
            + ", "
            + dtDataContabileDa
            + ", "
            + dtDataEsecuzioneA
            + ", "
            + dtDataEsecuzioneDa
            + ", "
            + dtDataEsitoA
            + ", "
            + dtDataEsitoDa
            + ", "
            + dtDataRegolamentoA
            + ", "
            + dtDataRegolamentoDa
            + ", "
            + dtDataUltimoAggiornamentoA
            + ", "
            + dtDataUltimoAggiornamentoDa
            + ", "
            + dtDataValutaA
            + ", "
            + dtDataValutaDa
            + ", "
            + dtUltimaModifica
            + ", "
            + numDimensioneFileGenerato
            + ", "
            + version
            + ", "
            + versioneTracciato
            + "]";
    }

}
