/**
 * Created on 27/feb/08
 */
package it.nch.eb.flatx.flatmodel.objectconverters.converters;

import java.math.BigDecimal;

import it.nch.eb.flatx.flatmodel.objectconverters.TypeSafeToObjectConverter;


/**
 * @author gdefacci
 */
public class ToBigDecimalConveter extends TypeSafeToObjectConverter {

	private static final long serialVersionUID = -6425611747121894754L;
	private final Integer implicitDecimalPositions;
	
	public ToBigDecimalConveter() {
		super(BigDecimal.class);
		this.implicitDecimalPositions = null;
	}
	
	public ToBigDecimalConveter(int implicitDecimalPositions) {
		super(BigDecimal.class);
		this.implicitDecimalPositions = new Integer( implicitDecimalPositions );
	}

	public Object safeConvert(String str) {
		BigDecimal n1 = new BigDecimal(str);
		if (implicitDecimalPositions!=null) {
			n1 = n1.movePointLeft(implicitDecimalPositions.intValue());
		}
		return n1;
	}

}
