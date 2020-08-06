/**
 * Created on 03/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class PositionXPathMapEffect {
	
	private final XPathPosition position;
	private final XPathFunctionAndEffectsExecutor xpathMapEffect;
	
	public PositionXPathMapEffect(XPathPosition position, XPathFunctionAndEffectsExecutor xpathMapEffect) {
		this.position = position;
		this.xpathMapEffect = xpathMapEffect;
	}
	
	public XPathPosition getPosition() {
		return position;
	}
	
	public XPathFunctionAndEffectsExecutor getXPathMapEffect() {
		return xpathMapEffect;
	}

}
