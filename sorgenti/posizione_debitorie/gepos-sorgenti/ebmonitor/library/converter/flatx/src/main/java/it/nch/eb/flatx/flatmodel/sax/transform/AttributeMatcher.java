/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public interface AttributeMatcher {

	boolean match(Attributes attrs, int i);
}
