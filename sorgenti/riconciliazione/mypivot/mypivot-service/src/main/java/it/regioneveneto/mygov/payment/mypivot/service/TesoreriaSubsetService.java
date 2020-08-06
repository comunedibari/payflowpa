package it.regioneveneto.mygov.payment.mypivot.service;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaNoMatchSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaSubsetDto;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface TesoreriaSubsetService {

	PageDto<TesoreriaSubsetDto> getTesoreriaSubsetPage(String codIpaEnte, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String importo, String conto,
			String codOr1, String classificazione, Boolean visualizzaNascosti, int page, int pageSize, Sort sort);

	PageDto<TesoreriaSubsetDto> getTesoreriaSubsetPageFunction(String codIpaEnte, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String importo, String conto,
			String codOr1, String classificazione, Boolean visualizzaNascosti, String codIuv, String codIuf, int page,
			int pageSize);

	PageDto<TesoreriaNoMatchSubsetDto> getTesoreriaNoMatchSubsetPageFunction(String cod_ipa_ente,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date data_valuta_da, Date data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String deCausaleT,
			String importo, String conto, String codOr1, String errorCode, Boolean visualizzaNascosti, int page,
			int size);

}
