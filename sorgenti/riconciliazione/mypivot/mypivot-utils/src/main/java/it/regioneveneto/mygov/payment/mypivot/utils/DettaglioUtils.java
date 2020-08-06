package it.regioneveneto.mygov.payment.mypivot.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.AnagraficaDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioPagamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioRicevutaTelematicaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioRicevuteRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioTesoreriaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.flussorendicontazione.DettaglioFlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneIdTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaIdTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO;

public class DettaglioUtils {
	private static Log log = LogFactory.getLog(DettaglioUtils.class);

	public static DettaglioDto createDettaglioDtoRendicontazione(String classificazioneCompletezza, String codiceIuf,
			PageDto<DettaglioFlussoRendicontazioneDto> listaPaginata, EnteTO enteTO,
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO) {
		DettaglioDto dettDto = null;
		if (listaPaginata != null) {
			dettDto = new DettaglioDto();
			dettDto.setClassificazioneCompletezza(classificazioneCompletezza);
			dettDto.setCodIpaEnte(enteTO.getCodIpaEnte());
			dettDto.setCodiceIuf(codiceIuf);
			dettDto.setCodiceIud(null);
			dettDto.setCodiceIuv(null);
			dettDto.setRendicontazione(toRendicontazioneConRicevute(listaPaginata));
			if (classificazioneCompletezza.equals(Constants.COD_ERRORE_IUF_TES_DIV_IMP)
					&& importExportRendicontazioneTesoreriaTO != null) {
				dettDto.setTesoreria(toTesoreria(importExportRendicontazioneTesoreriaTO));
			}
		}
		return dettDto;
	}

	public static DettaglioDto createDettaglioDtoFromImportExportRendicontazioneTesoreriaTO(
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO, EnteTO enteTO,
			List<EnteTipoDovutoTO> listaEnteTipoDovutoTO) {
		DettaglioDto dettDto = null;
		if (importExportRendicontazioneTesoreriaTO != null) {
			dettDto = new DettaglioDto();
			ImportExportRendicontazioneTesoreriaIdTO id = importExportRendicontazioneTesoreriaTO.getId();
			if (id != null) {
				dettDto.setCodIpaEnte(id.getCodiceIpaEnte());
				String classificazioneCompletezza = id.getClassificazioneCompletezza();
				dettDto.setClassificazioneCompletezza(classificazioneCompletezza);

				// valorizzazione del pagamento
				switch (classificazioneCompletezza) {
				case Constants.COD_ERRORE_IUD_RT_IUF_TES:// Pagamenti Notificati
					if (enteTO.getFlgPagati()) {
						dettDto.setPagamento(
								toPagamento(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					}
					if (enteTO.getFlgTesoreria()) {
						dettDto.setTesoreria(toTesoreria(importExportRendicontazioneTesoreriaTO));
					}
					dettDto.setRendicontazione(toRendicontazioneSenzaRicevute(importExportRendicontazioneTesoreriaTO));
					dettDto.setRicevuta(toRicevuta(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					break;
				case Constants.COD_ERRORE_IUD_RT_IUF:// Pagamenti Notificati e
														// Riconciliati
					dettDto.setPagamento(toPagamento(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					dettDto.setRendicontazione(toRendicontazioneSenzaRicevute(importExportRendicontazioneTesoreriaTO));
					dettDto.setRicevuta(toRicevuta(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					break;
				case Constants.COD_ERRORE_IUD_NO_RT:// Dovuti non correttamente
													// pagati
					dettDto.setPagamento(toPagamento(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					break;
				case Constants.COD_ERRORE_RT_NO_IUD:// Pagamenti non
													// correttamente notificati
				case Constants.COD_ERRORE_RT_IUF_TES:// Pagamenti Riconciliati
					dettDto.setTesoreria(toTesoreria(importExportRendicontazioneTesoreriaTO));
					dettDto.setRendicontazione(toRendicontazioneSenzaRicevute(importExportRendicontazioneTesoreriaTO));
					dettDto.setRicevuta(toRicevuta(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					break;
				case Constants.COD_ERRORE_RT_IUF:// Pagamenti Rendicontati
					dettDto.setRendicontazione(toRendicontazioneSenzaRicevute(importExportRendicontazioneTesoreriaTO));
					dettDto.setRicevuta(toRicevuta(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					break;
				case Constants.COD_ERRORE_RT_NO_IUF:
					dettDto.setRicevuta(toRicevuta(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					break;
				case Constants.COD_ERRORE_IUF_NO_TES:// Rendicontazioni non
														// correttamente
														// riversate
				case Constants.COD_ERRORE_IUV_NO_RT:// Rendicontazioni di
													// pagamenti non eseguiti
					dettDto.setRendicontazione(toRendicontazioneSenzaRicevute(importExportRendicontazioneTesoreriaTO));
					break;
				case Constants.COD_ERRORE_TES_NO_IUF_OR_IUV:// Riversamenti non
															// rendicontati o di
															// pagamenti non
															// eseguiti
					dettDto.setTesoreria(toTesoreria(importExportRendicontazioneTesoreriaTO));
					break;
				case Constants.COD_ERRORE_RT_TES:
					dettDto.setRicevuta(toRicevuta(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					dettDto.setTesoreria(toTesoreria(importExportRendicontazioneTesoreriaTO));
					break;
				default:
					// si dovrebbe entrare in questo caso solo se vengono
					// aggiunte nuove classificazioni senza aggiungerle all'util
					log.warn("Tipologia di classificazione non prevista [" + classificazioneCompletezza + "]");
					dettDto.setPagamento(toPagamento(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					dettDto.setTesoreria(toTesoreria(importExportRendicontazioneTesoreriaTO));
					dettDto.setRendicontazione(toRendicontazioneSenzaRicevute(importExportRendicontazioneTesoreriaTO));
					dettDto.setRicevuta(toRicevuta(importExportRendicontazioneTesoreriaTO, listaEnteTipoDovutoTO));
					break;
				}
			}
		}
		return dettDto;
	}

	private static DettaglioPagamentoDto toPagamento(
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO,
			List<EnteTipoDovutoTO> listaEnteTipoDovutoTO) {
		DettaglioPagamentoDto dettPag = null;
		if (StringUtils.isNotBlank(importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoPagatore())) {
			dettPag = new DettaglioPagamentoDto();
			dettPag.setTipoDovuto(importExportRendicontazioneTesoreriaTO.getTipoDovuto());
			if (StringUtils.isNotBlank(importExportRendicontazioneTesoreriaTO.getTipoDovuto())) {
				String deTipo = getDeEnteTipoDovutoByCodTipo(listaEnteTipoDovutoTO,
						importExportRendicontazioneTesoreriaTO.getTipoDovuto());
				if (StringUtils.isNotBlank(deTipo)) {
					dettPag.setTipoDovuto(deTipo);
				} else {
					dettPag.setTipoDovuto(importExportRendicontazioneTesoreriaTO.getTipoDovuto());
				}
			}
			dettPag.setCodiceIud(importExportRendicontazioneTesoreriaTO.getCodiceIud());
			dettPag.setSingoloImportoPagato(importExportRendicontazioneTesoreriaTO.getSingoloImportoPagato());
			dettPag.setDeDataEsecuzionePagamento(importExportRendicontazioneTesoreriaTO.getDeDataEsecuzionePagamento());

			if (StringUtils
					.isNotBlank(importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoPagatore())) {
				String codice = importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoPagatore();
				String anagrafica = importExportRendicontazioneTesoreriaTO.getAnagraficaPagatore();
				String tipoIdentificativo = importExportRendicontazioneTesoreriaTO
						.getTipoIdentificativoUnivocoPagatore();
				dettPag.setPagatore(new AnagraficaDettaglioDto(codice, anagrafica, tipoIdentificativo));
			}

			dettPag.setCausaleVersamento(importExportRendicontazioneTesoreriaTO.getCausaleVersamento());
			dettPag.setDatiSpecificiRiscossione(importExportRendicontazioneTesoreriaTO.getDatiSpecificiRiscossione());
		}
		return dettPag;
	}

	private static DettaglioRendicontazioneDto toRendicontazioneConRicevute(
			PageDto<DettaglioFlussoRendicontazioneDto> dettaglioFlussoRendicontazionePaginato) {

		DettaglioRendicontazioneDto dettRendicontazione = null;

		// la lista paginata deve contenere oggetti con codIuf, codEnte e
		// classificazioneCompletezza omogenei tra loro
		List<DettaglioFlussoRendicontazioneDto> dettaglioFlussoRendicontazioneList = dettaglioFlussoRendicontazionePaginato
				.getList();
		if (CollectionUtils.isNotEmpty(dettaglioFlussoRendicontazioneList)) {
			dettRendicontazione = new DettaglioRendicontazioneDto();
			// i dati devono essere omogenei tra loro quindi posso prendere il
			// primo
			DettaglioFlussoRendicontazioneDto firstDettaglioFlussoRendicontazioneDto = dettaglioFlussoRendicontazioneList
					.get(0);
			FlussoRendicontazioneTO flussoRendicontazioneTO = firstDettaglioFlussoRendicontazioneDto
					.getFlussoRendicontazioneTO();
			String identificativoFlussoRendicontazione = flussoRendicontazioneTO.getCodIdentificativoFlusso();
			dettRendicontazione.setIdentificativoFlussoRendicontazione(identificativoFlussoRendicontazione);
			String dataOraFlussoRendicontazione = Constants.DDMMYYYY_HHMMSS
					.format(flussoRendicontazioneTO.getDtDataOraFlusso());
			dettRendicontazione.setDataOraFlussoRendicontazione(dataOraFlussoRendicontazione);
			String identificativoUnivocoRegolamento = flussoRendicontazioneTO.getCodIdentificativoUnivocoRegolamento();
			dettRendicontazione.setIdentificativoUnivocoRegolamento(identificativoUnivocoRegolamento);
			String deDataRegolamento = Constants.DDMMYYYY_HHMMSS.format(flussoRendicontazioneTO.getDtDataRegolamento());
			dettRendicontazione.setDeDataRegolamento(deDataRegolamento);
			String importoTotalePagamenti = String.valueOf(flussoRendicontazioneTO.getNumImportoTotalePagamenti());
			dettRendicontazione.setImportoTotalePagamenti(importoTotalePagamenti);

			PageDto<DettaglioRicevuteRendicontazioneDto> dettaglioRicevuteRendicontazionePage = toRicevuteRendicontazione(
					dettaglioFlussoRendicontazionePaginato);
			dettRendicontazione.setRicevute(dettaglioRicevuteRendicontazionePage);
		}

		return dettRendicontazione;
	}

	private static DettaglioRendicontazioneDto toRendicontazioneSenzaRicevute(
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO) {
		DettaglioRendicontazioneDto dettRendicontazione = new DettaglioRendicontazioneDto();
		dettRendicontazione.setIdentificativoFlussoRendicontazione(
				importExportRendicontazioneTesoreriaTO.getId().getIdentificativoFlussoRendicontazione());
		dettRendicontazione.setDataOraFlussoRendicontazione(
				importExportRendicontazioneTesoreriaTO.getDataOraFlussoRendicontazione());
		dettRendicontazione.setIdentificativoUnivocoRegolamento(
				importExportRendicontazioneTesoreriaTO.getIdentificativoUnivocoRegolamento());
		dettRendicontazione.setDeDataRegolamento(importExportRendicontazioneTesoreriaTO.getDeDataRegolamento());
		dettRendicontazione
				.setImportoTotalePagamenti(importExportRendicontazioneTesoreriaTO.getImportoTotalePagamenti());

		// Non valorizzo il dettaglio delle ricevute
		dettRendicontazione.setRicevute(null);
		return dettRendicontazione;
	}

	private static PageDto<DettaglioRicevuteRendicontazioneDto> toRicevuteRendicontazione(
			PageDto<DettaglioFlussoRendicontazioneDto> dettaglioPaginato) {
		PageDto<DettaglioRicevuteRendicontazioneDto> ricevute = new PageDto<>();
		List<DettaglioFlussoRendicontazioneDto> dettaglioList = dettaglioPaginato.getList();
		if (CollectionUtils.isNotEmpty(dettaglioList)) {
			List<DettaglioRicevuteRendicontazioneDto> dettaglioRicevutaRendicontazioneDtoList = new ArrayList<>();
			ricevute = new PageDto<>(dettaglioPaginato.getPage(), dettaglioPaginato.getPageSize(),
					dettaglioPaginato.isPreviousPage(), dettaglioPaginato.isNextPage(),
					dettaglioRicevutaRendicontazioneDtoList, dettaglioPaginato.getTotalPages(),
					dettaglioPaginato.getTotalRecords());

			for (DettaglioFlussoRendicontazioneDto dettaglioFlussoRendicontazioneDto : dettaglioList) {
				DettaglioRicevuteRendicontazioneDto dettaglioRicevutaRendicontazioneDto = new DettaglioRicevuteRendicontazioneDto();

				FlussoRendicontazioneTO flussoRendicontazioneTO = dettaglioFlussoRendicontazioneDto
						.getFlussoRendicontazioneTO();
				FlussoRendicontazioneIdTO flussoRendicontazioneId = flussoRendicontazioneTO.getId();
				dettaglioRicevutaRendicontazioneDto
						.setIndiceDatiSingoloPagamento(flussoRendicontazioneId.getIndiceDatiSingoloPagamento());
				dettaglioRicevutaRendicontazioneDto
						.setCodiceIuv(flussoRendicontazioneId.getCodDatiSingPagamIdentificativoUnivocoVersamento());
				dettaglioRicevutaRendicontazioneDto
						.setCodiceIur(flussoRendicontazioneId.getCodDatiSingPagamIdentificativoUnivocoRiscossione());
				String singoloImportoPagato = String
						.valueOf(flussoRendicontazioneTO.getNumDatiSingPagamSingoloImportoPagato());
				dettaglioRicevutaRendicontazioneDto.setSingoloImportoPagato(singoloImportoPagato);
				String deDataEsitoSingoloPagamento = Constants.DDMMYYYY
						.format(flussoRendicontazioneTO.getDtDatiSingPagamDataEsitoSingoloPagamento());
				dettaglioRicevutaRendicontazioneDto.setDeDataEsitoSingoloPagamento(deDataEsitoSingoloPagamento);

				FlussoExportTO flussoExportTO = dettaglioFlussoRendicontazioneDto.getFlussoExportTO();
				// non sempre ho tutti i dettagli
				if (flussoExportTO != null) {
					if (StringUtils.isNotBlank(dettaglioFlussoRendicontazioneDto.getDeTipoDovuto())) {
						dettaglioRicevutaRendicontazioneDto
								.setTipoDovuto(dettaglioFlussoRendicontazioneDto.getDeTipoDovuto());
					} else {
						dettaglioRicevutaRendicontazioneDto.setTipoDovuto(flussoExportTO.getCodTipoDovuto());
					}
					dettaglioRicevutaRendicontazioneDto.setCodiceIud(flussoExportTO.getCodIud());
					dettaglioRicevutaRendicontazioneDto
							.setCausaleVersamento(flussoExportTO.getDeEDatiPagDatiSingPagCausaleVersamento());

					// PAGATORE
					String codicePagatore = flussoExportTO.getCodESoggPagIdUnivPagCodiceIdUnivoco();
					String anagraficaPagatore = flussoExportTO.getCodESoggPagAnagraficaPagatore();
					String tipoIdentificativoPagatore = (flussoExportTO.getCodESoggPagIdUnivPagTipoIdUnivoco() != null)
							? String.valueOf(flussoExportTO.getCodESoggPagIdUnivPagTipoIdUnivoco()) : null;

					dettaglioRicevutaRendicontazioneDto.setPagatore(
							new AnagraficaDettaglioDto(codicePagatore, anagraficaPagatore, tipoIdentificativoPagatore));

					// VERSANTE
					String codiceVersante = flussoExportTO.getCodESoggVersIdUnivVersCodiceIdUnivoco();
					String anagraficaVersante = flussoExportTO.getCodESoggVersAnagraficaVersante();
					String tipoIdentificativoVersante = (flussoExportTO
							.getCodESoggVersIdUnivVersTipoIdUnivoco() != null)
									? String.valueOf(flussoExportTO.getCodESoggVersIdUnivVersTipoIdUnivoco()) : null;

					dettaglioRicevutaRendicontazioneDto.setVersante(
							new AnagraficaDettaglioDto(codiceVersante, anagraficaVersante, tipoIdentificativoVersante));

					// ATTESTANTE
					String codiceAttestante = flussoExportTO.getCodEIstitAttIdUnivAttCodiceIdUnivoco();
					String anagraficaAttestante = flussoExportTO.getDeEIstitAttDenominazioneAttestante();
					String tipoIdentificativoAttestante = (flussoExportTO
							.getCodEIstitAttIdUnivAttTipoIdUnivoco() != null)
									? String.valueOf(flussoExportTO.getCodEIstitAttIdUnivAttTipoIdUnivoco()) : null;

					dettaglioRicevutaRendicontazioneDto.setAttestante(new AnagraficaDettaglioDto(codiceAttestante,
							anagraficaAttestante, tipoIdentificativoAttestante));
				}

				dettaglioRicevutaRendicontazioneDtoList.add(dettaglioRicevutaRendicontazioneDto);
			}

		}
		return ricevute;
	}

	private static DettaglioTesoreriaDto toTesoreria(
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO) {
		DettaglioTesoreriaDto dettTesoreria = new DettaglioTesoreriaDto();
		dettTesoreria.setCodConto(importExportRendicontazioneTesoreriaTO.getCodConto());
		dettTesoreria.setDeDataValuta(importExportRendicontazioneTesoreriaTO.getDeDataValuta());
		dettTesoreria.setDeDataContabile(importExportRendicontazioneTesoreriaTO.getDeDataContabile());
		dettTesoreria.setDeImporto(importExportRendicontazioneTesoreriaTO.getDeImporto());
		dettTesoreria.setCodOr1(importExportRendicontazioneTesoreriaTO.getCodOr1());
		dettTesoreria.setDeAnnoBolletta(importExportRendicontazioneTesoreriaTO.getDeAnnoBolletta());
		dettTesoreria.setCodBolletta(importExportRendicontazioneTesoreriaTO.getCodBolletta());
		dettTesoreria.setSingoloImportoCommissioneCaricoPa(importExportRendicontazioneTesoreriaTO.getSingoloImportoCommissioneCaricoPa());
		dettTesoreria.setCodIdDominio(importExportRendicontazioneTesoreriaTO.getCodIdDominio());
		dettTesoreria.setDeDataRicezione(importExportRendicontazioneTesoreriaTO.getDeDataRicezione());
		dettTesoreria.setDeAnnoDocumento(importExportRendicontazioneTesoreriaTO.getDeAnnoDocumento());
		dettTesoreria.setCodDocumento(importExportRendicontazioneTesoreriaTO.getCodDocumento());
		dettTesoreria.setDeAnnoProvvisorio(importExportRendicontazioneTesoreriaTO.getDeAnnoProvvisorio());
		dettTesoreria.setCodProvvisorio(importExportRendicontazioneTesoreriaTO.getCodProvvisorio());
		dettTesoreria.setDeCausaleT(importExportRendicontazioneTesoreriaTO.getDeCausaleT());
		dettTesoreria.setDtEffettivaSospeso(importExportRendicontazioneTesoreriaTO.getDtEffettivaSospeso());
		dettTesoreria.setCodiceGestionaleProvvisorio(importExportRendicontazioneTesoreriaTO.getCodiceGestionaleProvvisorio());
		return dettTesoreria;
	}

	private static DettaglioRicevutaTelematicaDto toRicevuta(
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO,
			List<EnteTipoDovutoTO> listaEnteTipoDovutoTO) {
		DettaglioRicevutaTelematicaDto dettRicevuta = null;
		if (StringUtils.isNotBlank(importExportRendicontazioneTesoreriaTO.getCodiceIud())) {
			dettRicevuta = new DettaglioRicevutaTelematicaDto();
			if (StringUtils.isNotBlank(importExportRendicontazioneTesoreriaTO.getTipoDovuto())) {
				String deTipo = getDeEnteTipoDovutoByCodTipo(listaEnteTipoDovutoTO,
						importExportRendicontazioneTesoreriaTO.getTipoDovuto());
				if (StringUtils.isNotBlank(deTipo)) {
					dettRicevuta.setTipoDovuto(deTipo);
				} else {
					dettRicevuta.setTipoDovuto(importExportRendicontazioneTesoreriaTO.getTipoDovuto());
				}
			}
			dettRicevuta.setCodiceIud(importExportRendicontazioneTesoreriaTO.getCodiceIud());
			dettRicevuta.setCodiceIuv(importExportRendicontazioneTesoreriaTO.getId().getCodiceIuv());
			dettRicevuta
					.setCodiceIur(importExportRendicontazioneTesoreriaTO.getId().getIdentificativoUnivocoRiscossione());
			dettRicevuta.setSingoloImportoPagato(importExportRendicontazioneTesoreriaTO.getSingoloImportoPagato());
			dettRicevuta.setDeDataEsitoSingoloPagamento(
					importExportRendicontazioneTesoreriaTO.getDeDataEsitoSingoloPagamento());
			if (StringUtils
					.isNotBlank(importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoAttestante())) {
				String codice = importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoAttestante();
				String anagrafica = importExportRendicontazioneTesoreriaTO.getDenominazioneAttestante();
				String tipoIdentificativo = importExportRendicontazioneTesoreriaTO
						.getTipoIdentificativoUnivocoAttestante();
				dettRicevuta.setAttestante(new AnagraficaDettaglioDto(codice, anagrafica, tipoIdentificativo));
			}

			if (StringUtils
					.isNotBlank(importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoPagatore())) {
				String codice = importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoPagatore();
				String anagrafica = importExportRendicontazioneTesoreriaTO.getAnagraficaPagatore();
				String tipoIdentificativo = importExportRendicontazioneTesoreriaTO
						.getTipoIdentificativoUnivocoPagatore();
				dettRicevuta.setPagatore(new AnagraficaDettaglioDto(codice, anagrafica, tipoIdentificativo));
			}

			if (StringUtils
					.isNotBlank(importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoVersante())) {
				String codice = importExportRendicontazioneTesoreriaTO.getCodiceIdentificativoUnivocoVersante();
				String anagrafica = importExportRendicontazioneTesoreriaTO.getAnagraficaVersante();
				String tipoIdentificativo = importExportRendicontazioneTesoreriaTO
						.getTipoIdentificativoUnivocoVersante();
				dettRicevuta.setVersante(new AnagraficaDettaglioDto(codice, anagrafica, tipoIdentificativo));
			}

			dettRicevuta.setCausaleVersamento(importExportRendicontazioneTesoreriaTO.getCausaleVersamento());
		}
		return dettRicevuta;
	}

	private static String getDeEnteTipoDovutoByCodTipo(List<EnteTipoDovutoTO> listaEnteTipoDovutoTO, String codTipo) {
		for (EnteTipoDovutoTO etdTO : listaEnteTipoDovutoTO) {
			if (etdTO.getCodTipo().equals(codTipo)) {
				return etdTO.getDeTipo();
			}
		}
		return null;
	}
}
