/**
 * Created on 08/set/2008
 */
package it.nch.streaming.test;

import it.nch.eb.common.utils.loaders.FileSystemLoader;
import it.nch.eb.flatx.flatmodel.sax.ActivableLeafElementHandler;
import it.nch.eb.flatx.flatmodel.sax.SaxElementHandler;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.ChildAwareXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.fwk.core.NamespacesInfos;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import junit.framework.TestCase;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author gdefacci
 */
public class XPathTreeTest extends TestCase {
	
	
	NamespacesInfos	queriesNss	= new NamespacesInfos(new String[][] { 
			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
			{ "BODY", "urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" }, 
			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07" },
			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07" }, 
			{ "PMRQ", "urn:CBI:xsd:CBIPaymentRequest.00.03.00" },
			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04" }, 
	});
	
//	static RecordImplExp	r80	= new Record80();
//	static RecordImplExp	r70	= new Record70();
//	static RecordImplExp	r65	= new Record65();
//	static RecordImplExp	r50	= new Record50();
//	static RecordImplExp	r40	= new Record40();
//	static RecordImplExp	r30	= new Record30();
//	static RecordImplExp	r20	= new Record20();
//	static RecordImplExp	r01	= new Record01();
//	static RecordImplExp	r00	= new Record00();

//	OutputStream os;
//	{
//		try {
//			os = new FileOutputStream(new File("d:/temp/strxml/ser1"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
//	PrintStream printer = new PrintStream(os);
	
//	
//	XPathPosition posContainer = 
//		XPathsParser.instance.parseXPathPosition("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest");	
//	
//	
//	final long[] timeHolder = new long[] {
//		System.currentTimeMillis(),
//		0,
//	};
//	
//	private PrintStream log = TestDirector.sharedInstance.log;
//	
//	void trackTime() {
//		long now = System.currentTimeMillis();
//		long current = now - timeHolder[0];
//		long count = now - timeHolder[1];
//		count++;
//		timeHolder[0] = now;
//		timeHolder[1] = now - timeHolder[1];
//		log.print("-->(" + count + ")" + current + ".ms");
//	}
	
//	RecordModelLoaderBuilder modelLoaderBuilder = new RecordModelLoaderBuilder(posContainer, 
//			new ModelRecordModelLoaderCallback(PmtreqBody.class, "pmtrec-body", true));
	
//	RecordModelLoaderBuilder modelLoaderBuilder = new RecordModelLoaderBuilder(posContainer, 
//			new StreamModelLoaderCallback(os, PmtreqBody.class, "pmtrec-body"));
//	RecordModelLoaderBuilder modelLoaderBuilder = new RecordModelLoaderBuilder(posContainer,
//			new ModelRecordModelLoaderCallback(PmtreqBody.class, "pmtrec-body", new ModelPut() {
//
//				public void put(IBindingManager bindingManager, Object res) {
//					printer.print(res.toString());
//					trackTime();
//					log.print(" size " + bindingManager.names().size());
//				}
//				
//			}));
//	
//	{
//		modelLoaderBuilder.add(r00, Record00Model.class, "record00");
//		modelLoaderBuilder.add(r01, Record01Model.class, "record01");
//		modelLoaderBuilder.add(r20, Record20Model.class, "record20");
//		modelLoaderBuilder.add(r30, Record30Model.class, "record30");
//		modelLoaderBuilder.add(r40, Record40Model.class, "record40", true);
//		modelLoaderBuilder.add(r50, Record50Model.class, "record50");
//		modelLoaderBuilder.add(r65, Record65Model.class, "record60");
//		modelLoaderBuilder.add(r70, Record70Model.class, "record70");
//		modelLoaderBuilder.add(r80, Record80Model.class, "record80");
//	}
	
	public void testShowXPaths() throws Exception {
//		System.out.println("press a key");
//		System.in.read();
		
		long startTime = System.currentTimeMillis();
		System.out.println("starting time " + new Date(startTime));
		FileSystemLoader ldr = new FileSystemLoader(new File("."));
//		Reader r = new InputStreamReader( ldr.loadInputStream("test/resources/xml/large/pmtreq-666.xml"));
//		Reader r = new InputStreamReader( ldr.loadInputStream("test/resources/xml/large/pmtreq-80.xml"));
//		Reader r = new InputStreamReader( ldr.loadInputStream("test/resources/xml/large/pmtreq-4.xml"));
		Reader r = new InputStreamReader( ldr.loadInputStream("test/resources/xml/large/pmtreq-4.xml"));
//		Reader r = new InputStreamReader( ldr.loadInputStream("test/resources/stp/pmtreq-2000.xml") );

		ActivableLeafElementHandler hndlrProvider = new ActivableLeafElementHandler() {

			public boolean isActive() {
				return true;
			}

			public void text(BaseXPathPosition pos, String text) {
				System.out.println("[T]" + pos + " -- " +text);
			}

			public void endElement(XPathPosition pos) {
				System.out.println("[E]" + pos ) ;
			}

			public void startElement(XPathPosition pos) {
				System.out.println("[S]" + pos );
			}
			
		};
		//		ElementHandlerProvider hndlrProvider = modelLoaderBuilder.createElementHandlerProvider();
		SaxElementHandler dsh = new SaxElementHandler(hndlrProvider, queriesNss, new ChildAwareXPathBuilder());
		
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		xmlReader.setContentHandler(dsh);
		xmlReader.setErrorHandler(dsh);
		
		xmlReader.parse(new InputSource(r));
		
		long endTime = System.currentTimeMillis();
		long totalMillis = endTime - startTime;
		
		double secs = totalMillis / 1000;
		double mins = secs / 60;
		System.out.println("total time :" + totalMillis + ".ms");
		System.out.println("total time :" + secs + ".secs");
		System.out.println("total time :" + mins + ".mins");
		
		System.out.println("end time " + new Date(endTime));

	}


}
