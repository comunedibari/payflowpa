package it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRendicontazioneCommand;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

public class FlussiRendicontazioneViewFilter extends AbstractFlussiViewFilter {

	public FlussiRendicontazioneViewFilter() {
		super();
	}

	public void setFilters(HttpServletRequest request, String pg, String pgSize, Boolean dataRegolamentoCheck,
			String dataRegolamentoDa, String dataRegolamentoA, String iuf, String identificativoUnivocoRegolamento,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand) {
		String codIpaEnte = SecurityContext.getEnte().getCodIpaEnte();

		String action = SessionVariables.ACTION_VISUALIZZA_FLUSSO_RENDICONTAZIONE;

		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, action);
		Object session_pg = filtersMap == null ? null : filtersMap.get(SessionVariables.FREND_PG);
		setFilter_pg(request, pg, session_pg, visualizzaFlussoRendicontazioneCommand, action);

		Object session_pgSize = filtersMap == null ? null : filtersMap.get(SessionVariables.FREND_PG_SIZE);
		setFilter_pgSize(request, pgSize, session_pgSize, visualizzaFlussoRendicontazioneCommand, action);

		Object session_dt_regolamento_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FREND_DATA_REGOLAMENTO_CHECK);
		setFilter_dataRegolamentoCheck(request, dataRegolamentoCheck, session_dt_regolamento_check,
				visualizzaFlussoRendicontazioneCommand, action);

		Object session_data_regolamento_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FREND_DATA_REGOLAMENTO_DA);
		setFilter_dataRegolamentoDa(request, dataRegolamentoDa, session_data_regolamento_da,
				visualizzaFlussoRendicontazioneCommand, action);

		Object session_data_regolamento_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FREND_DATA_REGOLAMENTO_A);
		setFilter_dataRegolamentoA(request, dataRegolamentoA, session_data_regolamento_a,
				visualizzaFlussoRendicontazioneCommand, action);

		Object session_IUF = filtersMap == null ? null : filtersMap.get(SessionVariables.FREND_CODICE_IUF);
		setFilter_iuf(request, iuf, session_IUF, visualizzaFlussoRendicontazioneCommand, action);

		Object session_id_univoco_regolamento = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FREND_ID_UNIVOCO_REGOLAMENTO);
		setFilter_id_univoco_regolamento(request, identificativoUnivocoRegolamento, session_id_univoco_regolamento,
				visualizzaFlussoRendicontazioneCommand, action);
	}

	private void setFilter_pg(HttpServletRequest request, String request_pg, Object session_pg,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand, String action) {
		try {
			if (request_pg == null)
				visualizzaFlussoRendicontazioneCommand.setPage(Integer.parseInt(session_pg.toString()));
			else {
				visualizzaFlussoRendicontazioneCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			visualizzaFlussoRendicontazioneCommand.setPage(1);
		} catch (NumberFormatException e) {
			visualizzaFlussoRendicontazioneCommand.setPage(1);
		}

		setSingleFilter(request, SessionVariables.FREND_PG, visualizzaFlussoRendicontazioneCommand.getPage(), action);
	}

	private void setFilter_pgSize(HttpServletRequest request, String request_pgSize, Object session_pgSize,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand, String action) {
		try {
			if (request_pgSize == null)
				visualizzaFlussoRendicontazioneCommand.setPageSize(Integer.parseInt(session_pgSize.toString()));
			else
				visualizzaFlussoRendicontazioneCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			visualizzaFlussoRendicontazioneCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			visualizzaFlussoRendicontazioneCommand.setPageSize(5);
		}
		setSingleFilter(request, SessionVariables.FREND_PG_SIZE, visualizzaFlussoRendicontazioneCommand.getPageSize(),
				action);
	}

	private void setFilter_dataRegolamentoCheck(HttpServletRequest request, Boolean request_data_regolamento_check,
			Object session_data_regolamento_check,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand, String action) {
		try {
			if (request_data_regolamento_check == null)
				visualizzaFlussoRendicontazioneCommand
						.setDataRegolamentoCheck(Boolean.valueOf(session_data_regolamento_check.toString()));
			else
				visualizzaFlussoRendicontazioneCommand.setDataRegolamentoCheck(request_data_regolamento_check);
		} catch (NullPointerException e) {
			visualizzaFlussoRendicontazioneCommand.setDataRegolamentoCheck(false);
		}
		setSingleFilter(request, SessionVariables.FREND_DATA_REGOLAMENTO_CHECK,
				visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck(), action);
	}

	private void setFilter_dataRegolamentoDa(HttpServletRequest request, String request_data_contabile_da,
			Object session_data_regolamento_da,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand, String action) {
		try {
			if (request_data_contabile_da == null)
				visualizzaFlussoRendicontazioneCommand.setDataRegolamentoDa(session_data_regolamento_da.toString());
			else
				visualizzaFlussoRendicontazioneCommand.setDataRegolamentoDa(request_data_contabile_da);
		} catch (NullPointerException e) {
			visualizzaFlussoRendicontazioneCommand.setDataRegolamentoDa("");
		}
		setSingleFilter(request, SessionVariables.FREND_DATA_REGOLAMENTO_DA,
				visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa(), action);
	}

	private void setFilter_dataRegolamentoA(HttpServletRequest request, String request_data_contabile_a,
			Object session_data_regolamento_a,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand, String action) {
		try {
			if (request_data_contabile_a == null)
				visualizzaFlussoRendicontazioneCommand.setDataRegolamentoA(session_data_regolamento_a.toString());
			else
				visualizzaFlussoRendicontazioneCommand.setDataRegolamentoA(request_data_contabile_a);
		} catch (NullPointerException e) {
			visualizzaFlussoRendicontazioneCommand.setDataRegolamentoA("");
		}
		setSingleFilter(request, SessionVariables.FREND_DATA_REGOLAMENTO_A,
				visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA(), action);
	}

	private void setFilter_iuf(HttpServletRequest request, String request_iuf, Object session_iuf,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand, String action) {
		try {
			if (request_iuf == null)
				visualizzaFlussoRendicontazioneCommand.setIuf(session_iuf.toString());
			else
				visualizzaFlussoRendicontazioneCommand.setIuf(request_iuf);
		} catch (NullPointerException e) {
			visualizzaFlussoRendicontazioneCommand.setIuf("");
		}
		setSingleFilter(request, SessionVariables.FREND_CODICE_IUF, visualizzaFlussoRendicontazioneCommand.getIuf(),
				action);
	}

	private void setFilter_id_univoco_regolamento(HttpServletRequest request, String request_id_univoco_regolamento,
			Object session_id_univoco_regolamento,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand, String action) {
		try {
			if (request_id_univoco_regolamento == null)
				visualizzaFlussoRendicontazioneCommand
						.setIdentificativoUnivocoRegolamento(session_id_univoco_regolamento.toString());
			else
				visualizzaFlussoRendicontazioneCommand
						.setIdentificativoUnivocoRegolamento(request_id_univoco_regolamento);
		} catch (NullPointerException e) {
			visualizzaFlussoRendicontazioneCommand.setIdentificativoUnivocoRegolamento("");
		}
		setSingleFilter(request, SessionVariables.FREND_ID_UNIVOCO_REGOLAMENTO,
				visualizzaFlussoRendicontazioneCommand.getIdentificativoUnivocoRegolamento(), action);
	}

	public void initialize(VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand) {
		
		visualizzaFlussoRendicontazioneCommand.setDataRegolamentoCheck(true);

		Date data_regolamento_da = null;

		if (StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa())) {
			try {
				data_regolamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_regolamento_a = null;

		if (StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA())) {
			try {
				data_regolamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_regolamento_da == null) && (data_regolamento_a == null)) {
			data_regolamento_a = new Date();
			data_regolamento_a = DateUtils.setHours(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMinutes(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setSeconds(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMilliseconds(data_regolamento_a, 0);

			data_regolamento_da = DateUtils.addMonths(data_regolamento_a, -1);
		} else if ((data_regolamento_da != null) && (data_regolamento_a == null)) {
			data_regolamento_da = DateUtils.setHours(data_regolamento_da, 0);
			data_regolamento_da = DateUtils.setMinutes(data_regolamento_da, 0);
			data_regolamento_da = DateUtils.setSeconds(data_regolamento_da, 0);
			data_regolamento_da = DateUtils.setMilliseconds(data_regolamento_da, 0);

			data_regolamento_a = DateUtils.addMonths(data_regolamento_da, -1);
		} else if ((data_regolamento_da == null) && (data_regolamento_a != null)) {
			data_regolamento_a = DateUtils.setHours(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMinutes(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setSeconds(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMilliseconds(data_regolamento_a, 0);

			data_regolamento_da = DateUtils.addMonths(data_regolamento_a, -1);
		}

		visualizzaFlussoRendicontazioneCommand.setDataRegolamentoDa(Constants.DDMMYYYY.format(data_regolamento_da));
		visualizzaFlussoRendicontazioneCommand.setDataRegolamentoA(Constants.DDMMYYYY.format(data_regolamento_a));
	}
}
