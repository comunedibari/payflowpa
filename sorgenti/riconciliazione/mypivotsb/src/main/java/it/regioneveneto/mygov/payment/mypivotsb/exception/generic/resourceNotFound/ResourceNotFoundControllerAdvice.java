package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.resourceNotFound;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceNotFoundControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	public void handleResourceNotFound(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}
}