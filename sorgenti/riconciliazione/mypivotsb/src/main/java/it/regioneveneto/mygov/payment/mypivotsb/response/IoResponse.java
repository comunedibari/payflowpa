package it.regioneveneto.mygov.payment.mypivotsb.response;

import lombok.Data;

@Data
public class IoResponse implements ResponseIF {
	String status;
	String descr;
	
	public IoResponse() {}
	
	public IoResponse(String status, String descr) {
		this.status = status;
		this.descr = descr;
	}
}