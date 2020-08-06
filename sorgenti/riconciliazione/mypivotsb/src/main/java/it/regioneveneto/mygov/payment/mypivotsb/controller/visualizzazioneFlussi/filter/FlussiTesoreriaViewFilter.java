package it.regioneveneto.mygov.payment.mypivotsb.controller.visualizzazioneFlussi.filter;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoTesoreriaCommand;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

public class FlussiTesoreriaViewFilter {

	public FlussiTesoreriaViewFilter() {
		super();
	}

	
	public void setFilters(String pg, String pgSize, Boolean dataContabileCheck,
			String dataContabileDa, String dataContabileA, Boolean dataValutaCheck, String dataValutaDa,
			String dataValutaA, String deAnnoBolletta, String codBolletta, String importo, String conto,
			String codOr1, String iuv, String iuf, VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand) {

		visualizzaFlussoTesoreriaCommand.setPage(pg == null ? null : Integer.parseInt(pg));
		visualizzaFlussoTesoreriaCommand.setPageSize(pgSize == null ? null : Integer.parseInt(pgSize));
		visualizzaFlussoTesoreriaCommand.setDataContabileCheck(dataContabileCheck);
		visualizzaFlussoTesoreriaCommand.setDataContabileDa(dataContabileDa);
		visualizzaFlussoTesoreriaCommand.setDataContabileA(dataContabileA);
		visualizzaFlussoTesoreriaCommand.setDataValutaCheck(dataValutaCheck);
		visualizzaFlussoTesoreriaCommand.setDataValutaDa(dataValutaDa);
		visualizzaFlussoTesoreriaCommand.setDataValutaA(dataValutaA);
		visualizzaFlussoTesoreriaCommand.setDeAnnoBolletta(deAnnoBolletta);
		visualizzaFlussoTesoreriaCommand.setCodBolletta(codBolletta);
		visualizzaFlussoTesoreriaCommand.setImporto(importo);
		visualizzaFlussoTesoreriaCommand.setConto(conto);
		visualizzaFlussoTesoreriaCommand.setCodOr1(codOr1);
		visualizzaFlussoTesoreriaCommand.setIuv(iuv);
		visualizzaFlussoTesoreriaCommand.setIuf(iuf);

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
