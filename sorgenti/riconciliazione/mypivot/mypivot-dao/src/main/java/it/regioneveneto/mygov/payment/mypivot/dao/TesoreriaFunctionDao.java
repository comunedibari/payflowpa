package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;
import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaNoMatchSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaSubset;

public interface TesoreriaFunctionDao {

	List<TesoreriaSubset> getTesoreriaPageListFunction(final String cod_ipa_ente, final Date dt_data_contabile_da,
			final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a,
			final String deCausaleT, final String importo, final String conto, final String codOr1,
			final Boolean visualizzaNascosti, final String errorCode, final String codIuv, final String codIuf,
			final int page, final int size);

	Integer getCountTesoreriaPageListFunction(final String cod_ipa_ente, final Date dt_data_contabile_da,
			final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a,
			final String deCausaleT, final String importo, final String conto, final String codOr1,
			final Boolean visualizzaNascosti, final String errorCode, final String codIuv, final String codIuf);

	Integer getCountTesoreriaNoMatchPageListFunction(final String cod_ipa_ente, final Date dt_data_contabile_da,
			final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a,
			final String deCausaleT, final String importo, final String conto, final String codOr1,
			final Boolean visualizzaNascosti, final String errorCode);

	List<TesoreriaNoMatchSubset> getTesoreriaNoMatchPageListFunction(final String cod_ipa_ente,
			final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da,
			final Date data_valuta_a, final Date dt_data_ultimo_aggiornamento_da,
			final Date dt_data_ultimo_aggiornamento_a, final String deCausaleT, final String importo,
			final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode,
			final String codIuv, final String codIuf, final int page, final int size);
}
