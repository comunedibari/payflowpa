/**
 * Created on 08/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.bean.types.SizedConverter;


/**
 * @author gdefacci
 */
public abstract class ExpressionConversionInfo extends ConversionInfo implements XPathsMapBindinsManagerStringFunction {

	private static final long	serialVersionUID	= -9021648937162602188L;

	public ExpressionConversionInfo(int pos, SizedConverter delegate) {
		super(pos, delegate, null);
	}
	
	public ExpressionConversionInfo(int pos, SizedConverter delegate, String name) {
		super(pos, delegate, name);
	}
	
//	public boolean isModificationTrigger() {
//		return true;
//	}
	
	

}
