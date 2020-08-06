/**
 * 
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

/**
 * @author Admin
 *
 */
public class ElementPrinterEffectsContainer implements ElementPrinterEffect {
	
	private static final ElementPrinterEffect DEFAULT_EFFECT = new DefaultElementPrinterEffect();

	private static String indentString = "  ";

	private final Map/*<String, ElementPrinterEffect>*/ effectsMap = new HashMap();
	private final Stack/*<ElementPrinterEffect>*/ effectsStack = new Stack();
	private final ElementPrinterEffect defaultEffect ;
	
	public ElementPrinterEffectsContainer() {
		this(DEFAULT_EFFECT);
	}
	public ElementPrinterEffectsContainer(ElementPrinterEffect defaultEffect) {
		this.defaultEffect = defaultEffect !=  null ? defaultEffect : DEFAULT_EFFECT;
	}
	
	public static String indentation(int n) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<n; i++) {
			sb.append(indentString);
		}
		return sb.toString();
	}
	
	public static String attributesStringValue(Attributes attributes) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < attributes.getLength(); i++) {
			sb.append(" ");
			sb.append(attributes.getQName(i));
			sb.append("=");
			String value = attributes.getValue(i);
			appendAttributeValue(sb, value);
		}
		return sb.toString();
	}
	
	public static void appendAttributeValue(StringBuffer sb, String value) {
		String qte = value.indexOf('"') < 0 ? "\"" : "'";
		sb.append(qte);
		sb.append(value);
		sb.append(qte);
	}

	public void add(XPathPosition pos, ElementPrinterEffect efct) {
		add(pos.toString(), efct);
	}

	public void add(String posStr, ElementPrinterEffect efct) {
		this.effectsMap.put(posStr, efct);
	}
	
	public StartElementResult start(XPathPosition pos, Attributes attrs) {
		ElementPrinterEffect efct = (ElementPrinterEffect) effectsMap.get(pos.getUnindexed().toString());
		effectsStack.push(efct);
		if (efct!=null) {
			return efct.start(pos, attrs);
		} else {
			return defaultEffect.start(pos, attrs);
		}
	}

	public String end(XPathPosition pos, String content) {
		ElementPrinterEffect efct = (ElementPrinterEffect) effectsStack.pop();
		if (efct!=null) {
			return efct.end(pos, content);
		} else {
			return defaultEffect.end(pos, content);
		}
	}

}
