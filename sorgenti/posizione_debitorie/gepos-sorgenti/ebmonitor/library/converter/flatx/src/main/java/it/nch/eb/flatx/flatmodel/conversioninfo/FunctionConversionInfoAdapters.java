/**
 * Created on 30/mag/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import java.io.Serializable;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;


/**
 * @author gdefacci
 */
public interface FunctionConversionInfoAdapters {
	
	public class FromString implements XPathsMapBindinsManagerStringFunction,
		XPathMapFunction, Serializable {

		private static final long	serialVersionUID	= -6144525685765267618L;
		final String	value;

		public FromString(String value) {
			this.value = value;
		}

		public String getValue(XPathPosition pos, XPathsMapBindings elem, IBindingManager bindingManager) {
			return value;
		}

		public Object getValue(XPathPosition pos, IXPathMapScope elem) {
			return value;
		}
	}
	
	public class FromBindingManagerValue implements XPathsMapBindinsManagerStringFunction, Serializable {
		private static final long	serialVersionUID	= 3269745621149012501L;
		final String key;
		final boolean optional;

		public FromBindingManagerValue(String key) {
			this(key, false);
		}
		
		public FromBindingManagerValue(String key, boolean optional) {
			this.key = key;
			this.optional = optional;
		}

		public String getValue(XPathPosition pos, XPathsMapBindings elem, IBindingManager bindingManager) {
			String res = StringUtils.getStringValue(bindingManager, key);
			if (res==null && !optional) {
//				System.out.println("cant find key " + key + " on binding manager");
//				throw new IllegalStateException("cant find key " + key + " on binding manager");
			}
			return  res;
		}
		
	}
	
	public class FromXPathMapFunction implements XPathsMapBindinsManagerStringFunction,
		XPathMapFunction, Serializable {
	
		private static final long	serialVersionUID	= -1717900191231873291L;
		private final XPathMapFunction delegate;
	
		public FromXPathMapFunction(XPathMapFunction delegate) {
			this.delegate = delegate;
		}
	
		public String getValue(XPathPosition pos, XPathsMapBindings elem, IBindingManager bindingManager) {
			return (String) getValue(pos, elem);
		}
	
		public Object getValue(XPathPosition pos, IXPathMapScope elem) {
			return delegate.getValue(pos, elem);
		}
	}
		
}