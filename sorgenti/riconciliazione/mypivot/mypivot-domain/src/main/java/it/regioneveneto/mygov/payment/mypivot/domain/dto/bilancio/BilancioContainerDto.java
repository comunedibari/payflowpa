package it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio;

import java.util.ArrayList;
import java.util.List;

public class BilancioContainerDto {

	private List<BilancioDto> listaBilancioDto;

	public List<BilancioDto> getListaBilancioDto() {

		if (listaBilancioDto == null) {
			listaBilancioDto = new ArrayList<BilancioDto>();
		}

		return listaBilancioDto;
	}

	public void setListaBilancioDto(List<BilancioDto> listaBilancioDto) {
		this.listaBilancioDto = listaBilancioDto;
	}

}
