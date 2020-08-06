package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.accessForbidden;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccessForbiddenControllerAdvice {

	@ExceptionHandler(AccessForbiddenException.class)
	public void handleAccessForbiddenControllerAdvice(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.FORBIDDEN.value());
	}
}