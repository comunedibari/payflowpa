/**
 * Created on 11/ott/07
 */
package it.nch.fwk.checks.xom;

import java.io.InputStream;
import java.io.Reader;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;


/**
 * @author gdefacci
 */
public class XomUtils {
	
	public static Document parse(InputStream is) {
		try {
			Builder builder = new Builder();
			return builder.build(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static Document parse(Reader rdr) {
		try {
			Builder builder = new Builder();
			return builder.build(rdr);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static Element rootElement(InputStream is) {
		Document doc = parse(is);
		return doc.getRootElement();
	}
	
	public static Element rootElement(Reader rdr) {
		Document doc = parse(rdr);
		return doc.getRootElement();
	}


}
