/**
 * 
 */
package it.nch.eb.flatx.flatmodel.sax;

import it.nch.eb.flatx.flatmodel.xpath.AttributeXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.fwk.core.NamespacesInfos;

import org.xml.sax.Attributes;

/**
 * @author Admin
 *
 */
public class FirstElemSaxElementHandler extends SaxElementHandler {
	
	private boolean first = true;
	
	public FirstElemSaxElementHandler(ModelLoader dh, IXPathBuilder xpp) {
		super(dh, xpp);
	}

	public FirstElemSaxElementHandler(ModelLoader delegateElementHandler,
			NamespacesInfos nss, IXPathBuilder xpp) {
		super(delegateElementHandler, nss, xpp);
	}

	public FirstElemSaxElementHandler(ModelLoader dh, NamespacesInfos nss) {
		super(dh, nss);
	}

	public FirstElemSaxElementHandler(ModelLoader dh) {
		super(dh);
	}

	protected void doStartElement(XPathPosition elemPos, Attributes attrs) {
		if (first) {
			int len = attrs.getLength();
			for (int i=0; i<len; i++) {
				String text = attrs.getValue(i);
				AttributeXPathPosition pos = attributeXPathPosition(elemPos, attrs, i);
				getModelLoaderInternal().getXpathsMap().put(pos, text);
			}
			first = false;
		}
		super.doStartElement(elemPos, attrs);
	}
	
	protected ModelLoader getModelLoaderInternal() {
		return (ModelLoader) getModelLoader();
	}

}
