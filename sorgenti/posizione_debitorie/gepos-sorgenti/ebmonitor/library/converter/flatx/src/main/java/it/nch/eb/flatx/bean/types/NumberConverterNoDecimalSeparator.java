/**
 * Created on 05/dic/07
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;


/**
 * @author gdefacci
 */
public class NumberConverterNoDecimalSeparator implements Converter {

	private static final long	serialVersionUID	= -8517102459168866899L;

	public static final char DEFAULT_SEPARATOR = '.';
	
	public final int	decimalPos;
	public final String	decimalSeparator;
	public final String	filler;

	public NumberConverterNoDecimalSeparator(int decimalPos) {
		this( decimalPos, DEFAULT_SEPARATOR);
	}
	
	public NumberConverterNoDecimalSeparator(int decimalPos, char decimalSeparator) {
		this.decimalPos = decimalPos;
		this.decimalSeparator = Character.toString(decimalSeparator);
		this.filler = "0";
	}
	
	public NumberConverterNoDecimalSeparator(int decimalPos, char decimalSeparator, char toRemoveFiller) {
		this.decimalPos = decimalPos;
		this.decimalSeparator = Character.toString(decimalSeparator);
		this.filler = Character.toString(toRemoveFiller);
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof NumberConverterNoDecimalSeparator )) return false;
		NumberConverterNoDecimalSeparator othr = (NumberConverterNoDecimalSeparator)obj;
		boolean res = decimalPos == othr.decimalPos ; 
		if (res) res = decimalSeparator.equals(othr.decimalSeparator);
		if (res) res = filler.equals(othr.filler);
		return res;
	}

	public int hashCode() {
		return decimalPos + decimalSeparator.hashCode() + filler.hashCode();
	}

	public String encode(String stringModel) {
		if (stringModel==null || "".equals(stringModel.trim())) return null;
		String nonFractionalPart;
		String[] parts = RegexpUtils.split(stringModel, decimalSeparator);
		if (parts.length>2) throw new IllegalStateException("more than one separator");
		if (filler!=null) nonFractionalPart = RegexpUtils.removeFiller( parts[0], filler, Alignments.RIGHT );
		else nonFractionalPart = parts[0];
		if (parts.length==2) {
			String fractionalPart = parts[1];
			if (fractionalPart.length()>decimalPos) return nonFractionalPart + fractionalPart.substring(0, decimalPos);
			else return nonFractionalPart + RegexpUtils.fill(fractionalPart, decimalPos , "0", Alignments.LEFT);
		} else {
			return nonFractionalPart + RegexpUtils.makeFiller("0", decimalPos);
		}
	}
	

}
