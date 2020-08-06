/**
 * Created on 03/ago/07
 */
package it.nch.fwk.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

/**
 * @author gdefacci
 */
public class NamespacesInfos implements Serializable {
	
	private static final long	serialVersionUID	= 5524093495410498141L;
	
	public static final String PATH_SEPARATOR	= "/";
	public static final String NS_SEPARATOR = ":";
	
	private Set/*<NamespaceInfo>*/	nameSpacesList;
	
	public NamespacesInfos() {
		nameSpacesList	= new TreeSet();
	}
	
	public NamespacesInfos(Map/*<String, String>*/ prefixesUrisMap) {
		this();
		if (prefixesUrisMap!=null && prefixesUrisMap.keySet().size()>0) {
			for (Iterator it = prefixesUrisMap.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				String prefix = (String) entry.getKey();
				String uri = (String) entry.getValue(); // prefixesUrisMap.get(prefix);
				add(prefix, uri);
			}
		}
	}
	
	public NamespacesInfos(String[][] prefixesUris) {
		this();
		for (int i = 0; i < prefixesUris.length; i++) {
			String[] strPair = prefixesUris[i];
			if (strPair.length==2)  {
				add(strPair[0], strPair[1]);
			} else {
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < strPair.length; j++) {
					sb.append(strPair[j] + ", ");
				}
				throw new IllegalArgumentException("not a valid prefix uri pair" + sb.toString());
			}
		}
	}
	
	public NamespacesInfos(Set/*<NamespaceInfo>*/ nameSpacesList) {
		this.nameSpacesList = nameSpacesList;
	}
	
	public NamespacesInfos(NamespacesInfos infoToCopy) {
		this.nameSpacesList = infoToCopy.cloneInfos().nameSpacesList;
	}

	public NamespacesInfos add(NamespaceInfo nameSpaceInfo) {
		this.nameSpacesList.add(nameSpaceInfo);
		return this;
	}

	public NamespacesInfos add(String prefix, String uri, String location) {
		NamespaceInfo nameSpaceInfo = new NamespaceInfo(prefix, uri, location);
		return add(nameSpaceInfo);
	}

	public NamespacesInfos addSchemaLocation(String uri, String location) {
		NamespaceInfo nameSpaceInfo = new NamespaceInfo("", uri, location);
		return add(nameSpaceInfo);
	}

	public NamespacesInfos add(String prefix, String uri) {
		NamespaceInfo nameSpaceInfo = new NamespaceInfo(prefix, uri, null);
		return add(nameSpaceInfo);
	}

	public NamespacesInfos addAll(NamespacesInfos infos) {
		for (Iterator it = infos.nameSpacesList.iterator(); it.hasNext();) {
			NamespaceInfo element = (NamespaceInfo) it.next();
			add(element);
		}
		return this;
	}

	public Map/*<String,String>*/ getPrefixesMap() {
		Map res = new TreeMap();
		for (Iterator it = this.nameSpacesList.iterator(); it.hasNext();) {
			NamespaceInfo info = (NamespaceInfo) it.next();
			res.put(info.prefix, info.uri);
		}
		return res;
	}

	public Map/*<String,String>*/ getSchemaLocationsMap() {
		Map res = new TreeMap();
		for (Iterator it = this.nameSpacesList.iterator(); it.hasNext();) {
			NamespaceInfo info = (NamespaceInfo) it.next();
			if (info.location!=null) {
				res.put(info.location, info.uri);
			}
		}
		return res;
	}

	public String adaptXQueryFromNamespace(String xquery, NamespacesInfos xqueryNamespace) {
		boolean isAbsolute = false;
//		isAbsolute = isAbsolutePath(xquery);
		
		String[] parts = xquery.split(PATH_SEPARATOR);
		int startIdx = 0;
		if (parts.length>0)
			if ("".equals(parts[0].trim())) {
				isAbsolute = true;
				startIdx = 1;
			}
		StringBuffer sb = null;
		for (int i = startIdx; i < parts.length; i++) {
			String part = parts[i];
			if (!(part.indexOf("::")>0)) {
				int colonIdx = part.indexOf(NS_SEPARATOR);
				String prefix = null;
				String localName = null;
				if (colonIdx<1) {
	//				unqualified name
					prefix = "";
					if (part.startsWith(":")) localName = part.substring(1);
					else localName = part;
				} else {
	//				qualified name
					prefix = part.substring(0, colonIdx);
					localName = part.substring(colonIdx+1, part.length());
				}
				
				String targetPrefix = transcode(prefix, xqueryNamespace, this);
				if (targetPrefix==null) {
					NamespaceInfo srcNS = xqueryNamespace.getNamespaceInfoByPrefix(prefix);
					throw new IllegalArgumentException("cant adapt the query [" + xquery + "] cant transcode the prefix [" + prefix
							+ "]uri[" + (srcNS==null?"":srcNS.getUri()) + "]" 
							+ "\nfrom-namespaces:" + xqueryNamespace
							+ "\nto namespace:" + this);
				}
				
				if ("".equals(targetPrefix)) {
					if (sb==null) {
						sb = new StringBuffer();
						sb.append(localName);
					} else {
						sb.append(PATH_SEPARATOR + localName);
					}
				} else {
					if (sb==null) {
						sb = new StringBuffer();
						sb.append(targetPrefix + NS_SEPARATOR + localName);
					} else {
						sb.append(PATH_SEPARATOR + targetPrefix + NS_SEPARATOR + localName);
					}
				}
//				if (sb==null) {
//					sb = new StringBuffer();
//					sb.append(targetPrefix + NS_SEPARATOR + localName);
//				} else {
//					sb.append(PATH_SEPARATOR + targetPrefix + NS_SEPARATOR + localName);
//				}
			} else {
//				we leave the :: path segments inaltered
				if (sb==null) sb = new StringBuffer();
				sb.append(part);
			}
		}
		
		if (isAbsolute) sb.insert(0,PATH_SEPARATOR);
		return sb.toString();
	}
	
	private String transcode(String prefix, NamespacesInfos from, NamespacesInfos to) {
		NamespaceInfo sourceNs = from.getNamespaceInfoByPrefix(prefix);
		if (sourceNs==null) {
			return null;
		}
		NamespaceInfo targetNS = to.getNamespaceInfoByUri(sourceNs.getUri());
		if (targetNS==null) {
			return null;
		} 
		return targetNS.getPrefix(); 
	}
	
	/**
	 * NameSpaceInfos original = new NameSpaceInfos().add("p", "www.myxsd.net");
	 * Map mapping = new HashMap();
	 * mapping.put("p","p1");		
	 * NameSpaceInfos clonedUsingP1 = original.duplicateUsingPrefixes(mapping);
	 * @param prefixesMapping
	 * @return
	 */
	public NamespacesInfos duplicateUsingPrefixes(Map prefixesMapping) {
		NamespacesInfos res = new NamespacesInfos();
		for (Iterator it = this.nameSpacesList.iterator(); it.hasNext();) {
			NamespaceInfo namespace = (NamespaceInfo) it.next();
			String prefix = (String) prefixesMapping.get(namespace.prefix);
			if (prefix==null) prefix = namespace.prefix;
			NamespaceInfo newNamespace = new NamespaceInfo(prefix, namespace.uri, namespace.location);
			res.add(newNamespace);
		}
		return res;
	}

	public NamespacesInfos duplicateUsingPrefixes(String mappings[][]) {
		Map mappingsMap = new HashMap();
		for (int i = 0; i < mappings.length; i++) {
			String key = mappings[i][0];
			String entry = mappings[i][1];
			mappingsMap.put(key, entry);
		}
		return duplicateUsingPrefixes(mappingsMap);
	}
	
	public NamespacesInfos cloneInfos() {
		Set clonedList = new TreeSet();
		for (Iterator it = nameSpacesList.iterator(); it.hasNext();) {
			NamespaceInfo ns = (NamespaceInfo) it.next();
			clonedList.add(ns);
		}
		return new NamespacesInfos(clonedList);
	}

	/**
	 * @Override
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator it = this.nameSpacesList.iterator(); it.hasNext();) {
			NamespaceInfo info = (NamespaceInfo) it.next();
			sb.append(info.toString() + "\n");
		}
		return sb.toString();
	}
	
	public Iterator namespacesIterator() {
		return this.nameSpacesList.iterator();
	}
	
	public int size() {
		if (this.nameSpacesList == null) return 0;	// should never happen
		return this.nameSpacesList.size();
	}
	
	public NamespaceInfo getNamespaceInfoByUri(String uri) {
		if (uri==null) return null;
		for (Iterator it = namespacesIterator(); it.hasNext();) {
			NamespaceInfo ns = (NamespaceInfo) it.next();
			if (ns.getUri().equals(uri)) return ns;
		}
		return null;
	}
	
	public NamespaceInfo getNamespaceInfoByPrefix(String prefix) {
		String prfx = prefix == null ? "" : prefix;
		for (Iterator it = namespacesIterator(); it.hasNext();) {
			NamespaceInfo ns = (NamespaceInfo) it.next();
			
			if (ns.getPrefix().equals(prfx)) return ns;
		}
		return null;
	}
	
	public NamespaceInfo getNamespaceInfoByLocation(String location) {
		if (location==null) return null;
		for (Iterator it = namespacesIterator(); it.hasNext();) {
			NamespaceInfo ns = (NamespaceInfo) it.next();
			if (ns.getLocation().equals(location)) return ns;
		}
		return null;
	}
	
}
