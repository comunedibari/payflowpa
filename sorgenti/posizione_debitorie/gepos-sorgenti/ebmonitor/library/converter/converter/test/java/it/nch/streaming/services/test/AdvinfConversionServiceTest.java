/**
 * Created on 18/set/2008
 */
package it.nch.streaming.services.test;

import it.nch.eb.common.utils.loaders.FileSystemLoader;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.streaming.services.impl.BaseToFlatConversionService;
import it.nch.streaming.services.impl.exp.AdvinfToFlatConversionServiceNew;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class AdvinfConversionServiceTest extends TestCase {
	
	private static NamespacesInfos	queriesNss = new NamespacesInfos(new String[][] { 
			{ "MSG", 	"urn:CBI:xsd:CBIAdvInstrMsg.002.00"		},
			{ "BODY", "urn:CBI:xsd:CBIBdyAdvInstr.002.00"   },
			{ "HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07"        },
			{ "HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07"        },
			{ "ADIN", "urn:CBI:xsd:CBIAdvInstr.002.00"      },
			{ "SGNT", "urn:CBI:xsd:CBISgnInf.001.04"        }, 
		});
	
	public void testConvert1() throws Exception {
		BaseToFlatConversionService conversion = new AdvinfToFlatConversionServiceNew("\n", queriesNss);
		File basefolder = new File( "C:/Documents and Settings/Amministratore/Desktop/NCH dos/star-fuck/testing/Avvisatura Bonifico/Dispositivi/AVV-ISTR/" );
		FileSystemLoader fs = new FileSystemLoader(basefolder);
		Reader r = new InputStreamReader( fs.loadInputStream("source-xml/TC-1.xml"));
//		File outFile = new File("d:/temp/strxml/ser5");
		File outFile = new File("d:/temp/strxml/adv");
		Writer w = new FileWriter(outFile);
		conversion.convert(r, w);
		
		w.close();
	}
	
}
