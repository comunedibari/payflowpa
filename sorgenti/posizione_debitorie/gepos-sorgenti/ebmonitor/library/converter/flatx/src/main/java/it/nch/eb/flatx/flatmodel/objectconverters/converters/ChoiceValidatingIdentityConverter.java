/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import java.util.Set;
import java.util.TreeSet;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.exceptions.ConversionException;
/**
 * @author gdefacci
 */
public class ChoiceValidatingIdentityConverter implements SizedConverter {

	private static final long	serialVersionUID	= -2318132460829618379L;
	private final Integer	length;
	private final Set/*<String>*/ allowedValues = new TreeSet();
	
	public ChoiceValidatingIdentityConverter(String[] values) {
		if (values==null || values.length==0) throw new NullPointerException();
		
		int len = values[0].length();
		for (int i = 0; i < values.length; i++) {
			if (values[i].length()!=len) throw new IllegalStateException("all values should have the same length");
			allowedValues.add(values[i]);
		}	
		this.length = new Integer(len);
	}

	public Integer getLength() {
		return length;
	}

	public String encode(String stringModel) {
		if (!this.allowedValues.contains(stringModel)) 
			throw new ConversionException("expecting one of[" + StringUtils.toString(allowedValues) 
					+ " but got " + stringModel, this);
		return stringModel;
	}

}
