package it.regioneveneto.mygov.payment.mypivotsb.controller.visualizzazioneFlussi.filter;

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

public class FlussiRendicontazioneViewFilter {

	public FlussiRendicontazioneViewFilter() {
		super();
	}

	public void setFilters(String pg, String pgSize, Boolean dataRegolamentoCheck,
			String dataRegolamentoDa, String dataRegolamentoA, String iuf, String identificativoUnivocoRegolamento,
			VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand) {

		visualizzaFlussoRendicontazioneCommand.setPage(pg == null ? null : Integer.parseInt(pg));
		visualizzaFlussoRendicontazioneCommand.setPageSize(pgSize == null ? null : Integer.parseInt(pgSize));		

		visualizzaFlussoRendicontazioneCommand.setDataRegolamentoCheck(dataRegolamentoCheck);		
		visualizzaFlussoRendicontazioneCommand.setDataRegolamentoDa(dataRegolamentoDa);
		visualizzaFlussoRendicontazioneCommand.setDataRegolamentoA(dataRegolamentoA);
		visualizzaFlussoRendicontazioneCommand.setIuf(iuf);		

		visualizzaFlussoRendicontazioneCommand.setIdentificativoUnivocoRegolamento(identificativoUnivocoRegolamento);	
				
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
