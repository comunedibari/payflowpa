/**
 * Created on 29/gen/09
 */
package it.nch.eb.flatx.xml.split;

import it.nch.TestProperties;
import it.nch.eb.flatx.flatmodel.sax.splitter.FragmentHandler;
import it.nch.eb.flatx.flatmodel.sax.splitter.XmlSplitSaxHandler;
import it.nch.eb.flatx.flatmodel.sax.splitter.XmlSplitSaxHandlerBuilder;
import it.nch.fwk.core.NamespacesInfos;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author gdefacci
 */
public class XmlSplitSaxHandlerTest {
	
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
		/*
		 * i namespaces sono usati sia nella generazione dell'xml, sia nel parsing delle xpath expressions
		 */
		NamespacesInfos nss = new NamespacesInfos( new String[][] { 
				{ "", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
				{ "BODY","urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" },
				{ "HE2E","urn:CBI:xsd:CBIHdrSrv.001.07"              },
				{ "HTRT","urn:CBI:xsd:CBIHdrTrt.001.07"              },
				{ "PMRQ","urn:CBI:xsd:CBIPaymentRequest.00.03.00"    },
				{ "SGNT","urn:CBI:xsd:CBISgnInf.001.04"              },	
			});
		XmlSplitSaxHandlerBuilder bldr = new XmlSplitSaxHandlerBuilder().usingNamespaces(nss);

		final String[] testaHldr = new String[1];
		final List/*<String>*/ paymentReqs = new ArrayList();
		
		
		bldr.add("CBIPaymentRequestMsg/CBIHdrTrt", 
			new FragmentHandler() {

				public void onFragment(String xmlFragment) {
					testaHldr[0]  = xmlFragment;
				}
				
			});
		
		bldr.add("/CBIPaymentRequestMsg/CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/ssdsdsd/ssdsd/BODY:CBIPaymentRequest", 
				new FragmentHandler() {

					public void onFragment(String xmlFragment) {
						paymentReqs.add(xmlFragment);
					}
					
				});
		
		File xmlFile = new File("D:/java/projects/cbi2-new/converter/resources/xml/CBIPaymentReq_00.03.02_SCT_05bdy-nTrx_05428.xml"); 
		
		new File( TestProperties.resoucesFolder, "samples/pmtreq00/TC15.xml");
		
		XmlSplitSaxHandler handler = bldr.create();
		
		saxParse(new FileReader(xmlFile), handler);
		
		System.out.println("testa");
		System.out.println(testaHldr[0]);
		
		System.out.println("payment requests");
		for (Iterator it = paymentReqs.iterator(); it.hasNext();) {
			System.out.println("-------------------------");
			String pmtreq = (String) it.next();
			System.out.println(pmtreq);
		}
		
		
	}

}
