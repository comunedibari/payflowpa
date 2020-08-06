package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoTesoreriaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio.BilancioContainerDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface FlussoTesoreriaService {

	FlussoTesoreriaTO findByCodIpaEnteDeAnnoBollettaAndCodBolletta(final String codIpaEnte, final String deAnnoBolletta,
			final String codBolletta);

	PageDto<FlussoTesoreriaDto> getFlussoTesoreriaPage(String codIpaEnte, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, String deAnnoBolletta, String codBolletta,
			String importo, String conto, String codOr1, String iuv, String iuf, int page, int pageSize, Sort sort);

	PageDto<FlussoTesoreriaDto> getFlussoTesoreriaPage(String codIpaEnte, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, String deAnnoBolletta, String codBolletta,
			String importo, String conto, String codOr1, String iuv, String iuf, Pageable pageable);
	
	BilancioContainerDto estraiListaAccertamenti(final String codIpaEnte,
			final List<FlussoExportTO> listaFlussiExportTO);
}
