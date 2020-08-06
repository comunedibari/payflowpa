package it.nch.eb.flatx.flatmodel.sax;

import it.nch.fwk.core.NamespaceInfo;
import it.nch.fwk.core.NamespacesInfos;

import org.xml.sax.helpers.DefaultHandler;

/**
 * @author gdefacci
 */
public class PrefixProviderSaxHandler extends DefaultHandler {
	
	private NamespacesInfos nss;
	
	public PrefixProviderSaxHandler(NamespacesInfos nss) {
		super();
		this.nss = nss;
	}

	protected String getPrefix(String uri) {
		if (uri == null) throw new NullPointerException("uri");
		if (nss ==null) return null;
		NamespaceInfo namespaceInfo = uri.equals("") ? nss.getNamespaceInfoByPrefix("") : nss.getNamespaceInfoByUri(uri);
		if (namespaceInfo == null) {
			throw new IllegalStateException("cant find a prefix for namespace uri " + uri + " in prefixes map " + this.nss);
		}
		String res = namespaceInfo.getPrefix();
		if (res == null) throw new IllegalStateException("cant find a prefix for namespace uri " + uri + " in prefixes map " + this.nss);
		if (res.trim().length() == 0) return null;
		else return res;
	}

}
