package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.resourceNotFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.regioneveneto.mygov.payment.mypivotsb.exception.generic.BaseException;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException implements ResponseIF {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String field,Long id) {
		super( field +"id not found : " + id );
	}

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}