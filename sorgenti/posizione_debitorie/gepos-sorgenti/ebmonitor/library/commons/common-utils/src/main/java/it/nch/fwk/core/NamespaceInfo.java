/**
 * Created on 03/ago/07
 */
package it.nch.fwk.core;

import java.io.Serializable;


public class NamespaceInfo implements Comparable, Serializable {
	private static final long	serialVersionUID	= 8487436713100493794L;
	
	public final String prefix;
	public final String uri;
//	logical name resoved by a res
	public final String location;
	public NamespaceInfo(final String prefix, final String url, final String location) {
		this.prefix = prefix;
		this.uri = url;
		this.location = location;
	}
	
	public NamespaceInfo(final String prefix, final String url) {
		this(prefix, url, null);
	}
	/**
	 * @Override
	 */
	public String toString() {
		return "prefix[" + prefix + "]uri[" + uri + "]location[" + location +"]";
	}
	public String getLocation() {
		return location;
	}
	public String getPrefix() {
		return prefix;
	}
	public String getUri() {
		return uri;
	}
	
	/*
	 * @Override
	 */
	public int hashCode() {
		return ( this.uri == null ? 		"" : this.uri
				+ this.prefix == null ? 	"" : this.prefix
				+ this.location == null ? 	"" : this.location ).hashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj==null) return false;
		else return compareTo(obj)==0;
	}

	public int compareTo(Object o) {
		if (!(o instanceof NamespaceInfo)) {
			throw new IllegalArgumentException("cant compare " + NamespaceInfo.class.getName() + " instances with a " + o.getClass().getName() + " instance");
		}
//		if (o == null) return +1;
		NamespaceInfo target = (NamespaceInfo) o;
		int outcome = stringCompare(this.uri, target.getUri());
		if (outcome==0) {
			outcome = stringCompare(this.prefix, target.getPrefix());
			if (outcome==0) {
				outcome = stringCompare(this.location, target.getLocation());
			}
		}
		return outcome;
	}
	
	int stringCompare(String s1, String s2) {
		if (s1==null && s2==null) return 0;
		if (s1==null) return -1;
		if (s2==null) return +1;
		return s1.compareTo(s2);
	}
	
}