package it.regioneveneto.mygov.payment.mypivotsb.controller.visualizzazioneFlussi.filter;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRicevutaCommand;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

public class FlussiRicevutaViewFilter {

	public FlussiRicevutaViewFilter() {
		super();
	}

	public void setFilters(String pg, String pgSize, Boolean dataEsitoSingoloPagamentoCheck,
			String dataEsitoSingoloPagamentoDa, String dataEsitoSingoloPagamentoA, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf,
			VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand) {
		

		visualizzaFlussoRicevutaCommand.setPage(pg == null ? null : Integer.parseInt(pg));
		visualizzaFlussoRicevutaCommand.setPageSize(pgSize == null ? null : Integer.parseInt(pgSize));		
		visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoCheck(dataEsitoSingoloPagamentoCheck);
		visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoDa(dataEsitoSingoloPagamentoDa);
		visualizzaFlussoRicevutaCommand.setDataEsitoSingoloPagamentoA(dataEsitoSingoloPagamentoA);
		visualizzaFlussoRicevutaCommand.setIud(iud);
		visualizzaFlussoRicevutaCommand.setIuv(iuv);
		visualizzaFlussoRicevutaCommand.setDenominazioneAttestante(denominazioneAttestante);
		visualizzaFlussoRicevutaCommand.setIdentificativoUnivocoRiscossione(identificativoUnivocoRiscossione);
		visualizzaFlussoRicevutaCommand.setCodiceIdentificativoUnivocoPagatore(codiceIdentificativoUnivocoPagatore);
		visualizzaFlussoRicevutaCommand.setAnagraficaPagatore(anagraficaPagatore);
		visualizzaFlussoRicevutaCommand.setCodiceIdentificativoUnivocoVersante(codiceIdentificativoUnivocoVersante);
		visualizzaFlussoRicevutaCommand.setAnagraficaVersante(anagraficaVersante);
		visualizzaFlussoRicevutaCommand.setCodTipoDovuto(codTipoDovuto);
		visualizzaFlussoRicevutaCommand.setIuf(iuf == null ? null : iuf);
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
