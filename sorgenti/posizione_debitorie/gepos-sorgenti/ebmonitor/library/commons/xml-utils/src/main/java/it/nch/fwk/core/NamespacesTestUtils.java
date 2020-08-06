/**
 * Created on 06/set/07
 */
package it.nch.fwk.core;

import java.util.Iterator;

import junit.framework.Assert;

/**
 * @author gdefacci
 */
public class NamespacesTestUtils {

	public static void assertNamespacesEquals(NamespacesInfos expecetedNss, NamespacesInfos namespaces) {
		Assert.assertEquals(expecetedNss.size(), namespaces.size());
		
		for (Iterator it=namespaces.namespacesIterator();it.hasNext();) {
			NamespaceInfo ns = (NamespaceInfo) it.next();
			NamespaceInfo expecetedNs = expecetedNss.getNamespaceInfoByUri(ns.getUri());
			
			Assert.assertEquals(expecetedNs, ns);
		}
	}
}
