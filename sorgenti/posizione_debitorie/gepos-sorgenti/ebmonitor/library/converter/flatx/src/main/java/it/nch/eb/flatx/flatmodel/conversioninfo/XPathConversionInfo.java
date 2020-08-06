/**
 * Created on 07/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import java.io.Serializable;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.MissingXPathHandler;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;


/**
 * @author gdefacci
 */
public class XPathConversionInfo extends ExpressionConversionInfo { 
	
	private static final long	serialVersionUID	= -709716337019538123L;
	public static MissingXPathHandler fakeMissingXPathHandler = new SerializableMissingXPathHandler() {

		private static final long	serialVersionUID	= 3574890703785979578L;

		public String missingXPath(BaseXPathPosition pos, IXPathMapScope elem) {
			return null;
		}
		
	};

	public static class DfltMissingXPathHandler implements SerializableMissingXPathHandler {
		
		private static final long	serialVersionUID	= 1503411431782796561L;
		private String	name;
		private BaseXPathPosition	pos;
		
		public DfltMissingXPathHandler(String name, BaseXPathPosition pos) {
			this.name = name;
			this.pos = pos;
		}

		public String getName() {
			return name;
		}
		
		public BaseXPathPosition getPosition() {
			return pos;
		}

		public String missingXPath(BaseXPathPosition path, IXPathMapScope elem) {
			throw new RuntimeException("pos)" + getPosition() + " element)" + getName() + "\ncant find xpath " + path + " on elements map " + elem);
		}
	}

	private final BaseXPathPosition				xPath;
	private final boolean						optional;
	private final MissingXPathHandler 			missingXPathHandler; 
				
	public XPathConversionInfo(int pos, SizedConverter delegate, BaseXPathPosition xpath, boolean optional) {
		this(pos, delegate, null, xpath, optional);
	}
	
	protected XPathConversionInfo(int pos, SizedConverter delegate, BaseXPathPosition xpath) {
		this(pos, delegate, null, xpath, false);
	}
	
	protected XPathConversionInfo(int pos, final SizedConverter delegate, String name, final BaseXPathPosition xpath, 
			final boolean optional) {
		this(pos, delegate, name, xpath, optional, new DfltMissingXPathHandler(name, xpath) );
	}
	
	protected XPathConversionInfo(int pos, final SizedConverter delegate, String name, final BaseXPathPosition xpath, 
			final boolean optional, MissingXPathHandler mh) {
		super(pos, delegate, name);
		this.xPath = xpath;
		this.optional = optional;
		this.missingXPathHandler = mh;
	}
	
	public BaseXPathPosition getXPath() {
		return xPath;
	}

	public boolean isOptional() {
		return optional;
	}
	
	public String getValue(XPathPosition pos, XPathsMapBindings/*<String,Object>*/ elem, IBindingManager bindingManager) {
		return getConverter().encode(getStringValue(pos, elem));
	}

	protected String getStringValue(XPathPosition basePos, XPathsMapBindings elem) {
		String res = null;
		BaseXPathPosition rpos = basePos.concat(this.xPath);
		try {
			res = elem.get(rpos);
		} catch (Exception e) {
			throw new RuntimeException("error retrieving xpath " +  this.xPath + " from elem " + elem , e);
		}
		if (res==null && !optional) {
			return missingXPathHandler.missingXPath(rpos, elem);
		}
		else return res;
	}

	public String toString() {
		return super.toString() + " xpath[" + xPath + "]" + (isOptional() ? "optional" : "");
	}
	
}

interface SerializableMissingXPathHandler extends Serializable, MissingXPathHandler {
	
}