//This is a common http response model for providing data series

package it.regioneveneto.mygov.payment.mypivotsb.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SingleDataResponse<T> extends OperationResponse {
	
	public SingleDataResponse() {
		super();
	}

	private T items;
	
	public SingleDataResponse(T items) {
		this.items = items;
	}
}