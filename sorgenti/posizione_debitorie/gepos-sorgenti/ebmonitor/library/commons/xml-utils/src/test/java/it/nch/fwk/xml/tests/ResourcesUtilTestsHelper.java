/**
 * Created on 05/giu/07
 */
package it.nch.fwk.xml.tests;

import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.xom.ElementCursor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * this copy is a subset of it.nch.eb.flatx.common.ResourcesUtil (project flax)
 * to levereage tests resources retrival
 * @author gdefacci
 */
public class ResourcesUtilTestsHelper {
	
	public static IElementCursor parse(String classpathResourceName) throws IOException, ParsingException, ValidityException {
		InputStream xml = ResourcesUtilTestsHelper.getResourceAsInputStream(classpathResourceName);
		Builder builder = new Builder();
		Document doc = builder.build(xml);
		Element xomElement = doc.getRootElement();
		IElementCursor elem = new ElementCursor(xomElement);
		return elem;
	}

	public static IElementCursor parseContent(String xmlContent) throws IOException, ParsingException, ValidityException {
		InputStream xml = new ByteArrayInputStream(xmlContent.getBytes());
		Builder builder = new Builder();
		Document doc = builder.build(xml);
		Element xomElement = doc.getRootElement();
		IElementCursor elem = new ElementCursor(xomElement);
		return elem;
	}

	public static InputStream getResourceAsInputStream(String name) throws IOException {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		if (is==null) throw new IOException("Cant find the resource named [" + name + "] in the current cclasspath");
		return is;
	}

}
