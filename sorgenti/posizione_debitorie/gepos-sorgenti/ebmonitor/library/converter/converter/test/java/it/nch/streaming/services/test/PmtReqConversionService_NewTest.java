/**
 * Created on 18/set/2008
 */
package it.nch.streaming.services.test;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.common.utils.loaders.ReaderFactory;
import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BaseParser;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.streaming.services.impl.PrevisitToFlatConversion;
import it.nch.streaming.services.impl.TotalLineNumberPrevisitToFlatConversion;
import it.nch.streaming.services.impl.exp.AdvinfToFlatConversionServiceNew;
import it.nch.streaming.services.impl.exp.PmtreqToFlatConversionServiceNew;
import it.nch.streaming.services.impl.exp.UbiPmtreqToFlatConversionServiceNew;
import it.nch.testools.FileCompareEffect;
import it.nch.testools.FilecompareEffects;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class PmtReqConversionService_NewTest extends TestCase {
	
	final static File testsResourcesBase = new File("../../flowmanager/flowmanager-functional-tests/resources/ts");
	final static File expecttationsBase = new File( "../../flowmanager/flowmanager-functional-tests/resources/ts");
	
	final static FileCompareEffect comparatorEfct = FilecompareEffects.filesMatchOrCompare;
	
	final static NamespacesInfos nss = new NamespacesInfos( new String[][] { 
			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.05" },
			{ "BODY","urn:CBI:xsd:CBIBdyPaymentRequest.00.03.05" },
			{ "HE2E","urn:CBI:xsd:CBIHdrSrv.001.07"              },
			{ "HTRT","urn:CBI:xsd:CBIHdrTrt.001.07"              },
			{ "PMRQ","urn:CBI:xsd:CBIPaymentRequest.00.03.05"    },
			{ "SGNT","urn:CBI:xsd:CBISgnInf.001.04"              },	
		});
	
	private static final NamespacesInfos	advinfNss	= new NamespacesInfos(new String[][] {
			{ "MSG", "urn:CBI:xsd:CBIAdvInstrMsg.002.04"  },
			{ "BODY", "urn:CBI:xsd:CBIBdyAdvInstr.002.04" },
			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07"      },
			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07"      },
			{ "ADIN", "urn:CBI:xsd:CBIAdvInstr.002.04"    },
			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04"      },

	});
	
//	private static final NamespacesInfos	nssPmtreq00	= new NamespacesInfos(new String[][] { 
//			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.00" },
//			{ "BODY", "urn:CBI:xsd:CBIBdyPaymentRequest.00.03.00" }, 
//			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07" },
//			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07" }, 
//			{ "PMRQ", "urn:CBI:xsd:CBIPaymentRequest.00.03.00" },
//			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04" }, 
//	});
	
	
	
	public void _testFinfBaseXPath() throws Exception {
		PmtreqParsersFactory pf = new PmtreqParsersFactory();
		XPathPosition bp = BaseParser.findBaseXPath(pf.createBodyParser());
		XPathPosition bpb = BaseParser.findBaseXPath(pf.createBodyItemParser());
		System.out.println(bp);
		System.out.println(bpb);
		assertEquals("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest", bp.toString());
		assertEquals("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf", bpb.toString());
		
	}
	
	public void _testAdvinf() throws Exception {
		ReadersFactories rff = new ReadersFactories();
		File baseFldr = new File(testsResourcesBase, "Avvisatura Bonifico/Dispositivi/AVV-ISTR");
		ReaderFactory rf = rff.file(new File(baseFldr, "source-xml/TC-1.xml"));
		
		AdvinfToFlatConversionServiceNew cs = new AdvinfToFlatConversionServiceNew("\n", advinfNss);
		TotalLineNumberPrevisitToFlatConversion cs1 = new TotalLineNumberPrevisitToFlatConversion(cs);

		File outFile = File.createTempFile("pmtreq-", "to-flat");
		Writer w = new FileWriter(outFile);
		cs1.convert(rf, w);
		File expctd = new File(baseFldr, "target-flat/TC-1.xml.txt");
		comparatorEfct.compare(expctd, outFile);
		
	}
	
	public void _testCnv1() throws Exception {
		
		ReadersFactories rff = new ReadersFactories();
		File baseFldr = new File(testsResourcesBase, "Disposizioni Pagamento XML/Dispositivi/PMTREC-BT");
		ReaderFactory rf = rff.file(new File(baseFldr, "source-xml/TC3.xml"));
		
		PmtreqToFlatConversionServiceNew cs = new PmtreqToFlatConversionServiceNew("\n", nss);
		TotalLineNumberPrevisitToFlatConversion cs1 = new TotalLineNumberPrevisitToFlatConversion(cs);

		File outFile = File.createTempFile("pmtreq-", "to-flat");
		Writer w = new FileWriter(outFile);
		cs1.convert(rf, w);
		File expctd = new File(baseFldr, "target-flat-v/TC3.xml.txt");
		comparatorEfct.compare(expctd, outFile);
		
		
//		assertTrue( ResourcesUtil.fileCompare(expctd, outFile) );
//		w.flush();
//		w.close();
	}
	
	public void _testCnv2() throws Exception {
		
		ReadersFactories rff = new ReadersFactories();
		File baseFldr = new File(testsResourcesBase, "Disposizioni Pagamento XML/Dispositivi/PMTREC-UBI");
		ReaderFactory rf = rff.file(new File(baseFldr, "source-xml/TC-3.xml"));
		
		UbiPmtreqToFlatConversionServiceNew cs = new UbiPmtreqToFlatConversionServiceNew("\n", nss);
		TotalLineNumberPrevisitToFlatConversion cs1 = new TotalLineNumberPrevisitToFlatConversion(cs);

		System.out.println( cs.getParserFactory().createBodySequenceParser() );
		File outFile = File.createTempFile("pmtreq-", "to-flat");
		Writer w = new FileWriter(outFile);
		
		Properties props = ResourcesUtil.CLASSPATH.load("it/nch/cucsiamap.properties");
		Map map = new HashMap();
		map.put(ConversionsConsts.CUC_SIA_MAP, props);
		PrevisitToFlatConversion realCnv = cs1.create(map);
		
		realCnv.convert(rf, w);
		File expctd = new File(baseFldr, "target-flat/TC-3.xml.txt");
		comparatorEfct.compare(expctd, outFile);
		
	}

	
	public void testRepeCnv1() throws Exception {
		
		ReaderFactory rf = ResourceLoaders.CLASSPATH.readerFactory("repe/SEPACONCVS.txt.xml");
		
		PmtreqToFlatConversionServiceNew cs = new PmtreqToFlatConversionServiceNew("\n", nss);
		TotalLineNumberPrevisitToFlatConversion cs1 = new TotalLineNumberPrevisitToFlatConversion(cs);

		Writer w = new PrintWriter(System.out);
//		Writer w = new FileWriter(new File("d:/temp/cnv1.txt"));
		cs1.convert(rf, w);
		
		System.out.println("\n\n\n");
		
//		rf = ResourceLoaders.CLASSPATH.readerFactory("repe/sepacvsminimali.txt.xml");

//		cs1.convert(rf, w);
	}
}
