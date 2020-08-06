/**
 * Created on 03/giu/07
 */
package it.nch.eb.flatx.files.tokeniners.tests;

import it.nch.eb.flatx.files.TokenizationUtil;
import it.nch.eb.flatx.files.model.TokenizedLine;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class DelimitedTokenizerTest extends TestCase {
	
	private String	content = "123456**78901**2345678901**23456789012345678**90**";
	private TokenizedLine dt = TokenizationUtil.createDelimiterTokenizer(content, "\\*\\*");
	
//	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void test1() {
		assertEquals("123456",				dt.nextToken());
		assertEquals("78901",              	dt.nextToken());
		assertEquals("2345678901",         	dt.nextToken());
		assertEquals("23456789012345678",  	dt.nextToken());
		assertEquals("90",                 	dt.nextToken());
	}
	
	public void testAll() {
		String[] all = { "123456", "78901", "2345678901", "23456789012345678", "90" };
		for (int i=0;i<all.length;i++) {
			assertEquals(all[i], dt.all()[i]);
		}
		
	}

}
