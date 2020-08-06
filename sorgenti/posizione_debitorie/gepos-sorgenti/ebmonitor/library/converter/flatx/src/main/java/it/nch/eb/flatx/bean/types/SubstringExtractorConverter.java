/**
 * Created on 10/gen/08
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;
import it.nch.eb.flatx.exceptions.ConversionException;
import it.nch.eb.flatx.flatmodel.converters.Conversions;



/**
 * @author gdefacci
 */
public class SubstringExtractorConverter implements FillerConverter, ReversibleConverter {
	
	private static final long	serialVersionUID	= -9194032962126232944L;
	public final int initialOffset;
	public final int finalOffset;
	public SizedConverter filler;
	
	public SubstringExtractorConverter(int initialOffset, int finalOffset, SizedConverter filler) {
		if (!(finalOffset > initialOffset)) 
			throw new IllegalStateException("initial offset[" + initialOffset + "] greather than final one[" + finalOffset + "]");
		this.initialOffset = initialOffset;
		this.finalOffset = finalOffset;
		int delta = finalOffset-initialOffset;
		this.filler = filler;
		if (filler!=null) {
			if (filler.getLength().intValue() != delta) {
				throw new ConversionException("the given converter " + filler + " is not " + delta + " long", this);
			}
		}
	}

	public SubstringExtractorConverter(int initialOffset, int finalOffset) {
		this( initialOffset, finalOffset, null);
	}

	public Integer getLength() {
		return new Integer( finalOffset - initialOffset);
	}

	public String encode(String stringModel) {
		if (finalOffset<0 || initialOffset<0 || finalOffset<initialOffset) {
			throw new IllegalStateException("initial offset(" + initialOffset + ")final(" +  finalOffset + ")");
		}
		if (stringModel==null) {
			if (filler!=null) return filler.encode(" ");
			else return makeFiller();
		}
		if (finalOffset < stringModel.length()) {
			String res = getPartial(stringModel, initialOffset, filler);
			return res;
		} else {
			String res = stringModel.substring(initialOffset);
			int len = finalOffset - initialOffset;
			return RegexpUtils.fill(res, len);
		}
	}

	private String makeFiller() {
		return RegexpUtils.makeFiller(" ", finalOffset-initialOffset);
	}

	private String getPartial(String stringModel, int iniOffset, SizedConverter filler2) {
		if (iniOffset>stringModel.length()) {
			if (filler2!=null) return filler2.encode(" ");
			else return makeFiller();
		}
		
		String substring = stringModel.substring(iniOffset);
		if (filler2!=null) return filler2.encode(substring);
		else return substring;
	}

	public String toString() {
		return "substrintg extract [" + initialOffset + ", " + finalOffset + "]";
	}

	public int getAlignment() {
		return Alignments.LEFT;
	}

	public String getFiller() {
		return " ";
	}

	public Converter getReverse() {
		return new SizedConverterImpl(finalOffset -initialOffset,getFiller(), getAlignment(), Conversions.TRIMMER );
	}
	
}
