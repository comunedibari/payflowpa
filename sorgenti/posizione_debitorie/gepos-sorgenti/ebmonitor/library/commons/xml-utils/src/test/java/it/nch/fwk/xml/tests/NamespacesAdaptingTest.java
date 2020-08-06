/**
 * Created on 06/set/07
 */
package it.nch.fwk.xml.tests;

import it.nch.fwk.core.NamespaceInfo;
import it.nch.fwk.core.NamespacesInfos;
import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class NamespacesAdaptingTest extends TestCase {
	
	NamespacesInfos nss1 = new NamespacesInfos()
		.add(new NamespaceInfo("p","urn:CBI:xsd:CBIBdyAdvInstr.002.00"))
		.add(new NamespaceInfo("p0","urn:CBI:xsd:CBISgnInf.001.04"))
		.add(new NamespaceInfo("p1","urn:CBI:xsd:CBIAdvInstr.002.00"))
		.add(new NamespaceInfo("xsi","http://www.w3.org/2001/XMLSchema-instance"))
		.add(new NamespaceInfo("p2", "urn:CBI:xsd:CBIAdvInstrMsg.002.00"))
		.add(new NamespaceInfo("","urn:CBI:xsd:CBIHdrSrv.001.08"));
	
	NamespacesInfos nss2 = new NamespacesInfos()
		.add(new NamespaceInfo("","urn:CBI:xsd:CBIAdvInstrMsg.002.00")) 
		.add(new NamespaceInfo("ADIN","urn:CBI:xsd:CBIAdvInstr.002.00")) 
		.add(new NamespaceInfo("BODY","urn:CBI:xsd:CBIBdyAdvInstr.002.00"))
		.add(new NamespaceInfo("HE2E","urn:CBI:xsd:CBIHdrSrv.001.08"))
		.add(new NamespaceInfo("HTRT","urn:CBI:xsd:CBIHdrTrt.001.08"))
		.add(new NamespaceInfo("SGNT","urn:CBI:xsd:CBISgnInf.001.04"))
		.add(new NamespaceInfo("xs", "http://www.w3.org/2001/XMLSchema-instance"));
	
	public void testAdapt1() throws Exception {
		String path1 = "p:elem1/p0:elem2";
		String path2 = nss2.adaptXQueryFromNamespace(path1, nss1);
		
		System.out.println(path2);
		
		assertEquals("BODY:elem1/SGNT:elem2", path2);
	}
	
	public void testAdapt2() throws Exception {
		String path1 = "/p2:elem1/xsi:elem2";
		String path2 = nss2.adaptXQueryFromNamespace(path1, nss1);
		
		System.out.println(path2);
		
		assertEquals("/elem1/xs:elem2", path2);
	}
	
	public void testAdapt3() throws Exception {
		String path1 = "/p2:elem1[0]/elem2";
		String path2 = nss2.adaptXQueryFromNamespace(path1, nss1);
		
		System.out.println(path2);
		
		assertEquals("/elem1[0]/HE2E:elem2", path2);
	}

}
