/**
 * 23/ott/2009
 */
package it.nch.xml2sql.validations;

import java.math.BigDecimal;

import it.nch.eb.xsqlcmd.dbtrpos.error.XmlErrorInfosFactory;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class XmlErrsTest extends TestCase {
	
	public void testXmlErr() throws Exception {
		CondizioniPagamentoModel cp = new CondizioniPagamentoModel();
		cp.setIdPagamento("32323");
		cp.setImTotale(new BigDecimal(676));
		
		XmlErrorInfosFactory xmlErrsFactory = new XmlErrorInfosFactory();
		System.out.println(
		xmlErrsFactory.condizionePagamentoImTotaleInvalid(cp, new BigDecimal(777)) );
	}

}
