package it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoTesoreriaCommand;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

public class FlussiTesoreriaViewFilter extends AbstractFlussiViewFilter {

	public FlussiTesoreriaViewFilter() {
		super();
	}

	public void setFilters(HttpServletRequest request, String pg, String pgSize, Boolean dataContabileCheck,
			String dataContabileDa, String dataContabileA, Boolean dataValutaCheck, String dataValutaDa,
			String dataValutaA, String deAnnoBolletta, String codBolletta, String importo, String conto,
			String codOr1, String iuv, String iuf, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand) {
		String codIpaEnte = SecurityContext.getEnte().getCodIpaEnte();
		
		String action = SessionVariables.ACTION_VISUALIZZA_FLUSSO_TESORERIA;
		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte,
				action);
		Object session_pg = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_PG);
		setFilter_pg(request, pg, session_pg, visualizzaFlussoTesoreriaCommand, action);

		Object session_pgSize = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_PG_SIZE);
		setFilter_pgSize(request, pgSize, session_pgSize, visualizzaFlussoTesoreriaCommand, action);

		Object session_dt_contabile_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FTES_DATA_CONTABILE_CHECK);
		setFilter_datacontabileCheck(request, dataContabileCheck, session_dt_contabile_check,
				visualizzaFlussoTesoreriaCommand, action);

		Object session_data_contabile_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FTES_DATA_CONTABILE_DA);
		setFilter_dataContabileDa(request, dataContabileDa, session_data_contabile_da,
				visualizzaFlussoTesoreriaCommand, action);

		Object session_data_contabile_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FTES_DATA_CONTABILE_A);
		setFilter_dataContabileA(request, dataContabileA, session_data_contabile_a,
				visualizzaFlussoTesoreriaCommand, action);

		Object session_dt_valuta_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FTES_DATA_VALUTA_CHECK);
		setFilter_dataValutaCheck(request, dataValutaCheck, session_dt_valuta_check,
				visualizzaFlussoTesoreriaCommand, action);

		Object session_data_valuta_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FTES_DATA_VALUTA_DA);
		setFilter_dataValutaDa(request, dataValutaDa, session_data_valuta_da, visualizzaFlussoTesoreriaCommand, action);

		Object session_data_valuta_a = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_DATA_VALUTA_A);
		setFilter_dataValutaA(request, dataValutaA, session_data_valuta_a, visualizzaFlussoTesoreriaCommand, action);

		Object session_anno_bolletta = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_ANNO_BOLLETTA);
		setFilter_annoBolletta(request, deAnnoBolletta, session_anno_bolletta, visualizzaFlussoTesoreriaCommand, action);

		Object session_codice_bolletta = filtersMap == null ? null
				: filtersMap.get(SessionVariables.FTES_CODICE_BOLLETTA);
		setFilter_codiceBolletta(request, codBolletta, session_codice_bolletta, visualizzaFlussoTesoreriaCommand, action);

		Object session_importo = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_IMPORTO);
		setFilter_importo(request, importo, session_importo, visualizzaFlussoTesoreriaCommand, action);

		Object session_conto = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_CODICE_CONTO);
		setFilter_conto(request, conto, session_conto, visualizzaFlussoTesoreriaCommand, action);

		Object session_codOr1 = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_CODICE_ORDINANTE);
		setFilter_codOr1(request, codOr1, session_codOr1, visualizzaFlussoTesoreriaCommand, action);

		Object session_IUV = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_CODICE_IUV);
		setFilter_iuv(request, iuv, session_IUV, visualizzaFlussoTesoreriaCommand, action);

		Object session_IUF = filtersMap == null ? null : filtersMap.get(SessionVariables.FTES_CODICE_IUF);
		setFilter_iuf(request, iuf, session_IUF, visualizzaFlussoTesoreriaCommand, action);
	}

	private void setFilter_pg(HttpServletRequest request, String request_pg, Object session_pg,
			VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_pg == null)
				visualizzaFlussoTesoreriaCommand.setPage(Integer.parseInt(session_pg.toString()));
			else {
				visualizzaFlussoTesoreriaCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setPage(1);
		} catch (NumberFormatException e) {
			visualizzaFlussoTesoreriaCommand.setPage(1);
		}

		setSingleFilter(request, SessionVariables.FTES_PG, visualizzaFlussoTesoreriaCommand.getPage(), action);
	}

	private void setFilter_pgSize(HttpServletRequest request, String request_pgSize, Object session_pgSize,
			VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_pgSize == null)
				visualizzaFlussoTesoreriaCommand.setPageSize(Integer.parseInt(session_pgSize.toString()));
			else
				visualizzaFlussoTesoreriaCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			visualizzaFlussoTesoreriaCommand.setPageSize(5);
		}
		setSingleFilter(request, SessionVariables.FTES_PG_SIZE, visualizzaFlussoTesoreriaCommand.getPageSize(), action);
	}

	private void setFilter_datacontabileCheck(HttpServletRequest request, Boolean request_data_contabile_check,
			Object session_data_contabile_check, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_data_contabile_check == null)
				visualizzaFlussoTesoreriaCommand.setDataContabileCheck(Boolean.valueOf(session_data_contabile_check.toString()));
			else
				visualizzaFlussoTesoreriaCommand.setDataContabileCheck(request_data_contabile_check);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setDataContabileCheck(false);
		}
		setSingleFilter(request, SessionVariables.FTES_DATA_CONTABILE_CHECK,
				visualizzaFlussoTesoreriaCommand.getDataContabileCheck(), action);
	}

	private void setFilter_dataContabileDa(HttpServletRequest request, String request_data_contabile_da,
			Object session_data_contabile_da, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_data_contabile_da == null)
				visualizzaFlussoTesoreriaCommand.setDataContabileDa(session_data_contabile_da.toString());
			else
				visualizzaFlussoTesoreriaCommand.setDataContabileDa(request_data_contabile_da);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setDataContabileDa("");
		}
		setSingleFilter(request, SessionVariables.FTES_DATA_CONTABILE_DA,
				visualizzaFlussoTesoreriaCommand.getDataContabileDa(), action);
	}

	private void setFilter_dataContabileA(HttpServletRequest request, String request_data_contabile_a,
			Object session_data_contabile_a, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_data_contabile_a == null)
				visualizzaFlussoTesoreriaCommand.setDataContabileA(session_data_contabile_a.toString());
			else
				visualizzaFlussoTesoreriaCommand.setDataContabileA(request_data_contabile_a);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setDataContabileA("");
		}
		setSingleFilter(request, SessionVariables.FTES_DATA_CONTABILE_A,
				visualizzaFlussoTesoreriaCommand.getDataContabileA(), action);
	}

	private void setFilter_dataValutaCheck(HttpServletRequest request, Boolean request_data_valuta_check,
			Object session_data_valuta_check, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_data_valuta_check == null)
				visualizzaFlussoTesoreriaCommand.setDataValutaCheck(Boolean.valueOf(session_data_valuta_check.toString()));
			else
				visualizzaFlussoTesoreriaCommand.setDataValutaCheck(request_data_valuta_check);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setDataValutaCheck(false);
		}
		setSingleFilter(request, SessionVariables.FTES_DATA_VALUTA_CHECK,
				visualizzaFlussoTesoreriaCommand.getDataValutaCheck(), action);
	}

	private void setFilter_dataValutaDa(HttpServletRequest request, String request_data_valuta_da,
			Object session_data_valuta_da, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_data_valuta_da == null)
				visualizzaFlussoTesoreriaCommand.setDataValutaDa(session_data_valuta_da.toString());
			else
				visualizzaFlussoTesoreriaCommand.setDataValutaDa(request_data_valuta_da);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setDataValutaDa("");
		}
		setSingleFilter(request, SessionVariables.FTES_DATA_VALUTA_DA,
				visualizzaFlussoTesoreriaCommand.getDataValutaDa(), action);
	}

	private void setFilter_dataValutaA(HttpServletRequest request, String request_data_valuta_a,
			Object session_data_valuta_a, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_data_valuta_a == null)
				visualizzaFlussoTesoreriaCommand.setDataValutaA(session_data_valuta_a.toString());
			else
				visualizzaFlussoTesoreriaCommand.setDataValutaA(request_data_valuta_a);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setDataValutaA("");
		}
		setSingleFilter(request, SessionVariables.FTES_DATA_VALUTA_A,
				visualizzaFlussoTesoreriaCommand.getDataValutaA(), action);
	}

	private void setFilter_annoBolletta(HttpServletRequest request, String request_annoBolletta,
			Object session_annoBolletta, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_annoBolletta == null)
				visualizzaFlussoTesoreriaCommand.setDeAnnoBolletta(session_annoBolletta.toString());
			else
				visualizzaFlussoTesoreriaCommand.setDeAnnoBolletta(request_annoBolletta);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setDeAnnoBolletta("");
		}
		setSingleFilter(request, SessionVariables.FTES_ANNO_BOLLETTA,
				visualizzaFlussoTesoreriaCommand.getDeAnnoBolletta(), action);
	}

	private void setFilter_codiceBolletta(HttpServletRequest request, String request_codiceBolletta,
			Object session_codiceBolletta, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_codiceBolletta == null)
				visualizzaFlussoTesoreriaCommand.setCodBolletta(session_codiceBolletta.toString());
			else
				visualizzaFlussoTesoreriaCommand.setCodBolletta(request_codiceBolletta);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setCodBolletta("");
		}
		setSingleFilter(request, SessionVariables.FTES_CODICE_BOLLETTA,
				visualizzaFlussoTesoreriaCommand.getCodBolletta(), action);
	}

	private void setFilter_importo(HttpServletRequest request, String request_importo, Object session_importo,
			VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_importo == null)
				visualizzaFlussoTesoreriaCommand.setImporto(session_importo.toString());
			else
				visualizzaFlussoTesoreriaCommand.setImporto(request_importo);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setImporto("");
		}
		setSingleFilter(request, SessionVariables.FTES_IMPORTO, visualizzaFlussoTesoreriaCommand.getImporto(), action);
	}

	private void setFilter_conto(HttpServletRequest request, String request_conto, Object session_conto,
			VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_conto == null)
				visualizzaFlussoTesoreriaCommand.setConto(session_conto.toString());
			else
				visualizzaFlussoTesoreriaCommand.setImporto(request_conto);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setConto("");
		}
		setSingleFilter(request, SessionVariables.FTES_CODICE_CONTO, visualizzaFlussoTesoreriaCommand.getConto(), action);

	}

	private void setFilter_codOr1(HttpServletRequest request, String request_codOr1, Object session_codOr1,
			VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_codOr1 == null)
				visualizzaFlussoTesoreriaCommand.setCodOr1(session_codOr1.toString());
			else
				visualizzaFlussoTesoreriaCommand.setCodOr1(request_codOr1);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setCodOr1("");
		}
		setSingleFilter(request, SessionVariables.FTES_CODICE_ORDINANTE, visualizzaFlussoTesoreriaCommand.getCodOr1(), action);
	}

	private void setFilter_iuv(HttpServletRequest request, String request_iuv, Object session_iuv,
			VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_iuv == null)
				visualizzaFlussoTesoreriaCommand.setIuv(session_iuv.toString());
			else
				visualizzaFlussoTesoreriaCommand.setIuv(request_iuv);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setIuv("");
		}
		setSingleFilter(request, SessionVariables.FTES_CODICE_IUV, visualizzaFlussoTesoreriaCommand.getIuv(), action);
	}

	private void setFilter_iuf(HttpServletRequest request, String request_iuf, Object session_iuf,
			VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand, String action) {
		try {
			if (request_iuf == null)
				visualizzaFlussoTesoreriaCommand.setIuf(session_iuf.toString());
			else
				visualizzaFlussoTesoreriaCommand.setIuf(request_iuf);
		} catch (NullPointerException e) {
			visualizzaFlussoTesoreriaCommand.setIuf("");
		}
		setSingleFilter(request, SessionVariables.FTES_CODICE_IUF, visualizzaFlussoTesoreriaCommand.getIuf(), action);
	}

	public void initialize(VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand) {

		Date data_contabile_da = null;

		if (StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileDa())) {
			try {
				data_contabile_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_contabile_a = null;

		if (StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileA())) {
			try {
				data_contabile_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_contabile_da == null) && (data_contabile_a == null)) {
			data_contabile_a = new Date();
			data_contabile_a = DateUtils.setHours(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMinutes(data_contabile_a, 0);
			data_contabile_a = DateUtils.setSeconds(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMilliseconds(data_contabile_a, 0);
			data_contabile_da = DateUtils.addMonths(data_contabile_a, -1);
		} else if ((data_contabile_da != null) && (data_contabile_a == null)) {
			data_contabile_da = DateUtils.setHours(data_contabile_da, 0);
			data_contabile_da = DateUtils.setMinutes(data_contabile_da, 0);
			data_contabile_da = DateUtils.setSeconds(data_contabile_da, 0);
			data_contabile_da = DateUtils.setMilliseconds(data_contabile_da, 0);
			data_contabile_a = DateUtils.addMonths(data_contabile_da, -1);
		} else if ((data_contabile_da == null) && (data_contabile_a != null)) {
			data_contabile_a = DateUtils.setHours(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMinutes(data_contabile_a, 0);
			data_contabile_a = DateUtils.setSeconds(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMilliseconds(data_contabile_a, 0);
			data_contabile_da = DateUtils.addMonths(data_contabile_a, -1);
		}

		visualizzaFlussoTesoreriaCommand.setDataContabileA(Constants.DDMMYYYY.format(data_contabile_a));
		visualizzaFlussoTesoreriaCommand.setDataContabileDa(Constants.DDMMYYYY.format(data_contabile_da));

		Date data_valuta_da = null;

		if (StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaDa())) {
			try {
				data_valuta_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_valuta_a = null;

		if (StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaA())) {
			try {
				data_valuta_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_valuta_da == null) && (data_valuta_a == null)) {
			data_valuta_a = new Date();
			data_valuta_a = DateUtils.setHours(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMinutes(data_valuta_a, 0);
			data_valuta_a = DateUtils.setSeconds(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMilliseconds(data_valuta_a, 0);
			data_valuta_da = DateUtils.addMonths(data_valuta_a, -1);
		} else if ((data_valuta_da != null) && (data_valuta_a == null)) {
			data_valuta_da = DateUtils.setHours(data_valuta_da, 0);
			data_valuta_da = DateUtils.setMinutes(data_valuta_da, 0);
			data_valuta_da = DateUtils.setSeconds(data_valuta_da, 0);
			data_valuta_da = DateUtils.setMilliseconds(data_valuta_da, 0);
			data_valuta_a = DateUtils.addMonths(data_valuta_da, -1);
		} else if ((data_valuta_da == null) && (data_valuta_a != null)) {
			data_valuta_a = DateUtils.setHours(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMinutes(data_valuta_a, 0);
			data_valuta_a = DateUtils.setSeconds(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMilliseconds(data_valuta_a, 0);
			data_valuta_da = DateUtils.addMonths(data_valuta_a, -1);
		}

		visualizzaFlussoTesoreriaCommand.setDataValutaA(Constants.DDMMYYYY.format(data_valuta_a));
		visualizzaFlussoTesoreriaCommand.setDataValutaDa(Constants.DDMMYYYY.format(data_valuta_da));
	}
}
