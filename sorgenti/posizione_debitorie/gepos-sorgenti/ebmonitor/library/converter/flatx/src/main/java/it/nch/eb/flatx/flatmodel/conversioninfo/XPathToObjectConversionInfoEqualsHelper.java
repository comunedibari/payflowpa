/**
 * Created on 09/mar/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;


/**
 * @author gdefacci
 */
public class XPathToObjectConversionInfoEqualsHelper {
	
	public static final XPathToObjectConversionInfoEqualsHelper instance = 
		new XPathToObjectConversionInfoEqualsHelper();
	
	private static int compare(int a, int b) {
		if (a==b) return 0;
		else if (a<b) return -1;
		else return 1;
	}
	
	public int hashCode(IXPathToObjectConversionInfo ths) {
		return ths.getPosition() * 11;
	}

	public int compare(IXPathToObjectConversionInfo ths, Object o) {
		if (o != null && o instanceof IXPathToObjectConversionInfo) {
			IXPathToObjectConversionInfo other = (IXPathToObjectConversionInfo) o;
			return compare(ths.getPosition(), other.getPosition());
		}
		return 1;
	}

}
