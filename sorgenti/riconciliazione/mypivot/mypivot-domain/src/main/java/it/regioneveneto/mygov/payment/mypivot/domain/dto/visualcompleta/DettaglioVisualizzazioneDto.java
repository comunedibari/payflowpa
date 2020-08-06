package it.regioneveneto.mygov.payment.mypivot.domain.dto.visualcompleta;

import java.math.BigDecimal;
import java.util.Date;

import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;

/*
 * Per retrocompatibilita non spacchetto gli attributi in sotto oggetti. Mi limito alla segnalazione
 */
public class DettaglioVisualizzazioneDto {
	private SegnalazioneDto segnalazione;
	private String codiceIpaEnte;
	private String codiceIuv;
	private String identificativoFlussoRendicontazione;
	private String identificativoUnivocoRiscossione;
	private String anagraficaPagatore;
	private String anagraficaVersante;
	private String bilancio;
	private String capAttestante;
	private String capBeneficiario;
	private String capPagatore;
	private String capVersante;
	private String causaleVersamento;
	private String civicoAttestante;
	private String civicoBeneficiario;
	private String civicoPagatore;
	private String civicoVersante;
	private String classificazioneCompletezza;
	private String codConto;
	private String codOr1;
	private String codiceContestoPagamento;
	private String codiceEsitoPagamento;
	private String codiceIdentificativoUnivocoAttestante;
	private String codiceIdentificativoUnivocoBeneficiario;
	private String codiceIdentificativoUnivocoPagatore;
	private String codiceIdentificativoUnivocoVersante;
	private String codiceIud;
	private String codiceUnitOperAttestante;
	private String codiceUnitOperBeneficiario;
	private String dataAcquisizione;
	private String dataOraFlussoRendicontazione;
	private String dataOraMessaggioRicevuta;
	private String datiSpecificiRiscossione;
	private String deDataContabile;
	private String deDataEsecuzionePagamento;
	private String deDataEsitoSingoloPagamento;
	private String deDataRegolamento;
	private String deDataUltimoAggiornamento;
	private String deDataValuta;
	private String deImporto;
	private String denomUnitOperAttestante;
	private String denomUnitOperBeneficiario;
	private String denominazioneAttestante;
	private String denominazioneBeneficiario;
	private Date dtDataContabile;
	private Date dtDataEsecuzionePagamento;
	private Date dtDataEsitoSingoloPagamento;
	private Date dtDataRegolamento;
	private Date dtDataUltimoAggiornamento;
	private Date dtDataValuta;
	private String emailPagatore;
	private String emailVersante;
	private String esitoSingoloPagamento;
	private String identificativoDominio;
	private String identificativoMessaggioRicevuta;
	private String identificativoStazioneRichiedente;
	private String identificativoUnivocoRegolamento;
	private String identificativoUnivocoVersamento;
	private String importoTotalePagamenti;
	private String importoTotalePagato;
	private String indirizzoAttestante;
	private String indirizzoBeneficiario;
	private String indirizzoPagatore;
	private String indirizzoVersante;
	private String localitaAttestante;
	private String localitaBeneficiario;
	private String localitaPagatore;
	private String localitaVersante;
	private String nazioneAttestante;
	private String nazioneBeneficiario;
	private String nazionePagatore;
	private String nazioneVersante;
	private String nomeFlussoImportEnte;
	private BigDecimal numImporto;
	private String numeroTotalePagamenti;
	private String provinciaAttestante;
	private String provinciaBeneficiario;
	private String provinciaPagatore;
	private String provinciaVersante;
	private String riferimentoDataRichiesta;
	private String riferimentoMessaggioRichiesta;
	private String rigaFlussoImportEnte;
	private String singoloImportoCommissioneCaricoPa;
	private String singoloImportoPagato;
	private String tipoDovuto;
	private String tipoIdentificativoUnivocoAttestante;
	private String tipoIdentificativoUnivocoBeneficiario;
	private String tipoIdentificativoUnivocoPagatore;
	private String tipoIdentificativoUnivocoVersante;
	private String verificaTotale;
	private String versioneOggetto;

	public DettaglioVisualizzazioneDto(ImportExportRendicontazioneTesoreriaIdTO id, String anagraficaPagatore,
			String anagraficaVersante, String bilancio, String capAttestante, String capBeneficiario,
			String capPagatore, String capVersante, String causaleVersamento, String civicoAttestante,
			String civicoBeneficiario, String civicoPagatore, String civicoVersante, 
			String codConto, String codOr1, String codiceContestoPagamento, String codiceEsitoPagamento,
			String codiceIdentificativoUnivocoAttestante, String codiceIdentificativoUnivocoBeneficiario,
			String codiceIdentificativoUnivocoPagatore, String codiceIdentificativoUnivocoVersante, String codiceIud,
			String codiceUnitOperAttestante, String codiceUnitOperBeneficiario, String dataAcquisizione,
			String dataOraFlussoRendicontazione, String dataOraMessaggioRicevuta, String datiSpecificiRiscossione,
			String deDataContabile, String deDataEsecuzionePagamento, String deDataEsitoSingoloPagamento,
			String deDataRegolamento, String deDataUltimoAggiornamento, String deDataValuta, String deImporto,
			String denomUnitOperAttestante, String denomUnitOperBeneficiario, String denominazioneAttestante,
			String denominazioneBeneficiario, Date dtDataContabile, Date dtDataEsecuzionePagamento,
			Date dtDataEsitoSingoloPagamento, Date dtDataRegolamento, Date dtDataUltimoAggiornamento, Date dtDataValuta,
			String emailPagatore, String emailVersante, String esitoSingoloPagamento, String identificativoDominio,
			String identificativoMessaggioRicevuta, String identificativoStazioneRichiedente,
			String identificativoUnivocoRegolamento, String identificativoUnivocoVersamento,
			String importoTotalePagamenti, String importoTotalePagato, String indirizzoAttestante,
			String indirizzoBeneficiario, String indirizzoPagatore, String indirizzoVersante, String localitaAttestante,
			String localitaBeneficiario, String localitaPagatore, String localitaVersante, String nazioneAttestante,
			String nazioneBeneficiario, String nazionePagatore, String nazioneVersante, String nomeFlussoImportEnte,
			BigDecimal numImporto, String numeroTotalePagamenti, String provinciaAttestante,
			String provinciaBeneficiario, String provinciaPagatore, String provinciaVersante,
			String riferimentoDataRichiesta, String riferimentoMessaggioRichiesta, String rigaFlussoImportEnte,
			String singoloImportoCommissioneCaricoPa, String singoloImportoPagato, String tipoDovuto,
			String tipoIdentificativoUnivocoAttestante, String tipoIdentificativoUnivocoBeneficiario,
			String tipoIdentificativoUnivocoPagatore, String tipoIdentificativoUnivocoVersante, String verificaTotale,
			String versioneOggetto, SegnalazioneDto segnalazione) {
		super();
		this.segnalazione = segnalazione;
		this.codiceIpaEnte = id.codiceIpaEnte;
		this.codiceIuv = id.codiceIpaEnte;
		this.identificativoFlussoRendicontazione = id.identificativoFlussoRendicontazione;
		this.identificativoUnivocoRiscossione = id.identificativoUnivocoRiscossione;
		this.classificazioneCompletezza = id.classificazioneCompletezza;

		this.anagraficaPagatore = anagraficaPagatore;
		this.anagraficaVersante = anagraficaVersante;
		this.bilancio = bilancio;
		this.capAttestante = capAttestante;
		this.capBeneficiario = capBeneficiario;
		this.capPagatore = capPagatore;
		this.capVersante = capVersante;
		this.causaleVersamento = causaleVersamento;
		this.civicoAttestante = civicoAttestante;
		this.civicoBeneficiario = civicoBeneficiario;
		this.civicoPagatore = civicoPagatore;
		this.civicoVersante = civicoVersante;
		this.classificazioneCompletezza = classificazioneCompletezza;
		this.codConto = codConto;
		this.codOr1 = codOr1;
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
		this.deDataContabile = deDataContabile;
		this.deDataEsecuzionePagamento = deDataEsecuzionePagamento;
		this.deDataEsitoSingoloPagamento = deDataEsitoSingoloPagamento;
		this.deDataRegolamento = deDataRegolamento;
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
	}

	public static DettaglioVisualizzazioneDto copyOf(ImportExportRendicontazioneTesoreriaTO o, SegnalazioneTO s) {
		return new DettaglioVisualizzazioneDto(
				it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO
						.copyOf(((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).id),
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).anagraficaPagatore,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).anagraficaVersante,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).bilancio,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capAttestante,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capBeneficiario,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capPagatore,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).capVersante,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).causaleVersamento,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoAttestante,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoBeneficiario,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoPagatore,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).civicoVersante,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codConto,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).codOr1,
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
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataContabile,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataEsecuzionePagamento,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataEsitoSingoloPagamento,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO) o).deDataRegolamento,
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
				SegnalazioneDto.copyOf(s));
	}

	public SegnalazioneDto getSegnalazione() {
		return segnalazione;
	}

	public void setSegnalazione(SegnalazioneDto segnalazione) {
		this.segnalazione = segnalazione;
	}

	public String getCodiceIpaEnte() {
		return codiceIpaEnte;
	}

	public void setCodiceIpaEnte(String codiceIpaEnte) {
		this.codiceIpaEnte = codiceIpaEnte;
	}

	public String getCodiceIuv() {
		return codiceIuv;
	}

	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}

	public String getIdentificativoFlussoRendicontazione() {
		return identificativoFlussoRendicontazione;
	}

	public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
		this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
	}

	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}

	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
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

	public String getClassificazioneCompletezza() {
		return classificazioneCompletezza;
	}

	public void setClassificazioneCompletezza(String classificazioneCompletezza) {
		this.classificazioneCompletezza = classificazioneCompletezza;
	}

	public String getCodConto() {
		return codConto;
	}

	public void setCodConto(String codConto) {
		this.codConto = codConto;
	}

	public String getCodOr1() {
		return codOr1;
	}

	public void setCodOr1(String codOr1) {
		this.codOr1 = codOr1;
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

}
