/**
    This is the common structure for all responses
    if the response contains a list(array) then it will have 'items' field
    if the response contains a single item then it will have 'item'  field
 */

package it.regioneveneto.mygov.payment.mypivotsb.response;

import java.sql.Timestamp;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import lombok.Data;

@Data
public class OperationResponse implements ResponseIF {

	public OperationResponse() {
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public OperationResponse(MessagesDto messages) {
		this.messages = messages;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	private Timestamp timestamp;
	private MessagesDto messages;
}