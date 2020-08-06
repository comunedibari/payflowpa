/**
 * Created on 08/ott/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class PositionCallbackPair {
	private final XPathPosition position;
	private final XPathsMapBindinsManagerEffect callback;
	public PositionCallbackPair(XPathPosition pos, XPathsMapBindinsManagerEffect callback) {
		super();
		this.position = pos;
		this.callback = callback;
	}
	public XPathPosition getPosition() {
		return position;
	}
	public XPathsMapBindinsManagerEffect getCallback() {
		return callback;
	}
	
	public String toString() {
		return "pos: " +  position + " effect: " +  this.callback;
	}
	
}