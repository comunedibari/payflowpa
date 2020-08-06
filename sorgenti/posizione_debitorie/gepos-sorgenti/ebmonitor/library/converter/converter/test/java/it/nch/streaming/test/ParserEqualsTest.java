/**
 * Created on 02/mar/2009
 */
package it.nch.streaming.test;

import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class ParserEqualsTest extends TestCase {
	
	public void test1() throws Exception {
		PmtreqParsersFactory parserFactory = new PmtreqParsersFactory();
		IParser bi = parserFactory.createBodyItemParser();
		IParser bi1 = parserFactory.createBodyItemParser();
		
		IParser bd1 = parserFactory.createBodyParser();
		IParser bd2 = parserFactory.createBodyParser();
		
		IParser bds1 = parserFactory.createBodySeqParser();
		IParser bds2 = parserFactory.createBodySeqParser();
		
		IParser bdis1 = parserFactory.createBodySequenceParser();
		IParser bdis2 = parserFactory.createBodySequenceParser();
		
		assertFalse(bi == bi1);
		assertEquals(bi, bi1);
		
		assertFalse(bd1 == bd2);
		assertEquals(bd1, bd2);
		
		assertFalse(bds1 == bds2);
		assertEquals(bds1, bds2);
		
		assertFalse(bdis1 == bdis2);
		assertEquals(bdis1, bdis2);
		
		assertFalse( bd1.equals(bds2) );
		assertFalse( bi.equals(bds2) );
		assertFalse( bi.equals(bd2) );
		assertFalse( bd2.equals(bds2) );
		assertFalse( bds2.equals(bdis2) );
	}

}
