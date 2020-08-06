package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import java.util.List;

public class AccertamentoVisualizzaCapitoliPagamentiDto {
	
	AccertamentoDettaglioDto accertamentoDettaglioDto;

	List<AccertamentoUfficioCapitoloDto> resultList;
	
	
	
	public AccertamentoVisualizzaCapitoliPagamentiDto() {
//		super();
	}
	
	/**
	 * @return the accertamentoDto
	 */
	public AccertamentoDettaglioDto getAccertamentoDettaglioDto() {
		return accertamentoDettaglioDto;
	}
	/**
	 * @param accertamentoDto the accertamentoDto to set
	 */
	public void setAccertamentoDettaglioDto(AccertamentoDettaglioDto accertamentoDettaglioDto) {
		this.accertamentoDettaglioDto = accertamentoDettaglioDto;
	}

	/**
	 * @return the resultList
	 */
	public List<AccertamentoUfficioCapitoloDto> getResultList() {
		return resultList;
	}
	/**
	 * @param resultList the resultList to set
	 */
	public void setResultList(List<AccertamentoUfficioCapitoloDto> resultList) {
		this.resultList = resultList;
	}

}