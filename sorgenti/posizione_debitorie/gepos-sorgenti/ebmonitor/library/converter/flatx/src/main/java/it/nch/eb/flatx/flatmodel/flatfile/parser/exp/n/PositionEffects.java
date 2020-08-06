/**
 * Created on 03/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp.n;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class PositionEffects {
	
	private final XPathPosition position;
	private final XPathFunctionAndEffects functionAndEffects;
	public PositionEffects(XPathPosition position, XPathFunctionAndEffects functionAndEffects) {
		this.position = position;
		this.functionAndEffects = functionAndEffects;
	}
	
	public XPathPosition getPosition() {
		return position;
	}
	
	public XPathFunctionAndEffects getFunctionAndEffects() {
		return functionAndEffects;
	}
	
}
