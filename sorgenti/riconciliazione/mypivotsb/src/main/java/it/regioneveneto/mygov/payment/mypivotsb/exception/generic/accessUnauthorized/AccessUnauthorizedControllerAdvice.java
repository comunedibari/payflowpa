package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.accessUnauthorized;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccessUnauthorizedControllerAdvice {

	@ExceptionHandler(AccessUnauthorizedException.class)
	public void handleAccessForbidden(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.FORBIDDEN.value());
	}
}