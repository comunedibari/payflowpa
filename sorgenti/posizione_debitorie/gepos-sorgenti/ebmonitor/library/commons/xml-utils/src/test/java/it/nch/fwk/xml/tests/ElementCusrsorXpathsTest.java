/**
 * Created on 02/lug/2008
 */
package it.nch.fwk.xml.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.IteratorsFactory;
import it.nch.fwk.checks.xom.ElementCursor;
import it.nch.fwk.checks.xom.XomUtils;
import it.nch.fwk.core.NamespacesInfos;
import nu.xom.Document;


/**
 * @author gdefacci
 */
public class ElementCusrsorXpathsTest extends TestCase {
	
	File f = new File("C:\\Documents and Settings\\Amministratore\\Desktop\\NCH dos\\star-fuck\\testing\\Disposizioni Pagamento XML\\Dispositivi\\LikePC\\source-xml\\LIKEPC-2-MF-custom.xml");
	static Map nssMap = new HashMap();
	static {
		nssMap.put("BONXML", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" 	);
		nssMap.put("BODY",		"urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" );
		nssMap.put("HE2E", 	"urn:CBI:xsd:CBIHdrSrv.001.07"                );
		nssMap.put("HTRT", 	"urn:CBI:xsd:CBIHdrTrt.001.07"                );
		nssMap.put("PMRQ", 	"urn:CBI:xsd:CBIPaymentRequest.00.03.00"      );
		nssMap.put("SGNT", 	"urn:CBI:xsd:CBISgnInf.001.04"                );
	}
	static NamespacesInfos nss = new NamespacesInfos(nssMap);
	
	public void _testXpaths() throws Exception {
		InputStream is = new FileInputStream(f);
		Document model = XomUtils.parse(is);
		IElementCursor elem = new ElementCursor(model.getRootElement() , nss);
		IElementCursor chld = elem.child("/BONXML:CBIPaymentRequestMsg[1]/BONXML:CBIHdrSrv[1]");
		System.out.println(chld);
		
	}
	
	public void testDescOrSelf() throws Exception {
		InputStream is = new FileInputStream(f);
		Document model = XomUtils.parse(is);
		IElementCursor rootelem = new ElementCursor(model.getRootElement() , nss);
		
		Iterator it = rootelem.iterator(IteratorsFactory.descendantOrSelf);
		while (it.hasNext()) {
			Object elem = it.next();
			String path = ((ElementCursor)elem).getIndexsPath();
			System.out.println(path);
		}
	}
}
