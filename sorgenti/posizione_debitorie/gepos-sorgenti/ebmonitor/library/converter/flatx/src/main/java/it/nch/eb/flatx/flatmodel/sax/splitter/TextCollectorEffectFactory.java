/**
 * Created on 29/gen/09
 */
package it.nch.eb.flatx.flatmodel.sax.splitter;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

public interface TextCollectorEffectFactory {
	TextCollectorEffect create(XPathPosition pos);
}