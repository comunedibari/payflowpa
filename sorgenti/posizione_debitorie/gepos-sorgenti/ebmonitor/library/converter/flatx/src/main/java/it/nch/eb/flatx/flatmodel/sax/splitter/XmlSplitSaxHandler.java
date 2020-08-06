/**
 * Created on 29/gen/09
 */
package it.nch.eb.flatx.flatmodel.sax.splitter;

import it.nch.eb.flatx.flatmodel.sax.PrefixProviderSaxHandler;
import it.nch.eb.flatx.flatmodel.xpath.AttributeXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.ChildAwareXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.IXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.fwk.core.NamespacesInfos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author gdefacci
 */
public class XmlSplitSaxHandler extends PrefixProviderSaxHandler {

	private final IXPathBuilder xpathbBuilder = new ChildAwareXPathBuilder();

	private final Map/* <XPathPosition, TextCollectorEffectFactory> */effectsPool;
	private List/* <TextCollectorEffect> */activeEffects = new ArrayList();
	
	private Map/*<AttributeXPathPosition, FragmentHandler>*/ attributeEffects;

	public XmlSplitSaxHandler(final Map effectsPool) {
		this(null, effectsPool, new HashMap());
	}

	public XmlSplitSaxHandler(NamespacesInfos nss, final Map effectsPool) {
		this(nss, effectsPool, new HashMap());
	}
	
	public XmlSplitSaxHandler(NamespacesInfos nss, 
			final Map effectsPool,
			Map/*<AttributeXPathPosition, FragmentHandler>*/ attributeEffects ) {
		super(nss);
		this.effectsPool = effectsPool;
		this.attributeEffects = attributeEffects;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		String prfx = getPrefix(uri);
		xpathbBuilder.pathSegment(prfx, localName);

		XPathPosition pos = (XPathPosition) xpathbBuilder.getPosition().getUnindexed();
		TextCollectorEffectFactory efct = (TextCollectorEffectFactory) effectsPool
				.get(pos);
		if (efct != null) {
			activeEffects.add(efct.create(pos));
		}
		for (int i=0; i< attributes.getLength(); i++) {
			String attQname = attributes.getQName(i);
			String attPrfx = XPathUtils.sharedInstance.prefixPart(attQname);
			String attName = XPathUtils.sharedInstance.namePart(attQname);
			AttributeXPathPosition attpos = pos.attribute(attPrfx, attName);
			FragmentHandler attHandler = (FragmentHandler) attributeEffects.get(attpos);
			if (attHandler!=null) {
				attHandler.onFragment(attributes.getValue(i));
			}
		}
		
		for (Iterator it = activeEffects.iterator(); it.hasNext();) {
			TextCollectorEffect textEfct = (TextCollectorEffect) it.next();
			textEfct.start(prfx, localName, attributes);
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		for (Iterator it = activeEffects.iterator(); it.hasNext();) {
			TextCollectorEffect textEfct = (TextCollectorEffect) it.next();
			textEfct.characters(ch, start, length);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String prfx = getPrefix(uri);
		for (Iterator it = activeEffects.iterator(); it.hasNext();) {
			TextCollectorEffect textEfct = (TextCollectorEffect) it.next();
			textEfct.endElement(prfx, localName);
		}
		List activeEffectsCopy = new ArrayList();
		if (activeEffects.size() > 0) {
			XPathPosition pos = (XPathPosition) xpathbBuilder.getPosition().getUnindexed();
			for (Iterator it = activeEffects.iterator(); it.hasNext();) {
				TextCollectorEffect actEftc = (TextCollectorEffect) it.next();
				if (!actEftc.getPosition().equals(pos)) {
					activeEffectsCopy.add(actEftc);
				} else {
					actEftc.executeCallbacks();
				}
			}
		}
		this.activeEffects = activeEffectsCopy;

		xpathbBuilder.pathSegmentEnd(prfx, localName);
	}

}
