/**
 * Created on 03/apr/07
 */
package it.nch.fwk.checks.util;

import it.nch.fwk.core.NamespaceInfo;
import it.nch.fwk.core.NamespacesInfos;

import java.io.File;


/**
 * @author gdefacci
 */
public interface Samples {
	
	public static final String JAXB_CONTEXT_PATH = "it.nch.cbi2.common.model:it.nch.cbi2.common.model.bonord:it.nch.cbi2.common.model.esord";
	
	public static class SamplesCommon {
		public static String fileName(String folder, String f) {
			return new File(folder,f).getAbsolutePath();
		}
	}
	
	public static class S1 {
		
		public static final String	FILENAME	= "C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/test/resources/xml/CBIEnvelBdyBonOrd.001.011.xml";
		public static final String	FILENAME1	= "C:/java/projects/CBI/cbi2-projects/cbi2-engine/src/test/resources/xml/CBIBonOrd_001.01.xml";
		
		public static final String	CBIENVELBDYBONORD_NAMESPACE_URI	= "urn:CBI:xsd:CBIEnvelBdyBonOrd.001.01";
		public static final String	CBISGNINF_NAMESPACE_URI			= "urn:CBI:xsd:CBISgnInf.001.03"; 
		public static final String	XSI_NAMESPACE_URI				= "http://www.w3.org/2001/XMLSchema-instance";
		
		
		public static final NamespacesInfos NAMESPACES = new NamespacesInfos()
					.add(new NamespaceInfo("p", CBIENVELBDYBONORD_NAMESPACE_URI ,"C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/main/resources/model/schema/bonord/CBIEnvelBdyBonOrd.001.01.xsd"))
					.add(new NamespaceInfo("p0", CBISGNINF_NAMESPACE_URI, "C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/main/resources/model/schema/CBISgnInf.001.03.xsd"))
					.add(new NamespaceInfo("xsi", XSI_NAMESPACE_URI));
		
	}
	
	public static class S2 {
		public static final String	XML_BOOKS_FILE	= "C:/java/jakarta/nux/samples/data/books.xml";
		
		public static final String	FILENAME	= "C:/java/projects/CBI/cbi2-projects/cbi2-engine/src/test/resources/xml/CBIBonOrd_001.test.xml";
		
		public static final NamespacesInfos NAMESPACES = new NamespacesInfos()
			.add( new NamespaceInfo("p",	"urn:CBI:xsd:CBIBonOrd.001.04" 				, "C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/main/resources/model/schema/bonord/CBIBonOrd.001.04.xsd" ))
			.add( new NamespaceInfo("p0",	"urn:CBI:xsd:CBIHdrTrt.001.06"              , "C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/main/resources/model/schema/CBIHdrTrt.001.06.xsd" ))
			.add( new NamespaceInfo("p1",	"urn:CBI:xsd:CBIHdrSrv.001.06"              , "C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/main/resources/model/schema/CBIHdrSrv.001.06.xsd" ))
			.add( new NamespaceInfo("p2",	"urn:CBI:xsd:CBIEnvelBdyBonOrd.001.01"      , "C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/main/resources/model/schema/bonord/CBIEnvelBdyBonOrd.001.01.xsd" ))
			.add( new NamespaceInfo("p3",	"urn:CBI:xsd:CBISgnInf.001.03"              , "C:/java/projects/CBI/cbi2-projects/cbi2-data-model/src/main/resources/model/schema/CBISgnInf.001.03.xsd" ))
			.add( new NamespaceInfo("xsi",	"http://www.w3.org/2001/XMLSchema-instance"  ) );
	}
	
	public static class S3 {
		
		private static final String	XML_INPUT_FOLDER	= "D:/java/projects/NCH-Frameworks/xsd-validation/resources/xmls";

		public static String fileName(String folder, String f) {
			return new File(folder,f).getAbsolutePath();
		}
		
		public static final String	XSD_ROOT_FOLDER	= "D:/java/projects/NCH-CBI2/cbi2-data-model/src/main/resources/model/schema";

		public static final String	V1_XSD	= fileName(XSD_ROOT_FOLDER ,"bonord/CBIBonOrd.001.04.xsd" );

		public static final String	V1_FILENAME	= fileName(XML_INPUT_FOLDER , "v1-1.xml");
		
		public static final String	XSD_LOCATION = fileName(XSD_ROOT_FOLDER ,"bonord/CBIBonOrd.001.04.xsd");
		
		public static final NamespacesInfos NAMESPACES = new NamespacesInfos()
			.add( new NamespaceInfo("p",	"urn:CBI:xsd:CBIBonOrd.001.04" 				, V1_XSD))
			.add( new NamespaceInfo("HTRT",	"urn:CBI:xsd:CBIHdrTrt.001.06"              , fileName(XSD_ROOT_FOLDER , "CBIHdrTrt.001.06.xsd" )))
			.add( new NamespaceInfo("HE2E",	"urn:CBI:xsd:CBIHdrSrv.001.06"              , fileName(XSD_ROOT_FOLDER , "CBIHdrSrv.001.06.xsd" )))
			.add( new NamespaceInfo("BODY",	"urn:CBI:xsd:CBIEnvelBdyBonOrd.001.01"      , fileName(XSD_ROOT_FOLDER , "bonord/CBIEnvelBdyBonOrd.001.01.xsd" )))
			.add( new NamespaceInfo("SGNT",	"urn:CBI:xsd:CBISgnInf.001.03"              , fileName(XSD_ROOT_FOLDER , "CBISgnInf.001.03.xsd" )))
			.add( new NamespaceInfo("xsi",	"http://www.w3.org/2001/XMLSchema-instance"  ) );
		
		public static final NamespacesInfos NAMESPACES1 = new NamespacesInfos()
			.add( new NamespaceInfo("p",  "urn:CBI:xsd:CBIBonOrd.001.04" 					, fileName(XSD_ROOT_FOLDER , "bonord/CBIBonOrd.001.04.xsd")					))
			.add( new NamespaceInfo("p0", "urn:CBI:xsd:CBIHdrTrt.001.06" 					, fileName(XSD_ROOT_FOLDER , "CBIHdrTrt.001.06.xsd")                ))
			.add( new NamespaceInfo("p1", "urn:CBI:xsd:CBIHdrSrv.001.06" 					, fileName(XSD_ROOT_FOLDER , "CBIHdrSrv.001.06.xsd")                ))
			.add( new NamespaceInfo("p2", "urn:CBI:xsd:CBIEnvelBdyBonOrd.001.01" 			, fileName(XSD_ROOT_FOLDER , "bonord/CBIEnvelBdyBonOrd.001.01.xsd") ))
			.add( new NamespaceInfo("p3", "urn:CBI:xsd:CBISgnInf.001.03" 					, fileName(XSD_ROOT_FOLDER , "CBISgnInf.001.03.xsd")                ))
			.add( new NamespaceInfo("xsi","http://www.w3.org/2001/XMLSchema-instance" ));
	}
	
	public static class AdviceSamples extends SamplesCommon {
		public static final String			INPUT_DIR	= "D:/java/projects/NCH-CBI2/cbi-common-cbi2engine/src/main/resources/model/cbiadvice";

		public static final NamespacesInfos	NAMESPACES	= new NamespacesInfos()
			.add(new NamespaceInfo("root" , "urn:CBI:xsd:CBIAdv.001.00" , fileName(INPUT_DIR, "CBIAdv.001.01.xsd")))
			.add(new NamespaceInfo("BODY", "urn:CBI:xsd:CBIBdySrvAdv.001.00", 	fileName(INPUT_DIR, "CBIBdyAvvIstr.001.01.xsd")))
			.add(new NamespaceInfo("HE2E", "urn:CBI:xsd:CBIHdrSrv.001.07", 		fileName(INPUT_DIR, "CBIHdrSrv.001.07.xsd")))
			.add(new NamespaceInfo("HTRT", "urn:CBI:xsd:CBIHdrTrt.001.07", 		fileName(INPUT_DIR, "CBIHdrTrt.001.07.xsd")))
			.add(new NamespaceInfo("xsi", "http://www.w3.org/2001/XMLSchema-instance"));

	}
	/**
	 * d xmlns="urn:CBI:xsd:CBIBonOrd.001.04" 
	 * BODY="urn:CBI:xsd:CBIEnvelBdyBonOrd.001.01" 
	 * HE2E="urn:CBI:xsd:CBIHdrSrv.001.06" 
	 * HTRT="urn:CBI:xsd:CBIHdrTrt.001.06" 
	 * SGNT="urn:CBI:xsd:CBISgnInf.001.03" 
	 * xsi="http://www.w3.org/2001/XMLSchema-instance" 
	 * 
	 * 
"p", "urn:CBI:xsd:CBIBonOrd.001.04" ,
"p0","urn:CBI:xsd:CBIHdrTrt.001.06" ,
"p1","urn:CBI:xsd:CBIHdrSrv.001.06" ,
"p2","urn:CBI:xsd:CBIEnvelBdyBonOrd.001.01", 
"p3","urn:CBI:xsd:CBISgnInf.001.03" ,
"xsi","http://www.w3.org/2001/XMLSchema-instance", 
	 * 
	 */

}

