/**
 * Created on 06/mar/2009
 */
package it.nch.streaming.services.test;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.common.utils.loaders.ReaderFactory;
import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.streaming.services.impl.PrevisitToFlatConversion;
import it.nch.streaming.services.impl.TotalLineNumberPrevisitToFlatConversion;
import it.nch.streaming.services.impl.exp.UbiPmtreqToFlatConversionServiceNew;
import it.nch.testools.FileCompareEffect;
import it.nch.testools.FilecompareEffects;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class UbiFiPmtreqTest extends TestCase {
	
	private static final FileCompareEffect fileComparator = FilecompareEffects.filesMatchOrCompare;

	final static File testsResourcesBase = new File("../../flowmanager/flowmanager-functional-tests/resources/ts");
		
	final static NamespacesInfos nss = new NamespacesInfos( new String[][] { 
			{ "MSG", "urn:CBI:xsd:CBIPaymentRequestMsg.00.03.05" },
			{ "BODY","urn:CBI:xsd:CBIBdyPaymentRequest.00.03.05" },
			{ "HE2E","urn:CBI:xsd:CBIHdrSrv.001.07"              },
			{ "HTRT","urn:CBI:xsd:CBIHdrTrt.001.07"              },
			{ "PMRQ","urn:CBI:xsd:CBIPaymentRequest.00.03.05"    },
			{ "SGNT","urn:CBI:xsd:CBISgnInf.001.04"              },	
		});
	
	public void testPmtreqUbiIbanMap() throws Exception {

		ReadersFactories rff = new ReadersFactories();
		ReaderFactory rf = rff.classpath("pmtreq/TC-1-iban-map.xml");

		UbiPmtreqToFlatConversionServiceNew cs = new UbiPmtreqToFlatConversionServiceNew("\n", nss);
		TotalLineNumberPrevisitToFlatConversion cs1 = new TotalLineNumberPrevisitToFlatConversion(cs);

		System.out.println(cs.getParserFactory().createBodySequenceParser());
		File outFile = File.createTempFile("pmtreq-", "to-flat");
		Writer w = new FileWriter(outFile);

		Properties props = ResourcesUtil.CLASSPATH.load("it/nch/cucsiamap.properties");
		Map map = new HashMap();
		
		map.put(ConversionsConsts.CUC_SIA_MAP, props);
		Map ibanMap = new HashMap();
		ibanMap.put("IT60V0552649430000000000257", "ZZPIPPO");
		ibanMap.put("IT69F0542811164000000080630", "IT69F0350011164000000080630");
		
		Map instIdMap = new HashMap();
		Map cucLogCucMap = new HashMap();
		
		map.put(ConversionsConsts.CUC_CUC_LOGIC_DEST_MAP, cucLogCucMap);
		map.put(ConversionsConsts.INST_ID_MAP, instIdMap);
		map.put(ConversionsConsts.IBAN_MAP, ibanMap);
		
		PrevisitToFlatConversion realCnv = cs1.create(map);

		realCnv.convert(rf, w);
		File expctd = new File("resources/pmtreq/TC-1-iban-map-decode.xml.txt");
		fileComparator.compare(expctd, outFile);

		// assertTrue( ResourcesUtil.fileCompare(expctd, outFile) );
		// w.flush();
		// w.close();
	}
	

	public void testPmtreqUbiBretellaggioMap() throws Exception {

		ReadersFactories rff = new ReadersFactories();
		ReaderFactory rf = rff.classpath("pmtreq/TC-1-iban-map.xml");

		UbiPmtreqToFlatConversionServiceNew cs = new UbiPmtreqToFlatConversionServiceNew("\n", nss);
		TotalLineNumberPrevisitToFlatConversion cs1 = new TotalLineNumberPrevisitToFlatConversion(cs);

		System.out.println(cs.getParserFactory().createBodySequenceParser());
		File outFile = File.createTempFile("pmtreq-", "to-flat");
		Writer w = new FileWriter(outFile);

		Properties props = ResourcesUtil.CLASSPATH.load("it/nch/cucsiamap.properties"); 
		
		Map map = new HashMap();
		
		map.put(ConversionsConsts.CUC_SIA_MAP, props);
		Map ibanMap = new HashMap();
		ibanMap.put("IT60V0552649430000000000257", "ZZPIPPO");
		ibanMap.put("IT69F0542811164000000080630", "IT69F0350011164000000080630");
		map.put(ConversionsConsts.IBAN_MAP, ibanMap);
		
		
		Map instIdMap = ResourcesUtil.CLASSPATH.load("it/nch/bretellaggiomap.properties");
		Map cucLogCucMap = ResourcesUtil.CLASSPATH.load("it/nch/bretellaggiomap.properties");;
		
		map.put(ConversionsConsts.CUC_CUC_LOGIC_DEST_MAP, cucLogCucMap);
		map.put(ConversionsConsts.INST_ID_MAP, instIdMap);
		
		PrevisitToFlatConversion realCnv = cs1.create(map);

		realCnv.convert(rf, w);
		File expctd = new File("resources/pmtreq/TC-1-iban-map.xml.txt");
		fileComparator.compare(expctd, outFile);

		// assertTrue( ResourcesUtil.fileCompare(expctd, outFile) );
		// w.flush();
		// w.close();
	}
}
