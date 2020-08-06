/**
 * Created on 12/nov/08
 */
package it.nch.eb.validator.idp;

import it.nch.eb.cbi2.validator.ErrorSetHandler;
import it.nch.eb.cbi2.validator.SerializableValidationHandlerFactory;
import it.nch.fwk.checks.errors.ParserErrorsFactory;


public class IdpXsdValidationFactory implements SerializableValidationHandlerFactory  {
	
	private static final long	serialVersionUID	= -153747810223418641L;
	private final ParserErrorsFactory parserErrorsFactory;

	public IdpXsdValidationFactory(ParserErrorsFactory parserErrorsFactory) {
		this.parserErrorsFactory = parserErrorsFactory;
	}

	public ErrorSetHandler create() {
		return new IdpValidationHandler(parserErrorsFactory);
	}

}
