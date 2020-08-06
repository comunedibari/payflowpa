package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class FlussoRendicontazioneTO  implements java.io.Serializable {

    public FlussoRendicontazioneIdTO id;
    public EnteTO ente;
    public ManageFlussoTO manageFlusso;
    public String codDatiSingPagamCodiceEsitoSingoloPagamento;
    public String codIdentificativoFlusso;
    public String codIdentificativoUnivocoRegolamento;
    public String codIstMittIdUnivMittCodiceIdentificativoUnivoco;
    public char codIstMittIdUnivMittTipoIdentificativoUnivoco;
    public String codIstRicevIdUnivRicevCodiceIdentificativoUnivoco;
    public Character codIstRicevIdUnivRicevTipoIdentificativoUnivoco;
    public String codiceBicBancaDiRiversamento;
    public String deIstMittDenominazioneMittente;
    public String deIstRicevDenominazioneRicevente;
    public Date dtAcquisizione;
    public Date dtCreazione;
    public Date dtDataOraFlusso;
    public Date dtDataRegolamento;
    public Date dtDatiSingPagamDataEsitoSingoloPagamento;
    public Date dtUltimaModifica;
    public String identificativoPsp;
    public BigDecimal numDatiSingPagamSingoloImportoPagato;
    public BigDecimal numImportoTotalePagamenti;
    public long numNumeroTotalePagamenti;
    public int version;
    public String versioneOggetto;

    protected FlussoRendicontazioneTO() {
    }

    public FlussoRendicontazioneTO(FlussoRendicontazioneIdTO id, EnteTO ente, ManageFlussoTO manageFlusso, String codDatiSingPagamCodiceEsitoSingoloPagamento, String codIdentificativoFlusso, String codIdentificativoUnivocoRegolamento, String codIstMittIdUnivMittCodiceIdentificativoUnivoco, char codIstMittIdUnivMittTipoIdentificativoUnivoco, String codIstRicevIdUnivRicevCodiceIdentificativoUnivoco, Character codIstRicevIdUnivRicevTipoIdentificativoUnivoco, String codiceBicBancaDiRiversamento, String deIstMittDenominazioneMittente, String deIstRicevDenominazioneRicevente, Date dtAcquisizione, Date dtCreazione, Date dtDataOraFlusso, Date dtDataRegolamento, Date dtDatiSingPagamDataEsitoSingoloPagamento, Date dtUltimaModifica, String identificativoPsp, BigDecimal numDatiSingPagamSingoloImportoPagato, BigDecimal numImportoTotalePagamenti, long numNumeroTotalePagamenti, int version, String versioneOggetto) {
        super();
        this.id = id;
        this.ente = ente;
        this.manageFlusso = manageFlusso;
        this.codDatiSingPagamCodiceEsitoSingoloPagamento = codDatiSingPagamCodiceEsitoSingoloPagamento;
        this.codIdentificativoFlusso = codIdentificativoFlusso;
        this.codIdentificativoUnivocoRegolamento = codIdentificativoUnivocoRegolamento;
        this.codIstMittIdUnivMittCodiceIdentificativoUnivoco = codIstMittIdUnivMittCodiceIdentificativoUnivoco;
        this.codIstMittIdUnivMittTipoIdentificativoUnivoco = codIstMittIdUnivMittTipoIdentificativoUnivoco;
        this.codIstRicevIdUnivRicevCodiceIdentificativoUnivoco = codIstRicevIdUnivRicevCodiceIdentificativoUnivoco;
        this.codIstRicevIdUnivRicevTipoIdentificativoUnivoco = codIstRicevIdUnivRicevTipoIdentificativoUnivoco;
        this.codiceBicBancaDiRiversamento = codiceBicBancaDiRiversamento;
        this.deIstMittDenominazioneMittente = deIstMittDenominazioneMittente;
        this.deIstRicevDenominazioneRicevente = deIstRicevDenominazioneRicevente;
        this.dtAcquisizione = dtAcquisizione;
        this.dtCreazione = dtCreazione;
        this.dtDataOraFlusso = dtDataOraFlusso;
        this.dtDataRegolamento = dtDataRegolamento;
        this.dtDatiSingPagamDataEsitoSingoloPagamento = dtDatiSingPagamDataEsitoSingoloPagamento;
        this.dtUltimaModifica = dtUltimaModifica;
        this.identificativoPsp = identificativoPsp;
        this.numDatiSingPagamSingoloImportoPagato = numDatiSingPagamSingoloImportoPagato;
        this.numImportoTotalePagamenti = numImportoTotalePagamenti;
        this.numNumeroTotalePagamenti = numNumeroTotalePagamenti;
        this.version = version;
        this.versioneOggetto = versioneOggetto;
    }

    public static FlussoRendicontazioneTO copyOf(FlussoRendicontazioneTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).id),
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).ente),
                it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).manageFlusso),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codDatiSingPagamCodiceEsitoSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codIdentificativoFlusso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codIdentificativoUnivocoRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codIstMittIdUnivMittCodiceIdentificativoUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codIstMittIdUnivMittTipoIdentificativoUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codIstRicevIdUnivRicevCodiceIdentificativoUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codIstRicevIdUnivRicevTipoIdentificativoUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).codiceBicBancaDiRiversamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).deIstMittDenominazioneMittente,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).deIstRicevDenominazioneRicevente,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).dtAcquisizione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).dtDataOraFlusso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).dtDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).dtDatiSingPagamDataEsitoSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).identificativoPsp,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).numDatiSingPagamSingoloImportoPagato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).numImportoTotalePagamenti,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).numNumeroTotalePagamenti,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).version,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO) o).versioneOggetto
                );
    }

    public FlussoRendicontazioneIdTO getId() {
        return id;
    }

    public void setId(FlussoRendicontazioneIdTO id) {
        this.id = id;
    }

    public EnteTO getEnte() {
        return ente;
    }

    public void setEnte(EnteTO ente) {
        this.ente = ente;
    }

    public ManageFlussoTO getManageFlusso() {
        return manageFlusso;
    }

    public void setManageFlusso(ManageFlussoTO manageFlusso) {
        this.manageFlusso = manageFlusso;
    }

    public String getCodDatiSingPagamCodiceEsitoSingoloPagamento() {
        return codDatiSingPagamCodiceEsitoSingoloPagamento;
    }

    public void setCodDatiSingPagamCodiceEsitoSingoloPagamento(String codDatiSingPagamCodiceEsitoSingoloPagamento) {
        this.codDatiSingPagamCodiceEsitoSingoloPagamento = codDatiSingPagamCodiceEsitoSingoloPagamento;
    }

    public String getCodIdentificativoFlusso() {
        return codIdentificativoFlusso;
    }

    public void setCodIdentificativoFlusso(String codIdentificativoFlusso) {
        this.codIdentificativoFlusso = codIdentificativoFlusso;
    }

    public String getCodIdentificativoUnivocoRegolamento() {
        return codIdentificativoUnivocoRegolamento;
    }

    public void setCodIdentificativoUnivocoRegolamento(String codIdentificativoUnivocoRegolamento) {
        this.codIdentificativoUnivocoRegolamento = codIdentificativoUnivocoRegolamento;
    }

    public String getCodIstMittIdUnivMittCodiceIdentificativoUnivoco() {
        return codIstMittIdUnivMittCodiceIdentificativoUnivoco;
    }

    public void setCodIstMittIdUnivMittCodiceIdentificativoUnivoco(String codIstMittIdUnivMittCodiceIdentificativoUnivoco) {
        this.codIstMittIdUnivMittCodiceIdentificativoUnivoco = codIstMittIdUnivMittCodiceIdentificativoUnivoco;
    }

    public char getCodIstMittIdUnivMittTipoIdentificativoUnivoco() {
        return codIstMittIdUnivMittTipoIdentificativoUnivoco;
    }

    public void setCodIstMittIdUnivMittTipoIdentificativoUnivoco(char codIstMittIdUnivMittTipoIdentificativoUnivoco) {
        this.codIstMittIdUnivMittTipoIdentificativoUnivoco = codIstMittIdUnivMittTipoIdentificativoUnivoco;
    }

    public String getCodIstRicevIdUnivRicevCodiceIdentificativoUnivoco() {
        return codIstRicevIdUnivRicevCodiceIdentificativoUnivoco;
    }

    public void setCodIstRicevIdUnivRicevCodiceIdentificativoUnivoco(String codIstRicevIdUnivRicevCodiceIdentificativoUnivoco) {
        this.codIstRicevIdUnivRicevCodiceIdentificativoUnivoco = codIstRicevIdUnivRicevCodiceIdentificativoUnivoco;
    }

    public Character getCodIstRicevIdUnivRicevTipoIdentificativoUnivoco() {
        return codIstRicevIdUnivRicevTipoIdentificativoUnivoco;
    }

    public void setCodIstRicevIdUnivRicevTipoIdentificativoUnivoco(Character codIstRicevIdUnivRicevTipoIdentificativoUnivoco) {
        this.codIstRicevIdUnivRicevTipoIdentificativoUnivoco = codIstRicevIdUnivRicevTipoIdentificativoUnivoco;
    }

    public String getCodiceBicBancaDiRiversamento() {
        return codiceBicBancaDiRiversamento;
    }

    public void setCodiceBicBancaDiRiversamento(String codiceBicBancaDiRiversamento) {
        this.codiceBicBancaDiRiversamento = codiceBicBancaDiRiversamento;
    }

    public String getDeIstMittDenominazioneMittente() {
        return deIstMittDenominazioneMittente;
    }

    public void setDeIstMittDenominazioneMittente(String deIstMittDenominazioneMittente) {
        this.deIstMittDenominazioneMittente = deIstMittDenominazioneMittente;
    }

    public String getDeIstRicevDenominazioneRicevente() {
        return deIstRicevDenominazioneRicevente;
    }

    public void setDeIstRicevDenominazioneRicevente(String deIstRicevDenominazioneRicevente) {
        this.deIstRicevDenominazioneRicevente = deIstRicevDenominazioneRicevente;
    }

    public Date getDtAcquisizione() {
        return dtAcquisizione;
    }

    public void setDtAcquisizione(Date dtAcquisizione) {
        this.dtAcquisizione = dtAcquisizione;
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

    public Date getDtDataRegolamento() {
        return dtDataRegolamento;
    }

    public void setDtDataRegolamento(Date dtDataRegolamento) {
        this.dtDataRegolamento = dtDataRegolamento;
    }

    public Date getDtDatiSingPagamDataEsitoSingoloPagamento() {
        return dtDatiSingPagamDataEsitoSingoloPagamento;
    }

    public void setDtDatiSingPagamDataEsitoSingoloPagamento(Date dtDatiSingPagamDataEsitoSingoloPagamento) {
        this.dtDatiSingPagamDataEsitoSingoloPagamento = dtDatiSingPagamDataEsitoSingoloPagamento;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public String getIdentificativoPsp() {
        return identificativoPsp;
    }

    public void setIdentificativoPsp(String identificativoPsp) {
        this.identificativoPsp = identificativoPsp;
    }

    public BigDecimal getNumDatiSingPagamSingoloImportoPagato() {
        return numDatiSingPagamSingoloImportoPagato;
    }

    public void setNumDatiSingPagamSingoloImportoPagato(BigDecimal numDatiSingPagamSingoloImportoPagato) {
        this.numDatiSingPagamSingoloImportoPagato = numDatiSingPagamSingoloImportoPagato;
    }

    public BigDecimal getNumImportoTotalePagamenti() {
        return numImportoTotalePagamenti;
    }

    public void setNumImportoTotalePagamenti(BigDecimal numImportoTotalePagamenti) {
        this.numImportoTotalePagamenti = numImportoTotalePagamenti;
    }

    public long getNumNumeroTotalePagamenti() {
        return numNumeroTotalePagamenti;
    }

    public void setNumNumeroTotalePagamenti(long numNumeroTotalePagamenti) {
        this.numNumeroTotalePagamenti = numNumeroTotalePagamenti;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersioneOggetto() {
        return versioneOggetto;
    }

    public void setVersioneOggetto(String versioneOggetto) {
        this.versioneOggetto = versioneOggetto;
    }

    public FlussoRendicontazioneTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "FlussoRendicontazioneTO["
            + id
            + ", "
            + ente
            + ", "
            + manageFlusso
            + ", "
            + codDatiSingPagamCodiceEsitoSingoloPagamento
            + ", "
            + codIdentificativoFlusso
            + ", "
            + codIdentificativoUnivocoRegolamento
            + ", "
            + codIstMittIdUnivMittCodiceIdentificativoUnivoco
            + ", "
            + codIstMittIdUnivMittTipoIdentificativoUnivoco
            + ", "
            + codIstRicevIdUnivRicevCodiceIdentificativoUnivoco
            + ", "
            + codIstRicevIdUnivRicevTipoIdentificativoUnivoco
            + ", "
            + codiceBicBancaDiRiversamento
            + ", "
            + deIstMittDenominazioneMittente
            + ", "
            + deIstRicevDenominazioneRicevente
            + ", "
            + dtAcquisizione
            + ", "
            + dtCreazione
            + ", "
            + dtDataOraFlusso
            + ", "
            + dtDataRegolamento
            + ", "
            + dtDatiSingPagamDataEsitoSingoloPagamento
            + ", "
            + dtUltimaModifica
            + ", "
            + identificativoPsp
            + ", "
            + numDatiSingPagamSingoloImportoPagato
            + ", "
            + numImportoTotalePagamenti
            + ", "
            + numNumeroTotalePagamenti
            + ", "
            + version
            + ", "
            + versioneOggetto
            + "]";
    }

}
