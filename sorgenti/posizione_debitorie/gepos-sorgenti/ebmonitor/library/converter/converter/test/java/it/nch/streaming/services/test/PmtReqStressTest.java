/**
 * Created on 18/set/2008
 */
package it.nch.streaming.services.test;

import it.nch.eb.common.utils.loaders.ClasspathLoader;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.streaming.services.impl.exp.PmtreqToFlatConversionServiceNew;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import junit.framework.TestCase;


/**
 * avviare con >= jdk1.6 (la jdk6 serve solo a scopo di onitoring) includendo la jvm option:
 * 
 * -Dcom.sun.management.jmxremote
 * 
 * per abilitare monitoring con visualvm o altro jmx-based profiler
 * @author gdefacci
 */
public class PmtReqStressTest extends TestCase {
	
	final static File testsResourcesBase = new File("../../flowmanager/flowmanager-functional-tests/resources/ts");
	final static File expecttationsBase = new File( "../../flowmanager/flowmanager-functional-tests/resources/ts");
	
	private static final NamespacesInfos	nssPmtreq00	= new NamespacesInfos(new String[][] { 
			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
			{ "BODY", "urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" }, 
			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07" },
			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07" }, 
			{ "PMRQ", "urn:CBI:xsd:CBIPaymentRequest.00.03.00" },
			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04" }, 
	});
	
	public void testStressConvert1() throws Exception {
		PmtreqToFlatConversionServiceNew conversion = new PmtreqToFlatConversionServiceNew("\n", nssPmtreq00);
		ClasspathLoader ldr = ResourceLoaders.CLASSPATH;
		Reader r = new InputStreamReader(ldr.loadInputStream("stp/pmtreq-5000.xml"));
		File outFile = File.createTempFile("it.nch.converter", "pmtreqToFlat");
		Writer w = new FileWriter(outFile);
		System.out.print("press a key");
		System.in.read();
		
		conversion.convert(r, w);
		
//		assertTrue( ResourcesUtil.fileCompare(expctd, outFile) );
		
		w.close();
	}
	
}
