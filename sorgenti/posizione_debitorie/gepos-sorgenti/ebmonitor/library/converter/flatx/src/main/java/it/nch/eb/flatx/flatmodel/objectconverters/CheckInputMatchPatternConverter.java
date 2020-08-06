/**
 * Created on 11/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.SizedConverter;

import java.util.regex.Pattern;


/**
 * TODO: actually not used
 * @author gdefacci
 */
public class CheckInputMatchPatternConverter extends ValidateInputAndDelegateConverter implements Converter {

	private static final long	serialVersionUID	= -3116071195335952336L;
	private final Pattern pattern;
	
	public CheckInputMatchPatternConverter(Pattern pattern, SizedConverter delegate) {
		super(" does not match the regexp [" + pattern.pattern() + "]", delegate);
		this.pattern = pattern;
	}
	
	protected boolean validateInput(String stringModel) {
		if (pattern.matcher(stringModel).matches()) return true;
		else return false;
	}
	
	public static CheckInputMatchPatternConverter create(String regexp, SizedConverter delegate) {
		return new CheckInputMatchPatternConverter(Pattern.compile(regexp), delegate);
	}
	

}
