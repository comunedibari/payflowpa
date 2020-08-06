package it.regioneveneto.mygov.payment.mypivotsb.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MultipleDataResponse<T> extends OperationResponse {
	
	@ApiModelProperty(required = true, value = "")
	private List<T> items;
	
	public MultipleDataResponse() {
		super();
	}

	public MultipleDataResponse(List<T> items) {
		super();
		this.items = items;
	}

	public MultipleDataResponse( List<T> items, MessagesDto message) {
		super( message);
		this.items = items;
	}
}