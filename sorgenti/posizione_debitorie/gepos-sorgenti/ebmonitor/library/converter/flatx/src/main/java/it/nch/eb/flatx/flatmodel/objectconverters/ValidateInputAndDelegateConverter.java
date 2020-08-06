/**
 * Created on 11/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.ConversionValidationException;


/**
 * TODO: actually never used
 * @author gdefacci
 */
public abstract class ValidateInputAndDelegateConverter implements SizedConverter {

	private static final long	serialVersionUID	= -6848187072835972900L;
	
	private final String	errorMessage;
	private SizedConverter	delegate;

	public ValidateInputAndDelegateConverter(String errorMessage, SizedConverter delegate) {
		this.errorMessage = errorMessage;
		this.delegate = delegate;
	}

	public final String encode(String stringModel) {
		if (validateInput(stringModel)) {
			return delegate.encode(stringModel);
		} else {
			throw new ConversionValidationException("[" + stringModel + "]caused error :" + errorMessage);
		}
	}
	
	public Integer getLength() {
		return delegate.getLength();
	}

	protected abstract  boolean validateInput(String stringModel);

}
