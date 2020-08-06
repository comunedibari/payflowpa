package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class AccertamentoVisualizzaPagamentiDto {
	
	AccertamentoDto accertamentoDto;

	PageDto<AccertamentoDettaglioDto> resultList;
	
	
	
	public AccertamentoVisualizzaPagamentiDto() {
//		super();
	}
	
	/**
	 * @return the accertamentoDto
	 */
	public AccertamentoDto getAccertamentoDto() {
		return accertamentoDto;
	}
	/**
	 * @param accertamentoDto the accertamentoDto to set
	 */
	public void setAccertamentoDto(AccertamentoDto accertamentoDto) {
		this.accertamentoDto = accertamentoDto;
	}

	/**
	 * @return the resultList
	 */
	public PageDto<AccertamentoDettaglioDto> getResultList() {
		return resultList;
	}
	/**
	 * @param resultList the resultList to set
	 */
	public void setResultList(PageDto<AccertamentoDettaglioDto> resultList) {
		this.resultList = resultList;
	}

}