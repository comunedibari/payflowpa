/**
 * Created on 17/gen/08
 */
package it.nch.eb.flatx.commons;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.bean.types.NumberConverterInsertDecimalSeparator;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;
import it.nch.eb.flatx.flatmodel.converters.ReplaceXmlEntitiesConverter;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class ConvertersTest extends TestCase implements BaseConverters {
	
	public void testH3() throws Exception {
		String numb = "69.69";
		String convNumb = fd_number18_3.encode(numb);
		NumberConverterInsertDecimalSeparator reverse = new NumberConverterInsertDecimalSeparator(BaseConverters.types.NUMBER_FIXED_DECIMALS[3]);
		String reverted = reverse.encode(convNumb);
		assertEquals("000000000000069690", convNumb);
		System.out.println(reverted);
	}
	
	public void testH1() throws Exception {
		String numb = "69.69";
		String convNumb = fd_number18_3.encode(numb);
		assertEquals("000000000000069690", convNumb);
	}

	public Converter textConverter() {
		return new ReplaceXmlEntitiesConverter();
	}

}
