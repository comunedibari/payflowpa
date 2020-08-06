/**
 * 08/mag/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

/**
 * @author gdefacci
 */
public class StableBaseXPathToObjectConversionInfo extends BaseXPathToObjectConversionInfo implements StableXPathMapFunction {

	private static final long serialVersionUID = 7322685614086817737L;

	public StableBaseXPathToObjectConversionInfo(int position, Class trgtClass,
			XPathMapFunction xpathMapFunction) {
		super(position, trgtClass, xpathMapFunction);
	}

	public StableBaseXPathToObjectConversionInfo(int position, String name,
			Class targetClass, XPathMapFunction xpathMapFunction) {
		super(position, name, targetClass, xpathMapFunction);
	}

	public StableBaseXPathToObjectConversionInfo(int position,
			XPathMapFunction xpathMapFunction) {
		super(position, xpathMapFunction);
	}
	
}
