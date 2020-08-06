/**
 * Created on 29/gen/09
 */
package it.nch.eb.flatx.flatmodel.sax.splitter;

import java.util.HashMap;
import java.util.Map;

import it.nch.eb.flatx.flatmodel.xpath.AttributeXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.fwk.core.NamespacesInfos;


/**
 * @author gdefacci
 */
public class XmlSplitSaxHandlerBuilder {
	
	private final Map/*<XPathPosition, TextCollectorEffectFactory>*/ effects = new HashMap();
	private Map/*<AttributeXPathPosition, FragmentHandler>*/ attributeEffects = new HashMap();
	private NamespacesInfos nss = null;
	private final XPathsParser xpathParser = XPathsParser.instance;
	
	public XmlSplitSaxHandlerBuilder add(String pos, final FragmentHandler[] handlers) {
		BaseXPathPosition xppos = parsePos(pos);
		if (xppos.getLastSegment().isAttribute()) {
			return add((AttributeXPathPosition) xppos, handlers );
		} else {
			return add((XPathPosition) xppos, new TextCollectorEffectFactory() {
	
				public TextCollectorEffect create(XPathPosition pos) {
					return new TextCollectorEffect(pos, handlers);
				}
				
			});	
		}
	}
	
	public XmlSplitSaxHandlerBuilder add(String pos, final FragmentHandler handler) {
		BaseXPathPosition xppos = parsePos(pos);
		if (xppos.getLastSegment().isAttribute()) {
			return add((AttributeXPathPosition) xppos, new FragmentHandler[] { handler });
		} else {
			return add((XPathPosition) xppos, new TextCollectorEffectFactory() {
	
				public TextCollectorEffect create(XPathPosition pos) {
					return new TextCollectorEffect(pos, new FragmentHandler[] { handler });
				}
				
			});
		}
	}
	
	private BaseXPathPosition parsePos(String xpath) {
		return XPathsParser.instance.parseXPath(xpath);
	}
	
	public XmlSplitSaxHandlerBuilder add(String pos, TextCollectorEffectFactory factory) {
		return add(xpathParser.parseXPathPosition(pos), factory);
	}
	public XmlSplitSaxHandlerBuilder add(XPathPosition pos, TextCollectorEffectFactory factory) {
		effects.put(pos, factory);
		return this;
	}
	
	public XmlSplitSaxHandlerBuilder add(AttributeXPathPosition pos, FragmentHandler[] attHandler) {
		for (int i = 0; i < attHandler.length; i++) {
			FragmentHandler fragmentHandler = attHandler[i];
			this.attributeEffects.put(pos, fragmentHandler);
		}
		return this;
	}
	
	public XmlSplitSaxHandlerBuilder usingNamespaces(NamespacesInfos nmsps) {
		this.nss = nmsps;
		return this;
	}
	
	public XmlSplitSaxHandler create() {
		return new XmlSplitSaxHandler(nss, effects, attributeEffects);
	}

}
