/**
 * Created on 22/ago/08
 */
package it.nch.eb.flatx.flatmodel.xpath;


/**
 * @author gdefacci
 */
public interface IXPathBuilder {

	public abstract IXPathBuilder pathSegment(String qualifiedName);
	public abstract IXPathBuilder pathSegment(String prefix, String name);

	public abstract IXPathBuilder pathSegmentEnd(String qualifiedName);
	public abstract IXPathBuilder pathSegmentEnd(String prfx, String localName);

	public abstract String getXPath();

	public abstract XPathPosition getPosition();

}