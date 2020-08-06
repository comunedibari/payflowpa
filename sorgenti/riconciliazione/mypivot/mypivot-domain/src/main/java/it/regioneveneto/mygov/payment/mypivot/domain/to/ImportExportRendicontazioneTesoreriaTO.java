package it.regioneveneto.mygov.payment.mypivot.domain.to;

import java.math.BigDecimal;
import java.util.Date;

@javax.annotation.Generated("dtonator")
public class ImportExportRendicontazioneTesoreriaTO  implements java.io.Serializable {

    public ImportExportRendicontazioneTesoreriaIdTO id;
    public String anagraficaPagatore;
    public String anagraficaVersante;
    public String bilancio;
    public String bilancioE;
    public String capAttestante;
    public String capBeneficiario;
    public String capPagatore;
    public String capVersante;
    public String causaleVersamento;
    public String civicoAttestante;
    public String civicoBeneficiario;
    public String civicoPagatore;
    public String civicoVersante;
    public String codBolletta;
    public String codConto;
    public String codDocumento;
    public String codIdDominio;
    public String codIudKey;
    public String codIufKey;
    public String codIuvKey;
    public String codOr1;
    public String codProvvisorio;
    public String codiceContestoPagamento;
    public String codiceEsitoPagamento;
    public String codiceIdentificativoUnivocoAttestante;
    public String codiceIdentificativoUnivocoBeneficiario;
    public String codiceIdentificativoUnivocoPagatore;
    public String codiceIdentificativoUnivocoVersante;
    public String codiceIud;
    public String codiceUnitOperAttestante;
    public String codiceUnitOperBeneficiario;
    public String dataAcquisizione;
    public String dataOraFlussoRendicontazione;
    public String dataOraMessaggioRicevuta;
    public String datiSpecificiRiscossione;
    public String deAnnoBolletta;
    public String deAnnoDocumento;
    public String deAnnoProvvisorio;
    public String deCausaleT;
    public String deDataContabile;
    public String deDataEsecuzionePagamento;
    public String deDataEsitoSingoloPagamento;
    public String deDataRegolamento;
    public String deDataRicezione;
    public String deDataUltimoAggiornamento;
    public String deDataValuta;
    public String deImporto;
    public String denomUnitOperAttestante;
    public String denomUnitOperBeneficiario;
    public String denominazioneAttestante;
    public String denominazioneBeneficiario;
    public Date dtDataContabile;
    public Date dtDataEsecuzionePagamento;
    public Date dtDataEsitoSingoloPagamento;
    public Date dtDataRegolamento;
    public Date dtDataUltimoAggiornamento;
    public Date dtDataValuta;
    public Date dtRicezione;
    public String emailPagatore;
    public String emailVersante;
    public String esitoSingoloPagamento;
    public String identificativoDominio;
    public String identificativoMessaggioRicevuta;
    public String identificativoStazioneRichiedente;
    public String identificativoUnivocoRegolamento;
    public String identificativoUnivocoVersamento;
    public String importoTotalePagamenti;
    public String importoTotalePagato;
    public Integer indiceDatiSingoloPagamento;
    public String indirizzoAttestante;
    public String indirizzoBeneficiario;
    public String indirizzoPagatore;
    public String indirizzoVersante;
    public String localitaAttestante;
    public String localitaBeneficiario;
    public String localitaPagatore;
    public String localitaVersante;
    public String nazioneAttestante;
    public String nazioneBeneficiario;
    public String nazionePagatore;
    public String nazioneVersante;
    public String nomeFlussoImportEnte;
    public BigDecimal numImporto;
    public String numeroTotalePagamenti;
    public String provinciaAttestante;
    public String provinciaBeneficiario;
    public String provinciaPagatore;
    public String provinciaVersante;
    public String riferimentoDataRichiesta;
    public String riferimentoMessaggioRichiesta;
    public String rigaFlussoImportEnte;
    public String singoloImportoCommissioneCaricoPa;
    public String singoloImportoPagato;
    public String tipoDovuto;
    public String tipoIdentificativoUnivocoAttestante;
    public String tipoIdentificativoUnivocoBeneficiario;
    public String tipoIdentificativoUnivocoPagatore;
    public String tipoIdentificativoUnivocoVersante;
    public String verificaTotale;
    public String versioneOggetto;
	private Date dtEffettivaSospeso;
	private String codiceGestionaleProvvisorio;

    protected ImportExportRendicontazioneTesoreriaTO() {
    }

    public ImportExportRendicontazioneTesoreriaTO(ImportExportRendicontazioneTesoreriaIdTO id, String anagraficaPagatore, String anagraficaVersante, String bilancio, String bilancioE, String capAttestante, String capBeneficiario, String capPagatore, String capVersante, String causaleVersamento, String civicoAttestante, String civicoBeneficiario, String civicoPagatore, String civicoVersante, String codBolletta, String codConto, String codDocumento, String codIdDominio, String codIudKey, String codIufKey, String codIuvKey, String codOr1, String codProvvisorio, String codiceContestoPagamento, String codiceEsitoPagamento, String codiceIdentificativoUnivocoAttestante, String codiceIdentificativoUnivocoBeneficiario, String codiceIdentificativoUnivocoPagatore, String codiceIdentificativoUnivocoVersante, String codiceIud, String codiceUnitOperAttestante, String codiceUnitOperBeneficiario, String dataAcquisizione, String dataOraFlussoRendicontazione, String dataOraMessaggioRicevuta, String datiSpecificiRiscossione, String deAnnoBolletta, String deAnnoDocumento, String deAnnoProvvisorio, String deCausaleT, String deDataContabile, String deDataEsecuzionePagamento, String deDataEsitoSingoloPagamento, String deDataRegolamento, String deDataRicezione, String deDataUltimoAggiornamento, String deDataValuta, String deImporto, String denomUnitOperAttestante, String denomUnitOperBeneficiario, String denominazioneAttestante, String denominazioneBeneficiario, Date dtDataContabile, Date dtDataEsecuzionePagamento, Date dtDataEsitoSingoloPagamento, Date dtDataRegolamento, Date dtDataUltimoAggiornamento, Date dtDataValuta, Date dtRicezione, String emailPagatore, String emailVersante, String esitoSingoloPagamento, String identificativoDominio, String identificativoMessaggioRicevuta, String identificativoStazioneRichiedente, String identificativoUnivocoRegolamento, String identificativoUnivocoVersamento, String importoTotalePagamenti, String importoTotalePagato, Integer indiceDatiSingoloPagamento, String indirizzoAttestante, String indirizzoBeneficiario, String indirizzoPagatore, String indirizzoVersante, String localitaAttestante, String localitaBeneficiario, String localitaPagatore, String localitaVersante, String nazioneAttestante, String nazioneBeneficiario, String nazionePagatore, String nazioneVersante, String nomeFlussoImportEnte, BigDecimal numImporto, String numeroTotalePagamenti, String provinciaAttestante, String provinciaBeneficiario, String provinciaPagatore, String provinciaVersante, String riferimentoDataRichiesta, String riferimentoMessaggioRichiesta, String rigaFlussoImportEnte, String singoloImportoCommissioneCaricoPa, String singoloImportoPagato, String tipoDovuto, String tipoIdentificativoUnivocoAttestante, String tipoIdentificativoUnivocoBeneficiario, String tipoIdentificativoUnivocoPagatore, String tipoIdentificativoUnivocoVersante, String verificaTotale, String versioneOggetto,
			Date dtEffettivaSospeso, String codiceGestionaleProvvisorio) {
        super();
        this.id = id;
        this.anagraficaPagatore = anagraficaPagatore;
        this.anagraficaVersante = anagraficaVersante;
        this.bilancio = bilancio;
        this.bilancioE = bilancioE;
        this.capAttestante = capAttestante;
        this.capBeneficiario = capBeneficiario;
        this.capPagatore = capPagatore;
        this.capVersante = capVersante;
        this.causaleVersamento = causaleVersamento;
        this.civicoAttestante = civicoAttestante;
        this.civicoBeneficiario = civicoBeneficiario;
        this.civicoPagatore = civicoPagatore;
        this.civicoVersante = civicoVersante;
        this.codBolletta = codBolletta;
        this.codConto = codConto;
        this.codDocumento = codDocumento;
        this.codIdDominio = codIdDominio;
        this.codIudKey = codIudKey;
        this.codIufKey = codIufKey;
        this.codIuvKey = codIuvKey;
        this.codOr1 = codOr1;
        this.codProvvisorio = codProvvisorio;
        this.codiceContestoPagamento = codiceContestoPagamento;
        this.codiceEsitoPagamento = codiceEsitoPagamento;
        this.codiceIdentificativoUnivocoAttestante = codiceIdentificativoUnivocoAttestante;
        this.codiceIdentificativoUnivocoBeneficiario = codiceIdentificativoUnivocoBeneficiario;
        this.codiceIdentificativoUnivocoPagatore = codiceIdentificativoUnivocoPagatore;
        this.codiceIdentificativoUnivocoVersante = codiceIdentificativoUnivocoVersante;
        this.codiceIud = codiceIud;
        this.codiceUnitOperAttestante = codiceUnitOperAttestante;
        this.codiceUnitOperBeneficiario = codiceUnitOperBeneficiario;
        this.dataAcquisizione = dataAcquisizione;
        this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
        this.dataOraMessaggioRicevuta = dataOraMessaggioRicevuta;
        this.datiSpecificiRiscossione = datiSpecificiRiscossione;
        this.deAnnoBolletta = deAnnoBolletta;
        this.deAnnoDocumento = deAnnoDocumento;
        this.deAnnoProvvisorio = deAnnoProvvisorio;
        this.deCausaleT = deCausaleT;
        this.deDataContabile = deDataContabile;
        this.deDataEsecuzionePagamento = deDataEsecuzionePagamento;
        this.deDataEsitoSingoloPagamento = deDataEsitoSingoloPagamento;
        this.deDataRegolamento = deDataRegolamento;
        this.deDataRicezione = deDataRicezione;
        this.deDataUltimoAggiornamento = deDataUltimoAggiornamento;
        this.deDataValuta = deDataValuta;
        this.deImporto = deImporto;
        this.denomUnitOperAttestante = denomUnitOperAttestante;
        this.denomUnitOperBeneficiario = denomUnitOperBeneficiario;
        this.denominazioneAttestante = denominazioneAttestante;
        this.denominazioneBeneficiario = denominazioneBeneficiario;
        this.dtDataContabile = dtDataContabile;
        this.dtDataEsecuzionePagamento = dtDataEsecuzionePagamento;
        this.dtDataEsitoSingoloPagamento = dtDataEsitoSingoloPagamento;
        this.dtDataRegolamento = dtDataRegolamento;
        this.dtDataUltimoAggiornamento = dtDataUltimoAggiornamento;
        this.dtDataValuta = dtDataValuta;
        this.dtRicezione = dtRicezione;
        this.emailPagatore = emailPagatore;
        this.emailVersante = emailVersante;
        this.esitoSingoloPagamento = esitoSingoloPagamento;
        this.identificativoDominio = identificativoDominio;
        this.identificativoMessaggioRicevuta = identificativoMessaggioRicevuta;
        this.identificativoStazioneRichiedente = identificativoStazioneRichiedente;
        this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
        this.importoTotalePagamenti = importoTotalePagamenti;
        this.importoTotalePagato = importoTotalePagato;
        this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
        this.indirizzoAttestante = indirizzoAttestante;
        this.indirizzoBeneficiario = indirizzoBeneficiario;
        this.indirizzoPagatore = indirizzoPagatore;
        this.indirizzoVersante = indirizzoVersante;
        this.localitaAttestante = localitaAttestante;
        this.localitaBeneficiario = localitaBeneficiario;
        this.localitaPagatore = localitaPagatore;
        this.localitaVersante = localitaVersante;
        this.nazioneAttestante = nazioneAttestante;
        this.nazioneBeneficiario = nazioneBeneficiario;
        this.nazionePagatore = nazionePagatore;
        this.nazioneVersante = nazioneVersante;
        this.nomeFlussoImportEnte = nomeFlussoImportEnte;
        this.numImporto = numImporto;
        this.numeroTotalePagamenti = numeroTotalePagamenti;
        this.provinciaAttestante = provinciaAttestante;
        this.provinciaBeneficiario = provinciaBeneficiario;
        this.provinciaPagatore = provinciaPagatore;
        this.provinciaVersante = provinciaVersante;
        this.riferimentoDataRichiesta = riferimentoDataRichiesta;
        this.riferimentoMessaggioRichiesta = riferimentoMessaggioRichiesta;
        this.rigaFlussoImportEnte = rigaFlussoImportEnte;
        this.singoloImportoCommissioneCaricoPa = singoloImportoCommissioneCaricoPa;
        this.singoloImportoPagato = singoloImportoPagato;
        this.tipoDovuto = tipoDovuto;
        this.tipoIdentificativoUnivocoAttestante = tipoIdentificativoUnivocoAttestante;
        this.tipoIdentificativoUnivocoBeneficiario = tipoIdentificativoUnivocoBeneficiario;
        this.tipoIdentificativoUnivocoPagatore = tipoIdentificativoUnivocoPagatore;
        this.tipoIdentificativoUnivocoVersante = tipoIdentificativoUnivocoVersante;
        this.verificaTotale = verificaTotale;
        this.versioneOggetto = versioneOggetto;
		this.dtEffettivaSospeso = dtEffettivaSospeso;
		this.codiceGestionaleProvvisorio = codiceGestionaleProvvisorio;
    }

    public static ImportExportRendicontazioneTesoreriaTO copyOf(ImportExportRendicontazioneTesoreriaTO o) {
            return new it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO(
                it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).id),
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).anagraficaPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).anagraficaVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).bilancio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).bilancioE,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).causaleVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codConto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codIdDominio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codIudKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codIufKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codIuvKey,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codOr1,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceContestoPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceEsitoPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceIdentificativoUnivocoAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceIdentificativoUnivocoBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceIdentificativoUnivocoPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceIdentificativoUnivocoVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceIud,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceUnitOperAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceUnitOperBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dataAcquisizione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dataOraFlussoRendicontazione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dataOraMessaggioRicevuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).datiSpecificiRiscossione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deAnnoBolletta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deAnnoDocumento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deAnnoProvvisorio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deCausaleT,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataContabile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataEsecuzionePagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataEsitoSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataValuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deImporto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).denomUnitOperAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).denomUnitOperBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).denominazioneAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).denominazioneBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtDataContabile,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtDataEsecuzionePagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtDataEsitoSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtDataRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtDataUltimoAggiornamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtDataValuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtRicezione,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).emailPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).emailVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).esitoSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).identificativoDominio,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).identificativoMessaggioRicevuta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).identificativoStazioneRichiedente,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).identificativoUnivocoRegolamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).identificativoUnivocoVersamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).importoTotalePagamenti,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).importoTotalePagato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).indiceDatiSingoloPagamento,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).indirizzoAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).indirizzoBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).indirizzoPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).indirizzoVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).localitaAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).localitaBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).localitaPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).localitaVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).nazioneAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).nazioneBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).nazionePagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).nazioneVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).nomeFlussoImportEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).numImporto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).numeroTotalePagamenti,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).provinciaAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).provinciaBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).provinciaPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).provinciaVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).riferimentoDataRichiesta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).riferimentoMessaggioRichiesta,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).rigaFlussoImportEnte,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).singoloImportoCommissioneCaricoPa,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).singoloImportoPagato,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).tipoDovuto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).tipoIdentificativoUnivocoAttestante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).tipoIdentificativoUnivocoBeneficiario,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).tipoIdentificativoUnivocoPagatore,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).tipoIdentificativoUnivocoVersante,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).verificaTotale,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).versioneOggetto,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).dtEffettivaSospeso,
                ((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codiceGestionaleProvvisorio
                );
    }

    public ImportExportRendicontazioneTesoreriaIdTO getId() {
        return id;
    }

    public void setId(ImportExportRendicontazioneTesoreriaIdTO id) {
        this.id = id;
    }

    public String getAnagraficaPagatore() {
        return anagraficaPagatore;
    }

    public void setAnagraficaPagatore(String anagraficaPagatore) {
        this.anagraficaPagatore = anagraficaPagatore;
    }

    public String getAnagraficaVersante() {
        return anagraficaVersante;
    }

    public void setAnagraficaVersante(String anagraficaVersante) {
        this.anagraficaVersante = anagraficaVersante;
    }

    public String getBilancio() {
        return bilancio;
    }

    public void setBilancio(String bilancio) {
        this.bilancio = bilancio;
    }

    public String getBilancioE() {
        return bilancioE;
    }

    public void setBilancioE(String bilancioE) {
        this.bilancioE = bilancioE;
    }

    public String getCapAttestante() {
        return capAttestante;
    }

    public void setCapAttestante(String capAttestante) {
        this.capAttestante = capAttestante;
    }

    public String getCapBeneficiario() {
        return capBeneficiario;
    }

    public void setCapBeneficiario(String capBeneficiario) {
        this.capBeneficiario = capBeneficiario;
    }

    public String getCapPagatore() {
        return capPagatore;
    }

    public void setCapPagatore(String capPagatore) {
        this.capPagatore = capPagatore;
    }

    public String getCapVersante() {
        return capVersante;
    }

    public void setCapVersante(String capVersante) {
        this.capVersante = capVersante;
    }

    public String getCausaleVersamento() {
        return causaleVersamento;
    }

    public void setCausaleVersamento(String causaleVersamento) {
        this.causaleVersamento = causaleVersamento;
    }

    public String getCivicoAttestante() {
        return civicoAttestante;
    }

    public void setCivicoAttestante(String civicoAttestante) {
        this.civicoAttestante = civicoAttestante;
    }

    public String getCivicoBeneficiario() {
        return civicoBeneficiario;
    }

    public void setCivicoBeneficiario(String civicoBeneficiario) {
        this.civicoBeneficiario = civicoBeneficiario;
    }

    public String getCivicoPagatore() {
        return civicoPagatore;
    }

    public void setCivicoPagatore(String civicoPagatore) {
        this.civicoPagatore = civicoPagatore;
    }

    public String getCivicoVersante() {
        return civicoVersante;
    }

    public void setCivicoVersante(String civicoVersante) {
        this.civicoVersante = civicoVersante;
    }

    public String getCodBolletta() {
        return codBolletta;
    }

    public void setCodBolletta(String codBolletta) {
        this.codBolletta = codBolletta;
    }

    public String getCodConto() {
        return codConto;
    }

    public void setCodConto(String codConto) {
        this.codConto = codConto;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getCodIdDominio() {
        return codIdDominio;
    }

    public void setCodIdDominio(String codIdDominio) {
        this.codIdDominio = codIdDominio;
    }

    public String getCodIudKey() {
        return codIudKey;
    }

    public void setCodIudKey(String codIudKey) {
        this.codIudKey = codIudKey;
    }

    public String getCodIufKey() {
        return codIufKey;
    }

    public void setCodIufKey(String codIufKey) {
        this.codIufKey = codIufKey;
    }

    public String getCodIuvKey() {
        return codIuvKey;
    }

    public void setCodIuvKey(String codIuvKey) {
        this.codIuvKey = codIuvKey;
    }

    public String getCodOr1() {
        return codOr1;
    }

    public void setCodOr1(String codOr1) {
        this.codOr1 = codOr1;
    }

    public String getCodProvvisorio() {
        return codProvvisorio;
    }

    public void setCodProvvisorio(String codProvvisorio) {
        this.codProvvisorio = codProvvisorio;
    }

    public String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }

    public void setCodiceContestoPagamento(String codiceContestoPagamento) {
        this.codiceContestoPagamento = codiceContestoPagamento;
    }

    public String getCodiceEsitoPagamento() {
        return codiceEsitoPagamento;
    }

    public void setCodiceEsitoPagamento(String codiceEsitoPagamento) {
        this.codiceEsitoPagamento = codiceEsitoPagamento;
    }

    public String getCodiceIdentificativoUnivocoAttestante() {
        return codiceIdentificativoUnivocoAttestante;
    }

    public void setCodiceIdentificativoUnivocoAttestante(String codiceIdentificativoUnivocoAttestante) {
        this.codiceIdentificativoUnivocoAttestante = codiceIdentificativoUnivocoAttestante;
    }

    public String getCodiceIdentificativoUnivocoBeneficiario() {
        return codiceIdentificativoUnivocoBeneficiario;
    }

    public void setCodiceIdentificativoUnivocoBeneficiario(String codiceIdentificativoUnivocoBeneficiario) {
        this.codiceIdentificativoUnivocoBeneficiario = codiceIdentificativoUnivocoBeneficiario;
    }

    public String getCodiceIdentificativoUnivocoPagatore() {
        return codiceIdentificativoUnivocoPagatore;
    }

    public void setCodiceIdentificativoUnivocoPagatore(String codiceIdentificativoUnivocoPagatore) {
        this.codiceIdentificativoUnivocoPagatore = codiceIdentificativoUnivocoPagatore;
    }

    public String getCodiceIdentificativoUnivocoVersante() {
        return codiceIdentificativoUnivocoVersante;
    }

    public void setCodiceIdentificativoUnivocoVersante(String codiceIdentificativoUnivocoVersante) {
        this.codiceIdentificativoUnivocoVersante = codiceIdentificativoUnivocoVersante;
    }

    public String getCodiceIud() {
        return codiceIud;
    }

    public void setCodiceIud(String codiceIud) {
        this.codiceIud = codiceIud;
    }

    public String getCodiceUnitOperAttestante() {
        return codiceUnitOperAttestante;
    }

    public void setCodiceUnitOperAttestante(String codiceUnitOperAttestante) {
        this.codiceUnitOperAttestante = codiceUnitOperAttestante;
    }

    public String getCodiceUnitOperBeneficiario() {
        return codiceUnitOperBeneficiario;
    }

    public void setCodiceUnitOperBeneficiario(String codiceUnitOperBeneficiario) {
        this.codiceUnitOperBeneficiario = codiceUnitOperBeneficiario;
    }

    public String getDataAcquisizione() {
        return dataAcquisizione;
    }

    public void setDataAcquisizione(String dataAcquisizione) {
        this.dataAcquisizione = dataAcquisizione;
    }

    public String getDataOraFlussoRendicontazione() {
        return dataOraFlussoRendicontazione;
    }

    public void setDataOraFlussoRendicontazione(String dataOraFlussoRendicontazione) {
        this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
    }

    public String getDataOraMessaggioRicevuta() {
        return dataOraMessaggioRicevuta;
    }

    public void setDataOraMessaggioRicevuta(String dataOraMessaggioRicevuta) {
        this.dataOraMessaggioRicevuta = dataOraMessaggioRicevuta;
    }

    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
        this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }

    public String getDeAnnoBolletta() {
        return deAnnoBolletta;
    }

    public void setDeAnnoBolletta(String deAnnoBolletta) {
        this.deAnnoBolletta = deAnnoBolletta;
    }

    public String getDeAnnoDocumento() {
        return deAnnoDocumento;
    }

    public void setDeAnnoDocumento(String deAnnoDocumento) {
        this.deAnnoDocumento = deAnnoDocumento;
    }

    public String getDeAnnoProvvisorio() {
        return deAnnoProvvisorio;
    }

    public void setDeAnnoProvvisorio(String deAnnoProvvisorio) {
        this.deAnnoProvvisorio = deAnnoProvvisorio;
    }

    public String getDeCausaleT() {
        return deCausaleT;
    }

    public void setDeCausaleT(String deCausaleT) {
        this.deCausaleT = deCausaleT;
    }

    public String getDeDataContabile() {
        return deDataContabile;
    }

    public void setDeDataContabile(String deDataContabile) {
        this.deDataContabile = deDataContabile;
    }

    public String getDeDataEsecuzionePagamento() {
        return deDataEsecuzionePagamento;
    }

    public void setDeDataEsecuzionePagamento(String deDataEsecuzionePagamento) {
        this.deDataEsecuzionePagamento = deDataEsecuzionePagamento;
    }

    public String getDeDataEsitoSingoloPagamento() {
        return deDataEsitoSingoloPagamento;
    }

    public void setDeDataEsitoSingoloPagamento(String deDataEsitoSingoloPagamento) {
        this.deDataEsitoSingoloPagamento = deDataEsitoSingoloPagamento;
    }

    public String getDeDataRegolamento() {
        return deDataRegolamento;
    }

    public void setDeDataRegolamento(String deDataRegolamento) {
        this.deDataRegolamento = deDataRegolamento;
    }

    public String getDeDataRicezione() {
        return deDataRicezione;
    }

    public void setDeDataRicezione(String deDataRicezione) {
        this.deDataRicezione = deDataRicezione;
    }

    public String getDeDataUltimoAggiornamento() {
        return deDataUltimoAggiornamento;
    }

    public void setDeDataUltimoAggiornamento(String deDataUltimoAggiornamento) {
        this.deDataUltimoAggiornamento = deDataUltimoAggiornamento;
    }

    public String getDeDataValuta() {
        return deDataValuta;
    }

    public void setDeDataValuta(String deDataValuta) {
        this.deDataValuta = deDataValuta;
    }

    public String getDeImporto() {
        return deImporto;
    }

    public void setDeImporto(String deImporto) {
        this.deImporto = deImporto;
    }

    public String getDenomUnitOperAttestante() {
        return denomUnitOperAttestante;
    }

    public void setDenomUnitOperAttestante(String denomUnitOperAttestante) {
        this.denomUnitOperAttestante = denomUnitOperAttestante;
    }

    public String getDenomUnitOperBeneficiario() {
        return denomUnitOperBeneficiario;
    }

    public void setDenomUnitOperBeneficiario(String denomUnitOperBeneficiario) {
        this.denomUnitOperBeneficiario = denomUnitOperBeneficiario;
    }

    public String getDenominazioneAttestante() {
        return denominazioneAttestante;
    }

    public void setDenominazioneAttestante(String denominazioneAttestante) {
        this.denominazioneAttestante = denominazioneAttestante;
    }

    public String getDenominazioneBeneficiario() {
        return denominazioneBeneficiario;
    }

    public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
        this.denominazioneBeneficiario = denominazioneBeneficiario;
    }

    public Date getDtDataContabile() {
        return dtDataContabile;
    }

    public void setDtDataContabile(Date dtDataContabile) {
        this.dtDataContabile = dtDataContabile;
    }

    public Date getDtDataEsecuzionePagamento() {
        return dtDataEsecuzionePagamento;
    }

    public void setDtDataEsecuzionePagamento(Date dtDataEsecuzionePagamento) {
        this.dtDataEsecuzionePagamento = dtDataEsecuzionePagamento;
    }

    public Date getDtDataEsitoSingoloPagamento() {
        return dtDataEsitoSingoloPagamento;
    }

    public void setDtDataEsitoSingoloPagamento(Date dtDataEsitoSingoloPagamento) {
        this.dtDataEsitoSingoloPagamento = dtDataEsitoSingoloPagamento;
    }

    public Date getDtDataRegolamento() {
        return dtDataRegolamento;
    }

    public void setDtDataRegolamento(Date dtDataRegolamento) {
        this.dtDataRegolamento = dtDataRegolamento;
    }

    public Date getDtDataUltimoAggiornamento() {
        return dtDataUltimoAggiornamento;
    }

    public void setDtDataUltimoAggiornamento(Date dtDataUltimoAggiornamento) {
        this.dtDataUltimoAggiornamento = dtDataUltimoAggiornamento;
    }

    public Date getDtDataValuta() {
        return dtDataValuta;
    }

    public void setDtDataValuta(Date dtDataValuta) {
        this.dtDataValuta = dtDataValuta;
    }

    public Date getDtRicezione() {
        return dtRicezione;
    }

    public void setDtRicezione(Date dtRicezione) {
        this.dtRicezione = dtRicezione;
    }

    public String getEmailPagatore() {
        return emailPagatore;
    }

    public void setEmailPagatore(String emailPagatore) {
        this.emailPagatore = emailPagatore;
    }

    public String getEmailVersante() {
        return emailVersante;
    }

    public void setEmailVersante(String emailVersante) {
        this.emailVersante = emailVersante;
    }

    public String getEsitoSingoloPagamento() {
        return esitoSingoloPagamento;
    }

    public void setEsitoSingoloPagamento(String esitoSingoloPagamento) {
        this.esitoSingoloPagamento = esitoSingoloPagamento;
    }

    public String getIdentificativoDominio() {
        return identificativoDominio;
    }

    public void setIdentificativoDominio(String identificativoDominio) {
        this.identificativoDominio = identificativoDominio;
    }

    public String getIdentificativoMessaggioRicevuta() {
        return identificativoMessaggioRicevuta;
    }

    public void setIdentificativoMessaggioRicevuta(String identificativoMessaggioRicevuta) {
        this.identificativoMessaggioRicevuta = identificativoMessaggioRicevuta;
    }

    public String getIdentificativoStazioneRichiedente() {
        return identificativoStazioneRichiedente;
    }

    public void setIdentificativoStazioneRichiedente(String identificativoStazioneRichiedente) {
        this.identificativoStazioneRichiedente = identificativoStazioneRichiedente;
    }

    public String getIdentificativoUnivocoRegolamento() {
        return identificativoUnivocoRegolamento;
    }

    public void setIdentificativoUnivocoRegolamento(String identificativoUnivocoRegolamento) {
        this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
    }

    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    public String getImportoTotalePagamenti() {
        return importoTotalePagamenti;
    }

    public void setImportoTotalePagamenti(String importoTotalePagamenti) {
        this.importoTotalePagamenti = importoTotalePagamenti;
    }

    public String getImportoTotalePagato() {
        return importoTotalePagato;
    }

    public void setImportoTotalePagato(String importoTotalePagato) {
        this.importoTotalePagato = importoTotalePagato;
    }

    public Integer getIndiceDatiSingoloPagamento() {
        return indiceDatiSingoloPagamento;
    }

    public void setIndiceDatiSingoloPagamento(Integer indiceDatiSingoloPagamento) {
        this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
    }

    public String getIndirizzoAttestante() {
        return indirizzoAttestante;
    }

    public void setIndirizzoAttestante(String indirizzoAttestante) {
        this.indirizzoAttestante = indirizzoAttestante;
    }

    public String getIndirizzoBeneficiario() {
        return indirizzoBeneficiario;
    }

    public void setIndirizzoBeneficiario(String indirizzoBeneficiario) {
        this.indirizzoBeneficiario = indirizzoBeneficiario;
    }

    public String getIndirizzoPagatore() {
        return indirizzoPagatore;
    }

    public void setIndirizzoPagatore(String indirizzoPagatore) {
        this.indirizzoPagatore = indirizzoPagatore;
    }

    public String getIndirizzoVersante() {
        return indirizzoVersante;
    }

    public void setIndirizzoVersante(String indirizzoVersante) {
        this.indirizzoVersante = indirizzoVersante;
    }

    public String getLocalitaAttestante() {
        return localitaAttestante;
    }

    public void setLocalitaAttestante(String localitaAttestante) {
        this.localitaAttestante = localitaAttestante;
    }

    public String getLocalitaBeneficiario() {
        return localitaBeneficiario;
    }

    public void setLocalitaBeneficiario(String localitaBeneficiario) {
        this.localitaBeneficiario = localitaBeneficiario;
    }

    public String getLocalitaPagatore() {
        return localitaPagatore;
    }

    public void setLocalitaPagatore(String localitaPagatore) {
        this.localitaPagatore = localitaPagatore;
    }

    public String getLocalitaVersante() {
        return localitaVersante;
    }

    public void setLocalitaVersante(String localitaVersante) {
        this.localitaVersante = localitaVersante;
    }

    public String getNazioneAttestante() {
        return nazioneAttestante;
    }

    public void setNazioneAttestante(String nazioneAttestante) {
        this.nazioneAttestante = nazioneAttestante;
    }

    public String getNazioneBeneficiario() {
        return nazioneBeneficiario;
    }

    public void setNazioneBeneficiario(String nazioneBeneficiario) {
        this.nazioneBeneficiario = nazioneBeneficiario;
    }

    public String getNazionePagatore() {
        return nazionePagatore;
    }

    public void setNazionePagatore(String nazionePagatore) {
        this.nazionePagatore = nazionePagatore;
    }

    public String getNazioneVersante() {
        return nazioneVersante;
    }

    public void setNazioneVersante(String nazioneVersante) {
        this.nazioneVersante = nazioneVersante;
    }

    public String getNomeFlussoImportEnte() {
        return nomeFlussoImportEnte;
    }

    public void setNomeFlussoImportEnte(String nomeFlussoImportEnte) {
        this.nomeFlussoImportEnte = nomeFlussoImportEnte;
    }

    public BigDecimal getNumImporto() {
        return numImporto;
    }

    public void setNumImporto(BigDecimal numImporto) {
        this.numImporto = numImporto;
    }

    public String getNumeroTotalePagamenti() {
        return numeroTotalePagamenti;
    }

    public void setNumeroTotalePagamenti(String numeroTotalePagamenti) {
        this.numeroTotalePagamenti = numeroTotalePagamenti;
    }

    public String getProvinciaAttestante() {
        return provinciaAttestante;
    }

    public void setProvinciaAttestante(String provinciaAttestante) {
        this.provinciaAttestante = provinciaAttestante;
    }

    public String getProvinciaBeneficiario() {
        return provinciaBeneficiario;
    }

    public void setProvinciaBeneficiario(String provinciaBeneficiario) {
        this.provinciaBeneficiario = provinciaBeneficiario;
    }

    public String getProvinciaPagatore() {
        return provinciaPagatore;
    }

    public void setProvinciaPagatore(String provinciaPagatore) {
        this.provinciaPagatore = provinciaPagatore;
    }

    public String getProvinciaVersante() {
        return provinciaVersante;
    }

    public void setProvinciaVersante(String provinciaVersante) {
        this.provinciaVersante = provinciaVersante;
    }

    public String getRiferimentoDataRichiesta() {
        return riferimentoDataRichiesta;
    }

    public void setRiferimentoDataRichiesta(String riferimentoDataRichiesta) {
        this.riferimentoDataRichiesta = riferimentoDataRichiesta;
    }

    public String getRiferimentoMessaggioRichiesta() {
        return riferimentoMessaggioRichiesta;
    }

    public void setRiferimentoMessaggioRichiesta(String riferimentoMessaggioRichiesta) {
        this.riferimentoMessaggioRichiesta = riferimentoMessaggioRichiesta;
    }

    public String getRigaFlussoImportEnte() {
        return rigaFlussoImportEnte;
    }

    public void setRigaFlussoImportEnte(String rigaFlussoImportEnte) {
        this.rigaFlussoImportEnte = rigaFlussoImportEnte;
    }

    public String getSingoloImportoCommissioneCaricoPa() {
        return singoloImportoCommissioneCaricoPa;
    }

    public void setSingoloImportoCommissioneCaricoPa(String singoloImportoCommissioneCaricoPa) {
        this.singoloImportoCommissioneCaricoPa = singoloImportoCommissioneCaricoPa;
    }

    public String getSingoloImportoPagato() {
        return singoloImportoPagato;
    }

    public void setSingoloImportoPagato(String singoloImportoPagato) {
        this.singoloImportoPagato = singoloImportoPagato;
    }

    public String getTipoDovuto() {
        return tipoDovuto;
    }

    public void setTipoDovuto(String tipoDovuto) {
        this.tipoDovuto = tipoDovuto;
    }

    public String getTipoIdentificativoUnivocoAttestante() {
        return tipoIdentificativoUnivocoAttestante;
    }

    public void setTipoIdentificativoUnivocoAttestante(String tipoIdentificativoUnivocoAttestante) {
        this.tipoIdentificativoUnivocoAttestante = tipoIdentificativoUnivocoAttestante;
    }

    public String getTipoIdentificativoUnivocoBeneficiario() {
        return tipoIdentificativoUnivocoBeneficiario;
    }

    public void setTipoIdentificativoUnivocoBeneficiario(String tipoIdentificativoUnivocoBeneficiario) {
        this.tipoIdentificativoUnivocoBeneficiario = tipoIdentificativoUnivocoBeneficiario;
    }

    public String getTipoIdentificativoUnivocoPagatore() {
        return tipoIdentificativoUnivocoPagatore;
    }

    public void setTipoIdentificativoUnivocoPagatore(String tipoIdentificativoUnivocoPagatore) {
        this.tipoIdentificativoUnivocoPagatore = tipoIdentificativoUnivocoPagatore;
    }

    public String getTipoIdentificativoUnivocoVersante() {
        return tipoIdentificativoUnivocoVersante;
    }

    public void setTipoIdentificativoUnivocoVersante(String tipoIdentificativoUnivocoVersante) {
        this.tipoIdentificativoUnivocoVersante = tipoIdentificativoUnivocoVersante;
    }

    public String getVerificaTotale() {
        return verificaTotale;
    }

    public void setVerificaTotale(String verificaTotale) {
        this.verificaTotale = verificaTotale;
    }

    public String getVersioneOggetto() {
        return versioneOggetto;
    }

    public void setVersioneOggetto(String versioneOggetto) {
        this.versioneOggetto = versioneOggetto;
    }

    public Date getDtEffettivaSospeso() {
		return dtEffettivaSospeso;
	}

	public void setDtEffettivaSospeso(Date dtEffettivaSospeso) {
		this.dtEffettivaSospeso = dtEffettivaSospeso;
	}

	public String getCodiceGestionaleProvvisorio() {
		return codiceGestionaleProvvisorio;
	}

	public void setCodiceGestionaleProvvisorio(String codiceGestionaleProvvisorio) {
		this.codiceGestionaleProvvisorio = codiceGestionaleProvvisorio;
	}

	public ImportExportRendicontazioneTesoreriaTO copy() {
        return it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO.copyOf(this);
    }

    @Override
    public String toString() {
        return "ImportExportRendicontazioneTesoreriaTO["
            + id
            + ", "
            + anagraficaPagatore
            + ", "
            + anagraficaVersante
            + ", "
            + bilancio
            + ", "
            + bilancioE
            + ", "
            + capAttestante
            + ", "
            + capBeneficiario
            + ", "
            + capPagatore
            + ", "
            + capVersante
            + ", "
            + causaleVersamento
            + ", "
            + civicoAttestante
            + ", "
            + civicoBeneficiario
            + ", "
            + civicoPagatore
            + ", "
            + civicoVersante
            + ", "
            + codBolletta
            + ", "
            + codConto
            + ", "
            + codDocumento
            + ", "
            + codIdDominio
            + ", "
            + codIudKey
            + ", "
            + codIufKey
            + ", "
            + codIuvKey
            + ", "
            + codOr1
            + ", "
            + codProvvisorio
            + ", "
            + codiceContestoPagamento
            + ", "
            + codiceEsitoPagamento
            + ", "
            + codiceIdentificativoUnivocoAttestante
            + ", "
            + codiceIdentificativoUnivocoBeneficiario
            + ", "
            + codiceIdentificativoUnivocoPagatore
            + ", "
            + codiceIdentificativoUnivocoVersante
            + ", "
            + codiceIud
            + ", "
            + codiceUnitOperAttestante
            + ", "
            + codiceUnitOperBeneficiario
            + ", "
            + dataAcquisizione
            + ", "
            + dataOraFlussoRendicontazione
            + ", "
            + dataOraMessaggioRicevuta
            + ", "
            + datiSpecificiRiscossione
            + ", "
            + deAnnoBolletta
            + ", "
            + deAnnoDocumento
            + ", "
            + deAnnoProvvisorio
            + ", "
            + deCausaleT
            + ", "
            + deDataContabile
            + ", "
            + deDataEsecuzionePagamento
            + ", "
            + deDataEsitoSingoloPagamento
            + ", "
            + deDataRegolamento
            + ", "
            + deDataRicezione
            + ", "
            + deDataUltimoAggiornamento
            + ", "
            + deDataValuta
            + ", "
            + deImporto
            + ", "
            + denomUnitOperAttestante
            + ", "
            + denomUnitOperBeneficiario
            + ", "
            + denominazioneAttestante
            + ", "
            + denominazioneBeneficiario
            + ", "
            + dtDataContabile
            + ", "
            + dtDataEsecuzionePagamento
            + ", "
            + dtDataEsitoSingoloPagamento
            + ", "
            + dtDataRegolamento
            + ", "
            + dtDataUltimoAggiornamento
            + ", "
            + dtDataValuta
            + ", "
            + dtRicezione
            + ", "
            + emailPagatore
            + ", "
            + emailVersante
            + ", "
            + esitoSingoloPagamento
            + ", "
            + identificativoDominio
            + ", "
            + identificativoMessaggioRicevuta
            + ", "
            + identificativoStazioneRichiedente
            + ", "
            + identificativoUnivocoRegolamento
            + ", "
            + identificativoUnivocoVersamento
            + ", "
            + importoTotalePagamenti
            + ", "
            + importoTotalePagato
            + ", "
            + indiceDatiSingoloPagamento
            + ", "
            + indirizzoAttestante
            + ", "
            + indirizzoBeneficiario
            + ", "
            + indirizzoPagatore
            + ", "
            + indirizzoVersante
            + ", "
            + localitaAttestante
            + ", "
            + localitaBeneficiario
            + ", "
            + localitaPagatore
            + ", "
            + localitaVersante
            + ", "
            + nazioneAttestante
            + ", "
            + nazioneBeneficiario
            + ", "
            + nazionePagatore
            + ", "
            + nazioneVersante
            + ", "
            + nomeFlussoImportEnte
            + ", "
            + numImporto
            + ", "
            + numeroTotalePagamenti
            + ", "
            + provinciaAttestante
            + ", "
            + provinciaBeneficiario
            + ", "
            + provinciaPagatore
            + ", "
            + provinciaVersante
            + ", "
            + riferimentoDataRichiesta
            + ", "
            + riferimentoMessaggioRichiesta
            + ", "
            + rigaFlussoImportEnte
            + ", "
            + singoloImportoCommissioneCaricoPa
            + ", "
            + singoloImportoPagato
            + ", "
            + tipoDovuto
            + ", "
            + tipoIdentificativoUnivocoAttestante
            + ", "
            + tipoIdentificativoUnivocoBeneficiario
            + ", "
            + tipoIdentificativoUnivocoPagatore
            + ", "
            + tipoIdentificativoUnivocoVersante
            + ", "
            + verificaTotale
            + ", "
            + versioneOggetto
            + "]";
    }

}
