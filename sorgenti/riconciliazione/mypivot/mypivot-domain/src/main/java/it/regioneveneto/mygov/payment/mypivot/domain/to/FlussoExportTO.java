package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class FlussoExportTO  implements java.io.Serializable {

    public FlussoExportIdTO id;
    public EnteTO ente;
    public ManageFlussoTO manageFlusso;
    public String bilancio;
    public String codEDatiPagCodiceContestoPagamento;
    public Character codEDatiPagCodiceEsitoPagamento;
    public String codEDatiPagIdUnivocoVersamento;
    public String codEDomIdDominio;
    public String codEDomIdStazioneRichiedente;
    public String codEEnteBenefCapBeneficiario;
    public String codEEnteBenefCodiceUnitOperBeneficiario;
    public String codEEnteBenefIdUnivBenefCodiceIdUnivoco;
    public Character codEEnteBenefIdUnivBenefTipoIdUnivoco;
    public String codEEnteBenefNazioneBeneficiario;
    public String codEIdMessaggioRicevuta;
    public String codEIstitAttCapAttestante;
    public String codEIstitAttCodiceUnitOperAttestante;
    public String codEIstitAttIdUnivAttCodiceIdUnivoco;
    public Character codEIstitAttIdUnivAttTipoIdUnivoco;
    public String codEIstitAttNazioneAttestante;
    public String codERiferimentoMessaggioRichiesta;
    public String codESoggPagAnagraficaPagatore;
    public String codESoggPagCapPagatore;
    public String codESoggPagIdUnivPagCodiceIdUnivoco;
    public Character codESoggPagIdUnivPagTipoIdUnivoco;
    public String codESoggPagNazionePagatore;
    public String codESoggVersAnagraficaVersante;
    public String codESoggVersCapVersante;
    public String codESoggVersIdUnivVersCodiceIdUnivoco;
    public Character codESoggVersIdUnivVersTipoIdUnivoco;
    public String codESoggVersNazioneVersante;
    public String codIud;
    public String codTipoDovuto;
    public String deEDatiPagDatiSingPagCausaleVersamento;
    public String deEDatiPagDatiSingPagDatiSpecificiRiscossione;
    public String deEDatiPagDatiSingPagEsitoSingoloPagamento;
    public String deEEnteBenefCivicoBeneficiario;
    public String deEEnteBenefDenomUnitOperBeneficiario;
    public String deEEnteBenefDenominazioneBeneficiario;
    public String deEEnteBenefIndirizzoBeneficiario;
    public String deEEnteBenefLocalitaBeneficiario;
    public String deEEnteBenefProvinciaBeneficiario;
    public String deEIstitAttCivicoAttestante;
    public String deEIstitAttDenomUnitOperAttestante;
    public String deEIstitAttDenominazioneAttestante;
    public String deEIstitAttIndirizzoAttestante;
    public String deEIstitAttLocalitaAttestante;
    public String deEIstitAttProvinciaAttestante;
    public String deESoggPagCivicoPagatore;
    public String deESoggPagEmailPagatore;
    public String deESoggPagIndirizzoPagatore;
    public String deESoggPagLocalitaPagatore;
    public String deESoggPagProvinciaPagatore;
    public String deESoggVersCivicoVersante;
    public String deESoggVersEmailVersante;
    public String deESoggVersIndirizzoVersante;
    public String deESoggVersLocalitaVersante;
    public String deESoggVersProvinciaVersante;
    public String deEVersioneOggetto;
    public String deImportaDovutoEsito;
    public String deImportaDovutoFaultCode;
    public String deImportaDovutoFaultDescription;
    public String deImportaDovutoFaultId;
    public String deImportaDovutoFaultString;
    public String deNomeFlusso;
    public Date dtAcquisizione;
    public Date dtCreazione;
    public Date dtEDataOraMessaggioRicevuta;
    public Date dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
    public Date dtERiferimentoDataRichiesta;
    public Date dtUltimaModifica;
    public BigDecimal numEDatiPagDatiSingPagSingoloImportoPagato;
    public BigDecimal numEDatiPagImportoTotalePagato;
    public Integer numImportaDovutoFaultSerial;
    public Integer numRigaFlusso;
    public int version;

    public FlussoExportTO() {
    }

    public FlussoExportTO(FlussoExportIdTO id, EnteTO ente, ManageFlussoTO manageFlusso, String bilancio, String codEDatiPagCodiceContestoPagamento, Character codEDatiPagCodiceEsitoPagamento, String codEDatiPagIdUnivocoVersamento, String codEDomIdDominio, String codEDomIdStazioneRichiedente, String codEEnteBenefCapBeneficiario, String codEEnteBenefCodiceUnitOperBeneficiario, String codEEnteBenefIdUnivBenefCodiceIdUnivoco, Character codEEnteBenefIdUnivBenefTipoIdUnivoco, String codEEnteBenefNazioneBeneficiario, String codEIdMessaggioRicevuta, String codEIstitAttCapAttestante, String codEIstitAttCodiceUnitOperAttestante, String codEIstitAttIdUnivAttCodiceIdUnivoco, Character codEIstitAttIdUnivAttTipoIdUnivoco, String codEIstitAttNazioneAttestante, String codERiferimentoMessaggioRichiesta, String codESoggPagAnagraficaPagatore, String codESoggPagCapPagatore, String codESoggPagIdUnivPagCodiceIdUnivoco, Character codESoggPagIdUnivPagTipoIdUnivoco, String codESoggPagNazionePagatore, String codESoggVersAnagraficaVersante, String codESoggVersCapVersante, String codESoggVersIdUnivVersCodiceIdUnivoco, Character codESoggVersIdUnivVersTipoIdUnivoco, String codESoggVersNazioneVersante, String codIud, String codTipoDovuto, String deEDatiPagDatiSingPagCausaleVersamento, String deEDatiPagDatiSingPagDatiSpecificiRiscossione, String deEDatiPagDatiSingPagEsitoSingoloPagamento, String deEEnteBenefCivicoBeneficiario, String deEEnteBenefDenomUnitOperBeneficiario, String deEEnteBenefDenominazioneBeneficiario, String deEEnteBenefIndirizzoBeneficiario, String deEEnteBenefLocalitaBeneficiario, String deEEnteBenefProvinciaBeneficiario, String deEIstitAttCivicoAttestante, String deEIstitAttDenomUnitOperAttestante, String deEIstitAttDenominazioneAttestante, String deEIstitAttIndirizzoAttestante, String deEIstitAttLocalitaAttestante, String deEIstitAttProvinciaAttestante, String deESoggPagCivicoPagatore, String deESoggPagEmailPagatore, String deESoggPagIndirizzoPagatore, String deESoggPagLocalitaPagatore, String deESoggPagProvinciaPagatore, String deESoggVersCivicoVersante, String deESoggVersEmailVersante, String deESoggVersIndirizzoVersante, String deESoggVersLocalitaVersante, String deESoggVersProvinciaVersante, String deEVersioneOggetto, String deImportaDovutoEsito, String deImportaDovutoFaultCode, String deImportaDovutoFaultDescription, String deImportaDovutoFaultId, String deImportaDovutoFaultString, String deNomeFlusso, Date dtAcquisizione, Date dtCreazione, Date dtEDataOraMessaggioRicevuta, Date dtEDatiPagDatiSingPagDataEsitoSingoloPagamento, Date dtERiferimentoDataRichiesta, Date dtUltimaModifica, BigDecimal numEDatiPagDatiSingPagSingoloImportoPagato, BigDecimal numEDatiPagImportoTotalePagato, Integer numImportaDovutoFaultSerial, Integer numRigaFlusso, int version) {
        super();
        this.id = id;
        this.ente = ente;
        this.manageFlusso = manageFlusso;
        this.bilancio = bilancio;
        this.codEDatiPagCodiceContestoPagamento = codEDatiPagCodiceContestoPagamento;
        this.codEDatiPagCodiceEsitoPagamento = codEDatiPagCodiceEsitoPagamento;
        this.codEDatiPagIdUnivocoVersamento = codEDatiPagIdUnivocoVersamento;
        this.codEDomIdDominio = codEDomIdDominio;
        this.codEDomIdStazioneRichiedente = codEDomIdStazioneRichiedente;
        this.codEEnteBenefCapBeneficiario = codEEnteBenefCapBeneficiario;
        this.codEEnteBenefCodiceUnitOperBeneficiario = codEEnteBenefCodiceUnitOperBeneficiario;
        this.codEEnteBenefIdUnivBenefCodiceIdUnivoco = codEEnteBenefIdUnivBenefCodiceIdUnivoco;
        this.codEEnteBenefIdUnivBenefTipoIdUnivoco = codEEnteBenefIdUnivBenefTipoIdUnivoco;
        this.codEEnteBenefNazioneBeneficiario = codEEnteBenefNazioneBeneficiario;
        this.codEIdMessaggioRicevuta = codEIdMessaggioRicevuta;
        this.codEIstitAttCapAttestante = codEIstitAttCapAttestante;
        this.codEIstitAttCodiceUnitOperAttestante = codEIstitAttCodiceUnitOperAttestante;
        this.codEIstitAttIdUnivAttCodiceIdUnivoco = codEIstitAttIdUnivAttCodiceIdUnivoco;
        this.codEIstitAttIdUnivAttTipoIdUnivoco = codEIstitAttIdUnivAttTipoIdUnivoco;
        this.codEIstitAttNazioneAttestante = codEIstitAttNazioneAttestante;
        this.codERiferimentoMessaggioRichiesta = codERiferimentoMessaggioRichiesta;
        this.codESoggPagAnagraficaPagatore = codESoggPagAnagraficaPagatore;
        this.codESoggPagCapPagatore = codESoggPagCapPagatore;
        this.codESoggPagIdUnivPagCodiceIdUnivoco = codESoggPagIdUnivPagCodiceIdUnivoco;
        this.codESoggPagIdUnivPagTipoIdUnivoco = codESoggPagIdUnivPagTipoIdUnivoco;
        this.codESoggPagNazionePagatore = codESoggPagNazionePagatore;
        this.codESoggVersAnagraficaVersante = codESoggVersAnagraficaVersante;
        this.codESoggVersCapVersante = codESoggVersCapVersante;
        this.codESoggVersIdUnivVersCodiceIdUnivoco = codESoggVersIdUnivVersCodiceIdUnivoco;
        this.codESoggVersIdUnivVersTipoIdUnivoco = codESoggVersIdUnivVersTipoIdUnivoco;
        this.codESoggVersNazioneVersante = codESoggVersNazioneVersante;
        this.codIud = codIud;
        this.codTipoDovuto = codTipoDovuto;
        this.deEDatiPagDatiSingPagCausaleVersamento = deEDatiPagDatiSingPagCausaleVersamento;
        this.deEDatiPagDatiSingPagDatiSpecificiRiscossione = deEDatiPagDatiSingPagDatiSpecificiRiscossione;
        this.deEDatiPagDatiSingPagEsitoSingoloPagamento = deEDatiPagDatiSingPagEsitoSingoloPagamento;
        this.deEEnteBenefCivicoBeneficiario = deEEnteBenefCivicoBeneficiario;
        this.deEEnteBenefDenomUnitOperBeneficiario = deEEnteBenefDenomUnitOperBeneficiario;
        this.deEEnteBenefDenominazioneBeneficiario = deEEnteBenefDenominazioneBeneficiario;
        this.deEEnteBenefIndirizzoBeneficiario = deEEnteBenefIndirizzoBeneficiario;
        this.deEEnteBenefLocalitaBeneficiario = deEEnteBenefLocalitaBeneficiario;
        this.deEEnteBenefProvinciaBeneficiario = deEEnteBenefProvinciaBeneficiario;
        this.deEIstitAttCivicoAttestante = deEIstitAttCivicoAttestante;
        this.deEIstitAttDenomUnitOperAttestante = deEIstitAttDenomUnitOperAttestante;
        this.deEIstitAttDenominazioneAttestante = deEIstitAttDenominazioneAttestante;
        this.deEIstitAttIndirizzoAttestante = deEIstitAttIndirizzoAttestante;
        this.deEIstitAttLocalitaAttestante = deEIstitAttLocalitaAttestante;
        this.deEIstitAttProvinciaAttestante = deEIstitAttProvinciaAttestante;
        this.deESoggPagCivicoPagatore = deESoggPagCivicoPagatore;
        this.deESoggPagEmailPagatore = deESoggPagEmailPagatore;
        this.deESoggPagIndirizzoPagatore = deESoggPagIndirizzoPagatore;
        this.deESoggPagLocalitaPagatore = deESoggPagLocalitaPagatore;
        this.deESoggPagProvinciaPagatore = deESoggPagProvinciaPagatore;
        this.deESoggVersCivicoVersante = deESoggVersCivicoVersante;
        this.deESoggVersEmailVersante = deESoggVersEmailVersante;
        this.deESoggVersIndirizzoVersante = deESoggVersIndirizzoVersante;
        this.deESoggVersLocalitaVersante = deESoggVersLocalitaVersante;
        this.deESoggVersProvinciaVersante = deESoggVersProvinciaVersante;
        this.deEVersioneOggetto = deEVersioneOggetto;
        this.deImportaDovutoEsito = deImportaDovutoEsito;
        this.deImportaDovutoFaultCode = deImportaDovutoFaultCode;
        this.deImportaDovutoFaultDescription = deImportaDovutoFaultDescription;
        this.deImportaDovutoFaultId = deImportaDovutoFaultId;
        this.deImportaDovutoFaultString = deImportaDovutoFaultString;
        this.deNomeFlusso = deNomeFlusso;
        this.dtAcquisizione = dtAcquisizione;
        this.dtCreazione = dtCreazione;
        this.dtEDataOraMessaggioRicevuta = dtEDataOraMessaggioRicevuta;
        this.dtEDatiPagDatiSingPagDataEsitoSingoloPagamento = dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
        this.dtERiferimentoDataRichiesta = dtERiferimentoDataRichiesta;
        this.dtUltimaModifica = dtUltimaModifica;
        this.numEDatiPagDatiSingPagSingoloImportoPagato = numEDatiPagDatiSingPagSingoloImportoPagato;
        this.numEDatiPagImportoTotalePagato = numEDatiPagImportoTotalePagato;
        this.numImportaDovutoFaultSerial = numImportaDovutoFaultSerial;
        this.numRigaFlusso = numRigaFlusso;
        this.version = version;
    }

    public static FlussoExportTO copyOf(FlussoExportTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).id),
                it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).ente),
                it.regioneveneto.mygov.payment.mypivot.domain.to.ManageFlussoTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).manageFlusso),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).bilancio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEDatiPagCodiceContestoPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEDatiPagCodiceEsitoPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEDatiPagIdUnivocoVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEDomIdDominio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEDomIdStazioneRichiedente,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEEnteBenefCapBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEEnteBenefCodiceUnitOperBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEEnteBenefIdUnivBenefCodiceIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEEnteBenefIdUnivBenefTipoIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEEnteBenefNazioneBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEIdMessaggioRicevuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEIstitAttCapAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEIstitAttCodiceUnitOperAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEIstitAttIdUnivAttCodiceIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEIstitAttIdUnivAttTipoIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codEIstitAttNazioneAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codERiferimentoMessaggioRichiesta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggPagAnagraficaPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggPagCapPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggPagIdUnivPagCodiceIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggPagIdUnivPagTipoIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggPagNazionePagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggVersAnagraficaVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggVersCapVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggVersIdUnivVersCodiceIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggVersIdUnivVersTipoIdUnivoco,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codESoggVersNazioneVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codIud,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).codTipoDovuto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEDatiPagDatiSingPagCausaleVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEDatiPagDatiSingPagDatiSpecificiRiscossione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEDatiPagDatiSingPagEsitoSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEEnteBenefCivicoBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEEnteBenefDenomUnitOperBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEEnteBenefDenominazioneBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEEnteBenefIndirizzoBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEEnteBenefLocalitaBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEEnteBenefProvinciaBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEIstitAttCivicoAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEIstitAttDenomUnitOperAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEIstitAttDenominazioneAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEIstitAttIndirizzoAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEIstitAttLocalitaAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEIstitAttProvinciaAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggPagCivicoPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggPagEmailPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggPagIndirizzoPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggPagLocalitaPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggPagProvinciaPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggVersCivicoVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggVersEmailVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggVersIndirizzoVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggVersLocalitaVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deESoggVersProvinciaVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deEVersioneOggetto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deImportaDovutoEsito,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deImportaDovutoFaultCode,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deImportaDovutoFaultDescription,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deImportaDovutoFaultId,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deImportaDovutoFaultString,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).deNomeFlusso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).dtAcquisizione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).dtCreazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).dtEDataOraMessaggioRicevuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).dtEDatiPagDatiSingPagDataEsitoSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).dtERiferimentoDataRichiesta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).dtUltimaModifica,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).numEDatiPagDatiSingPagSingoloImportoPagato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).numEDatiPagImportoTotalePagato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).numImportaDovutoFaultSerial,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).numRigaFlusso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO) o).version
                );
    }

    public FlussoExportIdTO getId() {
        return id;
    }

    public void setId(FlussoExportIdTO id) {
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

    public String getBilancio() {
        return bilancio;
    }

    public void setBilancio(String bilancio) {
        this.bilancio = bilancio;
    }

    public String getCodEDatiPagCodiceContestoPagamento() {
        return codEDatiPagCodiceContestoPagamento;
    }

    public void setCodEDatiPagCodiceContestoPagamento(String codEDatiPagCodiceContestoPagamento) {
        this.codEDatiPagCodiceContestoPagamento = codEDatiPagCodiceContestoPagamento;
    }

    public Character getCodEDatiPagCodiceEsitoPagamento() {
        return codEDatiPagCodiceEsitoPagamento;
    }

    public void setCodEDatiPagCodiceEsitoPagamento(Character codEDatiPagCodiceEsitoPagamento) {
        this.codEDatiPagCodiceEsitoPagamento = codEDatiPagCodiceEsitoPagamento;
    }

    public String getCodEDatiPagIdUnivocoVersamento() {
        return codEDatiPagIdUnivocoVersamento;
    }

    public void setCodEDatiPagIdUnivocoVersamento(String codEDatiPagIdUnivocoVersamento) {
        this.codEDatiPagIdUnivocoVersamento = codEDatiPagIdUnivocoVersamento;
    }

    public String getCodEDomIdDominio() {
        return codEDomIdDominio;
    }

    public void setCodEDomIdDominio(String codEDomIdDominio) {
        this.codEDomIdDominio = codEDomIdDominio;
    }

    public String getCodEDomIdStazioneRichiedente() {
        return codEDomIdStazioneRichiedente;
    }

    public void setCodEDomIdStazioneRichiedente(String codEDomIdStazioneRichiedente) {
        this.codEDomIdStazioneRichiedente = codEDomIdStazioneRichiedente;
    }

    public String getCodEEnteBenefCapBeneficiario() {
        return codEEnteBenefCapBeneficiario;
    }

    public void setCodEEnteBenefCapBeneficiario(String codEEnteBenefCapBeneficiario) {
        this.codEEnteBenefCapBeneficiario = codEEnteBenefCapBeneficiario;
    }

    public String getCodEEnteBenefCodiceUnitOperBeneficiario() {
        return codEEnteBenefCodiceUnitOperBeneficiario;
    }

    public void setCodEEnteBenefCodiceUnitOperBeneficiario(String codEEnteBenefCodiceUnitOperBeneficiario) {
        this.codEEnteBenefCodiceUnitOperBeneficiario = codEEnteBenefCodiceUnitOperBeneficiario;
    }

    public String getCodEEnteBenefIdUnivBenefCodiceIdUnivoco() {
        return codEEnteBenefIdUnivBenefCodiceIdUnivoco;
    }

    public void setCodEEnteBenefIdUnivBenefCodiceIdUnivoco(String codEEnteBenefIdUnivBenefCodiceIdUnivoco) {
        this.codEEnteBenefIdUnivBenefCodiceIdUnivoco = codEEnteBenefIdUnivBenefCodiceIdUnivoco;
    }

    public Character getCodEEnteBenefIdUnivBenefTipoIdUnivoco() {
        return codEEnteBenefIdUnivBenefTipoIdUnivoco;
    }

    public void setCodEEnteBenefIdUnivBenefTipoIdUnivoco(Character codEEnteBenefIdUnivBenefTipoIdUnivoco) {
        this.codEEnteBenefIdUnivBenefTipoIdUnivoco = codEEnteBenefIdUnivBenefTipoIdUnivoco;
    }

    public String getCodEEnteBenefNazioneBeneficiario() {
        return codEEnteBenefNazioneBeneficiario;
    }

    public void setCodEEnteBenefNazioneBeneficiario(String codEEnteBenefNazioneBeneficiario) {
        this.codEEnteBenefNazioneBeneficiario = codEEnteBenefNazioneBeneficiario;
    }

    public String getCodEIdMessaggioRicevuta() {
        return codEIdMessaggioRicevuta;
    }

    public void setCodEIdMessaggioRicevuta(String codEIdMessaggioRicevuta) {
        this.codEIdMessaggioRicevuta = codEIdMessaggioRicevuta;
    }

    public String getCodEIstitAttCapAttestante() {
        return codEIstitAttCapAttestante;
    }

    public void setCodEIstitAttCapAttestante(String codEIstitAttCapAttestante) {
        this.codEIstitAttCapAttestante = codEIstitAttCapAttestante;
    }

    public String getCodEIstitAttCodiceUnitOperAttestante() {
        return codEIstitAttCodiceUnitOperAttestante;
    }

    public void setCodEIstitAttCodiceUnitOperAttestante(String codEIstitAttCodiceUnitOperAttestante) {
        this.codEIstitAttCodiceUnitOperAttestante = codEIstitAttCodiceUnitOperAttestante;
    }

    public String getCodEIstitAttIdUnivAttCodiceIdUnivoco() {
        return codEIstitAttIdUnivAttCodiceIdUnivoco;
    }

    public void setCodEIstitAttIdUnivAttCodiceIdUnivoco(String codEIstitAttIdUnivAttCodiceIdUnivoco) {
        this.codEIstitAttIdUnivAttCodiceIdUnivoco = codEIstitAttIdUnivAttCodiceIdUnivoco;
    }

    public Character getCodEIstitAttIdUnivAttTipoIdUnivoco() {
        return codEIstitAttIdUnivAttTipoIdUnivoco;
    }

    public void setCodEIstitAttIdUnivAttTipoIdUnivoco(Character codEIstitAttIdUnivAttTipoIdUnivoco) {
        this.codEIstitAttIdUnivAttTipoIdUnivoco = codEIstitAttIdUnivAttTipoIdUnivoco;
    }

    public String getCodEIstitAttNazioneAttestante() {
        return codEIstitAttNazioneAttestante;
    }

    public void setCodEIstitAttNazioneAttestante(String codEIstitAttNazioneAttestante) {
        this.codEIstitAttNazioneAttestante = codEIstitAttNazioneAttestante;
    }

    public String getCodERiferimentoMessaggioRichiesta() {
        return codERiferimentoMessaggioRichiesta;
    }

    public void setCodERiferimentoMessaggioRichiesta(String codERiferimentoMessaggioRichiesta) {
        this.codERiferimentoMessaggioRichiesta = codERiferimentoMessaggioRichiesta;
    }

    public String getCodESoggPagAnagraficaPagatore() {
        return codESoggPagAnagraficaPagatore;
    }

    public void setCodESoggPagAnagraficaPagatore(String codESoggPagAnagraficaPagatore) {
        this.codESoggPagAnagraficaPagatore = codESoggPagAnagraficaPagatore;
    }

    public String getCodESoggPagCapPagatore() {
        return codESoggPagCapPagatore;
    }

    public void setCodESoggPagCapPagatore(String codESoggPagCapPagatore) {
        this.codESoggPagCapPagatore = codESoggPagCapPagatore;
    }

    public String getCodESoggPagIdUnivPagCodiceIdUnivoco() {
        return codESoggPagIdUnivPagCodiceIdUnivoco;
    }

    public void setCodESoggPagIdUnivPagCodiceIdUnivoco(String codESoggPagIdUnivPagCodiceIdUnivoco) {
        this.codESoggPagIdUnivPagCodiceIdUnivoco = codESoggPagIdUnivPagCodiceIdUnivoco;
    }

    public Character getCodESoggPagIdUnivPagTipoIdUnivoco() {
        return codESoggPagIdUnivPagTipoIdUnivoco;
    }

    public void setCodESoggPagIdUnivPagTipoIdUnivoco(Character codESoggPagIdUnivPagTipoIdUnivoco) {
        this.codESoggPagIdUnivPagTipoIdUnivoco = codESoggPagIdUnivPagTipoIdUnivoco;
    }

    public String getCodESoggPagNazionePagatore() {
        return codESoggPagNazionePagatore;
    }

    public void setCodESoggPagNazionePagatore(String codESoggPagNazionePagatore) {
        this.codESoggPagNazionePagatore = codESoggPagNazionePagatore;
    }

    public String getCodESoggVersAnagraficaVersante() {
        return codESoggVersAnagraficaVersante;
    }

    public void setCodESoggVersAnagraficaVersante(String codESoggVersAnagraficaVersante) {
        this.codESoggVersAnagraficaVersante = codESoggVersAnagraficaVersante;
    }

    public String getCodESoggVersCapVersante() {
        return codESoggVersCapVersante;
    }

    public void setCodESoggVersCapVersante(String codESoggVersCapVersante) {
        this.codESoggVersCapVersante = codESoggVersCapVersante;
    }

    public String getCodESoggVersIdUnivVersCodiceIdUnivoco() {
        return codESoggVersIdUnivVersCodiceIdUnivoco;
    }

    public void setCodESoggVersIdUnivVersCodiceIdUnivoco(String codESoggVersIdUnivVersCodiceIdUnivoco) {
        this.codESoggVersIdUnivVersCodiceIdUnivoco = codESoggVersIdUnivVersCodiceIdUnivoco;
    }

    public Character getCodESoggVersIdUnivVersTipoIdUnivoco() {
        return codESoggVersIdUnivVersTipoIdUnivoco;
    }

    public void setCodESoggVersIdUnivVersTipoIdUnivoco(Character codESoggVersIdUnivVersTipoIdUnivoco) {
        this.codESoggVersIdUnivVersTipoIdUnivoco = codESoggVersIdUnivVersTipoIdUnivoco;
    }

    public String getCodESoggVersNazioneVersante() {
        return codESoggVersNazioneVersante;
    }

    public void setCodESoggVersNazioneVersante(String codESoggVersNazioneVersante) {
        this.codESoggVersNazioneVersante = codESoggVersNazioneVersante;
    }

    public String getCodIud() {
        return codIud;
    }

    public void setCodIud(String codIud) {
        this.codIud = codIud;
    }

    public String getCodTipoDovuto() {
        return codTipoDovuto;
    }

    public void setCodTipoDovuto(String codTipoDovuto) {
        this.codTipoDovuto = codTipoDovuto;
    }

    public String getDeEDatiPagDatiSingPagCausaleVersamento() {
        return deEDatiPagDatiSingPagCausaleVersamento;
    }

    public void setDeEDatiPagDatiSingPagCausaleVersamento(String deEDatiPagDatiSingPagCausaleVersamento) {
        this.deEDatiPagDatiSingPagCausaleVersamento = deEDatiPagDatiSingPagCausaleVersamento;
    }

    public String getDeEDatiPagDatiSingPagDatiSpecificiRiscossione() {
        return deEDatiPagDatiSingPagDatiSpecificiRiscossione;
    }

    public void setDeEDatiPagDatiSingPagDatiSpecificiRiscossione(String deEDatiPagDatiSingPagDatiSpecificiRiscossione) {
        this.deEDatiPagDatiSingPagDatiSpecificiRiscossione = deEDatiPagDatiSingPagDatiSpecificiRiscossione;
    }

    public String getDeEDatiPagDatiSingPagEsitoSingoloPagamento() {
        return deEDatiPagDatiSingPagEsitoSingoloPagamento;
    }

    public void setDeEDatiPagDatiSingPagEsitoSingoloPagamento(String deEDatiPagDatiSingPagEsitoSingoloPagamento) {
        this.deEDatiPagDatiSingPagEsitoSingoloPagamento = deEDatiPagDatiSingPagEsitoSingoloPagamento;
    }

    public String getDeEEnteBenefCivicoBeneficiario() {
        return deEEnteBenefCivicoBeneficiario;
    }

    public void setDeEEnteBenefCivicoBeneficiario(String deEEnteBenefCivicoBeneficiario) {
        this.deEEnteBenefCivicoBeneficiario = deEEnteBenefCivicoBeneficiario;
    }

    public String getDeEEnteBenefDenomUnitOperBeneficiario() {
        return deEEnteBenefDenomUnitOperBeneficiario;
    }

    public void setDeEEnteBenefDenomUnitOperBeneficiario(String deEEnteBenefDenomUnitOperBeneficiario) {
        this.deEEnteBenefDenomUnitOperBeneficiario = deEEnteBenefDenomUnitOperBeneficiario;
    }

    public String getDeEEnteBenefDenominazioneBeneficiario() {
        return deEEnteBenefDenominazioneBeneficiario;
    }

    public void setDeEEnteBenefDenominazioneBeneficiario(String deEEnteBenefDenominazioneBeneficiario) {
        this.deEEnteBenefDenominazioneBeneficiario = deEEnteBenefDenominazioneBeneficiario;
    }

    public String getDeEEnteBenefIndirizzoBeneficiario() {
        return deEEnteBenefIndirizzoBeneficiario;
    }

    public void setDeEEnteBenefIndirizzoBeneficiario(String deEEnteBenefIndirizzoBeneficiario) {
        this.deEEnteBenefIndirizzoBeneficiario = deEEnteBenefIndirizzoBeneficiario;
    }

    public String getDeEEnteBenefLocalitaBeneficiario() {
        return deEEnteBenefLocalitaBeneficiario;
    }

    public void setDeEEnteBenefLocalitaBeneficiario(String deEEnteBenefLocalitaBeneficiario) {
        this.deEEnteBenefLocalitaBeneficiario = deEEnteBenefLocalitaBeneficiario;
    }

    public String getDeEEnteBenefProvinciaBeneficiario() {
        return deEEnteBenefProvinciaBeneficiario;
    }

    public void setDeEEnteBenefProvinciaBeneficiario(String deEEnteBenefProvinciaBeneficiario) {
        this.deEEnteBenefProvinciaBeneficiario = deEEnteBenefProvinciaBeneficiario;
    }

    public String getDeEIstitAttCivicoAttestante() {
        return deEIstitAttCivicoAttestante;
    }

    public void setDeEIstitAttCivicoAttestante(String deEIstitAttCivicoAttestante) {
        this.deEIstitAttCivicoAttestante = deEIstitAttCivicoAttestante;
    }

    public String getDeEIstitAttDenomUnitOperAttestante() {
        return deEIstitAttDenomUnitOperAttestante;
    }

    public void setDeEIstitAttDenomUnitOperAttestante(String deEIstitAttDenomUnitOperAttestante) {
        this.deEIstitAttDenomUnitOperAttestante = deEIstitAttDenomUnitOperAttestante;
    }

    public String getDeEIstitAttDenominazioneAttestante() {
        return deEIstitAttDenominazioneAttestante;
    }

    public void setDeEIstitAttDenominazioneAttestante(String deEIstitAttDenominazioneAttestante) {
        this.deEIstitAttDenominazioneAttestante = deEIstitAttDenominazioneAttestante;
    }

    public String getDeEIstitAttIndirizzoAttestante() {
        return deEIstitAttIndirizzoAttestante;
    }

    public void setDeEIstitAttIndirizzoAttestante(String deEIstitAttIndirizzoAttestante) {
        this.deEIstitAttIndirizzoAttestante = deEIstitAttIndirizzoAttestante;
    }

    public String getDeEIstitAttLocalitaAttestante() {
        return deEIstitAttLocalitaAttestante;
    }

    public void setDeEIstitAttLocalitaAttestante(String deEIstitAttLocalitaAttestante) {
        this.deEIstitAttLocalitaAttestante = deEIstitAttLocalitaAttestante;
    }

    public String getDeEIstitAttProvinciaAttestante() {
        return deEIstitAttProvinciaAttestante;
    }

    public void setDeEIstitAttProvinciaAttestante(String deEIstitAttProvinciaAttestante) {
        this.deEIstitAttProvinciaAttestante = deEIstitAttProvinciaAttestante;
    }

    public String getDeESoggPagCivicoPagatore() {
        return deESoggPagCivicoPagatore;
    }

    public void setDeESoggPagCivicoPagatore(String deESoggPagCivicoPagatore) {
        this.deESoggPagCivicoPagatore = deESoggPagCivicoPagatore;
    }

    public String getDeESoggPagEmailPagatore() {
        return deESoggPagEmailPagatore;
    }

    public void setDeESoggPagEmailPagatore(String deESoggPagEmailPagatore) {
        this.deESoggPagEmailPagatore = deESoggPagEmailPagatore;
    }

    public String getDeESoggPagIndirizzoPagatore() {
        return deESoggPagIndirizzoPagatore;
    }

    public void setDeESoggPagIndirizzoPagatore(String deESoggPagIndirizzoPagatore) {
        this.deESoggPagIndirizzoPagatore = deESoggPagIndirizzoPagatore;
    }

    public String getDeESoggPagLocalitaPagatore() {
        return deESoggPagLocalitaPagatore;
    }

    public void setDeESoggPagLocalitaPagatore(String deESoggPagLocalitaPagatore) {
        this.deESoggPagLocalitaPagatore = deESoggPagLocalitaPagatore;
    }

    public String getDeESoggPagProvinciaPagatore() {
        return deESoggPagProvinciaPagatore;
    }

    public void setDeESoggPagProvinciaPagatore(String deESoggPagProvinciaPagatore) {
        this.deESoggPagProvinciaPagatore = deESoggPagProvinciaPagatore;
    }

    public String getDeESoggVersCivicoVersante() {
        return deESoggVersCivicoVersante;
    }

    public void setDeESoggVersCivicoVersante(String deESoggVersCivicoVersante) {
        this.deESoggVersCivicoVersante = deESoggVersCivicoVersante;
    }

    public String getDeESoggVersEmailVersante() {
        return deESoggVersEmailVersante;
    }

    public void setDeESoggVersEmailVersante(String deESoggVersEmailVersante) {
        this.deESoggVersEmailVersante = deESoggVersEmailVersante;
    }

    public String getDeESoggVersIndirizzoVersante() {
        return deESoggVersIndirizzoVersante;
    }

    public void setDeESoggVersIndirizzoVersante(String deESoggVersIndirizzoVersante) {
        this.deESoggVersIndirizzoVersante = deESoggVersIndirizzoVersante;
    }

    public String getDeESoggVersLocalitaVersante() {
        return deESoggVersLocalitaVersante;
    }

    public void setDeESoggVersLocalitaVersante(String deESoggVersLocalitaVersante) {
        this.deESoggVersLocalitaVersante = deESoggVersLocalitaVersante;
    }

    public String getDeESoggVersProvinciaVersante() {
        return deESoggVersProvinciaVersante;
    }

    public void setDeESoggVersProvinciaVersante(String deESoggVersProvinciaVersante) {
        this.deESoggVersProvinciaVersante = deESoggVersProvinciaVersante;
    }

    public String getDeEVersioneOggetto() {
        return deEVersioneOggetto;
    }

    public void setDeEVersioneOggetto(String deEVersioneOggetto) {
        this.deEVersioneOggetto = deEVersioneOggetto;
    }

    public String getDeImportaDovutoEsito() {
        return deImportaDovutoEsito;
    }

    public void setDeImportaDovutoEsito(String deImportaDovutoEsito) {
        this.deImportaDovutoEsito = deImportaDovutoEsito;
    }

    public String getDeImportaDovutoFaultCode() {
        return deImportaDovutoFaultCode;
    }

    public void setDeImportaDovutoFaultCode(String deImportaDovutoFaultCode) {
        this.deImportaDovutoFaultCode = deImportaDovutoFaultCode;
    }

    public String getDeImportaDovutoFaultDescription() {
        return deImportaDovutoFaultDescription;
    }

    public void setDeImportaDovutoFaultDescription(String deImportaDovutoFaultDescription) {
        this.deImportaDovutoFaultDescription = deImportaDovutoFaultDescription;
    }

    public String getDeImportaDovutoFaultId() {
        return deImportaDovutoFaultId;
    }

    public void setDeImportaDovutoFaultId(String deImportaDovutoFaultId) {
        this.deImportaDovutoFaultId = deImportaDovutoFaultId;
    }

    public String getDeImportaDovutoFaultString() {
        return deImportaDovutoFaultString;
    }

    public void setDeImportaDovutoFaultString(String deImportaDovutoFaultString) {
        this.deImportaDovutoFaultString = deImportaDovutoFaultString;
    }

    public String getDeNomeFlusso() {
        return deNomeFlusso;
    }

    public void setDeNomeFlusso(String deNomeFlusso) {
        this.deNomeFlusso = deNomeFlusso;
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

    public Date getDtEDataOraMessaggioRicevuta() {
        return dtEDataOraMessaggioRicevuta;
    }

    public void setDtEDataOraMessaggioRicevuta(Date dtEDataOraMessaggioRicevuta) {
        this.dtEDataOraMessaggioRicevuta = dtEDataOraMessaggioRicevuta;
    }

    public Date getDtEDatiPagDatiSingPagDataEsitoSingoloPagamento() {
        return dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
    }

    public void setDtEDatiPagDatiSingPagDataEsitoSingoloPagamento(Date dtEDatiPagDatiSingPagDataEsitoSingoloPagamento) {
        this.dtEDatiPagDatiSingPagDataEsitoSingoloPagamento = dtEDatiPagDatiSingPagDataEsitoSingoloPagamento;
    }

    public Date getDtERiferimentoDataRichiesta() {
        return dtERiferimentoDataRichiesta;
    }

    public void setDtERiferimentoDataRichiesta(Date dtERiferimentoDataRichiesta) {
        this.dtERiferimentoDataRichiesta = dtERiferimentoDataRichiesta;
    }

    public Date getDtUltimaModifica() {
        return dtUltimaModifica;
    }

    public void setDtUltimaModifica(Date dtUltimaModifica) {
        this.dtUltimaModifica = dtUltimaModifica;
    }

    public BigDecimal getNumEDatiPagDatiSingPagSingoloImportoPagato() {
        return numEDatiPagDatiSingPagSingoloImportoPagato;
    }

    public void setNumEDatiPagDatiSingPagSingoloImportoPagato(BigDecimal numEDatiPagDatiSingPagSingoloImportoPagato) {
        this.numEDatiPagDatiSingPagSingoloImportoPagato = numEDatiPagDatiSingPagSingoloImportoPagato;
    }

    public BigDecimal getNumEDatiPagImportoTotalePagato() {
        return numEDatiPagImportoTotalePagato;
    }

    public void setNumEDatiPagImportoTotalePagato(BigDecimal numEDatiPagImportoTotalePagato) {
        this.numEDatiPagImportoTotalePagato = numEDatiPagImportoTotalePagato;
    }

    public Integer getNumImportaDovutoFaultSerial() {
        return numImportaDovutoFaultSerial;
    }

    public void setNumImportaDovutoFaultSerial(Integer numImportaDovutoFaultSerial) {
        this.numImportaDovutoFaultSerial = numImportaDovutoFaultSerial;
    }

    public Integer getNumRigaFlusso() {
        return numRigaFlusso;
    }

    public void setNumRigaFlusso(Integer numRigaFlusso) {
        this.numRigaFlusso = numRigaFlusso;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public FlussoExportTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "FlussoExportTO["
            + id
            + ", "
            + ente
            + ", "
            + manageFlusso
            + ", "
            + bilancio
            + ", "
            + codEDatiPagCodiceContestoPagamento
            + ", "
            + codEDatiPagCodiceEsitoPagamento
            + ", "
            + codEDatiPagIdUnivocoVersamento
            + ", "
            + codEDomIdDominio
            + ", "
            + codEDomIdStazioneRichiedente
            + ", "
            + codEEnteBenefCapBeneficiario
            + ", "
            + codEEnteBenefCodiceUnitOperBeneficiario
            + ", "
            + codEEnteBenefIdUnivBenefCodiceIdUnivoco
            + ", "
            + codEEnteBenefIdUnivBenefTipoIdUnivoco
            + ", "
            + codEEnteBenefNazioneBeneficiario
            + ", "
            + codEIdMessaggioRicevuta
            + ", "
            + codEIstitAttCapAttestante
            + ", "
            + codEIstitAttCodiceUnitOperAttestante
            + ", "
            + codEIstitAttIdUnivAttCodiceIdUnivoco
            + ", "
            + codEIstitAttIdUnivAttTipoIdUnivoco
            + ", "
            + codEIstitAttNazioneAttestante
            + ", "
            + codERiferimentoMessaggioRichiesta
            + ", "
            + codESoggPagAnagraficaPagatore
            + ", "
            + codESoggPagCapPagatore
            + ", "
            + codESoggPagIdUnivPagCodiceIdUnivoco
            + ", "
            + codESoggPagIdUnivPagTipoIdUnivoco
            + ", "
            + codESoggPagNazionePagatore
            + ", "
            + codESoggVersAnagraficaVersante
            + ", "
            + codESoggVersCapVersante
            + ", "
            + codESoggVersIdUnivVersCodiceIdUnivoco
            + ", "
            + codESoggVersIdUnivVersTipoIdUnivoco
            + ", "
            + codESoggVersNazioneVersante
            + ", "
            + codIud
            + ", "
            + codTipoDovuto
            + ", "
            + deEDatiPagDatiSingPagCausaleVersamento
            + ", "
            + deEDatiPagDatiSingPagDatiSpecificiRiscossione
            + ", "
            + deEDatiPagDatiSingPagEsitoSingoloPagamento
            + ", "
            + deEEnteBenefCivicoBeneficiario
            + ", "
            + deEEnteBenefDenomUnitOperBeneficiario
            + ", "
            + deEEnteBenefDenominazioneBeneficiario
            + ", "
            + deEEnteBenefIndirizzoBeneficiario
            + ", "
            + deEEnteBenefLocalitaBeneficiario
            + ", "
            + deEEnteBenefProvinciaBeneficiario
            + ", "
            + deEIstitAttCivicoAttestante
            + ", "
            + deEIstitAttDenomUnitOperAttestante
            + ", "
            + deEIstitAttDenominazioneAttestante
            + ", "
            + deEIstitAttIndirizzoAttestante
            + ", "
            + deEIstitAttLocalitaAttestante
            + ", "
            + deEIstitAttProvinciaAttestante
            + ", "
            + deESoggPagCivicoPagatore
            + ", "
            + deESoggPagEmailPagatore
            + ", "
            + deESoggPagIndirizzoPagatore
            + ", "
            + deESoggPagLocalitaPagatore
            + ", "
            + deESoggPagProvinciaPagatore
            + ", "
            + deESoggVersCivicoVersante
            + ", "
            + deESoggVersEmailVersante
            + ", "
            + deESoggVersIndirizzoVersante
            + ", "
            + deESoggVersLocalitaVersante
            + ", "
            + deESoggVersProvinciaVersante
            + ", "
            + deEVersioneOggetto
            + ", "
            + deImportaDovutoEsito
            + ", "
            + deImportaDovutoFaultCode
            + ", "
            + deImportaDovutoFaultDescription
            + ", "
            + deImportaDovutoFaultId
            + ", "
            + deImportaDovutoFaultString
            + ", "
            + deNomeFlusso
            + ", "
            + dtAcquisizione
            + ", "
            + dtCreazione
            + ", "
            + dtEDataOraMessaggioRicevuta
            + ", "
            + dtEDatiPagDatiSingPagDataEsitoSingoloPagamento
            + ", "
            + dtERiferimentoDataRichiesta
            + ", "
            + dtUltimaModifica
            + ", "
            + numEDatiPagDatiSingPagSingoloImportoPagato
            + ", "
            + numEDatiPagImportoTotalePagato
            + ", "
            + numImportaDovutoFaultSerial
            + ", "
            + numRigaFlusso
            + ", "
            + version
            + "]";
    }

}
