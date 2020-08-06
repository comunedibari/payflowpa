package it.regioneveneto.mygov.payment.mypivotsb.response;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageDtoResponse<T> extends OperationResponse {

	private PageDto<?> page;
	
	public PageDtoResponse() {}
	
	public PageDtoResponse(PageDto<?> pageDto) {
		this.page = pageDto;
	}
}