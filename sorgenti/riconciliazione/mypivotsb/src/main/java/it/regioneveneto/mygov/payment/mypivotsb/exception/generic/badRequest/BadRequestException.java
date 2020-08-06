package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.badRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.regioneveneto.mygov.payment.mypivotsb.exception.generic.BaseException;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class BadRequestException extends BaseException implements ResponseIF {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}
}