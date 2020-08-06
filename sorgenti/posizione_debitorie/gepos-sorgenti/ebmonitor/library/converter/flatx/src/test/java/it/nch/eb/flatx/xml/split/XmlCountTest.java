/**
 * Created on 29/gen/09
 */
package it.nch.eb.flatx.xml.split;

import it.nch.eb.flatx.flatmodel.sax.splitter.FragmentHandler;
import it.nch.eb.flatx.flatmodel.sax.splitter.GetContentTextCollectorEffectFactory;
import it.nch.eb.flatx.flatmodel.sax.splitter.XmlSplitSaxHandlerBuilder;
import it.nch.fwk.core.NamespacesInfos;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author gdefacci
 */
public class XmlCountTest {
	
	protected static void saxParse(Reader reader, DefaultHandler dsh) {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(dsh);
			xmlReader.setErrorHandler(dsh);
			
			xmlReader.parse(new InputSource(reader));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static void main(String[] args) throws Exception {
		
		NamespacesInfos nss = new NamespacesInfos( new String[][] { 
				{ "", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.05" },
				{ "BODY","urn:CBI:xsd:CBIBdyPaymentRequest.00.03.05" },
				{ "HE2E","urn:CBI:xsd:CBIHdrSrv.001.07"              },
				{ "HTRT","urn:CBI:xsd:CBIHdrTrt.001.07"              },
				{ "PMRQ","urn:CBI:xsd:CBIPaymentRequest.00.03.05"    },
				{ "SGNT","urn:CBI:xsd:CBISgnInf.001.04"              },	
			});
		XmlSplitSaxHandlerBuilder bldr = new XmlSplitSaxHandlerBuilder().usingNamespaces(nss);

		final String[] recvTypHolder = new String[1];
		final String[] recvTypHolder1 = new String[1];
		final int[] countHolder = new int[1];
		countHolder[0] = 0;
		
		bldr.add("/CBIPaymentRequestMsg/CBIHdrSrv/HE2E:Receiver/HE2E:RecvTyp", 
				new GetContentTextCollectorEffectFactory(new FragmentHandler() {

					public void onFragment(String xmlFragment) {
						recvTypHolder[0] = xmlFragment;
					}
					
				})); 
		
		
		bldr.add("/CBIPaymentRequestMsg/CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", 
				new FragmentHandler() {

					public void onFragment(String xmlFragment) {
						countHolder[0] = countHolder[0] + 1 ;
					}
					
				});
		
		File xmlFile = new File("D:/java/projects/flowmanager-svn/library/flowmanager/flowmanager-functional-tests/resources/samples/pmtreq05/TC15-H.xml"); 
		
		DefaultHandler handler = bldr.create();
		
		saxParse(new FileReader(xmlFile), handler);
		
		System.out.println(recvTypHolder1[0]);
		System.out.println(recvTypHolder[0]);
		System.out.println(countHolder[0]);
		
		
	}

}
