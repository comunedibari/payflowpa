package it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRicevutaCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

public class FlussiRicevutaViewFilter extends AbstractFlussiViewFilter {

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	public FlussiRicevutaViewFilter() {
		super();
	}

	public void setFilters(HttpServletRequest request, String action, String pg, String pgSize, Boolean dataEsitoSingoloPagamentoCheck,
			String dataEsitoSingoloPagamentoDa, String dataEsitoSingoloPagamentoA, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand) {
		String codIpaEnte = SecurityContext.getEnte().getCodIpaEnte();

		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, action);
		Object session_pg = filtersMap == null ? null : filtersMap.get(SessionVariables.FRIC_PG);
		setFilter_pg(request, pg, session_pg, visualizzaFlussoRicevutaCommand, action);

		Object session_pgSize = filtersMap == null ? null : filtersMap.get(SessionVariables.FRIC_PG_SIZE);
		setFilter_pgSize(request, pgSize, session_pgSize, visualizzaFlussoRicevutaCommand, action);

		Object session_dt_esito_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_DATA_ESITO_CHECK);
		setFilter_dataEsitoSingoloPagamentoCheck(request, dataEsitoSingoloPagamentoCheck, session_dt_esito_check,
				visualizzaFlussoRicevutaCommand, action);

		Object session_data_esito_singolo_pagamento_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_DATA_ESITO_DA);
		setFilter_dataEsitoSingoloPagamentoDa(request, dataEsitoSingoloPagamentoDa,
				session_data_esito_singolo_pagamento_da, visualizzaFlussoRicevutaCommand, action);

		Object session_data_esito_singolo_pagamento_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_DATA_ESITO_A);
		setFilter_dataEsitoSingoloPagamentoA(request, dataEsitoSingoloPagamentoA,
				session_data_esito_singolo_pagamento_a, visualizzaFlussoRicevutaCommand, action);

		Object session_codice_iud = filtersMap == null ? null : filtersMap.get(SessionVariables.FRIC_CODICE_IUD);
		setFilter_IUD(request, iud, session_codice_iud, visualizzaFlussoRicevutaCommand, action);

		Object session_codice_iuv = filtersMap == null ? null : filtersMap.get(SessionVariables.FRIC_CODICE_IUV);
		setFilter_IUV(request, iuv, session_codice_iuv, visualizzaFlussoRicevutaCommand, action);

		Object session_denominazione_attestante = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_DENOMINAZIONE_ATTESTANTE);
		setFilter_denominazioneAttestante(request, denominazioneAttestante, session_denominazione_attestante,
				visualizzaFlussoRicevutaCommand, action);

		Object session_identificativo_univoco_riscossione = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_ID_UNIVOCO_RISCOSSIONE);
		setFilter_identificativoUnivocoRiscossione(request, identificativoUnivocoRiscossione,
				session_identificativo_univoco_riscossione, visualizzaFlussoRicevutaCommand, action);

		Object session_codice_identificativo_univoco_pagatore = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_COD_ID_UNIVOCO_PAGATORE);
		setFilter_codiceIdentificativoUnivocoPagatore(request, codiceIdentificativoUnivocoPagatore,
				session_codice_identificativo_univoco_pagatore, visualizzaFlussoRicevutaCommand, action);

		Object session_anagrafica_pagatore = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_ANAGRAFICA_PAGATORE);
		setFilter_anagraficaPagatore(request, anagraficaPagatore, session_anagrafica_pagatore,
				visualizzaFlussoRicevutaCommand, action);

		Object session_codice_identificativo_univoco_versante = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_COD_ID_UNIVOCO_VERSANTE);
		setFilter_codiceIdentificativoUnivocoVersante(request, codiceIdentificativoUnivocoVersante,
				session_codice_identificativo_univoco_versante, visualizzaFlussoRicevutaCommand, action);

		Object session_anagrafica_versante = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_ANAGRAFICA_VERSANTE);
		setFilter_anagraficaVersante(request, anagraficaVersante, session_anagrafica_versante,
				visualizzaFlussoRicevutaCommand, action);

		Object session_cod_tipo_dovuto = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FRIC_COD_TIPO_DOVUTO);
		setFilter_codTipoDovuto(request, codTipoDovuto, session_cod_tipo_dovuto, visualizzaFlussoRicevutaCommand,
				action);

		Object session_codice_iuf = filtersMap == null ? null : filtersMap.get(SessionVariables.FRIC_CODICE_IUF);
		setFilter_IUF(request, iuf, session_codice_iuf, visualizzaFlussoRicevutaCommand, action);
		
		
	}

	private void setFilter_pg(HttpServletRequest request, String request_pg, Object session_pg,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_pg == null)
				visualizzaFlussoRicevutaCommand.setPage(Integer.parseInt(session_pg.toString()));
			else {
				visualizzaFlussoRicevutaCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setPage(1);
		} catch (NumberFormatException e) {
			visualizzaFlussoRicevutaCommand.setPage(1);
		}

		setSingleFilter(request, SessionVariables.FRIC_PG, visualizzaFlussoRicevutaCommand.getPage(), action);
	}

	private void setFilter_pgSize(HttpServletRequest request, String request_pgSize, Object session_pgSize,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_pgSize == null)
				visualizzaFlussoRicevutaCommand.setPageSize(Integer.parseInt(session_pgSize.toString()));
			else
				visualizzaFlussoRicevutaCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			visualizzaFlussoRicevutaCommand.setPageSize(5);
		}
		setSingleFilter(request, SessionVariables.FRIC_PG_SIZE, visualizzaFlussoRicevutaCommand.getPageSize(), action);
	}

	private void setFilter_dataEsitoSingoloPagamentoCheck(HttpServletRequest request, Boolean data_esito_check,
			Object session_dt_esito_check, VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand,
			String action) {
		try {
			if (data_esito_check == null)
				visualizzaFlussoRicevutaCommand
						.setDataEsitoSingoloPagamentoCheck(Boolean.valueOf(session_dt_esito_check.toString()));
			else
				visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoCheck(data_esito_check);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoCheck(false);
		}
		setSingleFilter(request, SessionVariables.FRIC_DATA_ESITO_CHECK,
				visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck(), action);
	}

	private void setFilter_dataEsitoSingoloPagamentoDa(HttpServletRequest request,
			String request_data_esito_singolo_pagamento_da, Object session_data_esito_singolo_pagamento_da,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_data_esito_singolo_pagamento_da == null)
				visualizzaFlussoRicevutaCommand
						.setDataEsitoSingoloPagamentoDa(session_data_esito_singolo_pagamento_da.toString());
			else
				visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoDa(request_data_esito_singolo_pagamento_da);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoDa("");
		}
		setSingleFilter(request, SessionVariables.FRIC_DATA_ESITO_DA,
				visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa(), action);
	}

	private void setFilter_dataEsitoSingoloPagamentoA(HttpServletRequest request,
			String request_data_esito_singolo_pagamento_a, Object session_data_esito_singolo_pagamento_a,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_data_esito_singolo_pagamento_a == null)
				visualizzaFlussoRicevutaCommand
						.setDataEsitoSingoloPagamentoA(session_data_esito_singolo_pagamento_a.toString());
			else
				visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoA(request_data_esito_singolo_pagamento_a);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoA("");
		}
		setSingleFilter(request, SessionVariables.FRIC_DATA_ESITO_A,
				visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA(), action);
	}

	private void setFilter_IUD(HttpServletRequest request, String request_codice_iud, Object session_codice_iud,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_codice_iud == null)
				visualizzaFlussoRicevutaCommand.setIud(session_codice_iud.toString());
			else
				visualizzaFlussoRicevutaCommand.setIud(request_codice_iud);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setIud("");
		}
		setSingleFilter(request, SessionVariables.FRIC_CODICE_IUD, visualizzaFlussoRicevutaCommand.getIud(), action);
	}

	private void setFilter_IUV(HttpServletRequest request, String request_codice_iuv, Object session_codice_iuv,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_codice_iuv == null)
				visualizzaFlussoRicevutaCommand.setIuv(session_codice_iuv.toString());
			else
				visualizzaFlussoRicevutaCommand.setIuv(request_codice_iuv);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setIuv("");
		}
		setSingleFilter(request, SessionVariables.FRIC_CODICE_IUV, visualizzaFlussoRicevutaCommand.getIuv(), action);
	}

	private void setFilter_denominazioneAttestante(HttpServletRequest request, String request_denominazione_attestante,
			Object session_denominazione_attestante, VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand,
			String action) {
		try {
			if (request_denominazione_attestante == null)
				visualizzaFlussoRicevutaCommand.setDenominazioneAttestante(session_denominazione_attestante.toString());
			else
				visualizzaFlussoRicevutaCommand.setDenominazioneAttestante(request_denominazione_attestante);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setDenominazioneAttestante("");
		}
		setSingleFilter(request, SessionVariables.FRIC_DENOMINAZIONE_ATTESTANTE,
				visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(), action);

	}

	private void setFilter_identificativoUnivocoRiscossione(HttpServletRequest request,
			String request_identificativo_univoco_riscossione, Object session_identificativo_univoco_riscossione,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_identificativo_univoco_riscossione == null)
				visualizzaFlussoRicevutaCommand
						.setIdentificativoUnivocoRiscossione(session_identificativo_univoco_riscossione.toString());
			else
				visualizzaFlussoRicevutaCommand
						.setIdentificativoUnivocoRiscossione(request_identificativo_univoco_riscossione);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setIdentificativoUnivocoRiscossione("");
		}
		setSingleFilter(request, SessionVariables.FRIC_ID_UNIVOCO_RISCOSSIONE,
				visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(), action);
	}

	private void setFilter_codiceIdentificativoUnivocoPagatore(HttpServletRequest request,
			String request_codice_identificativo_univoco_pagatore,
			Object session_codice_identificativo_univoco_pagatore,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_codice_identificativo_univoco_pagatore == null)
				visualizzaFlussoRicevutaCommand.setCodiceIdentificativoUnivocoPagatore(
						session_codice_identificativo_univoco_pagatore.toString());
			else
				visualizzaFlussoRicevutaCommand
						.setCodiceIdentificativoUnivocoPagatore(request_codice_identificativo_univoco_pagatore);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setCodiceIdentificativoUnivocoPagatore("");
		}
		setSingleFilter(request, SessionVariables.FRIC_COD_ID_UNIVOCO_PAGATORE,
				visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(), action);
	}

	private void setFilter_anagraficaPagatore(HttpServletRequest request, String request_anagrafica_pagatore,
			Object session_anagrafica_pagatore, VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand,
			String action) {
		try {
			if (request_anagrafica_pagatore == null)
				visualizzaFlussoRicevutaCommand.setAnagraficaPagatore(session_anagrafica_pagatore.toString());
			else
				visualizzaFlussoRicevutaCommand.setAnagraficaPagatore(request_anagrafica_pagatore);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setAnagraficaPagatore("");
		}
		setSingleFilter(request, SessionVariables.FRIC_ANAGRAFICA_PAGATORE,
				visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(), action);
	}

	private void setFilter_codiceIdentificativoUnivocoVersante(HttpServletRequest request,
			String request_codice_identificativo_univoco_versante,
			Object session_codice_identificativo_univoco_versante,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_codice_identificativo_univoco_versante == null)
				visualizzaFlussoRicevutaCommand.setCodiceIdentificativoUnivocoVersante(
						session_codice_identificativo_univoco_versante.toString());
			else
				visualizzaFlussoRicevutaCommand
						.setCodiceIdentificativoUnivocoVersante(request_codice_identificativo_univoco_versante);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setCodiceIdentificativoUnivocoVersante("");
		}
		setSingleFilter(request, SessionVariables.FRIC_COD_ID_UNIVOCO_VERSANTE,
				visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(), action);
	}

	private void setFilter_anagraficaVersante(HttpServletRequest request, String request_anagrafica_versante,
			Object session_anagrafica_versante, VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand,
			String action) {
		try {
			if (request_anagrafica_versante == null)
				visualizzaFlussoRicevutaCommand.setAnagraficaVersante(session_anagrafica_versante.toString());
			else
				visualizzaFlussoRicevutaCommand.setAnagraficaVersante(request_anagrafica_versante);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setAnagraficaVersante("");
		}
		setSingleFilter(request, SessionVariables.FRIC_ANAGRAFICA_VERSANTE,
				visualizzaFlussoRicevutaCommand.getAnagraficaVersante(), action);
	}

	private void setFilter_codTipoDovuto(HttpServletRequest request, String request_cod_tipo_dovuto,
			Object session_cod_tipo_dovuto, VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand,
			String action) {
		try {
			if (request_cod_tipo_dovuto == null)
				visualizzaFlussoRicevutaCommand.setCodTipoDovuto(session_cod_tipo_dovuto.toString());
			else {
				if (isCodTipoDovutoValidoPerOperatoreEdEnte(request_cod_tipo_dovuto))
					visualizzaFlussoRicevutaCommand.setCodTipoDovuto(request_cod_tipo_dovuto);
				else
					visualizzaFlussoRicevutaCommand.setCodTipoDovuto("");
			}
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setCodTipoDovuto("");
		}
		setSingleFilter(request, SessionVariables.FRIC_COD_TIPO_DOVUTO,
				visualizzaFlussoRicevutaCommand.getCodTipoDovuto(), action);
	}

	private void setFilter_IUF(HttpServletRequest request, String request_codice_iuf, Object session_codice_iuf,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand, String action) {
		try {
			if (request_codice_iuf == null)
				visualizzaFlussoRicevutaCommand.setIuf(session_codice_iuf.toString());
			else
				visualizzaFlussoRicevutaCommand.setIuf(request_codice_iuf);
		} catch (NullPointerException e) {
			visualizzaFlussoRicevutaCommand.setIuf("");
		}
		setSingleFilter(request, SessionVariables.FRIC_CODICE_IUF, visualizzaFlussoRicevutaCommand.getIuf(), action);
	}

	private boolean isCodTipoDovutoValidoPerOperatoreEdEnte(String codTipoDovuto) {
		UtenteTO utenteTO = SecurityContext.getUtente();
		EnteTO enteTO = SecurityContext.getEnte();
		return operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(),
				enteTO.getCodIpaEnte(), codTipoDovuto);
	}

	public void initialize(VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand) {

		Date data_esito_singolo_pagamento_da = null;

		if (StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa())) {
			try {
				data_esito_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_esito_singolo_pagamento_a = null;

		if (StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA())) {
			try {
				data_esito_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_esito_singolo_pagamento_da == null) && (data_esito_singolo_pagamento_a == null)) {
			data_esito_singolo_pagamento_a = new Date();
			data_esito_singolo_pagamento_a = DateUtils.setHours(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMinutes(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setSeconds(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMilliseconds(data_esito_singolo_pagamento_a, 0);

			data_esito_singolo_pagamento_da = DateUtils.addMonths(data_esito_singolo_pagamento_a, -1);
		} else if ((data_esito_singolo_pagamento_da != null) && (data_esito_singolo_pagamento_a == null)) {
			data_esito_singolo_pagamento_da = DateUtils.setHours(data_esito_singolo_pagamento_da, 0);
			data_esito_singolo_pagamento_da = DateUtils.setMinutes(data_esito_singolo_pagamento_da, 0);
			data_esito_singolo_pagamento_da = DateUtils.setSeconds(data_esito_singolo_pagamento_da, 0);
			data_esito_singolo_pagamento_da = DateUtils.setMilliseconds(data_esito_singolo_pagamento_da, 0);

			data_esito_singolo_pagamento_a = DateUtils.addMonths(data_esito_singolo_pagamento_da, -1);
		} else if ((data_esito_singolo_pagamento_da == null) && (data_esito_singolo_pagamento_a != null)) {
			data_esito_singolo_pagamento_a = DateUtils.setHours(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMinutes(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setSeconds(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMilliseconds(data_esito_singolo_pagamento_a, 0);

			data_esito_singolo_pagamento_da = DateUtils.addMonths(data_esito_singolo_pagamento_a, -1);
		}

		visualizzaFlussoRicevutaCommand
				.setDataEsitoSingoloPagamentoDa(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_da));
		visualizzaFlussoRicevutaCommand
				.setDataEsitoSingoloPagamentoA(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_a));
	}
}
