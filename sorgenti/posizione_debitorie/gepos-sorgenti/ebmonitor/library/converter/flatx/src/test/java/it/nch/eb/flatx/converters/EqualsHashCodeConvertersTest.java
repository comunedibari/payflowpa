/**
 * Created on 21/gen/09
 */
package it.nch.eb.flatx.converters;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.converters.Conversions;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class EqualsHashCodeConvertersTest extends TestCase {
	
	Conversions types = new Conversions();
	
	public void test1() throws Exception {
		Converter cnv1 = BaseConverters.fd_number11_10;
		Converter cnv2 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[10], 11, "0", Alignments.RIGHT);;
		int h1 = cnv1.hashCode();
		int h2 = cnv2.hashCode();
		assertTrue(h1 == h2);	
		assertTrue(cnv1.equals(cnv2));	
	}

}
