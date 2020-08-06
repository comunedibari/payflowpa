/**
 * Created on 05/dic/07
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;
import it.nch.eb.common.utils.StringUtils;



/**
 * @author gdefacci
 */
public class NumberConverterInsertDecimalSeparator implements Converter {

	private static final long	serialVersionUID	= -4403857622715387981L;

	public static final String DEFAULT_SEPARATOR = ".";
	
	public final int	decimalPos;
	public final String	filler;
	public final String separator;
	
	public NumberConverterInsertDecimalSeparator(int decimalPos, String filler, String separator) {
		this.decimalPos = decimalPos;
		this.filler = filler;
		this.separator = separator;
	}

	public NumberConverterInsertDecimalSeparator(int decimalPos) {
		this( decimalPos, "0", DEFAULT_SEPARATOR);
	}
	
	public NumberConverterInsertDecimalSeparator(int decimalPos, String filler) {
		this(decimalPos, filler, DEFAULT_SEPARATOR);
	}
	
	public NumberConverterInsertDecimalSeparator(NumberConverterNoDecimalSeparator converter) {
		this( converter.decimalPos, converter.filler, DEFAULT_SEPARATOR);
	}
	
	public String encode(String inputString) {
		if (inputString==null) return "0";
		String stringModel = RegexpUtils.removeFiller(inputString, filler, Alignments.RIGHT).trim();
		if (stringModel.length() < 1 ) return "0";
		else if (stringModel.length()>decimalPos) {
			int median = stringModel.length()-decimalPos;
			String decimalPart = StringUtils.removeTrailing( stringModel.substring(median, stringModel.length()), "0");
			String nonDecimalPart = stringModel.substring(0,median);
			String sfx = (decimalPart.length() > 0) ? separator + decimalPart : "";
			
			if (nonDecimalPart.length() < 1) return "0" + sfx;
			else return nonDecimalPart + sfx;
		} else {
			int delta = decimalPos - stringModel.length();
			String zeros = "";
			if (delta>0) zeros = RegexpUtils.makeFiller("0", delta);
			return "0" + separator + zeros + StringUtils.removeTrailing(stringModel, "0");
		}
	}
	

}
