/**
 * Created on 18/set/2008
 */
package it.nch.streaming.services.test;

import it.nch.eb.common.utils.loaders.ClasspathLoader;
import it.nch.eb.common.utils.loaders.ReaderFactory;
import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.streaming.services.impl.TotalLineNumberPrevisitToFlatConversion;
import it.nch.streaming.services.impl.exp.PmtreqToFlatConversionServiceNew;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class PmtReqConversionServiceTest extends TestCase {
	
	final static File testsResourcesBase = new File("../../flowmanager/flowmanager-functional-tests/resources/ts");
	final static File expecttationsBase = new File( "../../flowmanager/flowmanager-functional-tests/resources/ts");
	
	final static NamespacesInfos nss = new NamespacesInfos( new String[][] { 
			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.05" },
			{ "BODY","urn:CBI:xsd:CBIBdyPaymentRequest.00.03.05" },
			{ "HE2E","urn:CBI:xsd:CBIHdrSrv.001.07"              },
			{ "HTRT","urn:CBI:xsd:CBIHdrTrt.001.07"              },
			{ "PMRQ","urn:CBI:xsd:CBIPaymentRequest.00.03.05"    },
			{ "SGNT","urn:CBI:xsd:CBISgnInf.001.04"              },	
		});
	
	private static final NamespacesInfos	nssPmtreq00	= new NamespacesInfos(new String[][] { 
			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
			{ "BODY", "urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" }, 
			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07" },
			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07" }, 
			{ "PMRQ", "urn:CBI:xsd:CBIPaymentRequest.00.03.00" },
			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04" }, 
	});
	
	public void testCnv1() throws Exception {
		
		ReadersFactories rff = new ReadersFactories();
		File baseFldr = new File(testsResourcesBase, "Disposizioni Pagamento XML/Dispositivi/PMTREC-UBI");
		ReaderFactory rf = rff.file(new File(baseFldr, "source-xml/TC-5.xml"));
		
		PmtreqToFlatConversionServiceNew cs = new PmtreqToFlatConversionServiceNew("\n", nss);
		TotalLineNumberPrevisitToFlatConversion conversion = new TotalLineNumberPrevisitToFlatConversion(cs);
		File expctd = new File(".", "resources/expected/pmtreq/e1.txt");
		File outFile = File.createTempFile("pmtreq-", "to-flat");
		Writer w = new FileWriter(outFile);
		conversion.convert(rf, w);
		
		assertTrue( ResourcesUtil.fileCompare(expctd, outFile) );
		
		w.close();
	}
	
	public void _testConvert1() throws Exception {
		PmtreqToFlatConversionServiceNew conversion = new PmtreqToFlatConversionServiceNew("\n", nssPmtreq00);
		 ClasspathLoader ldr = ResourceLoaders.CLASSPATH;
		Reader r = new InputStreamReader( ldr.loadInputStream("stp/pmtreq-1000.xml"));
		File expctd = new File(".", "resources/expected/pmtreq/e2.txt");
		File outFile = File.createTempFile("it.nch.converter", "pmtreqToFlat");
		Writer w = new FileWriter(outFile);
//		Writer w = new FileWriter(expctd);
		conversion.convert(r, w);
		
		boolean filesMatch = ResourcesUtil.fileCompare(expctd, outFile);
		if (!filesMatch) {
			System.out.println(expctd.getCanonicalPath());	
			System.out.println(outFile.getCanonicalPath());	
		}
			
		assertTrue( filesMatch );
		
		w.close();
	}

	
}
