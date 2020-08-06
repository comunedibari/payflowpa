/**
 * 30/giu/2009
 */
package it.nch.eb.flatx.xml;

import junit.framework.TestCase;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.converters.ReplaceXmlReservedCharsConverter;

/**
 * @author gdefacci
 */
public class XmlEntitiesTest extends TestCase {
	
	public void test1() {
		String res = BaseConverters.types.createSizedType(new ReplaceXmlReservedCharsConverter(), 70).encode("sdkdssdk < dsd > ");
		assertEquals(70, res.length());
		assertEquals("sdkdssdk &lt; dsd &gt;                                                ", res);
	}

}
